package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

    // Shared Preferences
    private final SharedPreferences pref;

    private final Editor editor;

    // Shared preferences file name
    private static final String PREFS = "prefs";
    private static final String USERNAME = "username";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREFS, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void setLoggedIn(boolean isLoggedIn) {

        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public void setUsername(String username){

        editor.putString(USERNAME, username);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }
    public String getUsername() { return pref.getString(USERNAME, ""); }
}