package com.ist_311.sliding_puzzle_miller_huynh_white.models;

public class Settings {

    // Instance vars
    private int settingId, columns, rows, currentPuzzleId;

    /**
     * Gets the setting id.
     * @return the id.
     */
    public int getSettingId() {
        return settingId;
    }

    /**
     * Sets the setting id.
     * @param settingId the id.
     */
    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    /**
     * Gets the column settings.
     * @return the number of columns.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the column settings.
     * @param columns the number of columns.
     */
    public void setColumns(int columns) {

        this.columns = columns;
    }

    /**
     * Gets the row settings.
     * @return the number of rows.
     */
    public int getRows() {

        return rows;
    }

    /**
     * Sets the row settings.
     * @param rows the number of rows.
     */
    public void setRows(int rows) {

        this.rows = rows;
    }

    /**
     * Gets the row settings.
     * @return the number of rows.
     */
    public int getCurrentPuzzleId() {

        return currentPuzzleId;
    }

    /**
     * Sets the row settings.
     * @param currentPuzzleId the number of rows.
     */
    public void setCurrentPuzzleId(int currentPuzzleId) {

        this.currentPuzzleId = currentPuzzleId;
    }
}