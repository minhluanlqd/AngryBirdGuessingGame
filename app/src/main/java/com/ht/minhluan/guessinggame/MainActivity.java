package com.ht.minhluan.guessinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView txtScore;
    ImageView imgKey,imgChoose;
    public static ArrayList<String>arrayName;
    int REQUEST_CODE_IMAGE = 123;
    int score=100;
    String tenHinhGoc = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sharedPreferences = getSharedPreferences("dataScore",)
        Fucn();

        sharedPreferences = getSharedPreferences("dataScore",MODE_PRIVATE);
        //get score
        score = sharedPreferences.getInt("score",100);

        txtScore.setText(score + "");

        String[]array = getResources().getStringArray(R.array.list_name);
        arrayName = new ArrayList<>(Arrays.asList(array));

        //trộn mảng
        Collections.shuffle(arrayName);
        tenHinhGoc = arrayName.get(5);

        int idImage = getResources().getIdentifier(arrayName.get(5),"drawable",getPackageName());
        imgKey.setImageResource(idImage);
        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ImageActivity.class);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            String imageName = data.getStringExtra("ImageChoose");
            int idImage = getResources().getIdentifier(imageName,"drawable",getPackageName());
            imgChoose.setImageResource(idImage);
            //so sánh theo tên hình
            if (tenHinhGoc.equals(imageName)){
                Toast.makeText(this,"CORRECT!!! YOU HAVE 10 POINTS",Toast.LENGTH_SHORT).show();
                score += 10;
                saveScore();
                // đổi hình gốc
                new CountDownTimer(2000,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Collections.shuffle(arrayName);
                        tenHinhGoc = arrayName.get(5);

                        int idImage = getResources().getIdentifier(arrayName.get(5),"drawable",getPackageName());
                        imgKey.setImageResource(idImage);
                    }
                }.start();
            }else {
                score -=10;
                saveScore();
                Toast.makeText(this,"WRONG!!! MINUS 10 POINTS",Toast.LENGTH_SHORT).show();
            }
            txtScore.setText(score + "");
        }
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_CANCELED){
            Toast.makeText(this,"HEY!!! NO CHEATING, YOU CAN ONLY WATCH ONE TIME,MINUS 15 POINTS",Toast.LENGTH_LONG).show();
            score -=15;
            saveScore();
            txtScore.setText(score + "");

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reload,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuReload){
            Collections.shuffle(arrayName);
            tenHinhGoc = arrayName.get(5);

            int idImage = getResources().getIdentifier(arrayName.get(5),"drawable",getPackageName());
            imgKey.setImageResource(idImage);
        }
        return super.onOptionsItemSelected(item);
    }

    private  void saveScore(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score",score);
        editor.commit();
    }

    private void Fucn(){
        imgKey = findViewById(R.id.imageKey);
        imgChoose = findViewById(R.id.imageChoose);
        txtScore  = findViewById(R.id.textViewScore);
    }
}
