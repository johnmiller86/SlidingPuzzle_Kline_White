package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.User;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.DBHelper;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.DatabaseManager;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.UserFunctions;

public class LoginActivity extends AppCompatActivity{

    // UI Components
    private EditText editText_username;
    private EditText editText_password;

    // Session
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (sessionManager.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }

        // Database Manager
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);

        // Linking UI Components
        editText_username = (EditText) findViewById(R.id.editTextUsername);
        editText_password = (EditText) findViewById(R.id.editTextPassword);
    }

    /**
     * Login button listener.
     * @param view the login button.
     */
    @SuppressWarnings("unused")
    public void Login(@SuppressWarnings("UnusedParameters") View view) {

        User user = new User();
        UserFunctions userFunctions = new UserFunctions();

        user.setUsername(editText_username.getText().toString());
        user.setPassword(editText_password.getText().toString());

        // Verifying user exists
        if (!userFunctions.userExists(user)){

            // User not found
            Toast.makeText(this, "User not found!!", Toast.LENGTH_SHORT).show();
        }

        // Verifying password is correct
        else if (!userFunctions.passwordCorrect(user)){

            // Incorrect password
            Toast.makeText(LoginActivity.this, "Password is incorrect!!", Toast.LENGTH_SHORT).show();
        }

        // Logging in
        else{

            // Configuring user
            sessionManager.setLoggedIn(true);
            sessionManager.setUsername(user.getUsername());
            Intent intent = new Intent(this, MainMenuActivity.class);

            // Launching StartScreenActivity
            startActivity(intent);
            finish();
        }
    }

    @SuppressWarnings("unused")
    public void Register(@SuppressWarnings("UnusedParameters") View view){

        // Create Intent for RegisterActivity
        Intent intent = new Intent(this, RegisterActivity.class);

        // Launching RegisterActivity
        startActivity(intent);
    }
}

