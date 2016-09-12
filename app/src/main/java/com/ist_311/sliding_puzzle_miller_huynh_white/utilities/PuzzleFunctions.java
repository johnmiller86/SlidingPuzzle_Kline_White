package com.ist_311.sliding_puzzle_miller_huynh_white.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ist_311.sliding_puzzle_miller_huynh_white.objects.Puzzle;
import com.ist_311.sliding_puzzle_miller_huynh_white.objects.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Class to handle all puzzle DB functions.
 * @author John D. Miller.
 * @version 1.0.1
 * @since 09/10/2016
 */
class PuzzleFunctions {

    // No-args constructor
    public PuzzleFunctions(){
        Puzzle puzzle = new Puzzle();
    }

    // Method to create puzzle table
    public static String createTable(){
        return "CREATE TABLE " + Puzzle.TABLE  + "("
                + Puzzle.PUZZLE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Puzzle.USER_ID + " INTEGER,"
                + Puzzle.PUZZLE + " BLOB, "
                + "FOREIGN KEY(" + Puzzle.USER_ID + ") REFERENCES " + User.TABLE + "(" + User.USER_ID + "))";
    }

    /**
     * Inserts a puzzle into the DB.
     * @param user the user.
     * @param puzzle the puzzle.
     */
    public void insert(User user, Puzzle puzzle) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Puzzle.USER_ID, user.getUserId());
        values.put(Puzzle.PUZZLE, getBlob(puzzle.getPuzzle()));

        // Inserting Row
        db.insert(Puzzle.TABLE, null, values);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        db.delete(Puzzle.TABLE,null,null);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    /**
     * Gets the puzzles that have been added to the DB.
     * @return the puzzle list.
     */
    public ArrayList<Puzzle> getPuzzleList(int userId){

        ArrayList<Puzzle> puzzleList = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Puzzle.TABLE + " WHERE " + Puzzle.USER_ID + "=?", new String[]{String.valueOf(userId)});

        while (cursor.moveToNext()){
            Puzzle puzzle = new Puzzle();

            // Setting ID
            puzzle.setPuzzleId(cursor.getInt(cursor.getColumnIndex(Puzzle.PUZZLE_ID)));

            // Getting Blob
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex(Puzzle.PUZZLE));

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
    public static byte[] getBlob(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}