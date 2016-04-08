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
import com.msd.frontend.mimprove.sdutils.*;
import com.msd.frontend.mimprove.design.SwipeDisabledViewPager;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
public class QuizStart extends AppCompatActivity implements QuestionsInterfaces
{
    private TextView pageNo;
    private SwipeDisabledViewPager pager;
    private AppCompatImageButton previousImage,nextImage;
    private AppCompatButton submitButton;
    private int currentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_pager);
        init();
        try
        {
            if(getIntent().getBooleanExtra("isInputQuestion",true))
            {
                populateInputQuestionData();
            }
            else
            {
                populateData();
            }

        }catch (ParseException e1)
        {
            return;
        }catch (IOException e2)
        {
            return;
        }

        initListeners();
    }


    private void init()
    {
        pager = (SwipeDisabledViewPager)findViewById(R.id.questions_adapter);
        pageNo = (TextView)findViewById(R.id.page_number);
        previousImage = (AppCompatImageButton)findViewById(R.id.prev);
        nextImage = (AppCompatImageButton)findViewById(R.id.next);
        submitButton = (AppCompatButton)findViewById(R.id.submit_answers);
        previousImage.setVisibility(View.INVISIBLE);
        nextImage.setVisibility(View.VISIBLE);

    }

    private void initListeners()
    {

        nextImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(submitted.containsKey(currentPosition))
                {
                    if(submitted.get(currentPosition))
                    {
                        pager.setCurrentItem(currentPosition+1);
                    }
                    else
                    {
                        pager.setSelected(true);
                        Toast.makeText(getApplicationContext(),"Please answer the question",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        previousImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pager.setCurrentItem(currentPosition-1);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                for (Map.Entry<Integer, Boolean> booleanEntry : corrected.entrySet()) {
                    if (booleanEntry.getValue()) {
                        count++;
                    }
                }

                new AlertDialog.Builder(QuizStart.this).setTitle("Thanks for submitting").setMessage("You have answered " + count + " out of " + questionList.size()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        currentPosition = 0;
//                        init();
//                        initListeners();
//                        try {
//                            populateData();
//
//                        } catch (ParseException e) {
//
//                        } catch (IOException e1) {
//
//                        }


                        //redirect to home page


                    }
                }).show();
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                pageNo.setText("" + (position + 1) + " of " + questionList.size());
                checkPageDisplay(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void checkPageDisplay(int position)
    {
        if(position == 0)
        {
            previousImage.setVisibility(View.GONE);
            nextImage.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.GONE);
        }
        else if(position == (questionList.size()-1))
        {
            previousImage.setVisibility(View.VISIBLE);
            nextImage.setVisibility(View.GONE);
            submitButton.setVisibility(View.VISIBLE);
        }
        else
        {
            previousImage.setVisibility(View.VISIBLE);
            nextImage.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.GONE);

        }

    }


    private void populateData() throws IOException, ParseException
    {
        SdUtils.copyJsonToSdCard(QuizStart.this);
        UserQuiz quiz = createQuiz.getRandomisedQuiz(QuizStart.this);
        final ArrayList<QuestionKP> questions = quiz.getQuestionList();
        android.util.Log.e("Questions received",""+questions.size());
        questionList.clear();
        questionList.addAll(questions);
        android.util.Log.e("Questions added",""+questionList.size());
//        corrctAnswers.clear();
        corrected.clear();
        pageNo.setText("1 of "+questionList.size());
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions,getSupportFragmentManager());
        pager.setAdapter(questionsAdapter);
        pager.setCurrentItem(0);
    }

    private void populateInputQuestionData() throws IOException, ParseException
    {
        SdUtils.copyJsonToSdCard(QuizStart.this);

        final ArrayList<QuestionKP> questions = createQuiz.getQuiz_Input(QuizStart.this);
        android.util.Log.e("Questions received",""+questions.size());
        questionList.clear();
        questionList.addAll(questions);
        android.util.Log.e("Questions added",""+questionList.size());
//        corrctAnswers.clear();
        corrected.clear();
        pageNo.setText("1 of "+questionList.size());
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions,getSupportFragmentManager());
        pager.setAdapter(questionsAdapter);
        pager.setCurrentItem(0);
    }
}