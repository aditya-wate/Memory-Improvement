package com.msd.frontend.mimprove;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        Button signIn = (Button) findViewById(R.id.signInbutton);



       // HomeScreen



//        if (signIn != null) signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if (email.equals("admin@gmail.com")) {
//                    Intent intent = new Intent(LogInActivity.this, HomeScreen.class);
//                    startActivity(intent);
//                }
//            }
//        });

        //   addListenerOnLogInButton();
<<<<<<< HEAD
//        addListenerOnRegisterButton();
=======
        addListenerOnRegisterButton();
>>>>>>> origin/master


    }

    public void HomeScreen(View view)
    {
        EditText email = (EditText) findViewById(R.id.emailText);
        EditText password = (EditText) findViewById(R.id.passwordText);

        String  emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        if (emailText.equals("admin@gmail.com") && passwordText.equals("pwd")) {

            Intent intent = new Intent(LogInActivity.this, HomeScreen.class);
            startActivity(intent);
        }
    }


<<<<<<< HEAD
//    public void addListenerOnLogInButton() {
//        final Context context = this;
//
//
//            }
=======
    }
>>>>>>> origin/master




    public void addListenerOnRegisterButton() {

        final Context context = this;
        Button linkToRegister = (Button) findViewById(R.id.linkToRegister);
        if (linkToRegister != null) {
            linkToRegister.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {

                                                      Intent intent = new Intent(context,RegisterActivity.class);
                                                      startActivity(intent);

                                                  }
                                              }

            );
        }


    }

}
