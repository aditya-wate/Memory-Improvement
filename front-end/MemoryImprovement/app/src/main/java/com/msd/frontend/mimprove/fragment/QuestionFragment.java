package com.msd.frontend.mimprove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.msd.frontend.mimprove.MultipleChoiceQuestion;
import com.msd.frontend.mimprove.R;
import com.msd.frontend.mimprove.interfaces.QuestionsInterfaces;

import java.util.ArrayList;

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
            MultipleChoiceQuestion currentMcq = (MultipleChoiceQuestion)questionList.get(currentPosition);
            for(String answer:displayQuestions(currentMcq))
            {
                RadioButton radioButton = new RadioButton(getActivity());
                radioButton.setText(answer);
                radioGroup.addView(radioButton);
            }

        }
        else
        {
            currentView = inflater.inflate(R.layout.activity_question_missing_statement,null);
            questionText = (TextView)currentView.findViewById(R.id.question_missing_statement_title);
            EditText answerFromUser = (EditText)currentView.findViewById(R.id.question_missing_statement_text);
            answerFromUser.setOnEditorActionListener(new EditText.OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                {
                    if(actionId == EditorInfo.IME_ACTION_DONE)
                    {
                        Toast.makeText(getActivity(),"Submitted",Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });
        }

        questionText.setText(questionList.get(currentPosition).getQuestionText());

        return currentView;
    }


    private ArrayList<String> displayQuestions(MultipleChoiceQuestion multipleChoiceQuestion)
    {
        ArrayList<String> answers = new ArrayList<>();
        for(String temp:multipleChoiceQuestion.getInCorrectAnswers())
        {
            answers.add(temp);
        }

        for(String temp1:multipleChoiceQuestion.getCorrectAnswers())
        {
            answers.add(temp1);
        }


        return answers;
    }
}
