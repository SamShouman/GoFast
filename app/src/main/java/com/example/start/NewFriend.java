package com.example.start;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class NewFriend extends AppCompatActivity {
        Button facebook,whatsapp,instagram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        getSupportActionBar().hide();
        Slide animation=new Slide();
        animation.setSlideEdge(Gravity.BOTTOM);
        animation.setDuration(750);
        getWindow().setEnterTransition(animation);

        facebook=findViewById(R.id.facebook);
        whatsapp=findViewById(R.id.whatsapp);
        instagram=findViewById(R.id.instagram);

        facebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(NewFriend.this);
                Intent goToFB=new Intent(NewFriend.this,AddToFacebook.class);
                startActivity(goToFB,options.toBundle());
            }
        });

        instagram.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(NewFriend.this);
                Intent goToinsta=new Intent(NewFriend.this,AddToInstagram.class);
                startActivity(goToinsta,options.toBundle());
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(NewFriend.this);
                Intent goTowtsp=new Intent(NewFriend.this,AddToWhatsapp.class);
                startActivity(goTowtsp,options.toBundle());
            }
        });
    }


}
