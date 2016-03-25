package com.msd.frontend.mimprove;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.msd.frontend.mimprove.adapter.QuestionsAdapter;
import com.msd.frontend.mimprove.interfaces.QuestionsInterfaces;

import java.util.ArrayList;

/**
 * Created by Kiran on 3/23/2016.
 */
public class QuizStart  extends AppCompatActivity implements QuestionsInterfaces
{
    TextView pageNo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_pager);
        ViewPager pager = (ViewPager)findViewById(R.id.questions_adapter);
        pageNo = (TextView)findViewById(R.id.page_number);

        FetchQuiz fetcher = new FetchQuiz();
        Quiz quiz = fetcher.createQuiz();
        final ArrayList<Question> questions = quiz.getQuestions();
        questionList.clear();
        questionList.addAll(questions);
        pageNo.setText("1 of "+questionList.size());

        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions,getSupportFragmentManager());
        pager.setAdapter(questionsAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {
                pageNo.setText(""+(position+1)+" of "+questionList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
