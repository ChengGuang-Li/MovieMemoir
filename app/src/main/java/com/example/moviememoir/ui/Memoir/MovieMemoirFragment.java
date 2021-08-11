package com.example.moviememoir.ui.Memoir;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.moviememoir.MovieView;
import com.example.moviememoir.R;
import com.example.moviememoir.Tools.DownloadImage;
import com.example.moviememoir.model.Cinema;
import com.example.moviememoir.model.Memoir;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MovieMemoirFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private TextView myMemoir;
    NetworkConnection connection = new NetworkConnection();
    LinearLayout linearLayout;
    Memoir memoir = new Memoir();
    Cinema cinema = new Cinema();
    private String postcode;
    private float score;
    private TextView mNmae;
    private TextView mRelease;
    private TextView mWatchDate;
    private TextView cinemaPostcode;
    private TextView mComment;
    private RatingBar rating;
    private ImageView imageView;
    private String poster;
    private String mId;
    List<Memoir> memoirList = new ArrayList<Memoir>();
    List<String> mIdList = new ArrayList<String>();
    private Spinner soertM;
    List<String> omditList = new ArrayList<String>();
    List<String> publicScore = new ArrayList<String>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_memoir, container, false);
        myMemoir = root.findViewById(R.id.text_slideshow);
        myMemoir.setTextColor(Color.parseColor("#e53935"));
        soertM=root.findViewById(R.id.sort);
        soertM.setOnItemSelectedListener(this);
        linearLayout = root.findViewById(R.id.MFragment_content);
        new retriveMemoir().execute("movie.memoir", "");
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String select = parent.getItemAtPosition(position).toString();
         for(String ss: omditList){
             new getScoreFromApid().execute(ss);
         }

        if(select.equals("release date")){
            Collections.sort(memoirList, new Comparator<Memoir>() {
                @Override
                public int compare(Memoir o1, Memoir o2) {
                    return o1.getMrlsdate().compareTo(o2.getMrlsdate());
                }
            });

        }else if(select.equals("rating")){
            Collections.sort(memoirList, new Comparator<Memoir>() {
                @Override
                public int compare(Memoir o1, Memoir o2) {
                    return o1.getrScore().compareTo(o2.getrScore());
                }
            });

        } else if (select.equals("-----")){
            return;
        }
        addView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class retriveMemoir extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return connection.getResults(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //LayoutInflater view = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Gson gson1 = new GsonBuilder().setDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSXXX").create();
                    memoir=gson1.fromJson(jsonArray.getJSONObject(i).toString(),Memoir.class);
                    memoirList.add(memoir);
                    omditList.add(jsonArray.getJSONObject(i).getString("omdbid"));


                    //JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // final  View v = view.inflate(R.layout.memoir_contents,null);
                    //  imageView =v.findViewById(R.id.My_M_view_imag);
                    //  mNmae=v.findViewById(R.id.My_M_mView_name);
                    //  mRelease=v.findViewById(R.id.My_M_mView_release);
                    //   mWatchDate=v.findViewById(R.id.My_M_mView_watchDate);
                    // cinemaPostcode=v.findViewById(R.id.My_M_mView_Cpostcode);
                    //  mComment=v.findViewById(R.id.My_M_comment);
                    //  rating=v.findViewById(R.id.My_M_star);

                   /// JSONObject cinemaObject = jsonObject.getJSONObject("CNo");
                    //String name = jsonObject.getString("MName");
                   // String watch = jsonObject.getString("wcdatetime");
                   // String release = jsonObject.getString("mrlsdate");
                   // String score1 = jsonObject.getString("RScore");
                  //  String comment = jsonObject.getString("comment");
                   // postcode = cinemaObject.getString("location");
                  //  Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSXXX").create();
                  //  cinema = gson.fromJson(cinemaObject.toString(), Cinema.class);

                  //  poster = jsonObject.getString("image");
                   // mId = jsonObject.getString("omdbid");
                   // memoir.setmName(name);
                  /// memoir.setComment(comment);
                   // memoir.setImage(poster);
                   // memoir.setOmdbid(mId);
                  // memoir.setcNo(cinema);

                   // SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                   // Date watchDate = sf.parse(watch);
                   // Date releaseDate = sf.parse(release);

                   // SimpleDateFormat ss = new SimpleDateFormat("MM/dd/yyyy");
                  //  String watchS = ss.format(watchDate);
                   // String releaseS = ss.format(releaseDate);

                    //memoir.setMrlsdate(releaseS);
                    //memoir.setWcdatetime(watchS);
                  //  Double k = Double.valueOf(score1);
                  //  BigDecimal zzzz = new BigDecimal(k);
                   // memoir.setrScore(zzzz);

                  //  memoirList.add(memoir);

                    //Set value
                    // mNmae.setText("Name " + memoir.getmName());
                    //  mRelease.setText("Release Time" + memoir.getMrlsdate());
                    //  mWatchDate.setText("Watch Time" +  memoir.getWcdatetime());
                    // cinemaPostcode.setText("Postcode " + cinema.getLocation());
                    // mComment.setText(memoir.getComment());
                    //   rating.setRating(score);
                    //  Log.e("name---------",name.trim());
                    // Log.e("score1-----",score1);
                    //Log.e("Poster------",poster);
                    // new DownloadImage(imageView).execute(poster);
                    // v.setOnClickListener(new View.OnClickListener() {
                    //  @Override
                    //   public void onClick(View v) {
                    // try{
                    // Intent it = new Intent(getActivity(),MovieView.class);
                    // it.putExtra("id",mId);
                    //  startActivity(it);

                    //   }catch (Exception e){
                    //      e.printStackTrace();
                    //  }
                    // }
                    // });
                    //linearLayout.addView(v);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            addView();
        }
    }

    public void addView() {
        LayoutInflater view = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout.removeAllViews();
        for (Memoir m : memoirList) {
            View v = view.inflate(R.layout.memoir_contents, null);
            imageView = v.findViewById(R.id.My_M_view_imag);
            mNmae = v.findViewById(R.id.My_M_mView_name);
            mRelease = v.findViewById(R.id.My_M_mView_release);
            mWatchDate = v.findViewById(R.id.My_M_mView_watchDate);
            cinemaPostcode = v.findViewById(R.id.My_M_mView_Cpostcode);
            mComment = v.findViewById(R.id.My_M_comment);
            rating = v.findViewById(R.id.My_M_star);

            //Set value
             Log.e("errrrrrrrrrror",m.getMrlsdate());
             Log.e("waaaaaaaaaaaaaaatch",m.getWcdatetime());
             try {
                 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                 Date watchDate = sf.parse(m.getWcdatetime());
                 Date releaseDate = sf.parse(m.getMrlsdate());

                 SimpleDateFormat ss = new SimpleDateFormat("MM/dd/yyyy");
                 String watchS = ss.format(watchDate);
                 String releaseS = ss.format(releaseDate);

            Log.e("cNoooooo",m.getcNo().toString());

            mNmae.setText("Name: " + m.getmName());
            mRelease.setText("Release Time: " + releaseS );
            mWatchDate.setText("Watch Time: " + watchS);
             }catch (Exception e){
                 e.printStackTrace();
             }
            cinemaPostcode.setText("Postcode " + m.getcNo().getLocation());
            mComment.setText(m.getComment());
            BigDecimal kkkk = m.getrScore();
            score = kkkk.floatValue();
            rating.setRating(score);
            poster=m.getImage();
            Log.e("name---------", m.getmName());
            Log.e("score1-----", String.valueOf(score));
            //Log.e("Poster------",poster);
            new DownloadImage(imageView).execute(poster);
            final String mmId=m.getOmdbid();
            mIdList.add(mmId);
            Log.e("MmmmmmmmmmmId-",mmId);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                        try {
                            Intent it = new Intent(getActivity(), MovieView.class);
                            it.putExtra("id",mmId);
                            startActivity(it);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

            });
            linearLayout.addView(v);

        }


    }

    public class getScoreFromApid extends  AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return connection.searchMovieDetails(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                String publlicReview = jsonObject.getString("imdbRating");
                publicScore.add(publlicReview);

            }catch (Exception E){
                E.printStackTrace();
            }
        }
    }


}



