package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ist_311.sliding_puzzle_miller_huynh_white.models.Settings;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.User;

/**
 * Class to handle all puzzle DB functions.
 * @author John D. Miller.
 * @version 1.0.1
 * @since 09/10/2016
 */
public class SettingFunctions {

    // Table name
    public static final String SETTINGS_TABLE = "settings";

    // Column names
    private static final String SETTING_ID = "setting_id";
    private static final String USER_ID = "user_id";
    private static final String ROWS = "rows";
    private static final String COLUMNS = "columns";
    private static final String CURRENT_PUZZLE_ID = "current_puzzle_id";


    /**
     * Builds the settings table create statement.
     * @return the SQL statement.
     */
    public static String createTable(){
        return "CREATE TABLE " + SETTINGS_TABLE + "("
                + SETTING_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER,"
                + ROWS + " INTEGER, "
                + COLUMNS + " INTEGER, "
                + CURRENT_PUZZLE_ID + " INTEGER, "
                + "FOREIGN KEY(" + USER_ID + ") REFERENCES " + UserFunctions.USERS_TABLE + "(" + USER_ID + ") "
                + "FOREIGN KEY(" + CURRENT_PUZZLE_ID + ") REFERENCES " + PuzzleFunctions.PUZZLES_TABLE + "(" + PuzzleFunctions.PUZZLE_ID + "))";
    }

    /**
     * Inserts settings into the database.
     * @param user the user.
     * @param settings the settings.
     */
    public void insert(User user, Settings settings) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(ROWS, settings.getRows());
        values.put(COLUMNS, settings.getColumns());
        values.put(CURRENT_PUZZLE_ID, settings.getCurrentPuzzleId());

        // Inserting Row
        db.insert(SETTINGS_TABLE, null, values);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    public void update(User user, Settings settings){

        // Database
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();

        // Values
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(ROWS, settings.getRows());
        values.put(COLUMNS, settings.getColumns());
        values.put(CURRENT_PUZZLE_ID, settings.getCurrentPuzzleId());

        // Where
        String where = SETTING_ID + " = ?";
        String[] id = {String.valueOf(settings.getSettingId())};

        // Inserting Row
        db.update(SETTINGS_TABLE, values, where, id);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    /**
     * Gets the user's settings.
     * @return the settings.
     */
    public Settings getSettings(User user){

        Settings settings = new Settings();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SETTINGS_TABLE + " WHERE " + USER_ID + "=?", new String[]{String.valueOf(user.getUserId())});

        while (cursor.moveToNext()){

            settings.setSettingId(cursor.getInt(cursor.getColumnIndex(SETTING_ID)));
            settings.setColumns(cursor.getColumnIndex(COLUMNS));
            settings.setRows(cursor.getColumnIndex(ROWS));
            settings.setCurrentPuzzleId(cursor.getColumnIndex(CURRENT_PUZZLE_ID));
        }
        cursor.close();
        return settings;
    }
}