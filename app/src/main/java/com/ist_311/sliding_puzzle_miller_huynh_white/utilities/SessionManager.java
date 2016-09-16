package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    // LogCat tag
    private static final String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private final SharedPreferences pref;

    private final Editor editor;

    // Shared preferences file name
    private static final String PREF_NAME = "SlidingPuzzle";

    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String USERNAME = "username";
    private static final String ROWS = "rows";
    private static final String COLS = "cols";

    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
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

    public void setCols (int cols){

        editor.putInt(COLS, cols);
        editor.commit();
    }

    public void  setRows(int rows){

        editor.putInt(ROWS, rows);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }
    public String getUsername() { return pref.getString(USERNAME, ""); }
    public int getCols(){return pref.getInt(COLS, 3);}
    public int getRows(){return pref.getInt(ROWS, 3);}
}