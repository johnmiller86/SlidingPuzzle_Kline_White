package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.Puzzle;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.User;

/**
 * Class to handle all puzzle DB functions.
 * @author John D. Miller.
 * @version 1.0.1
 * @since 09/10/2016
 */
public class PuzzleFunctions {

    // Table name
    static final String PUZZLES_TABLE = "puzzles";

    // Column names
    private static final String PUZZLE_ID = "puzzle_id";
    private static final String USER_ID = "user_id";
    private static final String PUZZLE_PATH = "puzzle";


    /**
     * Builds the puzzles table create statement.
     * @return the SQL statement.
     */
    static String createTable(){
        return "CREATE TABLE " + PUZZLES_TABLE  + "("
                + PUZZLE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER,"
                + PUZZLE_PATH + " TEXT, "
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
        values.put(PUZZLE_PATH, puzzle.getPuzzlePath());

        // Inserting Row
        db.insert(PUZZLES_TABLE, null, values);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    /**
     * Updates the puzzle in the database.
     * @param user the user.
     * @param puzzle the new puzzle.
     */
    public void update(User user, Puzzle puzzle){

        // Database
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();

        // Values
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(PUZZLE_PATH, puzzle.getPuzzlePath());

        // Where
        String where = PUZZLE_ID + " = ?";
        String[] id = {String.valueOf(puzzle.getPuzzleId())};

        // Inserting Row
        db.update(PUZZLES_TABLE, values, where, id);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }


// --Commented out by Inspection START (9/20/2016 8:12 PM):
//    /**
//     * Gets the puzzles that have been added to the DB.
//     * @return the puzzle list.
//     */
//    public ArrayList<Puzzle> getPuzzleList(int userId){
//
//        ArrayList<Puzzle> puzzleList = new ArrayList<>();
//        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + PUZZLES_TABLE + " WHERE " + USER_ID + "=?", new String[]{String.valueOf(userId)});
//
//        // Populating ArrayList
//        while (cursor.moveToNext()){
//            Puzzle puzzle = new Puzzle();
//            puzzle.setPuzzleId(cursor.getInt(cursor.getColumnIndex(PUZZLE_ID)));
//            puzzle.setPuzzlePath(cursor.getString(cursor.getColumnIndex(PUZZLE_PATH)));
//            puzzleList.add(puzzle);
//        }
//        cursor.close();
//        DatabaseManager.getDatabaseManager().closeDatabase();
//        return puzzleList;
//    }
// --Commented out by Inspection STOP (9/20/2016 8:12 PM)

    /**
     * Gets the puzzles that have been added to the DB.
     * @return the puzzle list.
     */
    public Puzzle getPuzzle(User user){

        Puzzle puzzle = new Puzzle();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PUZZLES_TABLE + " WHERE " + USER_ID + " = ?", new String[]{String.valueOf(user.getUserId())});

        while (cursor.moveToNext()){

            puzzle.setPuzzleId(cursor.getInt(cursor.getColumnIndex(PUZZLE_ID)));
            puzzle.setPuzzlePath(cursor.getString(cursor.getColumnIndex(PUZZLE_PATH)));
        }
        cursor.close();
        DatabaseManager.getDatabaseManager().closeDatabase();
        return puzzle;
    }
}