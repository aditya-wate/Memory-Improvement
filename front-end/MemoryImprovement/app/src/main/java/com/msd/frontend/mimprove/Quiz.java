package com.msd.frontend.mimprove;

import java.util.ArrayList;

/**
 * Created by Kiran on 3/23/2016.
 */
public class Quiz {
    private ArrayList<Question> questions;
    private int questionsCount;
    private int questionsAnswered;
    private boolean isFullyAnswered;



    public Quiz(){
        this.questions = new ArrayList<Question>();

        this.questionsCount = questions.size();
        this.questionsAnswered = 0;
        this.isFullyAnswered = false;
    }


    public void addQuestion(Question q){
        this.questions.add(q);
    }
    public ArrayList<Question> getQuestions(){
        return this.questions;
    }



}
