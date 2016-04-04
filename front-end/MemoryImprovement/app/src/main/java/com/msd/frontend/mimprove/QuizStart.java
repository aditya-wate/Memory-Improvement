package com.msd.frontend.mimprove;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.kiran.msdapp1.adapter.QuestionsAdapter;
//import com.example.kiran.msdapp1.design.SwipeDisabledViewPager;
//import com.example.kiran.msdapp1.interfaces.QuestionsInterfaces;
//import com.example.kiran.msdapp1.sdutils.SdUtils;

import com.msd.frontend.mimprove.adapter.QuestionsAdapter;
import com.msd.frontend.mimprove.interfaces.QuestionsInterfaces;
import com.msd.frontend.mimprove.SdUtils;
import com.msd.frontend.mimprove.design.SwipeDisabledViewPager;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class QuizStart extends AppCompatActivity implements QuestionsInterfaces
{
    TextView pageNo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_pager);
        ViewPager pager = (ViewPager)findViewById(R.id.questions_adapter);
        pageNo = (TextView)findViewById(R.id.page_number);

//        createQuiz creator = new createQuiz();

//        quiz = null;
        try {
            SdUtils.copyJsonToSdCard(QuizStart.this);
            UserQuiz quiz = createQuiz.getRandomisedQuiz(QuizStart.this);
            final ArrayList<QuestionKP> questions = quiz.getQuestionList();
            questionList.clear();
            questionList.addAll(questions);
            pageNo.setText("1 of "+questionList.size());
            QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions,getSupportFragmentManager());
            pager.setAdapter(questionsAdapter);
            pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                {

                }

                @Override
                public void onPageSelected(int position) {
                    pageNo.setText("" + (position + 1) + " of " + questionList.size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}