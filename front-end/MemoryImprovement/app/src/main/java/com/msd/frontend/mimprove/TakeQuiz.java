package com.msd.frontend.mimprove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class TakeQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        FetchQuiz fetcher = new FetchQuiz();
        Quiz quiz = fetcher.createQuiz();
        ArrayList<Question> questions = quiz.getQuestions();

        for(int i=0; i<questions.size();i++){
            if(MultipleChoiceQuestion.class.isInstance(questions.get(i))){

                MultipleChoiceQuestion ref = (MultipleChoiceQuestion) questions.get(i);


                String questionText = questions.get(i).getQuestionText();
                TextView questionBox = (TextView) findViewById(R.id.questionTextBox);
                questionBox.setText(questionText);

                final RadioButton[] rb = new RadioButton[4];
                RadioGroup rg = new RadioGroup(this); //create the RadioGroup
                rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
                Set<String> set = ref.getInCorrectAnswers();
                Iterator iter = set.iterator();
                while (iter.hasNext()) {
                    rb[i]  = new RadioButton(this);
                    rg.addView(rb[i]);
                    rb[i].setText(iter.next().toString());
                }




                for(int j=0; j<4; j++){





                }





                RelativeLayout myLay = (RelativeLayout) findViewById(R.id.myLayout);
                RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                EditText myEditText = new EditText(getApplicationContext());
                myEditText.setLayoutParams(mRparams);
                myLay.addView(myEditText);


//                Intent intent = new Intent(this,)
            }
            else if(OneWord.class.isInstance(questions.get(i))){

            }

        }




//        Quiz quiz = new Quiz();
//        for(int i=0;i<quiz.questions.size();i++){
//            Question question = quiz.questions.get(i);
//        }

    }
}
