package com.ist_311.sliding_puzzle_miller_huynh_white.models;

import android.graphics.Bitmap;

public class Puzzle{

    // Instance Vars
    private int puzzleId;
    private Bitmap puzzle;

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
     * The puzzle bitmap.
     * @return the bitmap.
     */
    public Bitmap getPuzzle() {
        return puzzle;
    }

    /**
     * Sets the puzzle bitmap.
     * @param puzzle the puzzle bitmap.
     */
    public void setPuzzle(Bitmap puzzle) {
        this.puzzle = puzzle;
    }

}