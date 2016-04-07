package com.msd.frontend.mimprove;

/**
 * Created by Kiran on 4/2/2016.
 */

import java.util.LinkedList;
import java.util.List;

public class QuizKP {

    private List<QuestionKP> questionList;

    public QuizKP(){
        this.questionList = new LinkedList<QuestionKP>();
    }

    public List<QuestionKP> getQuestionList(){
        return this.questionList;
    }
    public void setQuestionList(LinkedList<QuestionKP> list){
        this.questionList = list;
    }

}
