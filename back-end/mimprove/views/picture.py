import flask, json, os, base64, uuid
from flask import request, abort, jsonify, current_app
import MySQLdb as mdb
from mimprove.views import *
from datetime import datetime

picture_view = flask.Blueprint('picture', __name__)

@picture_view.route('/get_quiz', methods=['POST'])
def get_quiz():
    """
    Return a quiz for the user in the request

    URL: /picture/get_quiz
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
        stmt = "SELECT * FROM picture pi\
                WHERE pi.patient_id = (SELECT p.patient_id FROM patient p, user u\
                WHERE p.user_id = u.user_id and u.username = %s) ORDER BY RAND() LIMIT 10"

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

@picture_view.route('/save_quiz', methods=['POST'])
def save_quiz():
    """
    This service takes the json from the request and saves the questions in the database
    for the user to view the history.
    
    Return success if request is executed successfully

    URL: /picture/save_quiz
    POST parameters:

    - json: the questions with the base64 encoding of respective pictures

    The response returns the success of the insert

    Errors:

    This method is allowed to all users who have been logged in
    Otherwise, return an HTTP 204 error.

    """

    directory = current_app.config['IMAGE_FILE_DIRECTORY']
    con = mdb.connect(host=MYSQL_HOST, port=MYSQL_PORT,user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
    with con:
        content = request.json
        username = content['username']
        cur = con.cursor()
        
        if username == "":
            abort(400)

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

            #tuple for rows to be inserted in db
            tupled_info = list()

            #iterate through request json quiz
            for quiz in content["pic_quiz"]:
                data = base64.b64decode(quiz['file'])
                filename = str(uuid.uuid4())+'.jpg'
            
                try:
                    with open(os.path.join(directory,filename), 'wb') as f:
                        f.write(data)
                except IOError as io:
                    print io
                    abort(422)

                #get quiz attributes
                correct_answer = quiz['correct_answer']
                text = quiz['text']
                
                #create tuple for db insertion
                tupled_info.append((text,correct_answer,filename,patient_id))
                
            cur = con.cursor()

            stmt_insert_quiz = "INSERT INTO picture (question_string,answer,loc,patient_id) VALUES (%s,%s,%s,%s)"

            row_count = cur.executemany(stmt_insert_quiz,tupled_info)

            if row_count > 0:
                resp['result'] = 'save_successful'
                return jsonify(resp)
            else:
                resp['result'] = 'save_failure'
                return jsonify(resp)
        else:
            return ('User not found', 204)
