package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities;

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

import java.util.List;

public class MainActivity extends FragmentActivity implements FragmentDrawer.FragmentDrawerListener {

    // Session
    private SessionManager sessionManager;
    private FragmentTransaction fragmentTransaction;
    public static Fragment fragment;
    private final String FRAGMENT_TAG = "fragment_tag";
    public static final String PUZZLE_MODE_TAG = "puzzle_mode_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Loading Fragment
        if (savedInstanceState != null && getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG) != null){
            // Recovering Fragment
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
        }else {
            // Creating new Fragment
            fragment = new MainMenuFragment();
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
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
//        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainMenuFragment();
                break;
            case 1:
                //fragment = new CampaignFragment();
                fragment = new PuzzleFragment();
                Bundle bundle = new Bundle();
                bundle.putString(PUZZLE_MODE_TAG, "campaign");
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new PuzzleFragment();
                break;
            case 3:
                //fragment = new SettingsFragment();
                break;
            case 4:
                fragment = new SettingsFragment();
                break;
            case 5:
                logoutUser();
                break;
            default:
                break;
        }

        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            if (fragment instanceof MainMenuFragment){
//
//            }
//            else {
//                fragmentTransaction.addToBackStack(null);
//            }
            fragmentTransaction.addToBackStack(null);
//            fragment.setRetainInstance(true);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStack();
//            setCurrentFragment();
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

                // Process the Fragment's permission request
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedFragment) {
        super.onSaveInstanceState(savedFragment);

        //Save the Fragment's instance
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().putFragment(savedFragment, FRAGMENT_TAG, fragment);
        }
    }

//    private void setCurrentFragment(){
//        String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
//        fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
//    }
}
