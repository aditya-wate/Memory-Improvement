import flask
import json, os
from flask import request, abort, jsonify, current_app
import MySQLdb as mdb
from mimprove.views import *
from datetime import datetime

quiz_view = flask.Blueprint('quiz', __name__)

@quiz_view.route('/get_quiz', methods=['POST'])
def get_quiz():
    """
    Return a quiz for the user in the request

    URL: /quiz/get_quiz
    POST parameters:

    - username: the user for which the questions are to be fetched

    The response is a json dictionary which contains the questions with the possible and the correct answers.

    Errors:

    This method is allowed to all users who have been logged in
    Otherwise, return an HTTP 204 error.


    """
    con = mdb.connect(host=MYSQL_HOST, port=MYSQL_PORT,user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
    with con:    
        username = request.form['username']
        if username == '':
            abort(400)
        cur = con.cursor()
        resp = dict()
        quiz = list()
        stmt = "SELECT * FROM question q\
                WHERE q.patient_id = (SELECT p.patient_id FROM patient p, user u\
                WHERE p.user_id = u.user_id and u.username = %s) ORDER BY RAND() LIMIT 10"
        """
        stmt = "SELECT * FROM question\
                WHERE patient_id = (SELECT patient_id FROM patient\
                                    WHERE user_id = (SELECT user_id FROM user\
                                                     WHERE username = %s)) ORDER BY RAND() LIMIT 10;"""
        row_count = cur.execute(stmt,[username])
        if row_count > 0:
            question_rows = cur.fetchall()
            resp['username'] = username
            for row in question_rows:
                question = dict()
                question['question_id'] = row[0]
                question['text'] = row[2]
                question['correct_answer'] = row[3]
                category = row[1]
                get_options = "SELECT c_name\
                                FROM %s\
                                ORDER BY RAND() LIMIT 3;"%(category)
                cur.execute(get_options)
                categories = cur.fetchall()
                question['incorrect_answer1'] = categories[0][0]
                question['incorrect_answer2'] = categories[1][0]
                question['incorrect_answer3'] = categories[2][0]
                quiz.append(question)
            resp['quiz'] = quiz
            return jsonify(resp)
        else:
            return ('User not found', 204)

@quiz_view.route('/save_quiz', methods=['POST'])
def save_quiz():
    """
    This service takes the json from the request and saves the questions in the database
    for the user to view the history.
    
    Return success if request is executed successfully

    URL: /quiz/save_quiz
    POST parameters:

    - json: the quiz and the score for the patient

    The response returns the success of the insert

    Errors:

    This method is allowed to all users who have been logged in
    Otherwise, return an HTTP 204 error.

    """

    con = mdb.connect(host=MYSQL_HOST, port=MYSQL_PORT,user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
    with con:
        content = request.json
        username = content['username']
        if username == "":
            abort(400)

        cur = con.cursor()
        
        stmt_patient = "SELECT p.patient_id FROM patient p, user u\
                WHERE p.user_id = u.user_id and u.username = %s"

        row_count = cur.execute(stmt_patient,[username])
        
        if row_count > 0:
            resp = dict()
            resp['username'] = username
            
            #getting parameters required for insert in quiz table
            #get patient_id
            patient_id = cur.fetchone()[0]
            
            #get score from the request
            score = content['score']
            
            #get current date-time
            now = datetime.now()
            start_date = now.strftime('%Y-%m-%d %H:%M:%S')
            
            #get quiz
            quiz=str(content['quiz'])
            
            cur = con.cursor()

            stmt_insert_quiz = "INSERT INTO quiz (score,patient_id,start_date,state) VALUES (%s,%s,%s,%s)"

            row_count = cur.execute(stmt_insert_quiz,(score,patient_id,start_date,quiz))

            if row_count == 1:
                resp['result'] = 'save_successful'
                return jsonify(resp)
            else:
                resp['result'] = 'save_failure'
                return jsonify(resp)
        else:
            return ('User not found', 204)

@quiz_view.route('/get_info', methods=['GET'])
def get_info():
    """
    This service gets the information to be gathered from the patient and sends it to frontend.
    
    Return the list of questions to ask the patient

    URL: /quiz/get_info
    GET parameters:

    none

    The response is a json dictionary which contains the questions to be asked to users.

    Errors:

    This method returns data from db.
    Otherwise, return an HTTP 204 error.

    """
    con = mdb.connect(host=MYSQL_HOST, port=MYSQL_PORT,user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
    with con:
        content = request.json
        
        cur = con.cursor()
        
        personal_info = "SELECT category, question_string FROM personal_info"
        
        try:
            row_count = cur.execute(personal_info)
        except MySQLdb.Error, e:
            try:
                print "MySQL Error [%d]: %s" % (e.args[0], e.args[1])
            except IndexError:
                print "MySQL Error: %s" % str(e)
            
        if row_count > 0:
            resp = dict()
            questions = list()
            
            question_rows = cur.fetchall()

            for row in question_rows:
                question = dict()
                question['category'] = row[0]
                question['text'] = row[1]
                questions.append(question)

            resp['info']=questions
            return jsonify(resp)
        
        else:
            return ('Content not found', 204)

@quiz_view.route('/save_info', methods=['POST'])
def save_info():
    """
    This service pushes the information gathered from the patient to the database.
    
    Returns the success/failure of the operation

    URL: /quiz/save_info
    POST parameters: username

    Errors:

    This method is allowed to all users who have been logged in
    Otherwise, return an HTTP 204 error.

    """

    con = mdb.connect(host=MYSQL_HOST, port=MYSQL_PORT, user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
    with con:
        content = request.json
        username = content['username']
        if username == "":
            abort(400)

        cur = con.cursor()
        
        stmt_patient = "SELECT p.patient_id FROM patient p, user u\
                WHERE p.user_id = u.user_id and u.username = %s"

        try:
            row_count = cur.execute(stmt_patient,[username])
        except MySQLdb.Error, e:
            try:
                print "MySQL Error [%d]: %s" % (e.args[0], e.args[1])
            except IndexError:
                print "MySQL Error: %s" % str(e)
        
        if row_count > 0:
            resp = dict()
            resp['username'] = username
            
            #getting parameters required for insert in question table
            #get patient_id
            patient_id = cur.fetchone()[0]
            
            #get quiz
            info=content['info']

            # initialize the list of tuple for insertion
            tupled_info = list()

            for question in info:
                tupled_info.append((question['category'],question['text'],question['user_answer'],patient_id))
            
            cur = con.cursor()

            stmt_insert_quiz = "INSERT INTO question (category,question_string,answer,patient_id) VALUES (%s,%s,%s,%s)"

            row_count = cur.executemany(stmt_insert_quiz,tupled_info)

            if row_count > 0:
                resp['result'] = 'save_successful'
                return jsonify(resp)
            else:
                resp['result'] = 'save_failure'
                return jsonify(resp)
        else:
            return ('User not found', 204)
