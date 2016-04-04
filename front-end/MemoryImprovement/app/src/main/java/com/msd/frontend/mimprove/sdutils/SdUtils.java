package com.msd.frontend.mimprove;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Kiran on 4/4/2016.
 */
public class SdUtils
{
    public static final String JSONSTRING = "";

    public static void copyJsonToSdCard(Context context)
    {
        File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/msdapp/");
        if(!parentDir.exists())
        {
            parentDir.mkdir();
        }

        File jsonDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/json/");
        if(!jsonDir.exists())
        {
            jsonDir.mkdir();
        }



        String fileName = "quizJson.json";
        try
        {
            InputStream myInput = context.getAssets().open(fileName);
            android.util.Log.e("Received file",fileName);
            String outFileName = context.getCacheDir() + "/" + fileName;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        }catch (Exception e)
        {
            return;
        }
    }
}
