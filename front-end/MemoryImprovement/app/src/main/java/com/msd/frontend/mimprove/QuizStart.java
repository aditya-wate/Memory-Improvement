package com.msd.frontend.mimprove;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.kiran.msdapp1.adapter.QuestionsAdapter;
//import com.example.kiran.msdapp1.design.SwipeDisabledViewPager;
//import com.example.kiran.msdapp1.interfaces.QuestionsInterfaces;
//import com.example.kiran.msdapp1.sdutils.SdUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.msd.frontend.mimprove.adapter.QuestionsAdapter;
import com.msd.frontend.mimprove.interfaces.QuestionsInterfaces;
import com.msd.frontend.mimprove.sdutils.*;
import com.msd.frontend.mimprove.design.SwipeDisabledViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

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
            if(getIntent().getBooleanExtra("isInputQuestion",false))
            {
                populateInputQuestionData();
            }
            else
            {
                ProgressDialog progressDialog = ProgressDialog.show(QuizStart.this,"Fetching data","Loading quiz..");
                getDataFromServer(progressDialog);
                //populateData();
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

                        try
                        {
                            JSONObject jsonObject = castMapToObject();
                            ProgressDialog progressDialog = ProgressDialog.show(QuizStart.this,"Fetching data","Loading quiz..");
                            saveQuiz(progressDialog,jsonObject);
                            android.util.Log.e("Parsed object",jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


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


    private void populateData(UserQuiz quiz) throws IOException, ParseException
    {
        SdUtils.copyJsonToSdCard(QuizStart.this);
        //UserQuiz quiz = createQuiz.getRandomisedQuiz(QuizStart.this);
        final ArrayList<QuestionKP> questions = quiz.getQuestionList();
        android.util.Log.e("Questions received",""+questions.size());
        questionList.clear();
        mapOfAnswerQuestions.clear();
        questionList.addAll(questions);
        android.util.Log.e("Questions added",""+questionList.size());
//        corrctAnswers.clear();
        corrected.clear();
        pageNo.setText("1 of "+questionList.size());
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions,getSupportFragmentManager());
        pager.setAdapter(questionsAdapter);
        pager.setCurrentItem(0);
    }

    private JSONObject castMapToObject() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        JSONArray questionsArray = new JSONArray();
        for (Map.Entry<String, ArrayList<String>> answersMap : mapOfAnswerQuestions.entrySet())
        {
            JSONObject currentQuestion = new JSONObject();
            currentQuestion.put("text",answersMap.getKey());
            ArrayList<String> temp = answersMap.getValue();
            currentQuestion.put("correct_answer",temp.get(0));
            currentQuestion.put("user_answer",temp.get(1));
            questionsArray.put(currentQuestion);
        }
        jsonObject.put("quiz",questionsArray);
        return jsonObject;
    }

    private void populateInputQuestionData() throws IOException, ParseException
    {
        SdUtils.copyJsonToSdCard(QuizStart.this);

        final ArrayList<QuestionKP> questions = createQuiz.getQuiz_Input(QuizStart.this);
        android.util.Log.e("Questions received", "" + questions.size());
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

    private void saveQuiz(final ProgressDialog progressDialog,JSONObject objectOfAnswers) throws JSONException, UnsupportedEncodingException {
        android.util.Log.e("Save Quiz call made", "yes");
        AsyncHttpClient clientHandler = new AsyncHttpClient();
       // RequestParams callParams = new RequestParams();
        StringEntity entity = new StringEntity(objectOfAnswers.toString());
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        client.post(context, restApiUrl, entity, "application/json",
//                responseHandler);
//        callParams.put("username", "test_patient");

        clientHandler.post(QuizStart.this,"http://54.172.172.152/quiz/save_quiz", entity, "application/json",new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.e("Response quiz succes", new String(responseBody));

//                String string = new String(responseBody);
//                String res = "" + string;
//                org.json.JSONObject jsonObject = null;
//                try {
//                    jsonObject = new org.json.JSONObject(res);
//                    UserQuiz userQuiz1 = createQuiz.getRandomisedQuiz(jsonObject);
//                    populateData(userQuiz1);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


                progressDialog.dismiss();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Response fail", new String(responseBody)+"\t"+statusCode);
                progressDialog.dismiss();
//                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(),"Sorry there was some problem",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getDataFromServer(final ProgressDialog progressDialog)
    {
        android.util.Log.e("Call made", "yes");
        AsyncHttpClient clientHandler = new AsyncHttpClient();
        RequestParams callParams = new RequestParams();
        callParams.put("username", "test_patient");


        clientHandler.post("http://54.172.172.152/quiz/get_quiz", callParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.e("Response succes", new String(responseBody));

                String string = new String(responseBody);
                String res = "" + string;
                org.json.JSONObject jsonObject = null;
                try {
                    jsonObject = new org.json.JSONObject(res);
                    UserQuiz userQuiz1 = createQuiz.getRandomisedQuiz(jsonObject);
                    populateData(userQuiz1);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                progressDialog.dismiss();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Response fail", new String(responseBody));
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sorry there was some problem",Toast.LENGTH_SHORT).show();
            }
        });
    }
}