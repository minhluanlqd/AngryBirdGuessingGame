package com.ht.minhluan.guessinggame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Collections;

public class ImageActivity extends Activity {

    TableLayout myTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        myTable = findViewById(R.id.tablelayoutImage);

        int row = 5;
        int column = 3;

        //trộn mảng
        Collections.shuffle(MainActivity.arrayName);

        // tạo dòng và cột
        for (int i = 1; i <= row; i++){
            TableRow tableRow = new TableRow(this);

            //tạo imageview cột
            for (int j = 1; j<= column; j++){
                ImageView imageView = new ImageView(this);

                float dip = 100;
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        r.getDisplayMetrics()
                );

                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px,px); // đơn vị là pixel
                imageView.setLayoutParams(layoutParams);

                final int position = column*(i-1) + j-1;

                int idImage = getResources().getIdentifier(MainActivity.arrayName.get(position),"drawable",getPackageName());
                imageView.setImageResource(idImage);
                tableRow.addView(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("ImageChoose",MainActivity.arrayName.get(position));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
            myTable.addView(tableRow);
        }
    }
}
