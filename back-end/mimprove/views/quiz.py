import flask
import json, os
from flask import request, abort, jsonify, current_app
import MySQLdb as mdb
from mimprove.views import *

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

    This method is allowed to all the user who has requested the quiz.
    Otherwise, return an HTTP 500 error.

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
