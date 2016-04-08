package com.msd.frontend.mimprove;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kiran on 3/23/2016.
 */
public class MultipleChoiceQuestion extends QuestionKP {

	private String userAnswer;
  private String correct_answer;
  private String incorrect_answer1;
  private String incorrect_answer2;
  private String incorrect_answer3;
	
//    private Set<String> correctAnswers;
//    private Set<String> inCorrectAnswers;
//    private Set<String> usersAnswer;

  public MultipleChoiceQuestion(){}

	public MultipleChoiceQuestion(String questionText){
		super(questionText);
	}
  
    public MultipleChoiceQuestion(int id,String questionText) {
        super(id,questionText);//
//        this.correctAnswers = new HashSet<String>();
//        this.inCorrectAnswers = new HashSet<String>();
//        this.usersAnswer = new HashSet<String>();
    }



    public String getUserAnswer() {
		return userAnswer;
	}



	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}



	public String getCorrect_answer() {
		return correct_answer;
	}



	public void setCorrect_answer(String correct_answer_temp) {
		this.correct_answer = correct_answer_temp;
	}



	public String getIncorrect_answer_1() {
		return incorrect_answer1;
	}



	public void setIncorrect_answer_1(String incorrect_answer_1) {
		this.incorrect_answer1 = incorrect_answer_1;
	}



	public String getIncorrect_answer_2() {
		return incorrect_answer2;
	}



	public void setIncorrect_answer_2(String incorrect_answer_2) {
		this.incorrect_answer2 = incorrect_answer_2;
	}



	public String getIncorrect_answer_3() {
		return incorrect_answer3;
	}



	public void setIncorrect_answer_3(String incorrect_answer_3) {
		this.incorrect_answer3 = incorrect_answer_3;
	}



	@Override
	public String toString() {
		return super.toString()+"MultipleChoiceQuestion [userAnswer=" + userAnswer + ", correct_answer=" + correct_answer
				+ ", incorrect_answer_1=" + incorrect_answer1 + ", incorrect_answer_2=" + incorrect_answer2
				+ ", incorrect_answer_3=" + incorrect_answer3 + "]";
	}


}
