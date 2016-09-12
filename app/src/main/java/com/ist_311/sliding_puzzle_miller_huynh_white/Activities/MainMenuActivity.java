package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;

public class MainMenuActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    //Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Options Item Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.menuitem_logout){
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sessionManager = new SessionManager(getApplicationContext());
    }

    /**
     * Login button listener.
     * @param view the login button.
     */
    public void play(View view) {

        Intent intent = new Intent(this, PuzzleActivity.class);
        startActivity(intent);
    }

//    /**
//     * Login button listener.
//     * @param view the login button.
//     */
//    public void settings(View view) {
//
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
//    }
//
//    /**
//     * Login button listener.
//     * @param view the login button.
//     */
//    public void highScores(View view) {
//
//        Intent intent = new Intent(this, HighScoresActivity.class);
//        startActivity(intent);
//    }

    /**
     * Logs out the user.
     * */
    private void logoutUser() {

        sessionManager.setLoggedIn(false);
        sessionManager.setUsername("");

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
