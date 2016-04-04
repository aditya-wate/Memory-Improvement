from mimprove.models import db

class User(db.Model):
    """
    This represents account table in the sqllite db
    
    :param int account_id: Unique id for this account
    :param str username: email address of the user
    :param str password_hash: password hash for the user's password created using bcrypt
    :param str first_name: first name of the user
    :param str last_name: last name of the user
    """

    __tablename__ = 'user'
    
    user_id = db.Column(db.Integer, primary_key = True)
    username = db.Column(db.String(30))
    password_hash = db.Column(db.String(20))
    first_name = db.Column(db.String(30))
    last_name = db.Column(db.String(30))

    guardian = relationship("Guardian",uselist=False, back_populates="user")
    patient = relationship("Patient",uselist=False, back_populates="user")

class Guardian(db.Model):
    __tablename__ = 'guardian'

    guardian_id = db.Column(db.Integer, primary_key = True)
    user_id = db.Column(db.Integer, db.ForeignKey('user_id'))
    user = relationship("User", back_populates="guardian")
    patient = relationship("Patient",uselist=False, back_populates="guardian")

class Patient(db.Model):
    __tablename__ = 'patient'

    patient_id = db.Column(db.Integer, primary_key = True)
    guardian_id = db.Column(db.Integer, db.ForeignKey('guardian_id'))
    user_id = db.Column(db.Integer, db.ForeignKey('user_id'))
    user = relationship("User", back_populates="patient")
    guardian = relationship("Guardian", back_populates="patient")
    quiz = relationship("Quiz", uselist=False, back_populates="patient")
    question = relationship("Question", uselist=False, back_populates="patient")

class Quiz(db.Model):
    __tablename__ = 'quiz'

    quiz_id = db.Column(db.Integer, primary_key = True)
    score = db.Column(db.Integer)
    start_date = db.Column(db.Date)
    end_date = db.Column(db.Date)
    patient_id = db.Column(db.Integer, db.ForeignKey('patient_id'))
    patient = relationship("Patient", back_populates="quiz")
    
class Question(db.Model):
    __tablename__ = 'question'

    question_id = db.Column(db.Integer, primary_key = True)
    category = db.Column(db.String(20))
    question_string = db.Column(db.String(50))
    answer = db.Column(db.String(30))
    patient_id = db.Column(db.Integer, db.ForeignKey('patient_id'))
    patient = relationship("Patient", back_populates="question")


