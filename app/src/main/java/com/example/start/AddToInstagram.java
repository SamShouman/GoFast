package com.example.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddToInstagram extends AppCompatActivity {
        DBHandler db;
        Button add;
        EditText name,destination;
        ConstraintLayout l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_instagram);
        getSupportActionBar().hide();
        Explode animation=new Explode();
        animation.setDuration(750);
        getWindow().setEnterTransition(animation);
        db=new DBHandler(AddToInstagram.this,null,null,3);
        name=findViewById(R.id.name);
        destination=findViewById(R.id.destination);
        add=findViewById(R.id.add);
        l=findViewById(R.id.l);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(destination.getText().toString().equals("") || name.getText().toString().equals("")){
                    Snackbar.make(l,"Some Fields Are Missing.",Snackbar.LENGTH_SHORT).show();
                }
                else {
                    String des = destination.getText().toString();
                    String name2 = name.getText().toString();
                    if(!db.checkInstagramUsername(name2)) {
                        db.addInstagramLink(StayLoggedIn.getUserName(AddToInstagram.this), name2, des);
                        Toast.makeText(AddToInstagram.this, "Contact has been added.", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(AddToInstagram.this,Main2Activity.class);
                        startActivity(i);
                    }
                    else{
                        Snackbar.make(l,"Name already exists.",Snackbar.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
