package com.example.moviememoir.ui.Search;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.moviememoir.MovieView;
import com.example.moviememoir.R;
import com.example.moviememoir.Tools.DownloadImage;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieSearchFragment extends Fragment {
     NetworkConnection connection = new NetworkConnection();
    LinearLayout linearLayout;

    private TextInputLayout searchMovie;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);
         searchMovie =(TextInputLayout) root.findViewById(R.id.search);
        Button search = root.findViewById(R.id.search_button);
        linearLayout = root.findViewById(R.id.searchContain);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeAllViews();
                new SearchMovie().execute(searchMovie.getEditText().getText().toString().trim());
            }
        });

        return root;
    }

    public class SearchMovie extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return connection.searchMovie(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("search",s);
            LayoutInflater view = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try{
                JSONObject jsObject = new JSONObject(s);
                if( jsObject.getString("Response").equals("False")){
                    searchMovie.setError("Cannot find this Movie Name");
                }else{
                    searchMovie.setError(null);
                    JSONArray jsArray = jsObject.getJSONArray("Search");
                    for (int i=0;i<jsArray.length();i++){
                        final JSONObject result = jsArray.getJSONObject(i);
                        final  View v = view.inflate(R.layout.search_results,null);
                        ImageView imageView = v.findViewById(R.id.poster);
                        TextView movieName = v.findViewById(R.id.movie_title);
                        TextView movieRelease = v.findViewById(R.id.searchRelease);
                        movieName.setText(result.getString("Title"));
                        movieRelease.setText(result.getString("Year"));
                        // add image
                        new DownloadImage(imageView).execute(result.getString("Poster"));
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try{
                                    Intent itt = new Intent(getActivity(), MovieView.class);
                                    itt.putExtra("id",result.getString("imdbID"));
                                    Log.e("Tile---------",result.getString("Title"));
                                    Log.e("id----------",result.getString("imdbID"));
                                    startActivity(itt);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        linearLayout.addView(v);
                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
