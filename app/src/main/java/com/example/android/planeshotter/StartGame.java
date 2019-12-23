package com.example.android.planeshotter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StartGame extends AppCompatActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
    }
}
