package com.ist_311.sliding_puzzle_miller_huynh_white.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Puzzle{

    // Instance Vars
    private int puzzleId;
    private String puzzlePath;

    /**
     * Gets the puzzle's id.
     * @return the id.
     */
    public int getPuzzleId() {
        return puzzleId;
    }

    /**
     * Sets the puzzle's id.
     * @param puzzleId the id.
     */
    public void setPuzzleId(int puzzleId) {
        this.puzzleId = puzzleId;
    }

    /**
     * Gets the puzzle's path.
     * @return the path.
     */
    public String getPuzzlePath() {
        return puzzlePath;
    }

    /**
     * Sets the puzzle's path.
     * @param puzzlePath the puzzle path.
     */
    public void setPuzzlePath(String puzzlePath) {
        this.puzzlePath = puzzlePath;
    }

    /**
     * Decodes and returns a bitmap from filepath.
     * @return the decoded bitmap.
     */
    public Bitmap getPuzzle(){
        return BitmapFactory.decodeFile(puzzlePath);
    }
}