package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;


public class MainMenuFragment extends Fragment {

    // UI components
    private View view;
    Button play, settings, leaderboards;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initialize();
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void initialize(){
        play = (Button) view.findViewById(R.id.button_play);
        settings = (Button) view.findViewById(R.id.button_settings);
        leaderboards = (Button) view.findViewById(R.id.button_leaderboards);
        play.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                play();
            }
        });
        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                settings();
            }
        });
//        leaderboards.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view){
//                leaderboards();
//            }
//        });
    }
    /**
     * Login button listener.
     */
    public void play() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new PuzzleFragment());
        fragmentTransaction.commit();
    }

    /**
     * Settings button listener.
     */
    private void settings() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //TODO Add animations
//            fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.replace(R.id.fragment_container, new SettingsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

//    /**
//     * Login button listener.
//     */
//    private void leaderboards() {
//
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        //TODO Add animations
////      fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
//        fragmentTransaction.replace(R.id.fragment_container, new leaderboardsFragment());
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        fragmentManager.executePendingTransactions();
//    }
}
