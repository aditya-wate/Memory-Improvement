package com.msd.frontend.mimprove.adapter;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.msd.frontend.mimprove.Question;
import com.msd.frontend.mimprove.fragment.QuestionFragment;

import java.util.ArrayList;

/**
 * Created by Kiran on 3/23/2016.
 */
public class QuestionsAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<Question> questions = new ArrayList<>();
    public QuestionsAdapter(ArrayList<Question> questionsToBeShown,FragmentManager fm)
    {
        super(fm);
        this.questions = questionsToBeShown;
    }
    @Override
    public QuestionFragment getItem(int position)
    {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("currentItem",position);
        questionFragment.setArguments(arguments);
        return questionFragment;
    }

    @Override
    public int getCount()
    {
        return questions.size();
    }
}
