package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.R;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.DBHelper;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.DatabaseManager;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.SessionManager;

@SuppressLint("CommitTransaction")
public class MainActivity extends FragmentActivity implements FragmentDrawer.FragmentDrawerListener {

    // Session
    public static SessionManager sessionManager;
    private FragmentTransaction fragmentTransaction;
    public static Fragment fragment;
    private final String FRAGMENT_TAG = "fragment_tag";
    public static final String PUZZLE_MODE_TAG = "puzzle_mode_tag";
    public static final String PUZZLE_TIMER_TAG = "puzzle_timer_tag";
    public static final String PUZZLE_LEVEL_TAG = "puzzle_level_tag";
    public static final String PUZZLE_ROW_TAG = "puzzle_row_tag";
    public static final String PUZZLE_COL_TAG = "puzzle_col_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiating Session
        sessionManager = new SessionManager(getApplicationContext());

        // FragmentDrawer
        FragmentDrawer fragmentDrawer = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        fragmentDrawer.setDrawerListener(this);

        // Database Manager
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);

        // Load/recover fragment
        if (savedInstanceState != null && getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG) != null){
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
        }else {
            fragment = new MainMenuFragment();
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

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
        boolean current = true;
        switch (position) {
            // Main menu
            case 0:
                if (!(fragment instanceof MainMenuFragment)){
                    fragment = new MainMenuFragment();
                    current = false;
                }
                break;
            // Campaign
            case 1:
                if (!(fragment instanceof CampaignFragment)){
                    fragment = new CampaignFragment();
                    current = false;
                }
                break;
            // Free play
            case 2:
                Intent freePlay = new Intent(this, PuzzleActivity.class);
                startActivity(freePlay);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            // Leaderboards
            case 3:
                if (fragment instanceof  LeaderboardFragment == false) {
                    fragment = new LeaderboardFragment();
                    current = false;
                }
                break;
            // Settings
            case 4:
                if (!(fragment instanceof SettingsFragment)){
                    fragment = new SettingsFragment();
                    current = false;
                }
                break;
            // Sign out
            case 5:
                logoutUser();
                break;
            default:
                break;
        }

        // Load only if not current to save memory
        if (!current) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {

        if (fragment instanceof MainMenuFragment) {
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to quit?")

                    // Open Settings button
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })

                    // Denied, close app
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher)
                    .show();
        }else{
            MainActivity.fragment = new MainMenuFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, MainActivity.fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Process the Fragment's permission request
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedFragment) {
        super.onSaveInstanceState(savedFragment);

        //Save the Fragment's instance
        getSupportFragmentManager().putFragment(savedFragment, FRAGMENT_TAG, fragment);
    }
}
