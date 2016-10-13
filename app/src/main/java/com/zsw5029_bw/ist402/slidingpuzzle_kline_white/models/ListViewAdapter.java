package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter{

    // Constants
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";
    public static final String FIFTH_COLUMN="Fifth";

    public ArrayList<HashMap<String, String>> list;
    private LayoutInflater layoutInflater;
    TextView txtFirst, txtSecond, txtThird, txtFourth, txtFifth;

    public ListViewAdapter(LayoutInflater layoutInflater, ArrayList<HashMap<String, String>> list){
        super();
        this.list=list;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {

//        LayoutInflater inflater = MainActivity.fragment.getLayoutInflater(null);

        if(view == null){

            view = layoutInflater.inflate(R.layout.list_view_row, null);

            txtFirst=(TextView) view.findViewById(R.id.username);
            txtSecond=(TextView) view.findViewById(R.id.score);
            txtThird=(TextView) view.findViewById(R.id.moves);
            txtFourth=(TextView) view.findViewById(R.id.timeLV);
            txtFifth=(TextView) view.findViewById(R.id.levelTV);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        txtFourth.setText(map.get(FOURTH_COLUMN));
        txtFifth.setText(map.get(FIFTH_COLUMN));

        return view;
    }

}