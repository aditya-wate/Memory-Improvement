package com.msd.frontend.mimprove;

/**
 * Created by Kiran on 3/23/2016.
 */
public class FetchQuiz {

    public Quiz q;

    public Quiz createQuiz() {
        this.q = new Quiz();
//        q.questions = new ArrayList<Question>();
        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("What is Your Favorite Colour ?");
        q.addQuestion(q1);
//        q1.setInCorrectAnswers("Yellow");
//        q1.setInCorrectAnswers("Red");
//        q1.setInCorrectAnswers("Green");
        q1.setInCorrectAnswers("Blue");
        q1.setInCorrectAnswers("White");
        q1.setInCorrectAnswers("Black");
        q1.setCorrectAnswers("Purple");


        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Which is your favorite holiday destination country?");
        q.addQuestion(q2);

//        q2.setInCorrectAnswers("Italy");
        q2.setInCorrectAnswers("India");
        q2.setInCorrectAnswers("Brazil");
//        q2.setInCorrectAnswers("France");
        q2.setInCorrectAnswers("Spain");
        q2.setCorrectAnswers("USA");



        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("What is your mother’s maiden name?");
        q.addQuestion(q3);


//        q3.setInCorrectAnswers("Mary");
//        q3.setInCorrectAnswers("Helen");
        q3.setInCorrectAnswers("Alex");
        q3.setInCorrectAnswers("Monica");
        q3.setInCorrectAnswers("Rachel");
        q3.setCorrectAnswers("Susan");


        MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("What is your cellphone last two digits?");
        q.addQuestion(q4);

//        q4.setInCorrectAnswers("88");
//        q4.setInCorrectAnswers("12");
        q4.setInCorrectAnswers("23");
        q4.setInCorrectAnswers("56");
        q4.setInCorrectAnswers("34");
        q4.setCorrectAnswers("89");


        MultipleChoiceQuestion q5 = new MultipleChoiceQuestion("In which city were you born?");
        q.addQuestion(q5);

//        q5.setInCorrectAnswers("Bangalore");
//        q5.setInCorrectAnswers("Paris");
        q5.setInCorrectAnswers("San Fransisco");
        q5.setInCorrectAnswers("San Jose");
        q5.setInCorrectAnswers("Boston");
        q5.setCorrectAnswers("London");


        OneWord q6 = new OneWord("In which month of the year is your Birthday?");
        q.addQuestion(q6);

        q6.setCorrectAnswer("October");
//        options.add("January");
//        options.add("Febrarury");
//        options.add("December");
//        options.add("July");
//        options.add("June");
//        q6.setPreSelectedOptions(options);


        OneWord q7 = new OneWord("Which is your favorite number?");
        q.addQuestion(q7);
//        options = new HashSet<String>();
//        options.add("7");
//        options.add("10");
//        options.add("14");
//        options.add("88");
//        options.add("0");
//        options.add("1");
        q7.setCorrectAnswer("7");


        OneWord q8 = new OneWord("Who is your favorite celebrity?");
        q.addQuestion(q8);
//        options = new HashSet<String>();
//        options.add("Christian Bale");
//        options.add("Leonardo De Caprio");
//        options.add("Sachin Tendulkar");
//        options.add("Roger Federer");
//        options.add("Kobe Bryant");
//        options.add("Lebron James");
        q8.setCorrectAnswer("Christian Bale");


        OneWord q9 = new OneWord("Who is your role model?");
        q.addQuestion(q9);
//        options = new HashSet<String>();
//        options.add("Brother");
//        options.add("Father");
//        options.add("Mother");
//        options.add("Sister");
//        options.add("Al Pacino");
        q9.setCorrectAnswer("Barack Obama");


        OneWord q10 = new OneWord("Your favorite type of food?");
        q.addQuestion(q10);
//        options = new HashSet<String>();
//        options.add("Indian");
//        options.add("Mexican");
//        options.add("Chinese");
//        options.add("Thai");
//        options.add("Meditterian");
//        options.add("Japanese");
        q10.setCorrectAnswer("Indian");

        return this.q;
    }
}

//        Question q11 = new MultipleChoiceQuestion("Your favorite movie?",11);
//        q.addQuestion(q11);
//        options = new HashSet<String>();
//        options.add("Dog Day Afternoon");
//        options.add("The Heat");
//        options.add("Taxi Driver");
//        options.add("Scarface");
//        options.add("Martian");
//        options.add("Serpico");
//        q11.setPreSelectedOptions(options);
//
//
//        Question q12 = new MultipleChoiceQuestion("How many siblings do you have?",12);
//        q.addQuestion(q12);
//        options = new HashSet<String>();
//        options.add("1");
//        options.add("0");
//        options.add("2");
//        options.add("3");
//        options.add("4");
//        options.add("5");
//        q12.setPreSelectedOptions(options);
//
//
//
//        Question q13 = new MultipleChoiceQuestion("Your favorite sports?",13);
//        q.addQuestion(q13);
//        options = new HashSet<String>();
//        options.add("Football");
//        options.add("Cricket");
//        options.add("Rugby");
//        options.add("Hockey");
//        options.add("Tennis");
//        options.add("Basket Ball");
//        q13.setPreSelectedOptions(options);
//
//
//        Question q14 = new MultipleChoiceQuestion("What is your favorite animal?",14);
//        q.addQuestion(q14);
//        options = new HashSet<String>();
//        options.add("Tiger");
//        options.add("Lion");
//        options.add("Monkey");
//        options.add("Dolphins");
//        options.add("Snakes");
//        options.add("Humming Bird");
//        q14.setPreSelectedOptions(options);
//
//
//        Question q15 = new MultipleChoiceQuestion("What is your nationality?",15);
//        q.addQuestion(q15);
//        options = new HashSet<String>();
//        options.add("Indian");
//        options.add("American");
//        options.add("French");
//        options.add("Italian");
//        options.add("Mexican");
//        options.add("Chinese");
//        q15.setPreSelectedOptions(options);







