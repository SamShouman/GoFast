package com.example.start;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class Register extends AppCompatActivity {
    EditText usernameInput, passwordInput, confirmationPasswordInput;
    EditText emailInput;
    Button register;
    TextView usernameText, passwordText, confirmationText, emailText;
    String username, password, confirmPassword, email;
    DBHandler db;
    ProgressDialog pd;
    boolean input = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Slide animation=new Slide();
        animation.setSlideEdge(Gravity.BOTTOM);
        animation.setDuration(750);
        getWindow().setEnterTransition(animation);
        db = new DBHandler(Register.this, null, null, 3);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        confirmationPasswordInput = findViewById(R.id.confirmationPassword);
        emailInput = findViewById(R.id.email);
        register = findViewById(R.id.register);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        confirmationText = findViewById(R.id.confirmPasswordText);
        emailText = findViewById(R.id.emailText);

        confirmPassword = confirmationPasswordInput.getText().toString();
        getSupportActionBar().hide();
        pd=new ProgressDialog(Register.this);
        pd.setMessage("Registering new account..");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                confirmPassword = confirmationPasswordInput.getText().toString();
                email = emailInput.getText().toString();
                usernameText.setVisibility(View.INVISIBLE);
                passwordText.setVisibility(View.INVISIBLE);
                confirmationText.setVisibility(View.INVISIBLE);
                emailText.setVisibility(View.INVISIBLE);
                input=true;
                if (db.checkUsername(username)) {
                    usernameText.setText("Username already used.");
                    usernameText.setVisibility(View.VISIBLE);
                    input = false;
                }
//username.contains("@")||username.contains("%")||username.contains("#")||username.contains("!")||username.contains("*")||username.contains("^")||username.contains("+")||username.contains("?")
                if(username.length()<3){
                    usernameText.setText("Username too short.");
                    usernameText.setVisibility(View.VISIBLE);
                    input=false;
                }
                if (!username.matches("^[a-zA-Z0-9_.]*$")) {
                    usernameText.setText("Username must contain only letters,numbers or underscore.");
                    usernameText.setVisibility(View.VISIBLE);
                    input = false;
                }

                if (password.length() < 8) {
                    passwordText.setText("Password must be 8 characters or more.");
                    passwordText.setVisibility(View.VISIBLE);
                    input = false;
                }

                if (!password.equals(confirmPassword)) {
                    passwordText.setText("Passwords don't match.");
                    confirmationText.setText("Passwords don't match.");
                    passwordText.setVisibility(View.VISIBLE);
                    confirmationText.setVisibility(View.VISIBLE);
                    input = false;
                }

                if (username.equals("")) {
                    usernameText.setText("Field can't be empty.");
                    usernameText.setVisibility(View.VISIBLE);
                    input = false;
                }

                if (password.equals("")) {
                    passwordText.setText("Field can't be empty.");
                    passwordText.setVisibility(View.VISIBLE);
                    input = false;
                }

                if (confirmPassword.equals("")) {
                    confirmationText.setText("Field can't be empty.");
                    confirmationText.setVisibility(View.VISIBLE);
                    input = false;
                }

                if (email.equals("")) {
                    emailText.setText("Field can't be empty.");
                    emailText.setVisibility(View.VISIBLE);
                    input = false;
                }

                //SEE VALID EMAIL
                if (!isValid(email)) {
                    emailText.setText("Invalid email.");
                    emailText.setVisibility(View.VISIBLE);
                    input = false;
                }

                if (input) {
                    pd.show();
                    db.addUser(username, password, email);

                    StayLoggedIn.setUserName(Register.this,username);
                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Register.this);
                    Intent newsFeed = new Intent(Register.this, Main2Activity.class);

                    startActivity(newsFeed,options.toBundle());
                }


            }
        });

        Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(Register.this);
                Intent login=new Intent(Register.this,Login.class);
                startActivity(login,options.toBundle());
            }
        });

    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Register.this);
        alert.setMessage("Are you sure you want to stop registering?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Register.super.onBackPressed();
            }
        }).setNegativeButton("No", null);

        AlertDialog a = alert.create();
        a.show();
    }

   /* private void sendMessage() {
        final ProgressDialog dialog = new ProgressDialog(Register.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender("blackdark_saso@hotmail.com", "chouman789");
                    sender.sendMail("Confirmation Email",
                            "7855 is your code.",
                            "blackdark_saso@hotmail.com",
                            "lusarcasticsociety@hotmail.com");
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }*/

}
