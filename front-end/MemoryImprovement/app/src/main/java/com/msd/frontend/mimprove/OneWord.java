package com.msd.frontend.mimprove;

/**
 * Created by Kiran on 3/23/2016.
 */
public class OneWord extends Question {
    private String correctAnswer;
    private String userAnswer;

    public OneWord(String questionText) {
        super(questionText);
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
}
