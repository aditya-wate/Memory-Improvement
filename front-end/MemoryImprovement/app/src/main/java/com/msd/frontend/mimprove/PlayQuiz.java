package com.msd.frontend.mimprove;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by singlasaahil on 4/12/2016.
 */


public class PlayQuiz extends Activity implements View.OnClickListener {
    Button submit;
    static int i = 0;
    EditText ans;
    String answer = "";
    int correctness = 0;
    TextView question;
    JSONArray array;
    ArrayList<String> list = null;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<String>();
        setContentView(R.layout.activity_play_quiz);
        submit = (Button) findViewById(R.id.submit);
        submit.setText("Next");
        i=0;
        submit.setClickable(false);
        question = (TextView) findViewById(R.id.question);
        ans = (EditText) findViewById(R.id.answer);
        view = (ImageView) findViewById(R.id.image);
        submit.setOnClickListener(this);
        getDataFromServer(getDialog("Please wait while question is being fetched"));
       // Log.e("Saahil",array.toString());


    }

    public void onClick(View view) {
        try {
            answer = ans.getText().toString();
            if (i == array.length() -1) {
                submit.setText("Submit");
                correctness = answer.equalsIgnoreCase(array.getJSONObject(i).getString("correct_answer")) ? correctness + 1 : correctness;
                Toast.makeText(getApplicationContext(), "You attempted "+correctness+" correct answers from "+array.length()+" questions", Toast.LENGTH_SHORT).show();
               // sendDataToServer(getDialog("Please wait while your response is being uploaded"));
                finish();
            }
            else if (!answer.equalsIgnoreCase("")) {
                Log.e("Saahil",answer+" "+array.getJSONObject(i).getString("correct_answer"));

                correctness = answer.equalsIgnoreCase(array.getJSONObject(i).getString("correct_answer")) ? correctness + 1 : correctness;
                list.add(answer);
                i++;
                populateQuestion();
            }else{
                Toast.makeText(getApplicationContext(), "Please enter your answer", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public JSONObject getJSONObject(){
        try{
            JSONObject object=new JSONObject();
            object.put("username", HomeScreen.userName);
            JSONArray arra=new JSONArray();

            for(int j=0;j<array.length();j++){
                JSONObject obj=new JSONObject();
                obj.put("text",array.getJSONObject(i).get("text"));
                obj.put("file",array.getJSONObject(i).get("file"));
                obj.put("correct_answer",array.getJSONObject(i).get("correct_answer"));
                obj.put("user_answer",list.get(j));
                arra.put(obj);
            }


            object.put("pic_quiz",array);
            return object;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void sendDataToServer(final ProgressDialog progressDialog){
        try{
        android.util.Log.e("Call made", "yes");
        AsyncHttpClient clientHandler = new AsyncHttpClient();
            JSONObject object=getJSONObject();
            StringEntity entity = new StringEntity(object.toString());


        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        String url="picture/save_quiz";

        Log.e("Saahil",object.toString());

        clientHandler.post(PlayQuiz.this,"http://54.172.172.152/" + url, entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.e("Response succes", new String(responseBody));

                String string = new String(responseBody);
                String res = "" + string;
                org.json.JSONObject jsonObject = null;
                try {
                    jsonObject = new org.json.JSONObject(res);
                    if (jsonObject.getString("result").equalsIgnoreCase("save_successful")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Answers uploaded successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                progressDialog.dismiss();

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Response fail", new String(responseBody));
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry there was some problem", Toast.LENGTH_SHORT).show();
            }
        });
        }catch(Exception e){
            e.getMessage();
        }
    }


    public void populateQuestion() throws Exception {
        JSONObject obj = array.getJSONObject(i);
        question.setText(obj.getString("text"));

        String image = obj.getString("file");
        byte b[] = image.getBytes();
        Inflater inflater = new Inflater();
        inflater.setInput(b);
        byte[] bitmapdata = Base64.decode(b, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        view.setImageBitmap(bitmap);
        ans.setText("");
    }

    public ProgressDialog getDialog(String text) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(text);
        dialog.show();
        ;
        return dialog;
    }

    private void getDataFromServer(final ProgressDialog progressDialog) {
        android.util.Log.e("Call made", "yes");
        AsyncHttpClient clientHandler = new AsyncHttpClient();
        RequestParams callParams = new RequestParams();
        callParams.put("username", HomeScreen.userName);


        Log.e("Saahil",callParams.toString());
        clientHandler.post("http://54.172.172.152/picture/get_quiz", callParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.e("Response succes", new String(responseBody));

                String string = new String(responseBody);



                String res = "" + string;
                try {
                    Toast.makeText(getApplicationContext(), "Question pulled successfully", Toast.LENGTH_SHORT).show();
                    array = (new org.json.JSONObject(res)).getJSONArray("pic_quiz");
                    Log.e("Saahil",array.length()+"");
                    submit.setClickable(true);
                    populateQuestion();


                } catch (Exception e) {
                    e.printStackTrace();
                }


                progressDialog.dismiss();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Response fail", new String(responseBody));
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry there was some problem", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
