package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static final String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private final SharedPreferences pref;

    private final Editor editor;

    // Shared preferences file name
    private static final String PREF_NAME = "SlidingPuzzle";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";

    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void setLoggedIn(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setUsername(String username){

        editor.putString(KEY_USERNAME, username);

        editor.commit();

        Log.d(TAG, "Username retrieved!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public String getUsername() { return pref.getString(KEY_USERNAME, ""); }
}