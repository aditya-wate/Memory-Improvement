import flask, json, os, base64, uuid, bcrypt
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

    If successful, return a message and error.

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
            except IndexError:
                print "MySQL Error: %s" % str(e)

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
