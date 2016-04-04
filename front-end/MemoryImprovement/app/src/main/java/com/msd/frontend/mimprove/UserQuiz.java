package com.msd.frontend.mimprove;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiran on 4/2/2016.
 */
public class UserQuiz {

    private String username;
    private ArrayList<QuestionKP> questionList;
    
    public UserQuiz() {
		questionList = new ArrayList<QuestionKP>();
	}
    
    public void setUserName(String username) {
		this.username = username;
	}
    public void setQuestionList(ArrayList<QuestionKP> questionList) {
		this.questionList.addAll(questionList);
	}

	public ArrayList<QuestionKP> getQuestionList(){
		return this.questionList;
	}
    
    
	@Override
	public String toString() {
		return "UserQuiz [username=" + username + ", quiz=" + questionList.toString() + "]";
	}

}
