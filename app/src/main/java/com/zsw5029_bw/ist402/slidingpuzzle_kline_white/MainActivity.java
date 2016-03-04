package com.zsw5029_bw.ist402.slidingpuzzle_kline_white;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView moves;
    ImageButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, previousButton;
    List<ImageButton> buttons;
    int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeReferences();
        randomize();
        //TODO start timer...
    }

    private void initializeReferences() {
        moves  = (TextView) findViewById(R.id.currentMoves);
        buttons = new ArrayList<>();

        button1 = (ImageButton) findViewById(R.id.button1);
        button1.setOnClickListener(imagesListener);
        buttons.add(button1);
        button2 = (ImageButton) findViewById(R.id.button2);
        button2.setOnClickListener(imagesListener);
        buttons.add(button2);
        button3 = (ImageButton) findViewById(R.id.button3);
        button3.setOnClickListener(imagesListener);
        buttons.add(button3);
        button4 = (ImageButton) findViewById(R.id.button4);
        button4.setOnClickListener(imagesListener);
        buttons.add(button4);
        button5 = (ImageButton) findViewById(R.id.button5);
        button5.setOnClickListener(imagesListener);
        buttons.add(button5);
        button6 = (ImageButton) findViewById(R.id.button6);
        button6.setOnClickListener(imagesListener);
        buttons.add(button6);
        button7 = (ImageButton) findViewById(R.id.button7);
        button7.setOnClickListener(imagesListener);
        buttons.add(button7);
        button8 = (ImageButton) findViewById(R.id.button8);
        button8.setOnClickListener(imagesListener);
        buttons.add(button8);
        button9 = (ImageButton) findViewById(R.id.button9);
        button9.setOnClickListener(imagesListener);
        buttons.add(button9);
        button10 = (ImageButton) findViewById(R.id.button10);
        button10.setOnClickListener(imagesListener);
        buttons.add(button10);
        button11 = (ImageButton) findViewById(R.id.button11);
        button11.setOnClickListener(imagesListener);
        buttons.add(button11);
        button12 = (ImageButton) findViewById(R.id.button12);
        button12.setOnClickListener(imagesListener);
        buttons.add(button12);
    }

    private void randomize() {
        List<Drawable> list = new ArrayList<>();
        for (int i = 0; i < 12; i++){
            list.add(buttons.get(i).getDrawable());
        }
        Collections.shuffle(list);
        for (int i = 0; i < 12; i++){
            buttons.get(i).setImageDrawable(list.get(i));
        }
    }

    private View.OnClickListener imagesListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (counter % 2 == 0) {
                setPrevious(v);
                moves.setText(String.valueOf(counter) + " move(s)");
                counter++;
            }
            else if(counter % 2 == 1) {
                swapTiles(v);
            }
        }
    };

    public void restart(View v){
        randomize();
        counter = 0;
        moves.setText("0 move(s)");
        //TODO Make a TextView for the time and set that to zero
        //TODO ...use string resources!
    }

    private void swapTiles(View v) {
        Drawable drawable;
        switch (v.getId()) {
            case R.id.button1:
                if (isAdjacent(previousButton, button1)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button1.getDrawable());
                    button1.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button2:
                if (isAdjacent(previousButton, button2)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button2.getDrawable());
                    button2.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button3:
                if (isAdjacent(previousButton, button3)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button3.getDrawable());
                    button3.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button4:
                if (isAdjacent(previousButton, button4)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button4.getDrawable());
                    button4.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button5:
                if (isAdjacent(previousButton, button5)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button5.getDrawable());
                    button5.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button6:
                if (isAdjacent(previousButton, button6)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button6.getDrawable());
                    button6.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button7:
                if (isAdjacent(previousButton, button7)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button7.getDrawable());
                    button7.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button8:
                if (isAdjacent(previousButton, button8)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button8.getDrawable());
                    button8.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button9:
                if (isAdjacent(previousButton, button9)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button9.getDrawable());
                    button9.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button10:
                if (isAdjacent(previousButton, button10)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button10.getDrawable());
                    button10.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button11:
                if (isAdjacent(previousButton, button11)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button11.getDrawable());
                    button11.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
            case R.id.button12:
                if (isAdjacent(previousButton, button12)) {
                    drawable = previousButton.getDrawable();
                    previousButton.setImageDrawable(button12.getDrawable());
                    button12.setImageDrawable(drawable);
                    moves.setText(String.valueOf(counter) + " move(s)");
                    counter++;
                }
                break;
        }
        //TODO check if solved...
    }

    private void setPrevious(View v) {
        switch (v.getId()) {
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

    private Boolean isAdjacent(ImageButton b1, ImageButton b2){

        switch (b1.getId()) {
            case R.id.button1:
                if (b2.getId() == R.id.button2 || b2.getId() == R.id.button4)
                    return true;
                break;
            case R.id.button2:
                if (b2.getId() == R.id.button1 || b2.getId() == R.id.button3 || b2.getId()
                        == R.id.button5)
                    return true;
                break;
            case R.id.button3:
                if (b2.getId() == R.id.button2 || b2.getId() == R.id.button6)
                    return true;
                break;
            case R.id.button4:
                if (b2.getId() == R.id.button1 || b2.getId() == R.id.button5 || b2.getId()
                        == R.id.button7)
                    return true;
                break;
            case R.id.button5:
                if (b2.getId() == R.id.button2 || b2.getId() == R.id.button4 || b2.getId()
                        == R.id.button6 || b2.getId() == R.id.button8)
                    return true;
                break;
            case R.id.button6:
                if (b2.getId() == R.id.button3 || b2.getId() == R.id.button5 || b2.getId()
                        == R.id.button9)
                    return true;
                break;
            case R.id.button7:
                if (b2.getId() == R.id.button4 || b2.getId() == R.id.button8 || b2.getId()
                        == R.id.button10)
                    return true;
                break;
            case R.id.button8:
                if (b2.getId() == R.id.button5 || b2.getId() == R.id.button7 || b2.getId()
                        == R.id.button9 || b2.getId() == R.id.button11)
                    return true;
                break;
            case R.id.button9:
                if (b2.getId() == R.id.button6 || b2.getId() == R.id.button8 || b2.getId()
                        == R.id.button12)
                    return true;
                break;
            case R.id.button10:
                if (b2.getId() == R.id.button7 || b2.getId() == R.id.button11)
                    return true;
                break;
            case R.id.button11:
                if (b2.getId() == R.id.button8 || b2.getId() == R.id.button10 || b2.getId()
                        == R.id.button12)
                    return true;
                break;
            case R.id.button12:
                if (b2.getId() == R.id.button9 || b2.getId() == R.id.button11)
                    return true;
                break;
        }
        Toast.makeText(MainActivity.this, "You must select two adjacent tiles!", Toast.LENGTH_SHORT).show();
        counter--;
        moves.setText(String.valueOf(counter) + " move(s)");
        return false;
    }
}

