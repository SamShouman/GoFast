package com.example.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  long f= System.currentTimeMillis()+3000;
      //  while(System.currentTimeMillis()<f);
        getSupportActionBar().hide();
        if(StayLoggedIn.getUserName(MainActivity.this).length() == 0){

        Thread r=new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        r.start();
        }

        else {

                Intent goToMain2Activity=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(goToMain2Activity);
        }



    }
}
