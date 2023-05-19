package com.example.qbomb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    drawGamemap gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemap);
        initView();
    }

    public void initView(){
        gameView = findViewById(R.id.gameView);
    }
}
