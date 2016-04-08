package com.msd.frontend.mimprove;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aditya.wate on 2016-03-25.
 */
public class MultipleChoiceQuestionTest extends TestCase {

    MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion("Which is your favorite holiday destination country?");

//    multipleChoiceQuestion.setInCorrectAnswers("India");
//    multipleChoiceQuestion.setInCorrectAnswers("Brazil");
//    multipleChoiceQuestion.setInCorrectAnswers("Spain");
//    multipleChoiceQuestion.setCorrectAnswers("USA");

    public void setUp() throws Exception {
        super.setUp();
        this.multipleChoiceQuestion.setCorrect_answer("Miami");
//        this.multipleChoiceQuestion.setCorrectAnswers("Miami");
        this.multipleChoiceQuestion.setIncorrect_answer_1("Brazil");
        this.multipleChoiceQuestion.setIncorrect_answer_2("Tehran");
        this.multipleChoiceQuestion.setIncorrect_answer_3("India");
        this.multipleChoiceQuestion.setUserAnswer("Miami");
//        this.multipleChoiceQuestion.setUsersAnswer("India");
    }

    public void tearDown() throws Exception {

    }

    public void testGetCorrectAnswers() throws Exception {
        String expectedAnswer1 = "Miami";
        String notExpectedAnswer1 = "Mumbai";
        String actualAnswer = multipleChoiceQuestion.getUserAnswer();
        String expectedAnswer = "Rome";


        //test for the correct answer
        assertTrue("Multiple Choice Question does not have the expected correct answer.", actualAnswer.equalsIgnoreCase(expectedAnswer1));
        //test for option not present in the set
        assertFalse("Multiple Choice Question should not have this option as correct answer.", actualAnswer.equalsIgnoreCase(notExpectedAnswer1));
        }

    public void testGetInCorrectAnswers() throws Exception {

    }

    public void testGetUsersAnswer() throws Exception {

    }

    public void testSetCorrectAnswers() throws Exception {

    }

    public void testSetInCorrectAnswers() throws Exception {

    }

    public void testSetUsersAnswer() throws Exception {

    }
}