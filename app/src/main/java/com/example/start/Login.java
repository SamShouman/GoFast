package com.example.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class Login extends AppCompatActivity {
    EditText usernameInput,passwordInput;
    Button login,register;
    String username,password;
    DBHandler db;
    ConstraintLayout l;
    ProgressDialog progressDialog;
    Button delete;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Slide animation=new Slide();
        animation.setDuration(750);
        animation.setSlideEdge(Gravity.LEFT);
        getWindow().setEnterTransition(animation);
        getWindow().setAllowEnterTransitionOverlap(false);
        usernameInput=findViewById(R.id.username);
        passwordInput=findViewById(R.id.password);
        l=findViewById(R.id.l);
        login=findViewById(R.id.login);
      //  delete=findViewById(R.id.delete);
        register=findViewById(R.id.register);
        /* toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.gofastwhitelogo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        db=new DBHandler(Login.this,null,null,3);



        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressDialog=new ProgressDialog(Login.this);
                progressDialog.setMessage("Signing in..");
                progressDialog.show();
                username=usernameInput.getText().toString();
                password=passwordInput.getText().toString();
                if(username.equals("")||password.equals(""))
                   Snackbar.make(l,"Some Fields Are Missing.",Snackbar.LENGTH_SHORT).show();
                else {

                    if (db.login(username, password)) {

                        StayLoggedIn.setUserName(Login.this,username);
                        Intent newsFeed = new Intent(Login.this, Main2Activity.class);
                        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this);
                        progressDialog.dismiss();
                        startActivity(newsFeed,options.toBundle());

                    }

                    else
                        Snackbar.make(l,"Invalid Username or Password.",Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    usernameInput.setText("");
                    passwordInput.setText("");
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* username=usernameInput.getText().toString();
                password=passwordInput.getText().toString();

                usernameInput.setText("");
                usernameInput.setText("");
                db.addUsername(username,password);
                Toast.makeText(Login.this,"User Added.",Toast.LENGTH_SHORT).show();*/
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this);
               Intent register=new Intent(Login.this,Register.class);
               startActivity(register,options.toBundle());

            }
        });




    }




    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
