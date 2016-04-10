import unittest
import os,sys,inspect
from flask import current_app
from flask.ext.login import current_user
import json

# Get the current directory path
currentdir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))

#Get the parent directory path
parentdir = os.path.dirname(currentdir)

#Add parent directory first in the system path
sys.path.insert(0,parentdir)

import mimprove

# Initialize the application
app = mimprove.create_app()
        
class mimproveGetInfoTestCase(unittest.TestCase):
    
    def setUp(self):
        app.testing = True
        
        with app.app_context():
            self.app = app.test_client()
        
    def tearDown(self):
        pass
    
    def get_info(self):
        return self.app.get('/quiz/get_info')
    
    def test_home_status_code(self):
        """Testing service availability"""
        # sends HTTP GET request to the application
        # on the specified path
        result = self.app.get('/')

        # assert the status code of the response
        self.assertEqual(result.status_code, 200, "Failed: The http status is not 200")

    def test_quiz_response_status(self):
        """Testing the fetching of info questions for a particular user, response check"""
        with app.app_context():
            
            #fetch the questions
            rv = self.get_info()

            #check if a valid response
            self.assertEqual(rv.status_code, 200, "Failed: The http status is not 200")

    def test_response_json(self):
        """Testing the fetching of info questions for a particular user, structure"""
        with app.app_context():
            
            #fetch the questions
            rv = self.get_info()
            
            with open(os.path.join('back-end/tests/files', 'get_info' + '.json'),'r') as rf:
                expected_resp = json.load(rf)
                
            #load the response data
            resp = json.loads(rv.data)

            #check the number of questions
            self.assertEqual(resp,expected_resp, "Failed: The response is not expected.")

    def test_quiz_verify_list(self):
        """Testing the fetching of info questions for a particular user, list verification"""
        with app.app_context():
            
            #fetch the questions
            rv = self.get_info()

            #load the response data
            resp = json.loads(rv.data)

            resp_info = resp['info']

            #check if the quiz is a list
            self.assertTrue(isinstance(resp_info,list),"Failed: The info questions are not a list")


    def test_quiz_verify_questions_keys(self):
        """Testing the fetching of infp questions for a particular user, verify keys"""
        with app.app_context():
            
            #fetch the questions
            rv = self.get_info()

            #load the response data
            resp = json.loads(rv.data)

            resp_info = resp['info']

            #check for keys
            for question in resp_info:
                assert 'text' in question
                assert 'category' in question

class mimproveSaveInfoTestCase(unittest.TestCase):
     
    def setUp(self):
        app.testing = True
        
        with app.app_context():
            self.app = app.test_client()
            
    def tearDown(self):
        pass
    
    def save_info(self, jsonfile):
        return self.app.post('/quiz/save_info', data=json.dumps(jsonfile), content_type = 'application/json')
    
    def test_home_status_code(self):
        """Testing service availability"""
        # sends HTTP GET request to the application
        # on the specified path
        result = self.app.get('/')

        # assert the status code of the response
        self.assertEqual(result.status_code, 200, "Failed: The http status is not 200")

    def test_quiz_response_status(self):
        """Testing the saving of information entered by the user, response check"""
        with app.app_context():
            with open(os.path.join('tests/files', 'save_info' + '.json'),'r') as rf:
                req = json.load(rf)
                
            #call save quiz
            rv = self.save_info(req)

            #check if a valid response
            self.assertEqual(rv.status_code, 200, "Failed: The http status is not 200")

    def test_quiz_response(self):
        """Testing the saving of information entered by the user, response check"""
        with app.app_context():
            with open(os.path.join('tests/files', 'save_info' + '.json'),'r') as rf:
                req = json.load(rf)
            
            #call save quiz
            rv = self.save_info(req)

            resp = json.loads(rv.data)
            
            #check if a valid response
            self.assertEqual(resp['username'], 'test_patient', "Failed: Usernames do not match")

            self.assertEqual(resp['result'], 'save_successful', "Failed: Response result does not match")

    def test_quiz_length(self):
        """Testing the saving of information for a particular user, length check"""
        with app.app_context():
            with open(os.path.join('tests/files', 'save_info' + '.json'),'r') as rf:
                req = json.load(rf)
            self.assertTrue(len(req['info']) == 2,"Failed: Length of the expected request is not 2")

    def test_null_user(self):
        """Testing the saving of information for a blank username"""
        with app.app_context():
            with open(os.path.join('tests/files', 'save_info_null_user' + '.json'),'r') as rf:
                req_null = json.load(rf)
                #call save info
                rv = self.save_info(req_null)

                #check if client input error
                self.assertTrue(rv.status_code == 400,"Failed: Incorrect status for blank username, expected 400")

    def test_invalid_user(self):
        """Testing the fetching of quiz questions for an invalid user"""
        with app.app_context():
            with open(os.path.join('tests/files', 'save_info_invalid_user' + '.json'),'r') as rf:
                req = json.load(rf)
                #call save quiz
                rv = self.save_info(req)
                #check if a valid response
                self.assertTrue(rv.status_code == 204,"Failed: Incorrect status for invalid username, expected 204")
             
if __name__ == '__main__':
    unittest.main()

