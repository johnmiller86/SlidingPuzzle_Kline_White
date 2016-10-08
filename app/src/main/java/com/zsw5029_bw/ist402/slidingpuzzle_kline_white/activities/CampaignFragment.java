package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.R;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.Puzzle;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.Settings;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models.User;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.LeaderboardFunctions;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.PuzzleFunctions;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.SessionManager;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.SettingFunctions;
import com.zsw5029_bw.ist402.slidingpuzzle_kline_white.utilities.UserFunctions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.zsw5029_bw.ist402.slidingpuzzle_kline_white.activities.MainActivity.PUZZLE_MODE_TAG;

public class CampaignFragment extends Fragment {

    // Session
    private User user;
    private Settings settings;
    private SettingFunctions settingFunctions;
    private Puzzle puzzle;
    private PuzzleFunctions puzzleFunctions;

    // Constants
    private final int ROWS = 7;
    private final int COLS = 3;

    // UI Components
    private View view;
    private List<RelativeLayout> relativeLayouts;
    private List<ImageButton> imageButtons;
    private List<Integer> unlocked;

    public CampaignFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialize() {

        // Configuring session
        SessionManager sessionManager = new SessionManager(view.getContext());
        UserFunctions userFunctions = new UserFunctions();
        LeaderboardFunctions leaderboardFunctions = new LeaderboardFunctions();
        user = userFunctions.getUser(sessionManager.getUsername());
        settingFunctions = new SettingFunctions();
        settings = settingFunctions.getSettings(user);
        puzzleFunctions = new PuzzleFunctions();
        puzzle = puzzleFunctions.getPuzzle(user);
        relativeLayouts = new ArrayList<>();
        imageButtons = new ArrayList<>();
        unlocked = leaderboardFunctions.getOpenLevels(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_campaign, container, false);
        initialize();
        createLayout();
        configureLayouts();
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

    private void createLayout(){

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.campaign_tablelayout);
        TableRow tableRow = new TableRow(getContext());
        RelativeLayout outerLayout, innerLayout;
        TableRow.LayoutParams layoutParams;
        RelativeLayout.LayoutParams textViewParams;
        TableRow.LayoutParams imageButtonParams;
        ImageButton imageButton;
        TextView textView;

        for (int i = 0; i < 20; i++){

            // Outer Layout
            outerLayout = new RelativeLayout(getContext());
            layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
            layoutParams.setMargins(5,5,5,5);
            outerLayout.setLayoutParams(layoutParams);

            // ImageButton
            imageButton = new ImageButton(getActivity());
            imageButtonParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            imageButton.setLayoutParams(imageButtonParams);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageButton.setBackgroundColor(Color.GRAY);
            imageButton.setOnClickListener(imagesListener);
            String levelNum = "level_" + (i + 1);
            imageButton.setTag(levelNum);
            loadImages(imageButton, i, levelNum);
//            imageButton.setForeground;
//            imageButton.setEnabled(false);
            imageButtons.add(imageButton);

            // Inner Layout
            innerLayout = new RelativeLayout(getContext());
            layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            innerLayout.setLayoutParams(layoutParams);

            // TextView
            textView = new TextView(getContext());
            textViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            textView.setLayoutParams(textViewParams);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setText("Level " + (i + 1));
            textView.setTextSize(18);
            textView.setBackgroundResource(R.drawable.gradient);
            textView.setTypeface(Typeface.create("serif", Typeface.BOLD));
            textView.setAlpha(.5f);

            // Adding
            innerLayout.addView(textView);
            outerLayout.addView(imageButton);
            outerLayout.addView(innerLayout);
            tableRow.addView(outerLayout);
            relativeLayouts.add(outerLayout);

            if (i > 0 && (i + 1) % 3 == 0){
                tableLayout.addView(tableRow);
                tableRow = new TableRow(getContext());
            }else if (i == 19){
                tableLayout.addView(tableRow);
            }
        }
    }

    private void loadImages(ImageButton imageButton, int i, String levelNum) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        if (i < unlocked.size()) {
            loadAsync(getResources().getIdentifier("checkmark", "drawable", getActivity().getPackageName()), imageButton, getActivity().getDrawable(getResources().getIdentifier(levelNum, "drawable", getActivity().getPackageName())));
        }else if(i == unlocked.size()){
            loadAsync(getResources().getIdentifier("glass", "drawable", getActivity().getPackageName()), imageButton, getActivity().getDrawable(getResources().getIdentifier(levelNum, "drawable", getActivity().getPackageName())));
        }else{
            loadAsync(getResources().getIdentifier("lock", "drawable", getActivity().getPackageName()), imageButton, getActivity().getDrawable(getResources().getIdentifier(levelNum, "drawable", getActivity().getPackageName())));
            imageButton.setEnabled(false);
        }
    }

    /**
     * Configures the sizing of the nested layouts.
     */
    private void configureLayouts() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        for (RelativeLayout relativeLayout : relativeLayouts) {
            relativeLayout.getLayoutParams().width = displayMetrics.widthPixels / COLS;
            relativeLayout.getLayoutParams().height = displayMetrics.heightPixels / ROWS;
            relativeLayout.requestLayout();
        }
    }

    /**
     * Click listener for ImageButtons.
     */
    private final View.OnClickListener imagesListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent campaign = new Intent(getActivity(), PuzzleActivity.class);
            campaign.putExtra(PUZZLE_MODE_TAG, (String)view.getTag());
            startActivity(campaign);
            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    };

    /**
     * Loads an image asynchronously.
     * @param resId the resource id.
     * @param imageView the receiving ImageView.
     * @param drawable the applying Drawable.
     */
    public void loadAsync(int resId, ImageView imageView, Drawable drawable) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, drawable);
        task.execute(resId);
    }



    // TODO Clean up this async task

    /**
     * Inner class for asynchronous image loading.
     */
    public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;
        private Drawable drawable;
        private boolean current;

        public BitmapWorkerTask(ImageView imageView, Drawable drawable) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            this.drawable = drawable;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                    imageView.setBackground(drawable);
                }
            }
        }

        public int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }

        public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                             int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }
    }
}
