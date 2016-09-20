package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.User;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.UserFunctions;

public class RegisterActivity extends AppCompatActivity {

    // UI Components
    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Linking UI Components
        usernameEditText = (EditText) findViewById(R.id.editTextRegisterUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextRegisterPassword);
        confirmPasswordEditText = (EditText) findViewById(R.id.editTextConfirmRegisterPassword);
    }



    /**
     * Registers a new account.
     * @param view the search button.
     */
    @SuppressWarnings("unused")
    public void registerAccount(@SuppressWarnings("UnusedParameters") View view){

        // Validating input
        if (usernameEditText.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "You must choose a username!!", Toast.LENGTH_SHORT).show();
        }
        else if (passwordEditText.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "You must choose a password!!", Toast.LENGTH_SHORT).show();
        }
        else if (usernameEditText.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "You must confirm your password!!", Toast.LENGTH_SHORT).show();
        }
        else if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Password and confirmation do not match!!", Toast.LENGTH_SHORT).show();
        }
        else{

            // User object
            User user = new User();

            // User Database functions object
            UserFunctions userFunctions = new UserFunctions();

            // Configuring user
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            // Checking uniqueness of username.
            if (userFunctions.userExists(user)){
                Toast.makeText(this, "Username already taken!!", Toast.LENGTH_SHORT).show();
            }
            else {
                // Inserting
                userFunctions.insertUser(user);

                // Finished registering, exit
                finish();
            }
        }
    }
}
