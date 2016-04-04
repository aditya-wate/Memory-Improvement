package com.msd.frontend.mimprove.interfaces;

import com.example.kiran.msdapp1.QuestionKP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kiran on 3/23/2016.
 */
public interface QuestionsInterfaces
{
    ArrayList<QuestionKP> questionList = new ArrayList<>();
    Map<Integer,Boolean> submitted  = new HashMap<>();
    Map<Integer,Boolean> corrected = new HashMap<>();
}
