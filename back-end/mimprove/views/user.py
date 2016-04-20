import flask, json, os, base64, uuid, bcrypt
from flask import request, abort, jsonify, current_app
import MySQLdb as mdb
from mimprove.views import *
from mimprove.models import auth
from datetime import datetime
from sshtunnel import SSHTunnelForwarder

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

    For verifying a password, use `models.check_password()`
    Current user accounts are stored in the `account` table
    in `data/credentials.db`. This is an sqlite file,
    you can access it with the python sqlite3 module.
    For testing, the plaintext passwords of the current
    users are in `data/passwords.txt`.

    Return: "OK" on success. On failure, return HTTP 403.
    """

    with SSHTunnelForwarder(
        ('54.172.172.152', 57347),
        ssh_private_key="./kp1.pem",
        ssh_username="ubuntu",
        remote_bind_address=(MYSQL_HOST, MYSQL_PORT)) as server:
        con = mdb.connect(host=MYSQL_HOST, port=server.local_bind_port,user=MYSQL_USER, passwd=MYSQL_PASSWD, db=MYSQL_DB)
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
                abort(204,'Invalid User')
