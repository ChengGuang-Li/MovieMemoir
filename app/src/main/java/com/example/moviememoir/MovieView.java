package com.example.moviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviememoir.Database.Movie;
import com.example.moviememoir.Database.MovieDB;
import com.example.moviememoir.Tools.DownloadImage;
import com.example.moviememoir.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieView extends AppCompatActivity {
    private  TextView  movie_name;
    private  TextView  movie_releaseDate;
    private  TextView  movie_genre;
    private  TextView  movie_cast;
    private  TextView  movie_country;
    private  TextView  movie_directory;
    private  TextView  movie_plot;
    private  ImageView imageView;
    private Button watchlist;
    private  Button memoir;
    private RatingBar ratingBar;
    NetworkConnection connection = new NetworkConnection();
    Movie movie;
    String poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        Intent it = getIntent();
        String id = it.getStringExtra("id");
        movie_name = findViewById(R.id.mView_name);
        movie_releaseDate = findViewById(R.id.mView_release);
        movie_genre=findViewById(R.id.mView_genre);
        movie_cast=findViewById(R.id.mView_cast);
        movie_country=findViewById(R.id.mView_country);
        movie_directory=findViewById(R.id.mView_directory);
        movie_plot=findViewById(R.id.mView_plot);
        imageView=findViewById(R.id.view_imag);
        watchlist=findViewById(R.id.mView_watchlist);
        memoir= findViewById(R.id.mView_memoir);
        ratingBar=findViewById(R.id.mView_star);
        movie = new Movie(id);
        new retriveMovie().execute(id);
        // watchlist button
        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              new addMovie().execute(movie);

            }
        });

        //memoir button
        memoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent it = new Intent(MovieView.this,AddMemoir.class);
              it.putExtra("release",movie.getReleaseDate());
              Log.e("MemoirVierelease---",movie.getReleaseDate());

              it.putExtra("MovieName",movie.getMovieName());

              it.putExtra("MovieId",movie.getMovieId());
              Log.e("MemoirVie---Mid",movie.getMovieId());

              it.putExtra("Poster",poster);

              startActivity(it);

            }
        });

    }

    public class retriveMovie extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String reult="";
            try {
                reult = connection.searchMovieDetails(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return reult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsObject = new JSONObject(s);
                if(jsObject.getString("Response").equals("False")){
                    movie_name.setError("cannot find this movie details");
                }else{
                    movie_name.setText("Movie Name:"+jsObject.getString("Title"));
                    movie_releaseDate.setText("Relelase Date:"+jsObject.getString("Released"));
                    movie_genre.setText("Genre :"+jsObject.getString("Genre"));
                    movie_cast.setText(jsObject.getString("Actors"));
                    movie_country.setText("Country :"+jsObject.getString("Country"));
                    movie_directory.setText("Directory :"+jsObject.getString("Director"));
                    movie_plot.setText(jsObject.getString("Plot"));
                    Log.e("Poster",jsObject.getString("Poster"));
                    poster=jsObject.getString("Poster");
                    new DownloadImage(imageView).execute(jsObject.getString("Poster"));
                    movie.setMovieName(jsObject.getString("Title"));
                    movie.setReleaseDate(jsObject.getString("Released"));
                    Date date = new Date();
                    SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String x = sf.format(date);
                    movie.setAddDateTime(x);
                    String scoreString = jsObject.getString("imdbRating");
                    if (!scoreString.equals("N/A")) {
                        Double k = Double.valueOf(scoreString);
                        float score = k.floatValue() / 2;
                        ratingBar.setRating(score);
                    }else {
                        Double ww = 1.0;
                        float www = ww.floatValue();
                        ratingBar.setRating(www);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public class addMovie extends AsyncTask<Movie,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Movie... movies) {
            Boolean xx = false;
            try{
                MovieDB.getInstance(getApplicationContext()).MovieDao().insertAll(movies[0]);
                xx = true;
            }catch (Exception e){
             e.printStackTrace();
            }
            return xx;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean == false){

                Toast.makeText(getBaseContext(),"Insert Failed",Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText(getBaseContext(),"Insert Successfully",Toast.LENGTH_LONG).show();
            }
        }
    }
}
