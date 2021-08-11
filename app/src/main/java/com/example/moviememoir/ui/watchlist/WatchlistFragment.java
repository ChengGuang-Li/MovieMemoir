package com.example.moviememoir.ui.watchlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviememoir.Database.Movie;
import com.example.moviememoir.Database.MovieDB;
import com.example.moviememoir.MovieView;
import com.example.moviememoir.R;

import java.util.List;


public class WatchlistFragment extends Fragment {
    private LinearLayout linearLayout;
    private TextView name;
    private TextView releaseDate;
    private TextView addTime;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_watchlist, container, false);
         linearLayout=root.findViewById(R.id.watchlist_contain);

         new getMovie().execute();


        return root;
    }

    public class getMovie extends AsyncTask<Void,Void, List<Movie> >{

        @Override
        protected List<Movie> doInBackground(Void... voids) {

            return  MovieDB.getInstance(getContext()).MovieDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            LayoutInflater view = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           for (final Movie mm: movies){
               final  View v = view.inflate(R.layout.watchlist_content,null);
               name=v.findViewById(R.id.watchlist_name);
               releaseDate=v.findViewById(R.id.watchlist_release);
               addTime=v.findViewById(R.id.watchlist_addTime);

               //add movie details to exteview
               name.setText(mm.getMovieName());
               releaseDate.setText(mm.getReleaseDate());
               addTime.setText(mm.getAddDateTime());
               v.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(final View v) {
                       new AlertDialog.Builder(getContext()).setTitle(mm.getMovieName()).setMessage("Please select the option: (Delete Or View)")
                                                             .setPositiveButton("View", new DialogInterface.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     Intent it = new Intent(getActivity(), MovieView.class);
                                                                     it.putExtra("id",mm.getMovieId());
                                                                     startActivity(it);

                                                                 }
                                                             })
                                                             .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     new DeleteMovie(v).execute(mm);

                                                                 }
                                                             })
                                                             .show();

                   }
               });

               linearLayout.addView(v);
           }
        }
    }
    public class DeleteMovie extends AsyncTask<Movie,Void,Boolean>{
        private View v;
        public DeleteMovie(View v){
            this.v=v;
        }


        @Override
        protected Boolean doInBackground(Movie... movies) {
            Boolean x = false;
            try{
                MovieDB.getInstance(getContext()).MovieDao().delete(movies[0]);
                 x = true;
            }catch (Exception e){
                e.printStackTrace();
            }
            return x;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean==true){

                linearLayout.removeView(v);
                Toast.makeText(getContext(),"Delete successfully",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"Delete Failed",Toast.LENGTH_LONG).show();
            }
        }
    }
}
