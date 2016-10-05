package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.R;

import java.util.ArrayList;

public class CampaignFragment extends Fragment {

    // UI Components
    private GridView gridView;
    private View view;

    // Instance vars
    private ArrayList<String> levels;
    private ArrayAdapter<String> arrayAdapter;

    public CampaignFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initializeReferences() {
        levels = new ArrayList<>();
//        gridView = (GridView) view.findViewById(R.id.campaign_gridview);
//        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, levels);
//        gridView.setNumColumns(3);
//        gridView.setBackgroundColor(Color.BLACK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_campaign, container, false);
        initializeReferences();
//        fillGridview();
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

//    private void fillGridview(){
//
//        for (int i = 0; i < levels.size(); i++){
//            ImageButton imageButton = new ImageButton(getActivity());
//            int level = (int) (Math.random() * 20) + 1;
//            String levelNum = "level_" + i + 1;
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(levelNum, "drawable", getActivity().getPackageName()));
//            imageButton.setImageBitmap(bitmap);
//            gridView.addView(imageButton);
//        }
//    }
}
