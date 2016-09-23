package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.DBHelper;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.DatabaseManager;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    // Session
    private SessionManager sessionManager;
    String currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Instantiating Session
        sessionManager = new SessionManager(getApplicationContext());

        // FragmentDrawer
        FragmentDrawer fragmentDrawer = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        fragmentDrawer.setDrawerListener(this);

        // Database Manager
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);

        // Loading main menu fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (savedInstanceState != null){
            fragmentTransaction.replace(R.id.fragment_container, getSupportFragmentManager().getFragment(savedInstanceState, "savedFragment"));
        }
        currentTag = "Main Menu";
        fragmentTransaction.replace(R.id.fragment_container, new MainMenuFragment(), currentTag);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
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
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainMenuFragment();
                currentTag = "Main Menu";
                break;
            case 1:
//                fragment = new CampaignFragment();
                //currentTag = "Campaign";
                break;
            case 2:
                fragment = new PuzzleFragment();
                currentTag = "Free Play";
                break;
            case 3:
                //fragment = new SettingsFragment();
                break;
            case 4:
                fragment = new SettingsFragment();
                currentTag = "Settings";
                break;
            case 5:
                logoutUser();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment, currentTag);
            //addToBackStack(fragmentTransaction);
            if (fragment instanceof MainMenuFragment){

            }
            else {
                fragmentTransaction.addToBackStack(null);
            }
            fragment.setRetainInstance(true);
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStack();
        } else {

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
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedFragment) {
        super.onSaveInstanceState(savedFragment);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(savedFragment, "savedFragment", getSupportFragmentManager().findFragmentByTag(currentTag));
    }

    /**
     * Adds a fragment transaction to the back stack.
     * @param fragmentTransaction the fragment transaction.
     */
//    private void addToBackStack(FragmentTransaction fragmentTransaction){
//        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
//            fragmentTransaction.addToBackStack(null);
//        }
//    }
}
