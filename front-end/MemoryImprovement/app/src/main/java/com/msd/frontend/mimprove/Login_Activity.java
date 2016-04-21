package com.msd.frontend.mimprove;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;


import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
;import cz.msebera.android.httpclient.Header;

/**
 * Created by saahil on 1/30/16.
 */
public class Login_Activity extends Activity implements View.OnClickListener {

    ProgressDialog dialog = null;
    Button loginButton = null, signUpButton = null;
    EditText userName = null;
    EditText password = null;
    static Login_Activity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        setContentView(R.layout.activity_log_in);
        loginButton = (Button) findViewById(R.id.signInbutton);
        signUpButton = (Button) findViewById(R.id.linkToRegister);

        userName = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);

        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Done on Destroy");
    }

    public void onBackPressed() {

        return;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signInbutton) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Verifying the user");
            dialog.show();
            ArrayList<String> data = new ArrayList<>();
            data.add(userName.getText().toString());
            data.add(password.getText().toString());
            //welcomeActivity();
            getDataFromServer(dialog,userName.getText().toString(),password.getText().toString());

        } else {
            Intent intent = new Intent(Login_Activity.this, RegisterActivity.class);
            Login_Activity.this.startActivity(intent);
        }

    }

    public static void buildAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
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
        Intent intent = new Intent(Login_Activity.this, HomeScreen.class);
        intent.putExtra("username",userName.getText().toString());
        startActivity(intent);
    }

    private void getDataFromServer(final ProgressDialog progressDialog,String username, String password) {
        android.util.Log.e("Call made", "yes");
        AsyncHttpClient clientHandler = new AsyncHttpClient();
        RequestParams callParams = new RequestParams();
        callParams.put("username", username);
        callParams.put("password", password);
        String url = "user/login";


        clientHandler.post("http://54.172.172.152/" + url, callParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (responseBody != null) {
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
                            buildAlertDialog("Error validating User", "Invalid username/password");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    progressDialog.dismiss();

                } else {
                    buildAlertDialog("Error validating User", "Invalid username/password");
                }
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
