package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;

public class SettingsActivity extends AppCompatActivity {

    // Session
    private SessionManager sessionManager;

    // Constants
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
    }

    private void initialize() {
        sessionManager = new SessionManager(getApplicationContext());
        NumberPicker numberPickerRows = (NumberPicker) findViewById(R.id.numberPickerRows);
        NumberPicker numberPickerCols = (NumberPicker) findViewById(R.id.numberPickerCols);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

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

        numberPickerRows.setMaxValue(8);
        numberPickerRows.setMinValue(2);
        numberPickerRows.setValue(sessionManager.getRows());
        numberPickerCols.setMaxValue(8);
        numberPickerCols.setMinValue(2);
        numberPickerCols.setValue(sessionManager.getCols());

    }

    @SuppressWarnings("unused")
    public void chooseImage(@SuppressWarnings("UnusedParameters") View view) {
        final CharSequence[] charSequences = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Photo");
        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (charSequences[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (charSequences[item].equals("Choose from Library")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select a Photo"),SELECT_IMAGE);
                }
                else if (charSequences[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
