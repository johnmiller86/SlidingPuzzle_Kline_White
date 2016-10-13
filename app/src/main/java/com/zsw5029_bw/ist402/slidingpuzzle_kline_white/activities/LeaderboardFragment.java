package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.R;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.LeaderboardEntry;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.ListViewAdapter;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.LeaderboardFunctions;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.ListViewAdapter.FIFTH_COLUMN;
import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.ListViewAdapter.FIRST_COLUMN;
import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.ListViewAdapter.FOURTH_COLUMN;
import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.ListViewAdapter.SECOND_COLUMN;
import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.ListViewAdapter.THIRD_COLUMN;


public class LeaderboardFragment extends Fragment {

    private View view;
    private ArrayList<HashMap<String, String>> list;
    private ListView listView;

    public LeaderboardFragment() {
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
        view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);
        list=new ArrayList<>();

        LeaderboardFunctions leaderboardFunctions = new LeaderboardFunctions();
        ArrayList<LeaderboardEntry> leaderboards = leaderboardFunctions.getLeaderboards();

        for (LeaderboardEntry leaderboardEntry : leaderboards){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(FIRST_COLUMN, leaderboardEntry.getUsername());
            hashMap.put(SECOND_COLUMN, String.valueOf(leaderboardEntry.getScore()));
            hashMap.put(THIRD_COLUMN, String.valueOf(leaderboardEntry.getMoves()));
            hashMap.put(FOURTH_COLUMN, leaderboardEntry.getTime());
            hashMap.put(FIFTH_COLUMN, String.valueOf(leaderboardEntry.getLevel_num()));
            list.add(hashMap);
        }

        ListViewAdapter adapter = new ListViewAdapter(getLayoutInflater(savedInstanceState), list);
        listView.setAdapter(adapter);
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
}
