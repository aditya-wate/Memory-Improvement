package com.msd.frontend.mimprove;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    static RegisterActivity activity=null;
    EditText email,password,phone,FN,LN,Y,M,D;;
    Spinner gender;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button = (Button)(findViewById(R.id.regButton));
        activity=this;
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        FN=(EditText) findViewById(R.id.firstName);
        LN=(EditText) findViewById(R.id.lastName);
        Y=(EditText) findViewById(R.id.year);
        M=(EditText) findViewById(R.id.month);
        D=(EditText) findViewById(R.id.day);
        gender=(Spinner) findViewById(R.id.gender);

        button.setOnClickListener(this);
    }
    public void onClick(View view){
        if(view.getId()==R.id.regButton){
            HomeScreen();
        }
    }

    public void HomeScreen()
    {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String phoneText = phone.getText().toString();
        ProgressDialog dialog=new ProgressDialog(activity);
        dialog.setMessage("Please wait while we register you.");
        dialog.show();;
        if ((emailText!= null)&&(passwordText != null)&&(phoneText != null)){
            getDataFromServer(dialog);

        }
    }

    private void getDataFromServer(final ProgressDialog progressDialog) {
        android.util.Log.e("Call made", "yes");
        AsyncHttpClient clientHandler = new AsyncHttpClient();
        RequestParams callParams = new RequestParams();
        callParams.put("username", email.getText().toString());
        callParams.put("password", password.getText().toString());
        callParams.put("phone", phone.getText().toString());
        callParams.put("firstname", FN.getText().toString());
        callParams.put("lastname", LN.getText().toString());
        callParams.put("dob", Y.getText().toString() + "-" + M.getText().toString() + "-" + D.getText().toString());
        callParams.put("gender", gender.getSelectedItem().toString());

        String url = "user/register";


        clientHandler.post("http://54.172.172.152/" + url, callParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.e("Response succes", new String(responseBody));

                String string = new String(responseBody);
                String res = "" + string;
                org.json.JSONObject jsonObject = null;
                try {
                    jsonObject = new org.json.JSONObject(res);
                    if (jsonObject.getString("message").equalsIgnoreCase("success")) {
                        progressDialog.dismiss();
                        welcomeActivity();
                    } else {
                        buildAlertDialog("Error Registering User","User already exists");
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
    }



    public static void buildAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
               activity );
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void welcomeActivity() {
        Intent intent = new Intent(RegisterActivity.this, HomeScreen.class);
        intent.putExtra("username",email.getText().toString());
        startActivity(intent);
    }


}
