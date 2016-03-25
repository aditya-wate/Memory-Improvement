package com.msd.frontend.mimprove;

import java.util.HashSet;

/**
 * Created by Kiran on 3/23/2016.
 */
public class Question {

  //  private int questionId;
    private String questionText;
    //private Set<String> preSelectedOptions;

    //public String userAnswer;


    public Question(String questionText){
        this.questionText = questionText;
   //     this.questionId = questionNumber;
    }

    public Question(String questionText, HashSet<String> preSelectedOptions){

        this.questionText = questionText;
      //  this.preSelectedOptions = preSelectedOptions;

    }


    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

//    public void setUserAnswer(String userAnswer) {
//        this.userAnswer = userAnswer;
//    }

//    public String getUserAnswer() {
//        return userAnswer;
//    }

//    public void setPreSelectedOptions(HashSet<String> preSelectedOptions1 ) {
//        this.preSelectedOptions = preSelectedOptions1;
//    }
//    public Set<String> getPreSelectedOptions() {
//        return preSelectedOptions;
//    }
}
