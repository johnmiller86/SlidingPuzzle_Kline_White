package com.ist_311.sliding_puzzle_miller_huynh_white.models;

public class User {

    // Instance vars
    private int userId;
    private String username, password;

    /**
     * Gets the user's id.
     * @return the id.
     */
    public int getUserId() {

        return userId;
    }

    /**
     * Sets the user's id.
     * @param userId the id.
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Gets the user's username.
     * @return the username.
     */
    public String getUsername() {

        return username;
    }

    /**
     * Sets the user's email.
     * @param username the email.
     */
    public void setUsername(String username) {

        this.username = username;
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