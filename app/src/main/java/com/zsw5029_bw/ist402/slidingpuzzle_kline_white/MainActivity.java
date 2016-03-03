package com.zsw5029_bw.ist402.slidingpuzzle_kline_white;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView moves;
    ImageButton image1, image2,image3,image4,image5,image6,image7,image8,image9,image10,image11,image12;
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

        ((ImageButton) findViewById(R.id.pic1)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic1)).setTag(R.drawable.pic1);

        ((ImageButton) findViewById(R.id.pic2)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic3)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic4)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic5)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic6)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic7)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic8)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic9)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic10)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic11)).setOnClickListener(imagesListener);
        ((ImageButton) findViewById(R.id.pic12)).setOnClickListener(imagesListener);

        randomize();

        moves  = (TextView) findViewById(R.id.currentMoves);

    }

    private void randomize() {
    }

    private View.OnClickListener imagesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_LONG).show();
            ImageButton btn1, btn2;
            Object ob1, ob2;
            int i = 0;
            imagesClicked.put(v.getId(), v.getTag());
            if(imagesClicked.size() == 2){
                Iterator it = imagesClicked.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if(i == 0){
                        String a = pair.getKey().toString();
                        int b = Integer.parseInt(a);
                        if(b == R.id.pic1)
                            Toast.makeText(MainActivity.this, pair.getKey().toString(), Toast.LENGTH_LONG).show();
                        //ob1 = pair.getValue();
                    }else if (i == 1){
                        ob2 = pair.getValue();
                    }
                    i++;
                }
            }
//            if (counter % 2 == 0)
//                switch (v.getId()) {
//                    case R.id.pic1:
//                        previousButton = image1;
//                    case R.id.pic2:
//                        previousButton = image2;
//                    case R.id.pic3:
//                        previousButton = image3;
//                    case R.id.pic4:
//                        previousButton = image4;
//                    case R.id.pic5:
//                        previousButton = image5;
//                    case R.id.pic6:
//                        previousButton = image6;
//                    case R.id.pic7:
//                        previousButton = image7;
//                    case R.id.pic8:
//                        previousButton = image8;
//                    case R.id.pic9:
//                        previousButton = image9;
//                    case R.id.pic10:
//                        previousButton = image10;
//                    case R.id.pic11:
//                        previousButton = image11;
//                    case R.id.pic12:
//                        previousButton = image12;
//                }
//
//            else if(counter%2 == 1)
//                switch (v.getId()) {
//                    case R.id.pic1:
//                        previousButton.setImageResource(R.drawable.pic1);
//                    case R.id.pic2:
//                        previousButton.setImageResource(R.drawable.pic2);
//                    case R.id.pic3:
//                        previousButton.setImageResource(R.drawable.pic3);
//                    case R.id.pic4:
//                        previousButton.setImageResource(R.drawable.pic4);
//                    case R.id.pic5:
//                        previousButton.setImageResource(R.drawable.pic5);
//                    case R.id.pic6:
//                        previousButton.setImageResource(R.drawable.pic6);
//                    case R.id.pic7:
//                        previousButton.setImageResource(R.drawable.pic7);
//                    case R.id.pic8:
//                        previousButton.setImageResource(R.drawable.pic8);
//                    case R.id.pic9:
//                        previousButton.setImageResource(R.drawable.pic9);
//                    case R.id.pic10:
//                        previousButton.setImageResource(R.drawable.pic10);
//                    case R.id.pic11:
//                        previousButton.setImageResource(R.drawable.pic11);
//                    case R.id.pic12:
//                        previousButton.setImageResource(R.drawable.pic12);
//                }

            moves.setText(String.valueOf(counter)+" move(s)");
            counter++;        }
    };
}

