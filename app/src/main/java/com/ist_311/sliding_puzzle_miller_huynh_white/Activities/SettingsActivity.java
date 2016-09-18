package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

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
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    // Session
    private SessionManager sessionManager;

    // Constants
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_IMAGE = 1;

    private ImageView imageView;

    public static Bitmap bitmap;

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

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA){
                //bitmap = (Bitmap) data.getExtras().get("data");
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    Uri imageUri = data.getData();
                    bitmap = fixOrientation(bitmap, imageUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == SELECT_IMAGE) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    Uri imageUri = data.getData();
                    bitmap = fixOrientation(bitmap, imageUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                // Store in SQL
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                byte[] bytes = byteArrayOutputStream.toByteArray();
//                String encodedBitmap = Base64.encodeToString(bytes, Base64.DEFAULT);
//
//                // Getting
//                byte[] bytes1 = Base64.decode(encodedBitmap, Base64.DEFAULT);
//                bitmap = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
//                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * Rotates bitmap to correct orientation.
     * @param bitmap the image bitmap.
     * @param imageUri the image uri.
     * @return the properly oriented bitmap.
     */
    private Bitmap fixOrientation(Bitmap bitmap, Uri imageUri){

        try {
            String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
            Cursor cursor = getContentResolver().query(imageUri, orientationColumn, null, null, null);
            int orientation = -1;
            if (cursor != null && cursor.moveToFirst()) {
                orientation = cursor.getInt(cursor.getColumnIndex(orientationColumn[0]));
            }
            cursor.close();
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        catch (Exception e) {
            Log.d(SettingsActivity.class.getSimpleName(), e.getMessage());
        }
        return bitmap;
    }
}
