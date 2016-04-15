package com.msd.frontend.mimprove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by singlasaahil on 4/11/2016.
 */
public class PictureQuiz extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_quiz);
    }

    public void PictureQuestion (View view){
        Intent intent = new Intent(PictureQuiz.this, PictureQuestion.class);
        startActivity(intent);
    }
    public void PlayQuiz (View view){
        Intent intent = new Intent(PictureQuiz.this, PlayQuiz.class);
        startActivity(intent);
    }
}
