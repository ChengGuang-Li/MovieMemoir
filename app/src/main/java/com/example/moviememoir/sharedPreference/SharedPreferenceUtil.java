package com.example.moviememoir.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil sharedPreferenceUtil;
    private static SharedPreferences personSP;
    private final static String KEY = "sp_person";
    private SharedPreferences.Editor editor;

    private SharedPreferenceUtil( Context context )
    {
        personSP = context.getSharedPreferences( KEY , Context.MODE_PRIVATE );
    }

    public static SharedPreferenceUtil getInstance( Context context )
    {
        if ( sharedPreferenceUtil == null )
        {
            sharedPreferenceUtil = new SharedPreferenceUtil( context );
        }
        return sharedPreferenceUtil;
    }

    /**
     * setting String
     *
     * @param key
     * @param value
     */
    public void putString(String key,String value )
    {
        editor = personSP.edit( );
        editor.putString( key , value );
        editor.apply();
    }

    /**
     * setting int
     *
     * @param key
     * @param value
     */
    public void putInt( String key , int value )
    {
        editor = personSP.edit( );
        editor.putInt( key , value );
        editor.commit( );
    }

    /**
     *  getting String value
     */
    public String getString(String st){

      return personSP.getString(st,"");
    }

    /**
     * getting int value
     */
    public int getInt(String st){
        return  personSP.getInt(st,0);
    }
}
