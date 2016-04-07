package com.msd.frontend.mimprove;

/**
 * Created by Kiran on 4/2/2016.
 */
public class QuestionKP {
	
	

	private int question_id;
    private String text;
//    private String userAnswer;
//    private String correct_answer;
//    private String incorrect_answer_1;
//    private String incorrect_answer_2;
//    private String incorrect_answer_3;


    public QuestionKP(){

    }

    public QuestionKP(String questionText){
        this.text = questionText;
    }


    public QuestionKP(int id, String questionText){
        this.question_id = id;
        this.text = questionText;

    }

    public QuestionKP(int id, String questionText, String userAnswer, String correctAnswer,
                      String in_correctAnswer_1, String in_correctAnswer_2, String in_correctAnswer_3){
        this.question_id = id;
        this.text = questionText;
//        this.userAnswer = userAnswer;
//        this.correct_answer = correctAnswer;
//        this.incorrect_answer_1 = in_correctAnswer_1;
//        this.incorrect_answer_2 = in_correctAnswer_2;
//        this.incorrect_answer_3 = in_correctAnswer_3;
    }
    
    @Override
	public String toString(){
		return "QuestionKP [question_id=" + question_id + ", text=" + text + ", correct_answer=" ;/*+
	correct_answer
				+ ", incorrect_answer_1=" + incorrect_answer_1 + ", incorrect_answer_2=" + incorrect_answer_2
				+ ", incorrect_answer_3=" + incorrect_answer_3 + "]";
				*/
	}

    /*
    public void setQuestionID(int id){ this.question_id = id; }
    public void setQuestionText(String questionText){ this.text = questionText; }
    public void setIn_correctAnswer_1(String in_correctAnswer_1){ this.incorrect_answer_1 = in_correctAnswer_1; }
    public void setIn_correctAnswer_2(String in_correctAnswer_2){ this.incorrect_answer_2 = in_correctAnswer_2; }
    public void setIn_correctAnswer_3(String in_correctAnswer_3){ this.incorrect_answer_3 = in_correctAnswer_3; }
    */



  //  public void setUserAnswer(String userAnswer){ this.userAnswer = userAnswer; }


    public int getQuestionID(){ return this.question_id; }
    public String getQuestionText(){ return this.text; }
//    public String getUserAnswer(){ return this.userAnswer; }
//    public String getCorrectAnswer(){ return this.correct_answer; }
//    public String getIn_correctAnswer_1(){ return this.incorrect_answer_1; }
//    public String getIn_correctAnswer_2(){ return this.incorrect_answer_2; }
//    public String getIn_correctAnswer_3(){ return this.incorrect_answer_3; }


}
