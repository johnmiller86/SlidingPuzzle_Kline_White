package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ist_311.sliding_puzzle_miller_huynh_white.models.Puzzle;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.User;

public class DBHelper extends SQLiteOpenHelper{

    // Defining the database and tables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Sliding_Puzzle";
    private static final String TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserFunctions.createTable());
        db.execSQL(PuzzleFunctions.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Puzzle.TABLE);
        onCreate(db);
    }
}
