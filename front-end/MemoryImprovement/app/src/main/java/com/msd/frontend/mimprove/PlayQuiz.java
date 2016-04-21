package com.msd.frontend.mimprove;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by singlasaahil on 4/12/2016.
 */


public class PlayQuiz extends Activity implements View.OnClickListener {
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);


    }


}
