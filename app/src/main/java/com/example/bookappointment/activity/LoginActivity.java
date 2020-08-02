package com.example.bookappointment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookappointment.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    TextView tv_forgotPssord, tv_signup;
    Button btn_login;
    ImageView img_facebook, img_gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        tv_forgotPssord = (TextView) findViewById(R.id.tv_forgotPssord);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        btn_login = (Button) findViewById(R.id.btn_login);
        img_facebook = (ImageView) findViewById(R.id.img_facebook);
        img_gmail = (ImageView) findViewById(R.id.img_gmail);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        tv_forgotPssord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(intent);
            }
        });
        img_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(intent);
            }
        });
        img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(intent);
            }
        });
    }


    private void checkValidation() {
        // Get email id and password
        String getEmailId = editTextEmail.getText().toString();
        String getPassword = editTextPassword.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile("\"\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b\"");


        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {


            Toast.makeText(LoginActivity.this, "Enter both credentials.", Toast.LENGTH_SHORT)
                    .show();
        }
        // Check if email id is valid or not
//        else if (!m.find()) {
//
//            Toast.makeText(LoginActivity.this, "Your Email Id is Invalid.", Toast.LENGTH_SHORT)
//                    .show();
//        }
        // Else do login and do your stuff
        else {
            if(getEmailId.equals("neeraj") && getPassword.equals("neeraj@1234")) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(LoginActivity.this, "Invalid credentials !", Toast.LENGTH_SHORT)
                        .show();
            }
        }


    }
}
