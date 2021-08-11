package com.example.moviememoir.ui.maps;

import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviememoir.R;
import com.example.moviememoir.model.Cinema;
import com.example.moviememoir.networkconnection.NetworkConnection;
import com.example.moviememoir.sharedPreference.SharedPreferenceUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.CancellationException;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Geocoder geocoder;
    SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(getContext());
    //Integer id = sp.getInt("pId");
    String address = sp.getInt("sNumber") + " " + sp.getString("sName");
    NetworkConnection connection = new NetworkConnection();
    private Cinema cinema;
    private LatLng latLng;
    Address local;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        geocoder = new Geocoder(root.getContext(), Locale.getDefault());
        smf.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        new getLocation().execute();
        new getCinema().execute("movie.cinema");
    }

    public class getLocation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //String address = "21 Toolambool Road Melbourne";
                local = geocoder.getFromLocationName(address,1).get(0);
                 if(geocoder.isPresent()){
                     Log.e("3333333333","success");
                 }
                if(!local.hasLatitude()){

                    Log.e("*********************-","cannot get the local");
                }
                //Log.e("local aaaaa",local.toString());

            } catch (Exception E) {
                E.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Log.e("address",address);
            //Log.e("latA",local.getLatitude()+"");
            //double latA = local.getLatitude();
            //double longA = local.getLongitude();
            try {
                latLng = new LatLng(local.getLatitude(), local.getLongitude());
                MarkerOptions home = new MarkerOptions();
                home.title("Home Address");
                home.position(latLng);
                home.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.addMarker(home);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public class getCinema extends  AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return connection.getResults(strings[0],"");

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            try{

                JSONArray jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        cinema = gson.fromJson(jsonArray.getJSONObject(i).toString(), Cinema.class);
                        Address location = geocoder.getFromLocationName(String.valueOf(cinema.getLocation()), 1).get(0);
                        Log.e("Location---",String.valueOf(cinema.getLocation()));
                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions cinemaAdd = new MarkerOptions();
                        cinemaAdd.title(cinema.getcName());
                        cinemaAdd.position(latLng);
                        cinemaAdd.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        mMap.addMarker(cinemaAdd);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
