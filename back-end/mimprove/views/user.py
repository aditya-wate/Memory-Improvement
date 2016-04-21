import flask, json, os, bcrypt
from flask import request, abort, jsonify, current_app
import MySQLdb as mdb
from mimprove.views import *
from mimprove.models import auth
from datetime import datetime

user_view = flask.Blueprint('user', __name__)

@user_view.route('/login', methods=['POST'])
def login():
    """
    Log in a user.

    If successful, return a message and error otherwise.

    URL: /user/login

    POST Parameters:

    - username
    - password

    Return: json {'message':'success'} on success. On failure, return HTTP 403.
    """
    con = mdb.connect(host=MYSQL_HOST, port=MYSQL_PORT,user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
    with con:
        username = request.form['username']
        password = request.form['password']
        cur = con.cursor()
        
        if username == "":
            abort(400,'Null Username')

        stmt_user = "SELECT password_hash FROM user WHERE username = %s"

        try:
            row_count = cur.execute(stmt_user,[username])
        except mdb.Error, e:
            try:
                print "MySQL Error [%d]: %s" % (e.args[0], e.args[1])
                abort(500,e.args[1])
            except IndexError:
                print "MySQL Error: %s" % str(e)
                abort(500,str(e))

        if row_count > 0:
            password_hash = cur.fetchone()[0]
            try:
                auth.check_password(password, password_hash)
                resp = dict()
                resp['message'] = 'success'
                return jsonify(resp)
            except Exception as e:
                raise auth.InvalidCredentials('Invalid Credentials', status_code=403)
        else:
            return ('User not found', 204)


@user_view.route('/register', methods=['POST'])
def register():
    """
    Register a user.

    If successful, return a message; error otherwise.

    URL: /user/register

    POST Parameters:

    - username
    - password
    - firstname
    - lastname
    - dob
    - gender


    Return: json on success. On failure, return failure message.
    """

    con = mdb.connect(host=MYSQL_HOST, port=MYSQL_PORT,user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
    with con:
        username = request.form['username']
        password = request.form['password']
        firstname = request.form['firstname']
        lastname = request.form['lastname']
        dob = request.form['dob']
        gender = request.form['gender']
        
        if username == "" or password== "" or firstname=="" or lastname=="" or dob=="" or gender=="":
            abort(400,'Invalid Request Parameters')

        cur = con.cursor()
        resp = dict()
        stmt_insert_user = "INSERT INTO user (username,password_hash,first_name,last_name,dob,gender) VALUES (%s,%s,%s,%s,%s,%s)"

        try:
            row_count = cur.execute(stmt_insert_user,(username,auth.generate_password_hash(password),firstname,lastname,dob,gender))
        except mdb.Error, e:
            try:
                print "MySQL Error [%d]: %s" % (e.args[0], e.args[1])
                abort(500,e.args[1])
            except IndexError:
                print "MySQL Error: %s" % str(e)
                abort(500,str(e))
                
        if row_count == 1:
            guardian_id = 1
            user_id = cur.lastrowid
            cur = con.cursor()
            stmt_insert_patient = "INSERT INTO patient (guardian_id,user_id) VALUES (%s,%s)"

            try:
                row_count = cur.execute(stmt_insert_patient,(guardian_id,user_id))
            except mdb.Error, e:
                try:
                    print "MySQL Error [%d]: %s" % (e.args[0], e.args[1])
                    abort(500,e.args[0])
                except IndexError:
                    print "MySQL Error: %s" % str(e)
                    abort(500,str(e))

            if row_count == 1:                    
                resp['message'] = 'success'
                return jsonify(resp)
            else:
                resp['message'] = 'failure'
                return jsonify(resp)
        else:
            resp['message'] = 'failure'
            return jsonify(resp)
