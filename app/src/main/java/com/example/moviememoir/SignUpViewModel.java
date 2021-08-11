package com.example.moviememoir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviememoir.model.Credential;
import com.example.moviememoir.model.Person;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.example.moviememoir.sharedPreference.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignUpViewModel extends ViewModel {

    NetworkConnection connectNet = new NetworkConnection();
    Person ps = new Person();
    private MutableLiveData<Integer> signUpLiveData;
    private Context context;


    public SignUpViewModel() {
        signUpLiveData = new MutableLiveData<Integer>();
        signUpLiveData.setValue(33);
    }

    public LiveData<Integer> getSignUpLiveData() {
        return signUpLiveData;
    }

    public void getContext(Context context) {

        this.context = context;
    }


    private class PostCredential extends AsyncTask<Credential, Void, String> {
        @Override
        protected String doInBackground(Credential... credentials) {
            return connectNet.addCredential(credentials[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("PostCredential", result);
            if (result.equals("200Ok")) {
                signUpLiveData.setValue(3);
            } else {
                signUpLiveData.setValue(11);
            }

        }

    }


    public void signUpProcess(Credential c) {

        try {

            PostCredential postCredential = new PostCredential();
            postCredential.execute(c);

        } catch (Exception e) {
            signUpLiveData.setValue(11);
        }

    }

    //check the email doest not exist in the database
    public class checkEmail extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return connectNet.checkEmail(strings[0]);
        }

        @Override
        protected void onPostExecute(String st) {
            try {
                JSONArray jsonArray = new JSONArray(st);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    if(jo.getString("username") != null){
                        signUpLiveData.setValue(11);
                    }else{
                        signUpLiveData.setValue(3);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //connect check
    public void checkEmailInfo(String s) {
        try {
            checkEmail ch = new checkEmail();
            ch.execute(s);

        } catch (Exception e) {
            signUpLiveData.setValue(11);
        }
    }
}
