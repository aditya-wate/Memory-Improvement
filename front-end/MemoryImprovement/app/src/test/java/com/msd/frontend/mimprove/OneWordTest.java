package com.msd.frontend.mimprove;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by singlasaahil on 2016-03-25.
 */
public class OneWordTest extends TestCase {

    OneWord oneword = new OneWord("What is your middle name?");

    public void setUp() throws Exception {
        super.setUp();
        this.oneword.setCorrectAnswer("Kumar");
        this.oneword.setUserAnswer("Kumar");
    }

    public void tearDown() throws Exception {

    }

    public void testGetCorrectAnswers() throws Exception {
        String expectedAnswer = "Kumar";
        String notExpectedAnswer = "Kaur";
        String actualAnswer = oneword.getUserAnswer();

        //test for the correct answer
        assertTrue("One Word Question does not have the expected correct answer.", actualAnswer.equals(expectedAnswer));
        //test for option not present in the set
            assertFalse("One Word Question should not have this option as correct answer.", actualAnswer.equals(notExpectedAnswer));
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