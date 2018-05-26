package com.example.alcra.silverhawksapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alcra on 01/04/2018.
 */

public class UserPreferences {
    private final SharedPreferences preferences;
    private static final String LOGGED = "Logged";

    public UserPreferences(Context context){
        this.preferences = context.getSharedPreferences("USER", 0);
    }

    public void setUserLogged (boolean logged){
        preferences.edit().putBoolean(LOGGED, logged).apply();
    }

    public boolean getUserLogged (){
        return preferences.getBoolean(LOGGED, false);
    }
}
