DROP SCHEMA IF EXISTS memory_improve;
CREATE SCHEMA memory_improve;
USE memory_improve;
SET AUTOCOMMIT=0;

SELECT 'CREATING SCHEMA STRUCTURE' as 'INFO';

DROP TABLE IF EXISTS user, 
					patient, 
					guardian, 
                    quiz, 
                    question;

-- Category tables to be deleted
DROP TABLE IF EXISTS category_city, 
					category_food, 
                    category_people_male, 
                    category_people_female,
                    category_tools,
                    category_cars,
                    category_hobbies,
                    category_sports,
					category_music,
					category_books,
					category_songs,
					category_celebrities,
					category_genre,
					category_animals,
					category_plants,
					category_flowers,
					category_vegetables,
                    category_color,
                    category_number,
                    category_month,
                    category_movie;

/*!50503 set default_storage_engine = InnoDB */;
/*!50503 select CONCAT('storage engine: ', @@default_storage_engine) as INFO */;


CREATE TABLE user (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  username CHAR(30) NOT NULL UNIQUE,
  password_hash CHAR(61) NOT NULL,
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

CREATE TABLE question (
  question_id INT(11) NOT NULL AUTO_INCREMENT,
  category CHAR(30) NOT NULL,
  question_string CHAR(50) NOT NULL,
  answer CHAR(30) NOT NULL,
  patient_id INT(11) NOT NULL,
  FOREIGN KEY (patient_id) REFERENCES patient (patient_id) ON DELETE CASCADE,
  PRIMARY KEY (question_id)
);

CREATE TABLE images (
  id INT(11) NOT NULL AUTO_INCREMENT,
  image blob NOT NULL DEFAULT '',
  question_id INT(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (question_id) REFERENCES question (question_id)
);

CREATE TABLE category_city (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)
) ;


CREATE TABLE category_food (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_people_male (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_people_female (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_tools (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_cars (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_hobbies (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_sports (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_music (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_books (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_songs (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_celebrities (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_genre (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_animals (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_plants (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_flowers (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_vegetables (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_color (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_number (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_month (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

CREATE TABLE category_movie (
  id int(11) NOT NULL AUTO_INCREMENT,
  c_name char(35) NOT NULL DEFAULT '',
  PRIMARY KEY (id)	
);

INSERT INTO category_city VALUES (1, "Mumbai");
INSERT INTO category_city VALUES (2, "Boston");
INSERT INTO category_city VALUES (3, "Seattle");
INSERT INTO category_city VALUES (4, "Rome");
INSERT INTO category_city VALUES (5, "London");

INSERT INTO category_food VALUES (1, "Sushi");
INSERT INTO category_food VALUES (2, "Chicken Curry");
INSERT INTO category_food VALUES (3, "Teriyaki Chiken");
INSERT INTO category_food VALUES (4, "Grilled Steak");
INSERT INTO category_food VALUES (5, "Sandwich");

INSERT INTO category_people_male VALUES (1, "John");
INSERT INTO category_people_male VALUES (2, "Matthew");
INSERT INTO category_people_male VALUES (3, "Brandon");
INSERT INTO category_people_male VALUES (4, "Raymond");
INSERT INTO category_people_male VALUES (5, "Kenneth");

INSERT INTO category_people_female VALUES (1, "Rebecca");
INSERT INTO category_people_female VALUES (2, "Whittany");
INSERT INTO category_people_female VALUES (3, "Kristina");
INSERT INTO category_people_female VALUES (4, "Jane");
INSERT INTO category_people_female VALUES (5, "Marissa");

INSERT INTO category_tools VALUES (1, "Hammer");
INSERT INTO category_tools VALUES (2, "Screw Driver");
INSERT INTO category_tools VALUES (3, "Chisel");
INSERT INTO category_tools VALUES (4, "Scissors");
INSERT INTO category_tools VALUES (5, "Pincers");

INSERT INTO category_cars VALUES (1, "Jeep");
INSERT INTO category_cars VALUES (2, "Ford");
INSERT INTO category_cars VALUES (3, "Mercedes");
INSERT INTO category_cars VALUES (4, "Lexus");
INSERT INTO category_cars VALUES (5, "Porsche");

INSERT INTO category_hobbies VALUES (1, "Reading");
INSERT INTO category_hobbies VALUES (2, "Philately");
INSERT INTO category_hobbies VALUES (3, "Numismatics");
INSERT INTO category_hobbies VALUES (4, "Sports");
INSERT INTO category_hobbies VALUES (5, "Gardening");

INSERT INTO category_sports VALUES (1, "Soccer");
INSERT INTO category_sports VALUES (2, "Baseball");
INSERT INTO category_sports VALUES (3, "Football");
INSERT INTO category_sports VALUES (4, "Hockey");
INSERT INTO category_sports VALUES (5, "Basketball");
INSERT INTO category_sports VALUES (6, "Tennis");

INSERT INTO category_music VALUES (1, "Pop");
INSERT INTO category_music VALUES (2, "Jazz");
INSERT INTO category_music VALUES (3, "Rock");
INSERT INTO category_music VALUES (4, "Classical");
INSERT INTO category_music VALUES (5, "Hip hop");

INSERT INTO category_books VALUES (1, "Harry Potter");
INSERT INTO category_books VALUES (2, "Twilight");
INSERT INTO category_books VALUES (3, "To kill a Mockingbird");
INSERT INTO category_books VALUES (4, "Life of Pi");
INSERT INTO category_books VALUES (5, "The Great Gatzby");

INSERT INTO category_songs VALUES (1, "Stairway to Heaven");
INSERT INTO category_songs VALUES (2, "Imagine");
INSERT INTO category_songs VALUES (3, "Smells like Teen Spirit");
INSERT INTO category_songs VALUES (4, "Hotel California");
INSERT INTO category_songs VALUES (5, "Sweet Child O'Mine");

INSERT INTO category_celebrities VALUES (1, "Michael Jackson");
INSERT INTO category_celebrities VALUES (2, "Tom Cruise");
INSERT INTO category_celebrities VALUES (3, "Beyonce");
INSERT INTO category_celebrities VALUES (4, "Al Pacino");
INSERT INTO category_celebrities VALUES (5, "Tom Brady");

INSERT INTO category_genre VALUES (1, "Action");
INSERT INTO category_genre VALUES (2, "Adventure");
INSERT INTO category_genre VALUES (3, "Comedy");
INSERT INTO category_genre VALUES (4, "Drama");
INSERT INTO category_genre VALUES (5, "Crime");

INSERT INTO category_animals VALUES (1, "Dog");
INSERT INTO category_animals VALUES (2, "Cat");
INSERT INTO category_animals VALUES (3, "Rabbit");
INSERT INTO category_animals VALUES (4, "Snake");
INSERT INTO category_animals VALUES (5, "Fish");

INSERT INTO category_plants VALUES (1, "Bamboo");
INSERT INTO category_plants VALUES (2, "Chestnut");
INSERT INTO category_plants VALUES (3, "Mango");
INSERT INTO category_plants VALUES (4, "Pine");
INSERT INTO category_plants VALUES (5, "Nighshade");

INSERT INTO category_flowers VALUES (1, "Lily");
INSERT INTO category_flowers VALUES (2, "Rose");
INSERT INTO category_flowers VALUES (3, "Marigold");
INSERT INTO category_flowers VALUES (4, "Orchid");
INSERT INTO category_flowers VALUES (5, "Lotus");

INSERT INTO category_vegetables VALUES (1, "Tomato");
INSERT INTO category_vegetables VALUES (2, "Spinach");
INSERT INTO category_vegetables VALUES (3, "Broccoli");
INSERT INTO category_vegetables VALUES (4, "Carrot");
INSERT INTO category_vegetables VALUES (5, "Mushroom");

INSERT INTO category_color VALUES (1, "Red");
INSERT INTO category_color VALUES (2, "Green");
INSERT INTO category_color VALUES (3, "Blue");
INSERT INTO category_color VALUES (4, "Yellow");
INSERT INTO category_color VALUES (5, "Magenta");

INSERT INTO category_number VALUES (1, "14");
INSERT INTO category_number VALUES (2, "46");
INSERT INTO category_number VALUES (3, "99");
INSERT INTO category_number VALUES (4, "44");
INSERT INTO category_number VALUES (5, "75");

INSERT INTO category_month VALUES (1, "January");
INSERT INTO category_month VALUES (2, "February");
INSERT INTO category_month VALUES (3, "March");
INSERT INTO category_month VALUES (4, "April");
INSERT INTO category_month VALUES (5, "May");
INSERT INTO category_month VALUES (6, "June");
INSERT INTO category_month VALUES (7, "July");
INSERT INTO category_month VALUES (8, "August");
INSERT INTO category_month VALUES (9, "September");
INSERT INTO category_month VALUES (10, "October");
INSERT INTO category_month VALUES (11, "November");
INSERT INTO category_month VALUES (12, "December");

INSERT INTO category_movie VALUES (1, "The Heat");
INSERT INTO category_movie VALUES (2, "Taxi Driver");
INSERT INTO category_movie VALUES (3, "Scarface");
INSERT INTO category_movie VALUES (4, "Ghostbusters");
INSERT INTO category_movie VALUES (5, "Shawshank Redemption");

-- Insertion of test data 'test_password'
INSERT INTO user VALUES (1, 
						'test_patient', 
						'$2a$08$trgwUanfBAPZCYSueQauP.CJS2WTNBYjGxYzbZIXfai2SuFFRDh0K',
                        'John',
                        'Doe');

INSERT INTO user VALUES (2, 
						'test_guardian', 
						'$2a$08$trgwUanfBAPZCYSueQauP.CJS2WTNBYjGxYzbZIXfai2SuFFRDh0K',
                        'Jane',
                        'Doe');

-- guardian for user 1
INSERT INTO guardian VALUES (1,2);

-- patient with user_id 1 and guardian_id 1
INSERT INTO patient VALUES (1,1,1);


INSERT INTO question VALUES(1, 
							'category_color',
                            'What is your favorite colour ?',
                            'Purple',
                            1);
                            
INSERT INTO question VALUES(2, 
							'category_city',
                            'Which is your favorite holiday destination?',
                            'Venice',
                            1);
                            
INSERT INTO question VALUES(3, 
							'category_people_female',
                            'What is your mother\'s maiden name?',
                            'Marie',
                            1);
                            
INSERT INTO question VALUES(4, 
							'category_number',
                            'What are the last two digits of your cellphone number?',
                            '23',
                            1);
                            
INSERT INTO question VALUES(5, 
							'category_city',
                            'In which city were your born?',
                            'Los Angeles',
                            1);
                            
INSERT INTO question VALUES(6, 
							'category_month',
                            'Which month of the year is your birthday?',
                            'June',
                            1);
                            
INSERT INTO question VALUES(7, 
							'category_celebrities',
                            'Who is your favorite celebrity?',
                            'Bill Murray',
                            1);
                            
INSERT INTO question VALUES(8, 
							'category_movie',
                            'Which is your favorite movie?',
                            'Dog Day Afternoon',
                            1);
                            
INSERT INTO question VALUES(9, 
							'category_sports',
                            'Which sport do you like the most?',
                            'Cricket',
                            1);
                            
INSERT INTO question VALUES(10, 
							'category_animals',
                            'Which pet do you have',
                            'Hamster',
                            1);                            

COMMIT;