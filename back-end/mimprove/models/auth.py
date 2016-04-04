import bcrypt

class Error(Exception):
    pass


class InvalidCredentials(Error):
    """
    User provided invalid credentials
    """
    status_code = 400
    
    def __init__(self, message, status_code=None):
        Exception.__init__(self)
        self.message = message
        if status_code is not None:
            self.status_code = status_code

    def to_dict(self):
        rv = dict()
        rv['message'] = self.message
        return rv


def generate_password_hash(password):
    """
    This method generates password hashes that can be used with `check_password()`

    :param password: password
    :return: password hash
    """
    return bcrypt.hashpw(password, bcrypt.gensalt())


def check_password(password, hashed_password):
    """
    Check the password of a user

    This simply uses bcrypt. The password salt is encoded in the hash
    by bcrypt.

    :param password: password, as provided in authentication request
    :param hashed_password: as stored in credentials database for this user

    :raise: InvalidCredentials if password is not valid
    """
    if not bcrypt.checkpw(password, hashed_password):
        raise InvalidCredentials()


