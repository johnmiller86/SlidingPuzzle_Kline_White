package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.R;


public class MainMenuFragment extends Fragment {

    // UI components
    private View view;
    private Button campaignPlayButton, freePlayButton, settingsButton, leaderboardsButton;
    private FragmentTransaction fragmentTransaction;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    private void initialize(){
        campaignPlayButton = (Button) view.findViewById(R.id.button_campaign);
        freePlayButton = (Button) view.findViewById(R.id.button_freeplay);
        settingsButton = (Button) view.findViewById(R.id.button_settings);
        leaderboardsButton = (Button) view.findViewById(R.id.button_leaderboards);
        campaignPlayButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                campaignPlay();
            }
        });
        freePlayButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                freePlay();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                settings();
            }
        });
        leaderboardsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                leaderboards();
            }
        });
    }

    /**
     * Free Play button listener.
     */
    private void campaignPlay() {
        MainActivity.fragment = new PuzzleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.PUZZLE_MODE_TAG, "campaign");
        MainActivity.fragment.setArguments(bundle);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, MainActivity.fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Free Play button listener.
     */
    private void freePlay() {
        MainActivity.fragment = new PuzzleFragment();
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, MainActivity.fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Settings button listener.
     */
    private void settings() {
        MainActivity.fragment = new SettingsFragment();
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, MainActivity.fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Leaderboards button listener.
     */
    private void leaderboards() {
//        MainActivity.fragment = new LeaderboardsFragment();
//        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//        fragmentTransaction.replace(R.id.fragment_container, MainActivity.fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }
}
