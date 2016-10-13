package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.LeaderboardEntry;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.User;

import java.util.ArrayList;

import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.UserFunctions.USERNAME;
import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.UserFunctions.USERS_TABLE;

/**
 * Class to handle all puzzle DB functions.
 * @author John D. Miller.
 * @version 1.0.1
 * @since 09/10/2016
 */
public class LeaderboardFunctions {

    // Table name
    static final String LEADERBOARDS_TABLE = "leaderboards";

    // Column names
    private static final String LEADERBOARD_ID = "leaderboard_id";
    private static final String USER_ID = "user_id";
    private static final String LEVEL_NUM = "level_num";
    private static final String SCORE = "score";
    private static final String MOVES = "moves";
    private static final String TIME = "time";


    /**
     * Builds the puzzles table create statement.
     * @return the SQL statement.
     */
    static String createTable(){
        return "CREATE TABLE " + LEADERBOARDS_TABLE + "("
                + LEADERBOARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " INTEGER,"
                + LEVEL_NUM + " TEXT,"
                + SCORE + " INTEGER,"
                + MOVES + " INTEGER,"
                + TIME + " TEXT,"
                + " FOREIGN KEY(" + USER_ID + ") REFERENCES " + USERS_TABLE + "(" + USER_ID + "))";
    }

    /**
     * Inserts an entry into the leaderboards table.
     * @param user the user.
     * @param entry the entry.
     */
    public void insert(User user, LeaderboardEntry entry) {
        if (highScore(user, entry)){
            update(user, entry);
        }else if (isEmpty(user, entry)){
            SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_ID, user.getUserId());
            values.put(LEVEL_NUM, entry.getLevel_num());
            values.put(SCORE, entry.getScore());
            values.put(MOVES, entry.getMoves());
            values.put(TIME, entry.getTime());

            // Inserting Row
            db.insert(LEADERBOARDS_TABLE, null, values);
            DatabaseManager.getDatabaseManager().closeDatabase();
        }
    }

    /**
     * Updates the high scores in the database.
     * @param user the user.
     * @param entry the new high score.
     */
    private void update(User user, LeaderboardEntry entry){

        // Database
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();

        // Values
        ContentValues values = new ContentValues();
        values.put(SCORE, entry.getScore());
        values.put(MOVES, entry.getMoves());
        values.put(TIME, entry.getTime());

        // Where
        String where = USER_ID + " = ? AND " + LEVEL_NUM + " = ?";
        String[] cols = {String.valueOf(user.getUserId()), String.valueOf(entry.getLevel_num())};

        // Inserting Row
        db.update(LEADERBOARDS_TABLE, values, where, cols);
        DatabaseManager.getDatabaseManager().closeDatabase();
    }

    /**
     * Gets the puzzles that have been added to the DB.
     * @return the puzzle list.
     */
    public LeaderboardEntry getLeaderboards(User user){

        LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LEADERBOARDS_TABLE + " WHERE " + USER_ID + " = ?", new String[]{String.valueOf(user.getUserId())});

        while (cursor.moveToNext()){

            leaderboardEntry.setLeaderboard_id(cursor.getInt(cursor.getColumnIndex(LEADERBOARD_ID)));
            leaderboardEntry.setLevel_num(cursor.getInt(cursor.getColumnIndex(LEVEL_NUM)));
            leaderboardEntry.setScore(cursor.getInt(cursor.getColumnIndex(SCORE)));
            leaderboardEntry.setMoves(cursor.getInt(cursor.getColumnIndex(MOVES)));
            leaderboardEntry.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
        }
        cursor.close();
        DatabaseManager.getDatabaseManager().closeDatabase();
        return leaderboardEntry;
    }

    /**
     * Gets the levels the user has unlocked.
     * @param user the user.
     * @return the list of levels.
     */
    public ArrayList<Integer> getOpenLevels(User user) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LEADERBOARDS_TABLE + " WHERE " + USER_ID + "=?", new String[]{String.valueOf(user.getUserId())});
        while (cursor.moveToNext()) {
            arrayList.add(cursor.getColumnIndex(LEVEL_NUM));
        }
        DatabaseManager.getDatabaseManager().closeDatabase();
        cursor.close();
        return arrayList;
    }

    /**
     * Checks if the
     * @param user
     * @param leaderboardEntry
     * @return
     */
    private boolean highScore(User user, LeaderboardEntry leaderboardEntry) {
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LEADERBOARDS_TABLE + " WHERE " + USER_ID + " = ? AND " + LEVEL_NUM + " = ?", new String[]{String.valueOf(user.getUserId()), String.valueOf(leaderboardEntry.getLevel_num())});
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(SCORE)) < leaderboardEntry.getScore()){
                return true;
            }
        }
        DatabaseManager.getDatabaseManager().closeDatabase();
        cursor.close();
        return false;
    }

    public ArrayList<LeaderboardEntry> getLeaderboards(){
        ArrayList<LeaderboardEntry> arrayList = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + LEADERBOARDS_TABLE + " ORDER BY " + SCORE + " DESC, " + MOVES + " DESC, " + TIME + " DESC", null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + LEADERBOARDS_TABLE + " INNER JOIN " + USERS_TABLE  + " ON "  + USERS_TABLE+ "." + USER_ID + " = " + LEADERBOARDS_TABLE + "." + USER_ID
                + " ORDER BY " + LEADERBOARDS_TABLE + "." + LEVEL_NUM + " DESC, " +  LEADERBOARDS_TABLE + "." + SCORE + " DESC, " +  LEADERBOARDS_TABLE + "." + MOVES + " DESC, " +  LEADERBOARDS_TABLE + "." + TIME + " DESC", null);
        while (cursor.moveToNext()) {
            LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
            leaderboardEntry.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
            leaderboardEntry.setLevel_num(cursor.getInt(cursor.getColumnIndex(LEVEL_NUM)));
            leaderboardEntry.setScore(cursor.getInt(cursor.getColumnIndex(SCORE)));
            leaderboardEntry.setMoves(cursor.getInt(cursor.getColumnIndex(MOVES)));
            leaderboardEntry.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
            arrayList.add(leaderboardEntry);
        }
        DatabaseManager.getDatabaseManager().closeDatabase();
        cursor.close();
        return arrayList;
    }

    public boolean isEmpty(User user, LeaderboardEntry leaderboardEntry){
        ArrayList<LeaderboardEntry> arrayList = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getDatabaseManager().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LEADERBOARDS_TABLE + " WHERE " + USER_ID + " = ? AND " + LEVEL_NUM + " = ?", new String[]{String.valueOf(user.getUserId()), String.valueOf(leaderboardEntry.getLevel_num())});
        if (!cursor.moveToFirst()){
            DatabaseManager.getDatabaseManager().closeDatabase();
            cursor.close();
            return true;
        }
        DatabaseManager.getDatabaseManager().closeDatabase();
        cursor.close();
        return false;
    }
}