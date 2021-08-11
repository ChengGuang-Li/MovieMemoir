package com.example.moviememoir.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.CalendarView;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String,Void, Bitmap> {
    ImageView imageView;
    public DownloadImage(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap mIcon11 = null;
        try{
            InputStream in = new java.net.URL(strings[0]).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);

        }catch (Exception e){
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
