package com.example.moviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviememoir.networkconnection.NetworkConnection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity {
    NetworkConnection networkConnection=null;
    private Button  signup;
    private Button  signIn;
    private TextInputLayout emailText;
    private TextInputLayout password;
    private SignInVieModel sIMpdel;
    private String email;
    private String passwordHS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sIMpdel = new ViewModelProvider(this).get(SignInVieModel.class);
        sIMpdel.getContext(this);
        signup = findViewById(R.id.signUp);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUp.class);
                startActivity(intent);
            }
        });

        emailText = (TextInputLayout)findViewById(R.id.useremail);
        password = (TextInputLayout)findViewById(R.id.password);


        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailText.getEditText().getText().toString().trim();
                passwordHS = password.getEditText().getText().toString().trim();
                if(email !=null && password!=null && email.length()<40) {
                    sIMpdel.validate(email, passwordHS);
                }else if(email.length() >= 40){
                    sIMpdel.setSignInLiveDate(11);
                    emailText.setError("Length must be less than 40");
                }else if (email.equals("")){
                    sIMpdel.setSignInLiveDate(11);
                    emailText.setError("Cannot be blank");
                }else if(passwordHS.equals("")){
                    password.setError("Password cannot be blank");
                    sIMpdel.setSignInLiveDate(11);
                }
            }
        });

        sIMpdel.getSignInLiveDate().observe(this, new Observer<Integer>() {
             @Override
             public void onChanged(Integer integer) {
               if(integer == 3){
                   Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                   startActivity(intent);
               }else if(integer == 11)
               {
                   Toast.makeText(getApplicationContext(),"Email Or Password is wrong",Toast.LENGTH_LONG).show();
               }
             }
         });

    }

}
