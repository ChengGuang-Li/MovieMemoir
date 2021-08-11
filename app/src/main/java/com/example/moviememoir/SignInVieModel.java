package com.example.moviememoir;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviememoir.model.Person;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.example.moviememoir.sharedPreference.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInVieModel extends ViewModel {

    NetworkConnection connectNet = new NetworkConnection();
    Person ps = new Person();
    private MutableLiveData<Integer> signInLiveDate;
    private Context context;
    public SignInVieModel()
    {
         signInLiveDate= new MutableLiveData<Integer>();
         signInLiveDate.setValue(33);
    }

    public void setSignInLiveDate(int i)
    {
        signInLiveDate.setValue(11);
    }



    public void getContext(Context ct){

        this.context = ct;
    }



    public class connect extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return connectNet.getInfo(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(String st){
            try {
                JSONArray jsonArray = new JSONArray(st);
                for(int i=0; i< jsonArray.length();i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    ps = new Person(
                            jo.getInt("PId"),
                            jo.getString("firstname"),
                            jo.getString("surname"),
                            jo.getString("gender"),
                            jo.getString("dob"),
                            jo.getInt("SNumber"),
                            jo.getString("SName"),
                            jo.getString("stateOR"),
                            jo.getInt("postcode")
                    );
                }
                SharedPreferenceUtil spUtil = SharedPreferenceUtil.getInstance(context);
                spUtil.putInt("pId",ps.getpId());
                spUtil.putString("firstname",ps.getFirstname());
                spUtil.putString("surname",ps.getSurname());
                spUtil.putString("gender",ps.getGender());
                spUtil.putString("dob",ps.getDob());
                spUtil.putInt("sNumber",ps.getsNumber());
                spUtil.putString("sName",ps.getsName());
                spUtil.putString("stateOR",ps.getStateOR());
                spUtil.putInt("postcode",ps.getPostcode());
                if(ps.getpId() != null)
                {
                    signInLiveDate.setValue(3);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                signInLiveDate.setValue(11);
            }

        }

    }

     public void  validate(String email, String password)
     {
         try {
             connect connect = new connect();
             connect.execute(email, password);
         }catch (Exception e)
         {
             signInLiveDate.setValue(11);
              e.printStackTrace();
         }

     }

    public LiveData<Integer> getSignInLiveDate() {
        return signInLiveDate;
    }
}
