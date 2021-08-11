package com.example.moviememoir.networkconnection;

import android.util.Log;

import com.example.moviememoir.model.Credential;
import com.example.moviememoir.model.Memoir;
import com.example.moviememoir.model.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentNavigableMap;

public class NetworkConnection {
    private static final String BASE_URL ="http://192.168.56.1:8080/Movie/webresources/";

    //for sign in user eamil + password
    public static String  getInfo(String username,String password){
        final String methodPath = "movie.credential/findByUserNameAndPassword/"+ username + "/" + getMD5(password);
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        //String path = methodPath + email + "/" + pass;
        String textResult = "";
        //Making Http request
         try{
            url = new URL(BASE_URL + methodPath );
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.next();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return  textResult;
    }

    // MD5 encryption
    public static String getMD5(String str) {
        try {
            // Generate an MD5 encrypted calculation summary
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Calculate md5 function
            md.update(str.getBytes());
            // digest () finally determines to return the md5 hash value, and the return value is 8 as a string.
            // Because the md5 hash value is a 16-bit hex value, which is actually an 8-bit character
            // The BigInteger function converts an 8-bit string into a 16-bit hex value, which is represented by a string;
            // the hash value in the form of a string is obtained
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }

    //Post Credential and person
    public static String addCredential(Credential credential){
        URL url = null;
        HttpURLConnection conn = null;
        String result = "fail";
        final String methodPath ="movie.credential/postPersonCredential";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSXXX").create();
            String stringCredential = gson.toJson(credential);
            Log.e("Check Credential",stringCredential);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connectionmethod to POST
            conn.setRequestMethod("POST");
            //set the output to true;
            conn.setDoOutput(true);
            //set the length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCredential.getBytes().length);
            // add HTTP headers
            conn.setRequestProperty("Content-Type","application/json");
            //send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCredential);
            out.close();
            if(conn.getResponseCode()== 204){
                result="200Ok";
            }else{
                result="fail";
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    // check does not exist in the backend database
    public static String  checkEmail(String username){
        final String methodPath = "movie.credential/findByUsername/"+ username ;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making Http request
        try{
            url = new URL(BASE_URL + methodPath );
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.next();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return  textResult;
    }
     //GET method, get method from server
     public static String  getRecentTop5Movie(Integer input){
         final String methodPath = "movie.memoir/task4_f_findRecentYearTop5MovieNameAndRLYearAndScore/"+ input;
         //initialise
         URL url = null;
         HttpURLConnection conn = null;
         String textResult = "";
         //Making Http request
         try{
             url = new URL(BASE_URL + methodPath );
             //open the connection
             conn = (HttpURLConnection) url.openConnection();
             //set the timeout
             conn.setReadTimeout(10000);
             conn.setConnectTimeout(15000);
             //set the connection method to GET
             conn.setRequestMethod("GET");
             //add http headers to set your response type to json
             conn.setRequestProperty("Content-Type", "application/json");
             conn.setRequestProperty("Accept", "application/json");
             //Read the response
             Scanner inStream = new Scanner(conn.getInputStream());
             //read the input steream and store it as string
             while (inStream.hasNextLine()) {
                 textResult += inStream.next();
             }
         }catch(Exception e) {
             e.printStackTrace();
         }
         return  textResult;
     }

     //searc Movie
     public static String  searchMovie(String search){
         final String methodPath = "http://www.omdbapi.com/?apikey=6dc7686d&s=";
         String  searchPath = methodPath + search;
         //initialise
         URL url = null;
         HttpURLConnection conn = null;
         String textResult = "";
         //Making Http request
         try{
             url = new URL(searchPath );
             //open the connection
             conn = (HttpURLConnection) url.openConnection();
             //set the timeout
             conn.setReadTimeout(10000);
             conn.setConnectTimeout(15000);
             //set the connection method to GET
             conn.setRequestMethod("GET");
             //add http headers to set your response type to json
             conn.setRequestProperty("Content-Type", "application/json");
             conn.setRequestProperty("Accept", "application/json");
             //Read the response
             Scanner inStream = new Scanner(conn.getInputStream());
             //read the input steream and store it as string
             while (inStream.hasNextLine()) {
                 textResult += inStream.next();
             }
         }catch(Exception e) {
             e.printStackTrace();
         }
         return  textResult;
     }

     //movie detials
     public static String  searchMovieDetails(String search){
         final String methodPath = "http://www.omdbapi.com/?apikey=6dc7686d&i=";
         String  searchPath = methodPath + search;
         //initialise
         URL url = null;
         HttpURLConnection conn = null;
         String textResult = "";
         //Making Http request
         try{
             url = new URL(searchPath );
             //open the connection
             conn = (HttpURLConnection) url.openConnection();
             //set the timeout
             conn.setReadTimeout(10000);
             conn.setConnectTimeout(15000);
             //set the connection method to GET
             conn.setRequestMethod("GET");
             //add http headers to set your response type to json
             conn.setRequestProperty("Content-Type", "application/json");
             conn.setRequestProperty("Accept", "application/json");
             //Read the response
             Scanner inStream = new Scanner(conn.getInputStream());
             //read the input steream and store it as string
             while (inStream.hasNextLine()) {
                 textResult += inStream.nextLine();
             }
         }catch(Exception e) {
             e.printStackTrace();
         }
         return  textResult;
     }

     //get results from server-side database
     public static String  getResults(String entity,String method){
         final String methodPath = entity+method;
         Log.e("PATH-------",BASE_URL+methodPath);
         //initialise
         URL url = null;
         HttpURLConnection conn = null;
         String textResult = "";
         //Making Http request
         try{
             url = new URL(BASE_URL + methodPath );
             //open the connection
             conn = (HttpURLConnection) url.openConnection();
             //set the timeout
             conn.setReadTimeout(10000);
             conn.setConnectTimeout(15000);
             //set the connection method to GET
             conn.setRequestMethod("GET");
             //add http headers to set your response type to json
             conn.setRequestProperty("Content-Type", "application/json");
             conn.setRequestProperty("Accept", "application/json");
             //Read the response
             Scanner inStream = new Scanner(conn.getInputStream());
             //read the input steream and store it as string
             while (inStream.hasNextLine()) {
                 textResult += inStream.nextLine();
             }
         }catch(Exception e) {
             e.printStackTrace();
         }
         return  textResult;
     }

     //Post Memoir and Cinema
     //Post DATA
     public static String addMemoir(Memoir memoir){
         URL url = null;
         HttpURLConnection conn = null;
         String result = "fail";
         final String methodPath ="movie.memoir/postMemoirCinema";
         try {
             Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSSXXX").create();
             String stringMemoir = gson.toJson(memoir);
             Log.e("Check memoir",stringMemoir);
             url = new URL(BASE_URL + methodPath);
             //open the connection
             conn = (HttpURLConnection)url.openConnection();
             //set the timeout
             conn.setReadTimeout(10000);
             conn.setConnectTimeout(15000);
             //set the connectionmethod to POST
             conn.setRequestMethod("POST");
             //set the output to true;
             conn.setDoOutput(true);
             //set the length of the data you want to send
             conn.setFixedLengthStreamingMode(stringMemoir .getBytes().length);
             // add HTTP headers
             conn.setRequestProperty("Content-Type","application/json");
             //send the POST out
             PrintWriter out = new PrintWriter(conn.getOutputStream());
             out.print(stringMemoir);
             out.close();
             if(conn.getResponseCode()== 204){
                 result="200Ok";
             }else{
                 result="fail";
             }
         }catch (Exception e)
         {
             e.printStackTrace();
         }

         return result;
     }

     //

}
