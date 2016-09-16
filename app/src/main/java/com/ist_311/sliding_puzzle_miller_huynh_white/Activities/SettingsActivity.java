package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;

public class SettingsActivity extends AppCompatActivity {

    // Session
    SessionManager sessionManager;
    // UI components
    NumberPicker numberPickerRows, numberPickerCols;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
    }

    private void initialize() {
        sessionManager = new SessionManager(getApplicationContext());
        numberPickerRows = (NumberPicker) findViewById(R.id.numberPickerRows);
        numberPickerCols = (NumberPicker) findViewById(R.id.numberPickerCols);

        numberPickerRows.setMaxValue(8);
        numberPickerRows.setMinValue(2);
        numberPickerCols.setMaxValue(8);
        numberPickerCols.setMinValue(2);

        numberPickerCols.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                sessionManager.setCols(i2);
            }
        });

        numberPickerRows.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                sessionManager.setRows(i2);
            }
        });


        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void choosePuzzle(){
        // TODO Open Gallery, Set ImageView and Store Picture
    }
}
