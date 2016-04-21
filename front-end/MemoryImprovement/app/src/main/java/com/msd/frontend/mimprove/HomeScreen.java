package com.msd.frontend.mimprove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.msd.frontend.mimprove.sdutils.NameUtils;

public class HomeScreen extends AppCompatActivity
{
    static String userName="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //NameUtils.saveUserName(this,"test_patient");

     //   Intent intent=getIntent();
     //   userName=intent.getStringExtra("username");

        //createQuiz.getServerData();
    }
    public void OpenGallery(View view){
        Intent intent = new Intent(HomeScreen.this,ViewPhotos.class);
        startActivity(intent);
    }
    public void OpenCalendar(View view){
        Intent intent = new Intent(HomeScreen.this,ViewCalendar.class);
        startActivity(intent);
    }
    public void TakeQuiz(View view){
        Intent intent = new Intent(HomeScreen.this,QuizStart.class);
        startActivity(intent);
    }
    public void InputQuiz(View view){
       // Intent intent = new Intent(HomeScreen.this,PictureQuestion.class);
        Intent intent = new Intent(HomeScreen.this,QuizStart.class);
        intent.putExtra("isInputQuestion",true);
        startActivity(intent);
    }
    public void OpenPictureQuiz(View view){
        Intent intent = new Intent(HomeScreen.this,PictureQuiz.class);
        startActivity(intent);
    }

}
