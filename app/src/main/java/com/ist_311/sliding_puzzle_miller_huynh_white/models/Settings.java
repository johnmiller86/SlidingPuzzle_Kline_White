package com.ist_311.sliding_puzzle_miller_huynh_white.models;

public class Settings {

    // Table name
    public static final String TABLE = "settings";

    // Column names
    public static final String SETTING_ID = "setting_id";
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    // Instance vars
    private int userId;
    private String email, password;

    /**
     * Gets the user's id.
     * @return the id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the user's email.
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * @param email the email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}