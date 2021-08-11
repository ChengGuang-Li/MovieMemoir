package com.example.moviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moviememoir.model.Credential;
import com.example.moviememoir.model.Person;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.example.moviememoir.sharedPreference.SharedPreferenceUtil;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class SignUp extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Context context;
     private Button chooseDate;
     private EditText firstname;
     private  EditText surname;
     private RadioGroup radioGroup;
     private String date;
     private  EditText sNumber;
     private  EditText sName;
     private Spinner stateSP;
     private  EditText postcode;
     private  EditText email;
     private  EditText password;
     private  Button confirm;

     private String gender = "Female";
     private String notification = "";
     private String state="";
     private  String Pfirstname;
     private String Psurname;
     private  int PsNumber;
     private  String PsName;
     private  int Ppostcode;
     private String Pemail;
     private String Ppassword;

     private SignUpViewModel sUModel;
     private int check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sUModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        chooseDate = findViewById(R.id.choosedate);
        chooseDate.setOnClickListener(this);
        firstname = (EditText)findViewById(R.id.spFirstName);
        surname = (EditText)findViewById(R.id.spSurname);
        radioGroup= (RadioGroup)findViewById(R.id.radio);
        sNumber=(EditText)findViewById(R.id.streetNo);
        sName=(EditText)findViewById(R.id.streetName);
        stateSP=(Spinner)findViewById(R.id.stateSp);
        postcode=(EditText)findViewById(R.id.postcode);
        email=(EditText)findViewById(R.id.sp_email);
        password=(EditText)findViewById(R.id.sp_password);
        confirm = (Button)findViewById(R.id.confirm);

        firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!firstname.getText().toString().trim().isEmpty() && firstname.getText().toString().trim().length() < 20){
                    Pfirstname = firstname.getText().toString().trim();
                    notification = "Your Firstname is : " + Pfirstname;
                    Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                }else if(firstname.getText().toString().trim().length() > 20){
                    firstname.setError("Invalid input");
                    notification = "Your firstname must be less than 20 character";
                    Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();

                }
                else{
                    firstname.setError("cannot be blank");
                    notification = "Your firstname cannot be blank";
                    Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                }

            }
        });
        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!surname.getText().toString().trim().isEmpty() && surname.getText().toString().trim().length() < 20){
                    Psurname = surname.getText().toString().trim();
                    notification = "Your Surname is : " + Psurname;
                    Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                }else if(surname.getText().toString().trim().length() > 20){
                    surname.setError("Invalid input");
                    notification = "Your  Surname must be less than 20 character";
                    Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();

                }
                else{
                    surname.setError("cannot blank");
                    notification = "Your  Surname cannot be blank";
                    Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                }

            }
        });

        sNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!sNumber.getText().toString().trim().isEmpty() && sNumber.getText().toString().trim().length() < 8) {
                        String x = sNumber.getText().toString().replaceAll(" ", "");
                        PsNumber = Integer.parseInt(x);
                        notification = "Your Street Number is : " + PsNumber;
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    } else if (sNumber.getText().toString().trim().length() > 8) {
                        sNumber.setError("Invalid Input");
                        notification = "Your  Street Number must be less than 8 character";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();

                    } else {
                        sNumber.setError("cannot be blank");
                        notification = "Your Street Number cannot be blank";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        sName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!sName.getText().toString().trim().isEmpty() && sName.getText().toString().trim().length() < 20) {
                        PsName = sName.getText().toString().trim();
                        notification = "Your Street Name is : " + PsName;
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    } else if (sName.getText().toString().trim().length() > 20) {
                        sName.setError("Invalid Input");
                        notification = "Your  Street Name must be less than 20 character";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();

                    } else {
                        sName.setError("cannot be blank");
                        notification = "Your Street Name cannot be blank";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        postcode.addTextChangedListener(new TextWatcher()

        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!postcode.getText().toString().trim().isEmpty() && postcode.getText().toString().trim().length() < 6) {
                        String x = postcode.getText().toString().replaceAll(" ", "");
                        Ppostcode = Integer.parseInt(x);
                        notification = "Your Postcode is : " + PsNumber;
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    } else if (postcode.getText().toString().trim().length() > 6) {
                        postcode.setError("Invalid input");
                        notification = "Your Postcode must be less than 6 character";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();

                    } else {
                        postcode.setError("cannot be blank");
                        notification = "Your Postcode cannot be blank";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                        if (!email.getText().toString().trim().isEmpty() && email.getText().toString().trim().length() < 40) {
                            Pemail = email.getText().toString().trim();
                            notification = "Your User Email is : " + Pemail;
                            Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                        } else if (email.getText().toString().trim().length() > 40) {
                            email.setError("Invalid input");
                            notification = "Your  User Email must be less than 40 character";
                            Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();

                        } else {
                            email.setError("cannot be blank");
                            notification = "Your User Email cannot be blank";
                            Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                        }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!password.getText().toString().trim().isEmpty() && password.getText().toString().trim().length() < 32) {
                        Ppassword = password.getText().toString().trim();
                        notification = "Your Password is set";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    } else if (password.getText().toString().trim().length() > 32) {
                        password.setError("invalid input");
                        notification = "Your  Password must be less than 32 character";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();

                    } else {
                        password.setError("cannot be blank");
                        notification = "Your Password cannot be blank";
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_INDEFINITE).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        //extract data from radioGroup and store this data to Variable gender
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.bt_male:
                        gender = "Male";
                        notification = "Your Gender Is : " + gender;
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_SHORT).show();
                        break;

                    case R.id.bt_female:
                        gender="Female";
                        notification = "Your Gender Is : " + gender;
                        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        //extract data from Spinner selected by user
        stateSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = (String) stateSP.getSelectedItem();
                notification = "Your State Is : " + state;
                Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            // /////////////////////////////////////////////////////////////////////////////////////////// //

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sUModel.checkEmailInfo(email.getText().toString().trim());
                sUModel.signUpProcess(PostCredential());
            }
        });

        sUModel.getSignUpLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                  if (integer ==3){

                      Intent it = new Intent(SignUp.this,SignInActivity.class);
                      startActivity(it);

                  }else if (integer == 11){
                      notification = "Sign up failed. Email already exists";
                      Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_SHORT).show();
                  }

            }
        });
    }

    // build the datepicker and store pick date to variable date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = String.valueOf(month+1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
        chooseDate.setText(date);
        notification = "Your Birthday Is :  " + date;
        Snackbar.make(findViewById(R.id.signUpW), notification, Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,2020,6,1);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    // MD5 encryption
    public static String getMD5(String str) {
        try {
            // Generate an MD5 encrypted calculation summary
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Calculate md5 function
            md.update(str.getBytes());
            // digest () finally determines to return the md5 hash value, and the return value is 8 as a string.
            // Because the md5 hash value is a 16-bit hex value, which is actually an 8-bit character
            // The BigInteger function converts an 8-bit string into a 16-bit hex value, which is represented by a string;
            // the hash value in the form of a string is obtained
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person PostPerson(){
         Person person = new Person();
         person.setpId(1);
         person.setFirstname(Pfirstname);
         person.setSurname(Psurname);
         person.setGender(gender);
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String birthday = df.format(date1);
        person.setDob(birthday);
         person.setsNumber(PsNumber);
         person.setsName(PsName);
         person.setStateOR(state);
         person.setPostcode(Ppostcode);
        return person;
    }

    public Credential PostCredential(){
     Credential credential = new Credential();
     credential.setcId(1);
     credential.setUsername(Pemail);
      String ps = getMD5(Ppassword);
     credential.setPasswordhs(ps);
     //sign up date
     Date c = Calendar.getInstance().getTime();
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
     String formattedDate = df.format(c);
     //
     credential.setSignupdate(formattedDate);
     credential.setpId(PostPerson());
     return credential;
    }


}
