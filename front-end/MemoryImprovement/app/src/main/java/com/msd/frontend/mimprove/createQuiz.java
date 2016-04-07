package com.msd.frontend.mimprove;





import android.content.Context;
import android.os.Environment;

import java.io.FileReader;
import java.io.IOException;

import java.util.*;

//import org.json.JSONObject;
//import org.json.JSONArray;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Kiran on 4/2/2016.
 */
public class createQuiz
{

	public static ArrayList<QuestionKP> randomQuestionList = new ArrayList<>();

	public static UserQuiz getRandomisedQuiz(Context context) throws IOException, ParseException
	{
//	   FileReader reader = new FileReader(QuizStart.this.getCacheDir()+"/quizJson.json");
//	   FileReader reader = new FileReader(UserQuiz.getCacheDir()+"/quizJson.json");

		FileReader reader = new FileReader(context.getCacheDir()+"/quizJson.json");
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
		UserQuiz userQuiz = new UserQuiz();
		String userName = (String) jsonObject.get("username");

		String quiz = jsonObject.get("quiz").toString();
//	   System.out.println("Quiz:" + quiz);
		userQuiz.setUserName(userName);


		Gson gson = new Gson();
		List<MultipleChoiceQuestion> questionList;
		questionList = gson.fromJson(quiz, new TypeToken<List<MultipleChoiceQuestion>>() {
		}.getType());
//	   int i=1;
//	   for(MultipleChoiceQuestion question:questionList){
//		   System.out.println("Iteration: "+i++);
//		   System.out.println(question);
//	   }


//	   System.out.println("Before Random : " + questionList);

//	   List<QuestionKP> questions = randomize(questionList);
		randomize(questionList);
		userQuiz.setQuestionList(randomQuestionList);
		return userQuiz;
	}
//	   MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
//	   OneWord oneWord = new OneWord();
//	   System.out.println("For loop");

//	   for(QuestionKP question: questions){
//		   if(question.getClass().isInstance(mcq)){
////			   mcq = (MultipleChoiceQuestion) question;
////			   System.out.println(mcq);
//			   System.out.println("MCQ");
//		   }
//		   else{
//			   System.out.println("One Word");
////			   System.out.println(question);
//		   }
//	   }

//	   System.out.println("After Random : " + questions);

//	   System.out.println(quiz);


	public static ArrayList<QuestionKP> getQuiz_Input(Context context) throws IOException, ParseException
	{
//

		FileReader reader = new FileReader(context.getCacheDir()+"/input.json"); // fileName for questions JSON
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
		UserQuiz userQuiz = new UserQuiz();
		String userName = (String) jsonObject.get("username");

		String quiz = jsonObject.get("quiz").toString();    // get questions
//	   System.out.println("Quiz:" + quiz);
//		userQuiz.setUserName(userName);


		Gson gson = new Gson();
		List<String> questionList;
		questionList = gson.fromJson(quiz, new TypeToken<List<String>>() {
		}.getType());

		ArrayList<QuestionKP> questionObjects = createQuestions(questionList);


//		userQuiz.setQuestionList(randomQuestionList);
		return questionObjects;
	}

	private static ArrayList<QuestionKP> createQuestions(List<String> questionList) {
		ArrayList<QuestionKP> questionObjectList = new ArrayList<>();

     for(String questionText:questionList){
		 QuestionKP temp = new QuestionKP(questionText);
		 questionObjectList.add(temp);
     }

		return questionObjectList;
	}


	private static void randomize(List<MultipleChoiceQuestion> questionsArray)
	{
		LinkedList<MultipleChoiceQuestion> linkList = new LinkedList<>();
		linkList.addAll(questionsArray);
		randomQuestionList.clear();
		while(linkList.size()>0)
		{
			int number = (int)Math.floor(Math.random()*linkList.size());
			MultipleChoiceQuestion randomQuestion = linkList.get(number);
			linkList.remove(number);
			if(number % 2 == 0)
				randomQuestionList.add(randomQuestion);
			else
			createOneWordQuestion(randomQuestion);
		}
//		return randomQuestionList;
	}

//   private static void createMultipleChoiceQuestion(QuestionKP question) {
//		MultipleChoiceQuestion multi_choice_question = new MultipleChoiceQuestion(question.getQuestionText());
//		multi_choice_question.setInCorrectAnswers(question.getIn_correctAnswer_1(), question.getIn_correctAnswer_2()
//		, question.getIn_correctAnswer_3());
//		multi_choice_question.setCorrectAnswers(question.getCorrectAnswer());
//		randomQuestionList.add(multi_choice_question);
////		return randomQuestionList;
//	}

	private static void createOneWordQuestion(MultipleChoiceQuestion question) {
		OneWord oneWord_question = new OneWord(question.getQuestionID(),question.getQuestionText());
		oneWord_question.setCorrectAnswer(question.getCorrect_answer());
		randomQuestionList.add(oneWord_question);
//		return randomQuestionList;
	}


}
