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
from mimprove.models import db
from mimprove.models.db_models import User

# Initialize the application
app = mimprove.create_app()
        
class mimproveTestCase(unittest.TestCase):
    
    def setUp(self):
        
        app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:wstwbh57@localhost:3306/memory_improve'
        #app.config['SECRET_KEY'] = 'session_key'
        app.testing = True

        with app.app_context():
            db.init_app(current_app)
            self.db = db
            self.app = app.test_client()
        
    
    def tearDown(self):
        os.close(self.db)
        os.unlink(mimprove.app.config['DATABASE'])
    
    def get_quiz(self, username):
        return self.app.post('/quiz', data=dict(username=username))
    
    def test_home_status_code(self):
        # sends HTTP GET request to the application
        # on the specified path
        result = self.app.get('/')

        # assert the status code of the response
        self.assertEqual(result.status_code, 200)

    def test_quiz(self):
        """Testing the fetching of quiz questions for a particular user"""
        with app.app_context():
            user = User.query.get('3')

            #fetch the quiz
            rv = self.get_quiz(user.username)
            assert rv.status_code == 200

            #load test json file expected response
            with open(os.path.join('files', 'test_quiz' + '.json'),'r') as rf:
                exp_response = json.load(rf)
                
            assert json.loads(rv.data) == exp_response
             
if __name__ == '__main__':
    unittest.main()

