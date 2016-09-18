package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ist_311.sliding_puzzle_miller_huynh_white.models.User;

public class UserFunctions {

    // Table name
    public static final String USERS_TABLE = "users";

    // Column names
    public static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    /**
     * Builds the users table create statement.
     * @return the SQL statement.
     */
    public static String createTable() {
        return "CREATE TABLE " + USERS_TABLE + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT, "
                + PASSWORD + " TEXT)";
    }

    /**
     * Inserts a user into the database.
     * @param user the user.
     */
    public void insertUser(User user) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(USERS_TABLE, null, values);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    /**
     * Deletes a user from the database.
     */
    public void deleteUser() {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        db.delete(USERS_TABLE, null, null);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    /**
     * Gets the user's id from the database.
     * @param user the user.
     * @return the id.
     */
    public int getUserId(User user) {

        int userId = -1;
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT " + USER_ID + " FROM " + USERS_TABLE + " WHERE " + USERNAME + "=?", new String[]{user.getUsername()});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(USER_ID));
        }
        cursor.close();
        return userId;
    }

    /**
     * Checks if the user exists in the database.
     * @param user the user.
     * @return true or false/
     */
    public boolean userExists(User user) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + "=?", new String[]{user.getUsername()});
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        assert cursor != null;
        cursor.close();
        return false;
    }

    /**
     * Gets the user from the database.
     * @return the user.
     */
    public User getUser(String username){

        User user = new User();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + "=?", new String[]{username});

        while (cursor.moveToNext()){

            user.setUserId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
            user.setPassword("Wouldn't you like to know?");
        }
        cursor.close();
        return user;
    }

    /**
     * Checks the password against the database for correctness.
     * @param user the user.
     * @return true or false.
     */
    public boolean passwordCorrect(User user) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + "=? AND " + PASSWORD + "=?", new String[]{user.getUsername(), user.getPassword()});
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        assert cursor != null;
        cursor.close();
        return false;
    }
}
