package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {
    private Integer openInstances = 0;

    private static DatabaseManager databaseManager;
    private static SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
            sqLiteOpenHelper = helper;
        }
    }

    public static synchronized DatabaseManager getDatabaseManager() {
        if (databaseManager == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() + " is not initialized...");
        }
        return databaseManager;
    }

    public synchronized SQLiteDatabase openDatabase() {
        openInstances++;
        if(openInstances == 1) {
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    public synchronized void closeDatabase() {
        openInstances--;
        if(openInstances == 0) {
            sqLiteDatabase.close();
        }
    }
}