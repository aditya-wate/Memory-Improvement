import flask
from flask import jsonify
import mimprove.views.quiz
from mimprove.models.auth import InvalidCredentials
from models import db
from views import login_manager

def create_app():
    app = flask.Flask(__name__)

    @app.route('/')
    def hello_world():
        return 'Hello World!'

    app.register_blueprint(mimprove.views.quiz.quiz_view, url_prefix="/quiz")

    # Registering handle for InvalidCredentials 
    @app.errorhandler(InvalidCredentials)
    def handle_invalid_credentials(error):
        response = jsonify(error.to_dict())
        response.status_code = error.status_code
        return response


    return app


