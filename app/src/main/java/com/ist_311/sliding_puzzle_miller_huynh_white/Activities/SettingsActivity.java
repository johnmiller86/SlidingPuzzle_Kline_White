package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.Puzzle;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.Settings;
import com.ist_311.sliding_puzzle_miller_huynh_white.models.User;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.PuzzleFunctions;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SettingFunctions;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.UserFunctions;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    private User user;
    private UserFunctions userFunctions;
    private Settings settings;
    private SettingFunctions settingFunctions;
    private Puzzle puzzle;
    private PuzzleFunctions puzzleFunctions;

    // Constants
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_IMAGE = 1;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
    }

    private void initialize() {

        // Configuring session
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        userFunctions = new UserFunctions();
        user = userFunctions.getUser(sessionManager.getUsername());
        settingFunctions = new SettingFunctions();
        settings = settingFunctions.getSettings(user);
        puzzleFunctions = new PuzzleFunctions();
        puzzle = puzzleFunctions.getPuzzle(user);

        // UI components
        NumberPicker numberPickerRows = (NumberPicker) findViewById(R.id.numberPickerRows);
        NumberPicker numberPickerCols = (NumberPicker) findViewById(R.id.numberPickerCols);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(puzzle.getPuzzle());

        numberPickerCols.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                settings.setColumns(i2);
                if (settings.getSettingId() != 0) {
                    settingFunctions.update(user, settings);
                }
                else {
                    // First time insert and reassign settings to get settings id
                    settingFunctions.insert(user, settings);
                    settings = settingFunctions.getSettings(user);
                }
            }
        });

        numberPickerRows.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                settings.setRows(i2);
                if (settings.getSettingId() != 0) {
                    settingFunctions.update(user, settings);
                }
                else{
                    // Fist time insert and reassign settings to get settings id
                    settingFunctions.insert(user, settings);
                    settings = settingFunctions.getSettings(user);
                }
            }
        });

        numberPickerCols.setMaxValue(8);
        numberPickerRows.setMaxValue(8);
        numberPickerCols.setMinValue(2);
        numberPickerRows.setMinValue(2);
        if (settings.getSettingId() != 0) {
            numberPickerCols.setValue(settings.getColumns());
            numberPickerRows.setValue(settings.getRows());
        }

    }

    @SuppressWarnings("unused")
    public void chooseImage(@SuppressWarnings("UnusedParameters") View view) {
        final CharSequence[] charSequences = { "Take Photo", "Choose from Library", "Cancel" };
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

    /**
     * Handles the image response.
     * @param requestCode the requesting intent.
     * @param resultCode the status of the result.
     * @param data the image data.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA){
                Uri imageUri = data.getData();
                puzzle.setPuzzlePath(getRealPathFromURI(this, imageUri));
                Bitmap bitmap = fixOrientation(puzzle.getPuzzle(), puzzle.getPuzzlePath());
                imageView.setImageBitmap(bitmap);

                if (puzzle.getPuzzleId() != 0) {
                    // Update
                    puzzleFunctions.update(user, puzzle);
                }else{
                    // First time, insert
                    puzzleFunctions.insert(user, puzzle);

                    // Get the new autoincrement puzzleId
                    puzzle = puzzleFunctions.getPuzzle(user);
                }
            }
            else if (requestCode == SELECT_IMAGE) {
                Uri imageUri = data.getData();
                puzzle.setPuzzlePath(getRealPathFromURI(this, imageUri));
                Bitmap bitmap = fixOrientation(puzzle.getPuzzle(), puzzle.getPuzzlePath());
                imageView.setImageBitmap(bitmap);

                if (puzzle.getPuzzleId() != 0) {
                    // Update
                    puzzleFunctions.update(user, puzzle);
                }else{
                    // First time insert
                    puzzleFunctions.insert(user, puzzle);

                    // Get the new puzzleId
                    puzzle = puzzleFunctions.getPuzzle(user);
                }
            }
        }
    }

    // TODO get orientation right
    /**
     * Rotates bitmap to correct orientation.
     * @param bitmap the image bitmap.
     * @param path the image path.
     * @return the properly oriented bitmap.
     */
    private Bitmap fixOrientation(Bitmap bitmap, String path){

        try {
            String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
            Uri imageUri = Uri.fromFile(new File(path));
            Cursor cursor = getContentResolver().query(imageUri, orientationColumn, null, null, null);
            int orientation = -1;
            if (cursor != null && cursor.moveToFirst()) {
                orientation = cursor.getInt(cursor.getColumnIndex(orientationColumn[0]));
                cursor.close();
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        catch (Exception e) {
            Log.d(SettingsActivity.class.getSimpleName(), e.getMessage());
        }
        return bitmap;
    }

    /**
     * Gets the full filepath of the uri.
     * @param context the context.
     * @param contentUri the uri.
     * @return the path.
     */
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
