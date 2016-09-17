package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;

public class MainMenuActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    // Session
    private SessionManager sessionManager;

    // Navigation drawer
    private Toolbar toolbar;
    private FragmentDrawer fragmentDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sessionManager = new SessionManager(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fragmentDrawer = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        fragmentDrawer.setDrawerListener(this);
    }

    /**
     * Login button listener.
     *
     * @param view the login button.
     */
    @SuppressWarnings("unused")
    public void play(@SuppressWarnings("UnusedParameters") View view) {

        Intent intent = new Intent(this, PuzzleActivity.class);
        startActivity(intent);
    }

    /**
     * Login button listener.
     *
     * @param view the login button.
     */
    @SuppressWarnings("unused")
    public void settings(@SuppressWarnings("UnusedParameters") View view) {
//
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

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
     */
    private void logoutUser() {

        sessionManager.setLoggedIn(false);
        sessionManager.setUsername("");

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        Intent intent;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                break;
            case 1:
                intent = new Intent(this, PuzzleActivity.class);
                startActivity(intent);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case 5:
                logoutUser();
                break;
            default:
                break;
        }
    }
}
