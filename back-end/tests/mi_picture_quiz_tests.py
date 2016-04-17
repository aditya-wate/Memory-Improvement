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

class mimproveGetPictureQuizTestCase(unittest.TestCase):
    
    def setUp(self):
        app.testing = True
        
        with app.app_context():
            self.app = app.test_client()
        
    def tearDown(self):
        pass
    
    def get_quiz(self, username):
        return self.app.post('/picture/get_quiz', data=dict(username=username))
    
    def test_home_status_code(self):
        """Testing service availability"""
        # sends HTTP GET request to the application
        # on the specified path
        result = self.app.get('/')

        # assert the status code of the response
        self.assertEqual(result.status_code, 200)

    def test_quiz_response_status(self):
        """Testing the fetching of picture quiz questions for a particular user, response check"""
        with app.app_context():
            
            username = 'test_patient'
            #fetch the quiz
            rv = self.get_quiz(username)

            #check if a valid response
            assert rv.status_code == 200

    def test_quiz_length(self):
        """Testing the fetching of picture quiz questions for a particular user, length check"""
        with app.app_context():
            
            username = 'test_patient2'
            #fetch the quiz
            rv = self.get_quiz(username)

            #load the response data
            resp = json.loads(rv.data)

            resp_quiz = resp['pic_quiz']
            #check the number of questions
            assert len(resp_quiz) == 3

    def test_quiz_verify_list(self):
        """Testing the fetching of picture quiz questions for a particular user, list verification"""
        with app.app_context():
            
            username = 'test_patient2'
            #fetch the quiz
            rv = self.get_quiz(username)

            #load the response data
            resp = json.loads(rv.data)

            resp_quiz = resp['pic_quiz']

            #check if the quiz is a list
            assert isinstance(resp_quiz,list)


    def test_quiz_verify_questions_keys(self):
        """Testing the fetching of picture quiz questions for a particular user, verify keys"""
        with app.app_context():
            
            username = 'test_patient2'
            #fetch the quiz
            rv = self.get_quiz(username)

            #load the response data
            resp = json.loads(rv.data)

            resp_quiz = resp['pic_quiz']

            #check for keys
            for question in resp_quiz:
                assert 'text' in question
                assert 'correct_answer' in question
                assert 'file' in question

    def test_null_user(self):
        """Testing the fetching of quiz questions for a blank username"""
        with app.app_context():
            
            username = ""
            #fetch the quiz
            rv = self.get_quiz(username)

            #check if client input error
            assert rv.status_code == 400

    def test_invalid_user(self):
        """Testing the fetching of quiz questions for an invalid user"""
        with app.app_context():
            
            username = "invalid"
            #fetch the quiz
            rv = self.get_quiz(username)
            #check if a valid response
            assert rv.status_code == 204
            
class mimproveSavePictureQuizTestCase(unittest.TestCase):
     
    def setUp(self):
        app.testing = True
        
        with app.app_context():
            self.app = app.test_client()
            
    def tearDown(self):
        pass
    
    def save_quiz(self, jsonfile):
        return self.app.post('/picture/save_quiz', data=json.dumps(jsonfile), content_type = 'application/json')
    
    def test_home_status_code(self):
        """Testing service availability"""
        # sends HTTP GET request to the application
        # on the specified path
        result = self.app.get('/')

        # assert the status code of the response
        self.assertEqual(result.status_code, 200)

    def test_quiz_response_status(self):
        """Testing the saving of quiz score for a particular user, response check"""
        with app.app_context():
            with open(os.path.join('back-end/tests/files', 'save_pic_quiz' + '.json'),'r') as rf:
                req = json.load(rf)
                
            #call save quiz
            rv = self.save_quiz(req)

            #check if a valid response
            assert rv.status_code == 200

    def test_quiz_response(self):
        """Testing the saving of quiz score for a particular user, response check"""
        with app.app_context():
            with open(os.path.join('back-end/tests/files', 'save_pic_quiz' + '.json'),'r') as rf:
                req = json.load(rf)
            
            #call save quiz
            rv = self.save_quiz(req)

            resp = json.loads(rv.data)
            
            #check if a valid response
            assert resp['username'] == 'test_patient'

            assert resp['result'] == 'save_successful'

    def test_null_user(self):
        """Testing the fetching of quiz questions for a blank username"""
        with app.app_context():
            with open(os.path.join('back-end/tests/files', 'save_pic_quiz_null_user' + '.json'),'r') as rf:
                req_null = json.load(rf)
                #call save quiz
                rv = self.save_quiz(req_null)

                #check if client input error
                assert rv.status_code == 400

    def test_invalid_user(self):
        """Testing the fetching of quiz questions for an invalid user"""
        with app.app_context():
            with open(os.path.join('back-end/tests/files', 'save_pic_quiz_invalid_user' + '.json'),'r') as rf:
                req_null = json.load(rf)
                #call save quiz
                rv = self.save_quiz(req_null)
                #check if a valid response
                assert rv.status_code == 204
             
if __name__ == '__main__':
    unittest.main()

