package com.zsw5029_bw.ist402.slidingpuzzle_kline_white;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    TextView moves;
    ImageButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
    LinkedHashMap<Integer, Object> imagesClicked = new LinkedHashMap<>();
    ImageButton previousButton;
    int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_LONG);

        //HashMap<String, Integer> pictures = new HashMap<String, Integer>()
          //      pictures.put("pic1",R.drawable.pic1);
                //pictures

        initializeButtons();
        randomize();

        moves  = (TextView) findViewById(R.id.currentMoves);

    }

    private void initializeButtons() {
        button1 = (ImageButton) findViewById(R.id.button1);
        button1.setOnClickListener(imagesListener);
        button1.setTag(R.drawable.pic1);

        button2 = (ImageButton) findViewById(R.id.button2);
        button2.setOnClickListener(imagesListener);
        button2.setTag(R.drawable.pic2);


        button3 = (ImageButton) findViewById(R.id.button3);
        button3.setOnClickListener(imagesListener);
        button3.setTag(R.drawable.pic3);

        button4 = (ImageButton) findViewById(R.id.button4);
        button4.setOnClickListener(imagesListener);
        button4.setTag(R.drawable.pic4);

        button5 = (ImageButton) findViewById(R.id.button5);
        button5.setOnClickListener(imagesListener);
        button5.setTag(R.drawable.pic5);

        button6 = (ImageButton) findViewById(R.id.button6);
        button6.setOnClickListener(imagesListener);
        button6.setTag(R.drawable.pic6);

        button7 = (ImageButton) findViewById(R.id.button7);
        button7.setOnClickListener(imagesListener);
        button7.setTag(R.drawable.pic7);

        button8 = (ImageButton) findViewById(R.id.button8);
        button8.setOnClickListener(imagesListener);
        button8.setTag(R.drawable.pic8);

        button9 = (ImageButton) findViewById(R.id.button9);
        button9.setOnClickListener(imagesListener);
        button9.setTag(R.drawable.pic9);

        button10 = (ImageButton) findViewById(R.id.button10);
        button10.setOnClickListener(imagesListener);
        button10.setTag(R.drawable.pic10);

        button11 = (ImageButton) findViewById(R.id.button11);
        button11.setOnClickListener(imagesListener);
        button11.setTag(R.drawable.pic11);

        button12 = (ImageButton) findViewById(R.id.button12);
        button12.setOnClickListener(imagesListener);
        button12.setTag(R.drawable.pic12);
    }

    private void randomize() {
    }

    private View.OnClickListener imagesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Drawable drawable;
            int i = 0;

//            imagesClicked.put(v.getId(), v.getTag());
//            if(imagesClicked.size() == 2){
//                Iterator it = imagesClicked.entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry pair = (Map.Entry) it.next();
//                    if(i == 0){
//                        String a = pair.getKey().toString();
//                        int b = Integer.parseInt(a);
//                        if(b == R.id.button1)
//                            Toast.makeText(MainActivity.this, pair.getKey().toString(), Toast.LENGTH_LONG).show();
//                        //ob1 = pair.getValue();
//                    }else if (i == 1){
//                        ob2 = pair.getValue();
//                    }
//                    i++;
//                }
//            }
            if (counter % 2 == 0)
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

            else if(counter%2 == 1)
                switch (v.getId()) {
                    case R.id.button1:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button1.getDrawable());
                        button1.setImageDrawable(drawable);
                        break;
                    case R.id.button2:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button2.getDrawable());
                        button2.setImageDrawable(drawable);
                        break;
                    case R.id.button3:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button3.getDrawable());
                        button3.setImageDrawable(drawable);
                        break;
                    case R.id.button4:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button4.getDrawable());
                        button4.setImageDrawable(drawable);
                        break;
                    case R.id.button5:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button5.getDrawable());
                        button5.setImageDrawable(drawable);
                        break;
                    case R.id.button6:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button6.getDrawable());
                        button6.setImageDrawable(drawable);
                        break;
                    case R.id.button7:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button7.getDrawable());
                        button7.setImageDrawable(drawable);
                        break;
                    case R.id.button8:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button8.getDrawable());
                        button8.setImageDrawable(drawable);
                        break;
                    case R.id.button9:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button9.getDrawable());
                        button9.setImageDrawable(drawable);
                        break;
                    case R.id.button10:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button10.getDrawable());
                        button10.setImageDrawable(drawable);
                        break;
                    case R.id.button11:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button11.getDrawable());
                        button11.setImageDrawable(drawable);
                        break;
                    case R.id.button12:
                        drawable = previousButton.getDrawable();
                        previousButton.setImageDrawable(button12.getDrawable());
                        button12.setImageDrawable(drawable);
                        break;
                }

            moves.setText(String.valueOf(counter)+" move(s)");
            counter++;        }
    };
}

