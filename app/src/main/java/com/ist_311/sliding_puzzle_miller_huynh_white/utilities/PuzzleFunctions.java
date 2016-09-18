package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ist_311.sliding_puzzle_miller_huynh_white.models.Puzzle;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Class to handle all puzzle DB functions.
 * @author John D. Miller.
 * @version 1.0.1
 * @since 09/10/2016
 */
class PuzzleFunctions {

    // Table name
    public static final String PUZZLES_TABLE = "puzzles";

    // Column names
    public static final String PUZZLE_ID = "puzzle_id";
    private static final String USER_ID = "user_id";
    private static final String PUZZLE = "puzzle";


    /**
     * Builds the puzzles table create statement.
     * @return the SQL statement.
     */
    public static String createTable(){
        return "CREATE TABLE " + PUZZLES_TABLE  + "("
                + PUZZLE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER,"
                + PUZZLE + " BLOB, "
                + "FOREIGN KEY(" + USER_ID + ") REFERENCES " + UserFunctions.USERS_TABLE + "(" + USER_ID + "))";
    }

    /**
     * Inserts a puzzle into the database.
     * @param user the user.
     * @param puzzle the puzzle.
     */
    public void insert(User user, Puzzle puzzle) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(PUZZLE, getBlob(puzzle.getPuzzle()));

        // Inserting Row
        db.insert(PUZZLES_TABLE, null, values);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }


    /**
     * Gets the puzzles that have been added to the DB.
     * @return the puzzle list.
     */
    public ArrayList<Puzzle> getPuzzleList(int userId){

        ArrayList<Puzzle> puzzleList = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PUZZLES_TABLE + " WHERE " + USER_ID + "=?", new String[]{String.valueOf(userId)});

        while (cursor.moveToNext()){
            Puzzle puzzle = new Puzzle();

            // Setting ID
            puzzle.setPuzzleId(cursor.getInt(cursor.getColumnIndex(PUZZLE_ID)));

            // Getting Blob
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex(PUZZLE));

            // Adding Bitmap
            puzzle.setPuzzle(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

            // Adding to list
            puzzleList.add(puzzle);
        }
        cursor.close();
        return puzzleList;
    }

    /**
     * Converts a Bitmap to a Binary Large Object.
     * @param bitmap the Bitmap to convert.
     * @return a byte array representation of the Bitmap.
     */
    private static byte[] getBlob(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}