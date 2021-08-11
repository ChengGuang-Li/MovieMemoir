package com.example.moviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.moviememoir.Tools.DownloadImage;
import com.example.moviememoir.model.Cinema;
import com.example.moviememoir.model.Memoir;
import com.example.moviememoir.model.Person;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.example.moviememoir.sharedPreference.SharedPreferenceUtil;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMemoir extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private String date;
    private Button chooseDate;
    private Context context;
    private String poster;
    private String release;
    private String movieName;
   // private Calendar date1;

    private ImageView imageView;
    private TextView  nameM;
    private TextView  releaseM;
    private TextView  commentM;
    private List<String> storeCName;
    private List<String> storeClocation;
    private Spinner addMemoir_CinemaName;
    private Spinner addMemoir_CinemaPostcode;
    private TextView showCname;
    private EditText editName;
    private EditText editLocation;
    private  Button submit;
    private RatingBar rate;
    private int cNo;
    private  int maxCno;
    private String imdbId;
    Person person =new Person();
    Memoir memoir = new Memoir();
    ArrayList<Cinema> cinemaList = new  ArrayList<Cinema> ();
    NetworkConnection connection = new NetworkConnection();
    Calendar date1 = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memoir);
        chooseDate = findViewById(R.id.addMemoir_pickDate);
        chooseDate.setOnClickListener(this);
        Intent it = getIntent();
        poster=it.getStringExtra("Poster");
        release=it.getStringExtra("release");
        movieName=it.getStringExtra("MovieName");
        imdbId=it.getStringExtra("MovieId");

        storeCName = new ArrayList<String>();
        storeClocation= new ArrayList<String>();
        editName=findViewById(R.id.showCname);
        editLocation=findViewById(R.id.showClocation);
        imageView=findViewById(R.id.addMemoir_imag);
        commentM=findViewById(R.id.Edit);
        nameM=findViewById(R.id.addMemoir_name);
        releaseM=findViewById(R.id.addMemoir_release);
        showCname=findViewById(R.id.showCname);
        rate=findViewById(R.id.addMemoir_star);
        submit=findViewById(R.id.addMemoir_submit);
        addMemoir_CinemaName=findViewById(R.id.addMemoir_CinemaName);
        final Button nameSubmit=findViewById(R.id.nameSubmit);
        final Button locationSubmit=findViewById(R.id.locationSubmit);
         new DownloadImage(imageView).execute(poster);
         nameM.setText("Movie Name: "+movieName);
         releaseM.setText("Release Date: "+release);
         new getCinema().execute("movie.cinema","");
        addMemoir_CinemaPostcode=findViewById(R.id.addMemoir_CinemaPostcode);
        //Ada cinema name list into spinner
         final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,storeCName);
        addMemoir_CinemaName.setAdapter(adapter);

        addMemoir_CinemaName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectName  = (String)addMemoir_CinemaName.getSelectedItem();
                if (selectName!=null){
                    editName.setText(selectName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         nameSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String xxxx = editName.getText().toString().trim();
                 adapter.add(xxxx);
                 adapter.notifyDataSetChanged();
                 addMemoir_CinemaName.setSelection(adapter.getPosition(xxxx));
             }
         });



        //Ada cinema location list into spinner
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,storeClocation);
        addMemoir_CinemaPostcode.setAdapter(adapter1);
        addMemoir_CinemaPostcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectPostcode = (String)addMemoir_CinemaPostcode.getSelectedItem();
                if(selectPostcode!=null){
                    editLocation.setText(selectPostcode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locationSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yyyy = editLocation.getText().toString().trim();
                adapter1.add(yyyy);
                adapter.notifyDataSetChanged();
                addMemoir_CinemaPostcode.setSelection(adapter1.getPosition(yyyy));
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                // get Cinemal
                getCno();
                float score = rate.getRating();
                Log.e("Score",String.valueOf(score));
                Cinema sroreC = new Cinema(maxCno,editName.getText().toString().trim(),Long.parseLong(editLocation.getText().toString().trim()));
                Log.e("Cinema-----",sroreC.getcName());
                getPerson();
                Log.e("Person FirstN------",person.getFirstname());
                 //mNo's  value does not affect insertion,
                // beause  the maximum value is always inserted in the server
                memoir.setmNo(1);
                memoir.setmName(movieName);
                SimpleDateFormat sk = new SimpleDateFormat("d MMM yyyy");
                Date srr = sk.parse(release);
                SimpleDateFormat sgg = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                String syy = sgg.format(srr);
                Log.e("syyyyy release date",syy);
                memoir.setMrlsdate(syy);
                //memoir.setWcdatetime();

                    String xxx = String.valueOf(String.valueOf(date1.getTime()));
                    SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    Date datee = sf.parse(xxx);
                    SimpleDateFormat cc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                    String erre = cc.format(datee);
                    Log.e( "RRRRR TIME : ",erre);
                    memoir.setWcdatetime(erre);
                 memoir.setComment(commentM.getText().toString().trim());
                 Double scoreDD = Double.valueOf(score);
                 memoir.setrScore(new BigDecimal(scoreDD));
                 Log.e("memoir_Score",String.valueOf(memoir.getrScore()));
                 memoir.setcNo(sroreC );
                 Log.e("'setCCCCCCC---success",memoir.getcNo().getcName());
                 memoir.setpId(person);
                 Log.e("Set person-----success",memoir.getpId().getFirstname());
                 memoir.setOmdbid(imdbId);
                 memoir.setImage(poster);
                 Log.e("Set poster suc----",memoir.getImage());
                 new postMemoir().execute(memoir);
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });


    }

    public class postMemoir extends AsyncTask<Memoir,Void,String>{
        @Override
        protected String doInBackground(Memoir... memoirs) {

            return connection.addMemoir(memoirs[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("200Ok")){
                Toast.makeText(AddMemoir.this,"All information has been added to the server ",Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(AddMemoir.this,"Failed to add information to the server",Toast.LENGTH_LONG).show();
            }

        }
    }


    public void getCno(){
        try{
            for(int i=0;i<cinemaList.size();i++){
                long location = cinemaList.get(i).getLocation();
                long location1=Long.parseLong(editLocation.getText().toString().trim());
                String na1= cinemaList.get(i).getcName().trim();
                String na2 =editName.getText().toString().trim();

                if( location == location1 && na1.equals(na2)){
                    maxCno = cinemaList.get(i).getcNo();
                    Log.e("Cinema index:",String.valueOf(maxCno));
                }
            }

        }catch (Exception e){
            int max = 0;
            for(Cinema ccc: cinemaList){
                if(ccc.getcNo() > max){
                    max = ccc.getcNo();
                }
            }
            maxCno = max+1;
            Log.e("maxCo----",String.valueOf(maxCno));
        }


    }


    public class getCinema extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... voids) {
            return connection.getResults(voids[0],voids[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // storeCName = new ArrayList<String>();
         //   storeClocation= new ArrayList<String>();
            try {
                JSONArray  jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String k = jsonObject.getString("CName");
                    Log.e("cinima name!!!!!",k);
                    storeCName.add(k);

                    String l=jsonObject.getString("location");
                    Log.e("location !!!!!",l);
                    storeClocation.add(l);
                    String ii=jsonObject.getString("CNo");
                     cinemaList.add(new Cinema(Integer.parseInt(ii),k,Long.parseLong(l)));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // date picker

    public void showDateTimePicker() {
        final Calendar currentDate1 = Calendar.getInstance();
        new DatePickerDialog(AddMemoir.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date1.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(AddMemoir.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date1.set(Calendar.MINUTE, minute);
                        Log.e( "choose time : ", String.valueOf(date1.getTime()));
                    }
                }, currentDate1.get(Calendar.HOUR_OF_DAY), currentDate1.get(Calendar.MINUTE), false).show();
            }
        }, currentDate1.get(Calendar.YEAR), currentDate1.get(Calendar.MONTH), currentDate1.get(Calendar.DATE)).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Log.e("Format------date",sf.format(date1));
        chooseDate.setText(sf.format(date1));

    }

    @Override
    public void onClick(View v) {
        showDateTimePicker();
        try {
            String xxx1 = String.valueOf(String.valueOf(date1.getTime()));
            SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date datee1 = sf.parse(xxx1);
            SimpleDateFormat cc = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String erre = cc.format(datee1);
            chooseDate.setText(erre);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getPerson(){
        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        person.setpId(sp.getInt("pId"));
        person.setFirstname(sp.getString("firstname"));
        person.setSurname(sp.getString("surname"));
        person.setGender(sp.getString("gender"));
        person.setDob(sp.getString("dob"));
        person.setsNumber(sp.getInt("sNumber"));
        person.setsName(sp.getString("sName"));
        person.setStateOR(sp.getString("stateOR"));
        person.setPostcode(sp.getInt("postcode"));

    }
}
