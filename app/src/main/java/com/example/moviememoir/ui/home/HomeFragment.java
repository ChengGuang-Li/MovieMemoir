package com.example.moviememoir.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviememoir.R;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.example.moviememoir.sharedPreference.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {
     private TextView  name;
     private TextView  setDate;
     private  Integer id;
    NetworkConnection connectNet = new NetworkConnection();
     private LinearLayout linearLayout;
    private Context context;
    private  TextView setError;
    private  TextView movieName;
    private   TextView movieReleaseDate;
    private  TextView movieScore;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        name = root.findViewById(R.id.text_home);
        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        name.setText("Welcome "+ sp.getString("firstname"));
        setError=root.findViewById(R.id.set_error);
        linearLayout = root.findViewById(R.id.recentMovies);
        Date date  = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
        String stringDate = sf.format(date);
        setDate=root.findViewById(R.id.text_home_date);
        setDate.setText(stringDate);
         id = sp.getInt("pId");
         Log.e("person Id------------",String.valueOf(id));

         getTop5Movies get = new getTop5Movies();
         get.execute(id);
        return root;
    }

     public class getTop5Movies extends AsyncTask<Integer,Void,String>{
         @Override
         protected String doInBackground(Integer... integers) {
             return  connectNet.getRecentTop5Movie(integers[0]);
         }

         @Override
         protected void onPostExecute(String s) {
             Log.e("Top5movies------------",s);
             if(s.trim() == null){
                 setError.setText("Sorry, there are not top five movies given by you");
             }else{
                  try{
                      LayoutInflater view = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                      JSONArray jsArray = new JSONArray(s);
                      for (int i=0; i< jsArray.length();i++){
                          final View v = view.inflate(R.layout.contanier,null);
                          JSONObject jsObject = jsArray.getJSONObject(i);
                          movieName = v.findViewById(R.id.movie_name);
                          movieReleaseDate=v.findViewById(R.id.movie_releaseDate);
                          movieScore=v.findViewById(R.id.movie_score);
                          movieName.setText(jsObject.getString("MovieName"));
                          movieName.setTextColor(Color.parseColor("#0d47a1"));

                          movieReleaseDate.setText(jsObject.getString("ReleaseDate"));
                          movieReleaseDate.setTextColor(Color.parseColor("#0d47a1"));

                          movieScore.setText(jsObject.getString("MovieScore"));
                          movieScore.setTextColor(Color.parseColor("#0d47a1"));

                          linearLayout.addView(v);

                      }

                  }catch (Exception e){
                      e.printStackTrace();
                  }

             }
         }
     }

}
