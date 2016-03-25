package com.msd.frontend.mimprove;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kiran on 3/23/2016.
 */
public class MultipleChoiceQuestion extends Question {
    private Set<String> correctAnswers;
    private Set<String> inCorrectAnswers;
    private Set<String> usersAnswer;


    public MultipleChoiceQuestion(String questionText) {
        super(questionText);//
        this.correctAnswers = new HashSet<String>();
        this.inCorrectAnswers = new HashSet<String>();
        this.usersAnswer = new HashSet<String>();
    }

    public Set<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public Set<String> getInCorrectAnswers() {
        return inCorrectAnswers;
    }

    public Set<String> getUsersAnswer() {
        return usersAnswer;
    }

//    public void addAnswer(HashSet<String>){
//
//    }public void addAnswer(HashSet<String>){
//
//    }

    public void setCorrectAnswers(String answer) {
        this.correctAnswers.add(answer);
    }

    public void setInCorrectAnswers(String answer) {
        this.inCorrectAnswers.add(answer);
    }

    public void setUsersAnswer(String usersAnswer) {
        this.usersAnswer.add(usersAnswer);
    }
}
