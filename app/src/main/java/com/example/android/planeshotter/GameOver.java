package com.example.android.planeshotter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        activity = this;
        int score = getIntent().getExtras().getInt("score");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
        int pbScore = sharedPreferences.getInt("pbScore", 0);
        if(score > pbScore){
            pbScore = score;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("pbScore", score);
            editor.commit();
        }

        TextView personalBest = findViewById(R.id.personalBestScore);
        personalBest.setText(String.valueOf(pbScore));

        TextView scoreTextview = findViewById(R.id.scoreTextview);
        scoreTextview.setText(String.valueOf(score));

        findViewById(R.id.restartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
