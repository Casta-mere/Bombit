package com.example.qbomb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    drawGamemap gameView;
    MapData mapData = new MapData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemap);
        initView();
    }

    public void initView(){
        gameView = findViewById(R.id.gameView);
        mapData.mapdata();
        gameView.setMap(mapData.mapDataList.get(1));
    }
}