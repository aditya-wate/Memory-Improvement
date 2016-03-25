DROP SCHEMA IF EXISTS memory_improve;
CREATE SCHEMA memory_improve;
USE memory_improve;
SET AUTOCOMMIT=0;

SELECT 'CREATING SCHEMA STRUCTURE' as 'INFO';

DROP TABLE IF EXISTS user, patient, guardian, quiz;

/*!50503 set default_storage_engine = InnoDB */;
/*!50503 select CONCAT('storage engine: ', @@default_storage_engine) as INFO */;


CREATE TABLE user (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  username CHAR(30) NOT NULL,
  password_hash CHAR(20) NOT NULL,
  first_name CHAR(30) NOT NULL,
  last_name INT(30) NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE guardian (
  guardian_id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
  PRIMARY KEY (guardian_id)
);

CREATE TABLE patient (
  patient_id INT(11) NOT NULL AUTO_INCREMENT,
  guardian_id INT(11) NOT NULL,
  user_id INT(11) NOT NULL,
  /*Patient Information goes here*/
  FOREIGN KEY (guardian_id) REFERENCES guardian (guardian_id),
  FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
  PRIMARY KEY (patient_id)
);


CREATE TABLE quiz (
  quiz_id INT(11) NOT NULL AUTO_INCREMENT,
  score INT NOT NULL CHECK(score <= 100),
  patient_id INT(11) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE CASCADE,
  PRIMARY KEY (quiz_id)
);
