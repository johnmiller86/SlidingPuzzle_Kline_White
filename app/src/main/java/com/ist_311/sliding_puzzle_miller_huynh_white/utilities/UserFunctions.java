package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ist_311.sliding_puzzle_miller_huynh_white.objects.User;

public class UserFunctions {

    public UserFunctions() {
        User user = new User();
    }


    public static String createTable() {
        return "CREATE TABLE " + User.TABLE + "("
                + User.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + User.USERNAME + " TEXT, "
                + User.PASSWORD + " TEXT)";
    }


    public void insert(User user) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        ContentValues values = new ContentValues();
        values.put(User.USERNAME, user.getUsername());
        values.put(User.PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(User.TABLE, null, values);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    public boolean userExists(User user) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + User.TABLE + " WHERE " + User.USERNAME + "=?", new String[]{user.getUsername()});
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        assert cursor != null;
        cursor.close();
        return false;
    }

    public boolean passwordCorrect(User user) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + User.TABLE + " WHERE " + User.USERNAME + "=? AND " + User.PASSWORD + "=?", new String[]{user.getUsername(), user.getPassword()});
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        assert cursor != null;
        cursor.close();
        return false;
    }

    public int getUserId(User user) {

        int userId = -1;
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT " + User.USER_ID + " FROM " + User.TABLE + " WHERE " + User.USERNAME + "=?", new String[]{user.getUsername()});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(User.USER_ID));
        }
        cursor.close();
        return userId;
    }


    public void delete() {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        db.delete(User.TABLE, null, null);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }
}
