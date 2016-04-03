import unittest
import tempfile
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
        
class mimproveTestCase(unittest.TestCase):
    
    def setUp(self):
        app.testing = True
        
        with app.app_context():
            self.app = app.test_client()
        
    def tearDown(self):
        pass
    
    def get_quiz(self, username):
        return self.app.post('/quiz/get_quiz', data=dict(username=username))
        #return self.app.get('/quiz/get_quiz?username=test_patient')
    
    def test_home_status_code(self):
        # sends HTTP GET request to the application
        # on the specified path
        result = self.app.get('/')

        # assert the status code of the response
        self.assertEqual(result.status_code, 200)

    def test_quiz(self):
        """Testing the fetching of quiz questions for a particular user"""
        with app.app_context():
            
            username = 'test_patient'
            #fetch the quiz
            rv = self.get_quiz(username)

            #check if a valid response
            assert rv.status_code == 200

            #load the response data
            resp = json.loads(rv.data)

            resp_quiz = resp['quiz']
            #check the number of questions
            assert len(resp_quiz) == 10

            #check if the quiz is a list
            assert isinstance(resp_quiz,list)

            #check for keys
            for question in resp_quiz:
                assert 'text' in question
                assert 'correct_answer' in question
                assert 'incorrect_answer1' in question
                assert 'incorrect_answer2' in question
                assert 'incorrect_answer3' in question
             
if __name__ == '__main__':
    unittest.main()

