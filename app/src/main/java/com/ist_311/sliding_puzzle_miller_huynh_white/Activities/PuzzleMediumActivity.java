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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleMediumActivity extends AppCompatActivity {

    // UI components
    private TableLayout tableLayout;
    private TextView movesTextView, timerTextView;
    private ImageButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
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
    final int[] time = {1};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_medium);
        initializeReferences();
        createPuzzle(BitmapFactory.decodeResource(getResources(), R.drawable.emilia));
        startTimer(1);
    }

    /**
     * Initializes all references.
     */
    private void initializeReferences() {

        // Initializing Layout
        tableLayout = (TableLayout) findViewById(R.id.table_layout);

        // Initializing TextViews
        movesTextView = (TextView) findViewById(R.id.currentMoves);
        timerTextView = (TextView) findViewById(R.id.editTextTimer);

        // Initializing Lists
        imageButtons = new ArrayList<>();
        answerKey = new ArrayList<>();

        // TODO GET DIFFICULTY
        rows = 5;
        cols = 5;

        // Initializing ImageButtons and adding to list
        createBoard();

        configureButtons();

        // Initializing Pause Button
        pauseButton = (Button) findViewById(R.id.button_pause);
        resetButton = (Button) findViewById(R.id.button_pause);

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
    private View.OnClickListener imagesListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (counter % 2 == 0) {
                setPrevious(view);
                movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                counter++;
            }
            else if(counter % 2 == 1) {
                swapTiles(view);
            }
        }
    };

    /**
     * Restarts the puzzle.
     * @param view the restart button.
     */
    public void restart(View view){

        // Clearing
        timer.cancel();
        counter = 0;
        movesCounter = 0;
        answerKey.clear();
        movesTextView.setText(R.string.default_moves);
        timerTextView.setText(R.string.default_time);

        // Restarting
        createPuzzle(BitmapFactory.decodeResource(getResources(), R.drawable.emilia));
        enableButtons();
        pauseButton.setEnabled(true);
        startTimer(1);
    }

    /**
     * Pauses the game.
     * @param view the pause button.
     */
    public void pause(View view){
        isPause = !isPause;
        if (isPause) {
            disableButtons();
            timer.cancel();
        } else {
            enableButtons();
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
        switch (view.getId()) {
            case R.id.button1:
                if (isAdjacent(button1)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button1.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button1.getDrawable());
                    button1.setImageDrawable(drawable);
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                    counter++;
                }
                break;
            case R.id.button2:
                if (isAdjacent(button2)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button2.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button2.getDrawable());
                    button2.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button3:
                if (isAdjacent(button3)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button3.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button3.getDrawable());
                    button3.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button4:
                if (isAdjacent(button4)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button4.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button4.getDrawable());
                    button4.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button5:
                if (isAdjacent(button5)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button5.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button5.getDrawable());
                    button5.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button6:
                if (isAdjacent(button6)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button6.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button6.getDrawable());
                    button6.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button7:
                if (isAdjacent(button7)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button7.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button7.getDrawable());
                    button7.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button8:
                if (isAdjacent(button8)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button8.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button8.getDrawable());
                    button8.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button9:
                if (isAdjacent(button9)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button9.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button9.getDrawable());
                    button9.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button10:
                if (isAdjacent(button10)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button10.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button10.getDrawable());
                    button10.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button11:
                if (isAdjacent(button11)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button11.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button11.getDrawable());
                    button11.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
            case R.id.button12:
                if (isAdjacent(button12)) {
                    drawable = previousButton.getDrawable();
                    previousButton.startAnimation(previousAnimation);
                    button12.startAnimation(currentAnimation);
                    previousButton.setImageDrawable(button12.getDrawable());
                    button12.setImageDrawable(drawable);
                    counter++;
                    movesCounter++;
                    movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
                }
                break;
        }
        checkSolved();
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

        switch (view.getId()) {
            case R.id.button1:
                previousButton = button1;
                break;
            case R.id.button2:
                previousButton = button2;
                break;
            case R.id.button3:
                previousButton = button3;
                break;
            case R.id.button4:
                previousButton = button4;
                break;
            case R.id.button5:
                previousButton = button5;
                break;
            case R.id.button6:
                previousButton = button6;
                break;
            case R.id.button7:
                previousButton = button7;
                break;
            case R.id.button8:
                previousButton = button8;
                break;
            case R.id.button9:
                previousButton = button9;
                break;
            case R.id.button10:
                previousButton = button10;
                break;
            case R.id.button11:
                previousButton = button11;
                break;
            case R.id.button12:
                previousButton = button12;
                break;
        }
    }

    /**
     * Checks ImageButtons are adjacent and sets animations.
     * @param button the last ImageButton clicked.
     * @return true or false.
     */
    private Boolean isAdjacent(ImageButton button){

        switch (previousButton.getId()) {

            case R.id.button1:
                if (button.getId() == R.id.button2) {
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                else if (button.getId() == R.id.button4) {
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button2:
                if (button.getId() == R.id.button1){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
                else if(button.getId() == R.id.button3){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                else if(button.getId() == R.id.button5){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button3:
                if (button.getId() == R.id.button2){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
                else if(button.getId() == R.id.button6){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button4:
                if (button.getId() == R.id.button1){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if(button.getId() == R.id.button5){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                else if(button.getId() == R.id.button7){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button5:
                if (button.getId() == R.id.button2){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if (button.getId() == R.id.button4){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
                else if (button.getId() == R.id.button6){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                else if (button.getId() == R.id.button8){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button6:
                if (button.getId() == R.id.button3){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if (button.getId() == R.id.button5){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
                else if (button.getId() == R.id.button9){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button7:
                if (button.getId() == R.id.button4){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if (button.getId() == R.id.button8){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                else if (button.getId() == R.id.button10){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button8:
                if (button.getId() == R.id.button5){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if (button.getId() == R.id.button7){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
                else if(button.getId() == R.id.button9){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                else if (button.getId() == R.id.button11){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button9:
                if (button.getId() == R.id.button6){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if (button.getId() == R.id.button8){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
                else if (button.getId() == R.id.button12){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    return true;
                }
                break;
            case R.id.button10:
                if (button.getId() == R.id.button7){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if(button.getId() == R.id.button11){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                break;
            case R.id.button11:
                if (button.getId() == R.id.button8){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if (button.getId() == R.id.button10){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
                else if (button.getId() == R.id.button12){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    return true;
                }
                break;
            case R.id.button12:
                if (button.getId() == R.id.button9){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    return true;
                }
                else if (button.getId() == R.id.button11){
                    previousAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
                    currentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
                    return true;
                }
        }
        Toast.makeText(PuzzleMediumActivity.this, "You must select two adjacent tiles!", Toast.LENGTH_SHORT).show();
        counter--;
        movesTextView.setText(new StringBuilder().append(String.valueOf(movesCounter)).append(" move(s)").toString());
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
                            timerTextView.setText(time[0] / 60 + "m " + time[0] % 60 + "s");
                        }
                        else{
                            timerTextView.setText(time[0] + " seconds");
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    /**
     * Checks if the puzzle has been solved.
     */
    private void checkSolved()
    {
        if (imageButtons.get(0).getDrawable() == answerKey.get(0) && imageButtons.get(2).getDrawable() == answerKey.get(2) && imageButtons.get(3).getDrawable() == answerKey.get(3) && imageButtons.get(4).getDrawable() == answerKey.get(4)
                && imageButtons.get(5).getDrawable() == answerKey.get(5) && imageButtons.get(6).getDrawable() == answerKey.get(6) && imageButtons.get(7).getDrawable() == answerKey.get(7) && imageButtons.get(8).getDrawable() == answerKey.get(8)
                && imageButtons.get(9).getDrawable() == answerKey.get(9) && imageButtons.get(10).getDrawable() == answerKey.get(10) && imageButtons.get(11).getDrawable() == answerKey.get(11) && imageButtons.get(1).getDrawable() == answerKey.get(1))
        {
            timer.cancel();
            disableButtons();
            pauseButton.setEnabled(false);
            Toast.makeText(PuzzleMediumActivity.this, "Congratulations You Win!!!!!", Toast.LENGTH_LONG).show();
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
}
