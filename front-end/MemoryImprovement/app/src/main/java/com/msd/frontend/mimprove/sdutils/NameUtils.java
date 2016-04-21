package com.msd.frontend.mimprove.sdutils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kiran on 4/20/2016.
 */
public class NameUtils
{
    public static void saveUserName(Context context,String name)
    {
        SharedPreferences userPrefrences = context.getSharedPreferences("user_name",Context.MODE_PRIVATE);
        userPrefrences.edit().putString("name",name).apply();
    }

    public static String getUserName(Context context)
    {
        SharedPreferences userPrefrences = context.getSharedPreferences("user_name",Context.MODE_PRIVATE);
        return userPrefrences.getString("name","test_patient");
    }

}
