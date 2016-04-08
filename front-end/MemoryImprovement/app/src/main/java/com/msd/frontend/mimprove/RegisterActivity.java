package com.msd.frontend.mimprove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void HomeScreen(View view)
    {
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText phone = (EditText) findViewById(R.id.phone);

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String phoneText = phone.getText().toString();

        if ((emailText!= null)&&(passwordText != null)&&(phoneText != null)){
            Intent intent = new Intent(RegisterActivity.this, HomeScreen.class);
            startActivity(intent);
        }
    }


}
