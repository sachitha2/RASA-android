package com.example.rasachatbotapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    TextInputLayout regNameSign, regAgeSign, regtelNumSign, reggurNumber, regPasswordSign, regCpasswordSign;
    Button signupbtnSign, callLoginbtnSign;
    private static int SPLASH_SCREEN = 1000;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        regNameSign = findViewById(R.id.fNameSign);
        regAgeSign = findViewById(R.id.uAgeSign);
        regtelNumSign = findViewById(R.id.telNumSign);
        reggurNumber = findViewById(R.id.gurNumber);
        regPasswordSign = findViewById(R.id.pwdSign);
        regCpasswordSign = findViewById(R.id.cpwdSign);
        callLoginbtnSign = findViewById(R.id.loginbtn);
        signupbtnSign = findViewById(R.id.signup);

        callLoginbtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
    }


    private Boolean validateName() {
        String val = regNameSign.getEditText().getText().toString();

        if (val.isEmpty()) {
            regNameSign.setError("Field Can't be Empty");
            return false;
        } else {
            regNameSign.setError(null);
            return true;
        }
    }

    private Boolean validateUserAge() {
        String val = regAgeSign.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";

        if (val.isEmpty()) {
            regAgeSign.setError("Field Can't be Empty");
            return false;
        } else if (val.length() > 2) {
            regAgeSign.setError("Age too Long");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            regAgeSign.setError("Spaces are not Allowed");
            return false;
        } else {
            regAgeSign.setError(null);
            regAgeSign.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatetelNum() {
        String val = regtelNumSign.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";
        String valNumber = "[0-9]+";

        if (val.isEmpty()) {
            regtelNumSign.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(valNumber)) {
            regtelNumSign.setError("Wrong Mobile Number");
            return false;
        } else if (val.length() != 10) {
            regtelNumSign.setError("Wrong Mobile Number");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            regtelNumSign.setError("Can't Enter Spaces");
            return false;
        } else {
            regtelNumSign.setError(null);
            regtelNumSign.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validategurtelNum() {
        String val = reggurNumber.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";
        String valNumber = "[0-9]+";

        if (val.isEmpty()) {
            reggurNumber.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(valNumber)) {
            reggurNumber.setError("Wrong Mobile Number");
            return false;
        } else if (val.length() != 10) {
            reggurNumber.setError("Wrong Mobile Number");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            reggurNumber.setError("Can't Enter Spaces");
            return false;
        } else {
            reggurNumber.setError(null);
            reggurNumber.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPasswordSign.getEditText().getText().toString();
        String pass = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                //"(?=.*[@#$%^&-+=()])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$";

        if (val.isEmpty()) {
            regPasswordSign.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(pass)) {
            regPasswordSign.setError("Must be include a-z,A-Z,0-9");
            return false;
        } else {
            regPasswordSign.setError(null);
            regPasswordSign.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatecPassword() {
        String val = regCpasswordSign.getEditText().getText().toString();
        String checkpass = regPasswordSign.getEditText().getText().toString();

        if (val.isEmpty()) {
            regCpasswordSign.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(checkpass)) {
            regCpasswordSign.setError("Confirm Password Didn't Match");
            return false;
        } else {
            regCpasswordSign.setError(null);
            regCpasswordSign.setErrorEnabled(false);
            return true;
        }
    }


    public void registerUser(View view) {

        if (!validateName() | !validateUserAge() | !validatetelNum() | !validategurtelNum() |!validatePassword() | !validatecPassword()) {
            return;
        } else {
            rootNode = FirebaseDatabase.getInstance("https://smartpillowsliit-default-rtdb.asia-southeast1.firebasedatabase.app/");
            reference = rootNode.getReference("Admin");
            //Get All Values
            String fname = regNameSign.getEditText().getText().toString();
            String userage = regAgeSign.getEditText().getText().toString();
            String telNum = regtelNumSign.getEditText().getText().toString();
            String gurtelNum = reggurNumber.getEditText().getText().toString();
            String password = regPasswordSign.getEditText().getText().toString();
            String cpassword = regCpasswordSign.getEditText().getText().toString();

            userHelper helperClass = new userHelper(fname, userage, telNum, gurtelNum, password, cpassword);

            reference.child(telNum).setValue(helperClass);

            Log.d("Test","sachitha "+reference);


            Toast.makeText(this, "Your Account has been Created Successfully!", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),login.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_SCREEN);

            /*Intent intent = new Intent(getApplicationContext(),otpScreen.class);
            intent.putExtra("telNum",telNum);
            startActivity(intent);*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

}