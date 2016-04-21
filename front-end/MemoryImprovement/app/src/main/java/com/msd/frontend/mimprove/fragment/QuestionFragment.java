package com.msd.frontend.mimprove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.kiran.msdapp1.MultipleChoiceQuestion;
//import com.example.kiran.msdapp1.OneWord;
//import com.example.kiran.msdapp1.R;
//import com.example.kiran.msdapp1.interfaces.QuestionsInterfaces;

import com.msd.frontend.mimprove.MultipleChoiceQuestion;
import com.msd.frontend.mimprove.OneWord;
import com.msd.frontend.mimprove.QuestionKP;
import com.msd.frontend.mimprove.R;
import com.msd.frontend.mimprove.interfaces.QuestionsInterfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kiran on 3/23/2016.
 */
public class QuestionFragment extends Fragment implements QuestionsInterfaces
{
    private int currentPosition = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        currentPosition = getArguments().getInt("currentItem");
        View currentView = null;
        TextView questionText = null;
        if(MultipleChoiceQuestion.class.isInstance(questionList.get(currentPosition)))
        {
            currentView = inflater.inflate(R.layout.activity_question_mcq,null);
            questionText = (TextView)currentView.findViewById(R.id.question_mcq_title);
            RadioGroup radioGroup = (RadioGroup)currentView.findViewById(R.id.question_mcq_options);
            final MultipleChoiceQuestion currentMcq = (MultipleChoiceQuestion)questionList.get(currentPosition);
            for(final String userAnswer:displayQuestions(currentMcq))
            {
                RadioButton radioButton = new RadioButton(getActivity());
                radioButton.setText(userAnswer);
                radioButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {

                        submitted.put(currentPosition,true);
                        String correctAns = "";
//                        for(String temp:currentMcq.getCorrectAnswers())
//                        {
                          correctAns = currentMcq.getCorrect_answer();
                     //   }
                        if(userAnswer.equals(correctAns))
                        {
                            corrected.put(currentPosition,true);
                        }
                        else
                        {
                            corrected.put(currentPosition,false);
                        }
                        ArrayList<String> answers = new ArrayList<String>();
                        answers.add(correctAns);
                        answers.add(userAnswer);
                        mapOfAnswerQuestions.put(questionList.get(currentPosition).getQuestionText(),answers);
                    }
                });
                radioGroup.addView(radioButton);
            }

        }
        else if(OneWord.class.isInstance(questionList.get(currentPosition)))
        {
            currentView = inflater.inflate(R.layout.activity_question_missing_statement,null);
            questionText = (TextView)currentView.findViewById(R.id.question_missing_statement_title);
            final OneWord oneWord = (OneWord)questionList.get(currentPosition);
            final EditText answerFromUser = (EditText) currentView.findViewById(R.id.question_missing_statement_text);
            answerFromUser.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        submitted.put(currentPosition, true);
                        if (oneWord.getCorrectAnswer().toLowerCase().equals(answerFromUser.getText().toString().toLowerCase())) {
                            corrected.put(currentPosition,true);
                        }
                        ArrayList<String> answersList = new ArrayList<String>();
                        answersList.add(oneWord.getCorrectAnswer());
                        answersList.add(answerFromUser.getText().toString());
                        mapOfAnswerQuestions.put(questionList.get(currentPosition).getQuestionText(),answersList);
                        Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });

            answerFromUser.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    if(!answerFromUser.getText().toString().equals(""))
                    {
                        submitted.put(currentPosition, true);
                        if (oneWord.getCorrectAnswer().toLowerCase().equals(answerFromUser.getText().toString().toLowerCase())) {

                            corrected.put(currentPosition,true);
                        }
                    }
                    else
                    {
                        corrected.put(currentPosition,false);
                    }
                    ArrayList<String> answers = new ArrayList<String>();
                    answers.add(oneWord.getCorrectAnswer());
                    answers.add(answerFromUser.getText().toString());
                    mapOfAnswerQuestions.put(questionList.get(currentPosition).getQuestionText(),answers);
                }
            });
        }
        else
        {
            currentView = inflater.inflate(R.layout.activity_question_missing_statement,null);
            questionText = (TextView)currentView.findViewById(R.id.question_missing_statement_title);
            final QuestionKP oneWord =(QuestionKP) questionList.get(currentPosition);
            final EditText answerFromUser = (EditText) currentView.findViewById(R.id.question_missing_statement_text);
            answerFromUser.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        submitted.put(currentPosition, true);
                        ArrayList<String> answersList = new ArrayList<String>();
                        answersList.add(answerFromUser.getText().toString());
                        answersList.add(oneWord.getCategory());
                        mapOfAnswerQuestions.put(questionList.get(currentPosition).getQuestionText(),answersList);
                        Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });

            answerFromUser.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable)
                {
                    if(!answerFromUser.getText().toString().equals(""))
                    {
                        submitted.put(currentPosition, true);
                    }
                    else
                    {
                        corrected.put(currentPosition,false);
                    }
                    ArrayList<String> answersList = new ArrayList<String>();
                    answersList.add(answerFromUser.getText().toString());
                    answersList.add(oneWord.getCategory());
                    mapOfAnswerQuestions.put(questionList.get(currentPosition).getQuestionText(),answersList);
                    //mapOfAnswerQuestions.put(questionList.get(currentPosition).getQuestionText(),answerFromUser.getText().toString());
                }
            });
        }

        questionText.setText(questionList.get(currentPosition).getQuestionText());

        return currentView;
    }


    private ArrayList<String> displayQuestions(MultipleChoiceQuestion multipleChoiceQuestion)
    {
        ArrayList<String> answers = new ArrayList<>();
//        for(String temp:multipleChoiceQuestion.getInCorrectAnswers())
//        {
        answers.add(multipleChoiceQuestion.getIncorrect_answer_1());
        answers.add(multipleChoiceQuestion.getIncorrect_answer_2());
        answers.add(multipleChoiceQuestion.getIncorrect_answer_3());
//        }

//        for(String temp1:multipleChoiceQuestion.getCorrectAnswers())
//        {
            answers.add(multipleChoiceQuestion.getCorrect_answer());
//        }


        return answers;
    }
}
