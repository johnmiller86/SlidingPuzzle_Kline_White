package com.ist_311.sliding_puzzle_miller_huynh_white.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ist_311.sliding_puzzle_miller_huynh_white.R;
import com.ist_311.sliding_puzzle_miller_huynh_white.utilities.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleActivity extends AppCompatActivity {

    // UI components
    private TableLayout tableLayout;
    private TextView movesTextView, timerTextView;
    private ImageButton previousButton;
    private Button pauseButton, resetButton;

    // Lists
    private List<ImageButton> imageButtons;
    private List<Drawable> answerKey;

    // Vars
    private Timer timer;
    private Animation currentAnimation, previousAnimation;
    private int counter, movesCounter, rows, cols;
    private boolean isPause;
    private final int[] time = {1};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        initializeReferences();
        createPuzzle(BitmapFactory.decodeResource(getResources(), R.drawable.puzzle_pieces));
        startTimer(0);
    }

    /**
     * Initializes all references.
     */
    private void initializeReferences() {

        // Initializing Session
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        // Initializing Layout
        tableLayout = (TableLayout) findViewById(R.id.table_layout);

        // Initializing TextViews
        movesTextView = (TextView) findViewById(R.id.currentMoves);
        timerTextView = (TextView) findViewById(R.id.editTextTimer);

        // Initializing Lists
        imageButtons = new ArrayList<>();
        answerKey = new ArrayList<>();

        // TODO GET DIFFICULTY
        rows = sessionManager.getRows();
        cols = sessionManager.getCols();

        // Initializing ImageButtons and adding to list
        createBoard();

        configureButtons();

        // Initializing pause and reset buttons
        pauseButton = (Button) findViewById(R.id.button_pause);
        resetButton = (Button) findViewById(R.id.button_reset);

        // Initializing Counters
        counter = 0;
        movesCounter = 0;
        isPause = false;
    }

    /**
     * Creates the TableRows and adds them to the TableLayout.
     */
    private void createBoard(){
        for (int row = 0; row < rows; row++){

            // Creating TableRow
            TableRow tableRow = new TableRow(this);

            for (int col = 0; col < cols; col++){

                // Creating ImageButton
                ImageButton imageButton = new ImageButton(this);
                imageButtons.add(imageButton);
                tableRow.addView(imageButton);
            }
            tableLayout.addView(tableRow);
        }
    }

    /**
     * Sizes the ImageButtons after being added to the TableLayout.
     */
    private void configureButtons() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        for (ImageButton imageButton : imageButtons) {

            // Sizing Button
            imageButton.getLayoutParams().width = displayMetrics.widthPixels / cols;
            imageButton.getLayoutParams().height = displayMetrics.heightPixels / rows;
            imageButton.requestLayout();

            // Adding Listener
            imageButton.setOnClickListener(imagesListener);
        }
    }

    /**
     * Creates the bitmaps for the ImageButtons.
     * @param bitmap the source bitmap.
     */
    private void createPuzzle(Bitmap bitmap){

        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight= bitmap.getHeight();

        for (int h = 0; h < rows; h++){
            for (int w = 0; w < cols; w++){
                bitmaps.add(Bitmap.createBitmap(bitmap, (w * bitmapWidth) / cols, (h * bitmapHeight) / rows, bitmapWidth / cols, bitmapHeight / rows));
            }
        }
        drawPuzzle(bitmaps);
    }

    /**
     * Fills the image imageButtons with bitmaps.
     * @param bitmaps the ArrayList of bitmaps.
     */
    private void drawPuzzle(ArrayList<Bitmap> bitmaps) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        for (int i = 0; i < bitmaps.size(); i++){
            imageButtons.get(i).setImageBitmap(Bitmap.createScaledBitmap(bitmaps.get(i), width / cols, height / rows, false));
        }
        randomize();
    }

    /**
     * Shuffles the tiles.
     */
    private void randomize() {
        List<Drawable> list = new ArrayList<>();
        for (int i = 0; i < rows * cols; i++){
            list.add(imageButtons.get(i).getDrawable());
            answerKey.add(imageButtons.get(i).getDrawable());
        }
        Collections.shuffle(list);
        for (int i = 0; i < rows * cols; i++){
            imageButtons.get(i).setImageDrawable(list.get(i));
        }
    }

    /**
     * Click listener for ImageButtons.
     */
    private final View.OnClickListener imagesListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            ImageButton imageButton = (ImageButton) view;

            if (counter % 2 == 0) {
                setPrevious(view);
//                imageButton.setColorFilter(Color.argb(255, 255, 255, 255));
                imageButton.setAlpha(0.6f);
                movesTextView.setText(getResources().getQuantityString(R.plurals.moves, movesCounter, movesCounter));
                counter++;
            }
            else if(counter % 2 == 1) {
                previousButton.setAlpha(1.0f);
                swapTiles(view);
            }
        }
    };

    /**
     * Restarts the puzzle.
     * @param view the restart button.
     */
    @SuppressWarnings("unused")
    public void restart(@SuppressWarnings("UnusedParameters") View view){

        // Clearing
        timer.cancel();
        counter = 0;
        movesCounter = 0;
        answerKey.clear();
        movesTextView.setText(R.string.default_moves);
        timerTextView.setText(R.string.default_time);

        // Restarting
        createPuzzle(BitmapFactory.decodeResource(getResources(), R.drawable.puzzle_pieces));
        enableButtons();
        pauseButton.setEnabled(true);
        isPause = false;
        startTimer(0);
    }

    /**
     * Pauses the game.
     * @param view the pause button.
     */
    @SuppressWarnings("unused")
    public void pause(@SuppressWarnings("UnusedParameters") View view){
        isPause = !isPause;
        if (isPause) {
            disableButtons();
            resetButton.setEnabled(false);
            timer.cancel();
        } else {
            enableButtons();
            resetButton.setEnabled(true);
            startTimer(time[0]);
        }
    }

    /**
     * Disables the ImageButtons.
     */
    private void disableButtons(){
        for (ImageButton imageButton : imageButtons){
            imageButton.setEnabled(false);
        }
    }

    /**
     * Enables the ImageButtons.
     */
    private void enableButtons(){
        for (ImageButton imageButton : imageButtons){
            imageButton.setEnabled(true);
        }
    }

    /**
     * Swaps tiles between two ImageButtons.
     * @param view the last ImageButton clicked.
     */
    private void swapTiles(View view) {
        Drawable drawable;

        for (ImageButton imageButton : imageButtons){

            if (view == imageButton) {
                if (isAdjacent(imageButton)){
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    imageButton.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(imageButton.getDrawable());
                    imageButton.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(getResources().getQuantityString(R.plurals.moves, movesCounter, movesCounter));
                }
            }
        }
        if(isSolved()){
            timer.cancel();
            disableButtons();
            pauseButton.setEnabled(false);
            Toast.makeText(this, "Congratulations You Win!!!!!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sets the last ImageButton clicked.
     * @param view the ImageButton.
     */
    private void setPrevious(View view) {

        for (ImageButton imageButton : imageButtons){

            if (view.getId() == imageButton.getId()) {
                previousButton = (ImageButton) view;
            }
        }
    }

    /**
     * Checks ImageButtons are adjacent and sets animations.
     * @param button the last ImageButton clicked.
     * @return true or false.
     */
    private Boolean isAdjacent(ImageButton button){

        // Getting indicies
        int previousIndex = 0;
        int currentIndex = 0;
        for (int i = 0; i < imageButtons.size(); i++){
            if (previousButton == imageButtons.get(i)) {
                previousIndex = i;
            }
            if (button == imageButtons.get(i)){
                currentIndex = i;
            }
        }

        // Left
        if(currentIndex - 1 == previousIndex){
            currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
            previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
            return true;
        }
        // Right
        else if (currentIndex + 1 == previousIndex){
            currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
            previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
            return true;
        }
        // Up
        else if (currentIndex - cols == previousIndex){
            currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            return true;
        }
        // Down
        else if (currentIndex + cols == previousIndex){
            currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            return true;
        }
        Toast.makeText(this, "You must select two adjacent tiles!", Toast.LENGTH_SHORT).show();
        counter--;
        movesTextView.setText(getResources().getQuantityString(R.plurals.moves, movesCounter, movesCounter));
        return false;
    }

    /**
     * Starts a new timer.
     */
    private void startTimer(final int seconds) {
        timer = new Timer();
        time[0] = seconds;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                time[0]++;
                runOnUiThread(new Runnable()
                {
                    public void run() {

                        if (time[0] > 60){
                            timerTextView.setText(getString(R.string.minutes_seconds,time[0] / 60,time[0] % 60));
                        }
                        else{
                            timerTextView.setText(getResources().getQuantityString(R.plurals.seconds, time[0], time[0]));
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    /**
     * Checks if the puzzle has been solved.
     */
    private boolean isSolved()
    {
        for (int i = 0; i < imageButtons.size(); i ++) {
            if (imageButtons.get(i).getDrawable() != answerKey.get(i)) {
                return false;
            }
        }
        return true;
    }

}
