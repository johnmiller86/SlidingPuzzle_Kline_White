package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.R;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.Puzzle;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.Settings;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.User;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.PuzzleFunctions;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.SessionManager;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.SettingFunctions;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.UserFunctions;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    // Session
    private User user;
    private Settings settings;
    private SettingFunctions settingFunctions;
    private Puzzle puzzle;
    private PuzzleFunctions puzzleFunctions;

    private final int REQUEST_EXTERNAL_STORAGE_CAMERA = 1;
    private final int REQUEST_EXTERNAL_STORAGE_GALLERY = 2;
    private final int REQUEST_CAMERA = 3;
    private final int SELECT_IMAGE = 4;

    // UI components
    private View view;
    private ImageView imageView;

    public SettingsFragment() {
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
        view = inflater.inflate(R.layout.fragment_settings, container, false);
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

    private void initialize() {

        // Configuring session
        SessionManager sessionManager = new SessionManager(view.getContext());
        UserFunctions userFunctions = new UserFunctions();
        user = userFunctions.getUser(sessionManager.getUsername());
        settingFunctions = new SettingFunctions();
        settings = settingFunctions.getSettings(user);
        puzzleFunctions = new PuzzleFunctions();
        puzzle = puzzleFunctions.getPuzzle(user);

        // UI components
        NumberPicker numberPickerRows = (NumberPicker) view.findViewById(R.id.numberPickerRows);
        NumberPicker numberPickerCols = (NumberPicker) view.findViewById(R.id.numberPickerCols);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageBitmap(puzzle.getPuzzle(view.getContext()));
        Button imagePicker = (Button) view.findViewById(R.id.button_pick_puzzle);
        imagePicker.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                chooseImage();
            }
        });

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

    private void chooseImage() {
        final CharSequence[] charSequences = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select a Photo");
        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (charSequences[item].equals("Take Photo")) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_CAMERA);
                }
                else if (charSequences[item].equals("Choose from Library")) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_GALLERY);
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
                puzzle.setPuzzlePath(getImagePath(getContext(), imageUri));
                Bitmap bitmap = puzzle.getPuzzle(getContext());
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
                puzzle.setPuzzlePath(getImagePath(getContext(), imageUri));
                Bitmap bitmap = puzzle.getPuzzle(getContext());
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

    /**
     * Gets the full filepath of the uri.
     * @param context the context.
     * @param contentUri the uri.
     * @return the path.
     */
    private String getImagePath(Context context, Uri contentUri) {

        String[] imageData = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, imageData, null, null, null);
        assert cursor != null;
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

    /**
     * Handles operations based on permission results.
     * @param requestCode the request code.
     * @param permissions the result code.
     * @param grantResults the grant results array.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        switch (requestCode){
            case REQUEST_EXTERNAL_STORAGE_CAMERA: {
                // Granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                // Blocked
                else if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Permission was blocked!")
                            .setMessage("You have previously blocked this app from accessing external storage. To set a free play puzzle, the app needs to " +
                                    "retrieve image paths and will not function without this access. Would you like to go to settings and allow this permission?")

                            // Open Settings button
                            .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    goToSettings();
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                // Denied
                else{
                    new AlertDialog.Builder(getContext())
                            .setTitle("Permission was denied!")
                            .setMessage("You are unable to set a free play puzzle without access to external storage. Would you like to allow access?")

                            // Open Settings button
                            .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_CAMERA);
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return;
            }
            case REQUEST_EXTERNAL_STORAGE_GALLERY:{
                // Granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, SELECT_IMAGE);
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select a Photo"),SELECT_IMAGE);
                }
                // Blocked
                else if(!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Permission was blocked!")
                            .setMessage("You have previously blocked this app from accessing external storage. To set a free play puzzle, the app needs to " +
                                    "retrieve image paths and will not function without this access. Would you like to go to settings and allow this permission?")

                            // Open Settings button
                            .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    goToSettings();
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                // Denied
                else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Permission was denied!")
                            .setMessage("You are unable to set a free play puzzle without access to external storage. Would you like to allow access?")

                            // Open Settings button
                            .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_GALLERY);
                                }
                            })

                            // Denied, close app
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        }
    }

    /**
     * Opens the app's settings page in AppManager.
     */
    private void goToSettings(){
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        int REQUEST_PERMISSION = 0;
        startActivityForResult(intent, REQUEST_PERMISSION);
    }
}
