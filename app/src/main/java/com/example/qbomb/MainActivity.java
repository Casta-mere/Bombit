package com.example.qbomb;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    drawGamemap gameView;
    MapData map = new MapData();
    Button btn_up, btn_down, btn_left, btn_right, btn_bomb;
    boolean isButtonPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemap);
        initView();
    }

    public void initView(){
        gameView = findViewById(R.id.gameView);
        btn_up = findViewById(R.id.actionUp);
        btn_down = findViewById(R.id.actionDown);
        btn_left = findViewById(R.id.actionLeft);
        btn_right = findViewById(R.id.actionRight);
        btn_bomb = findViewById(R.id.actionBomb);

        btn_up.setOnTouchListener(this);
        btn_down.setOnTouchListener(this);
        btn_left.setOnTouchListener(this);
        btn_right.setOnTouchListener(this);
        btn_bomb.setOnTouchListener(this);

        gameView.setMap(map.mapDataList.get(1));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int ID = v.getId();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isButtonPressed = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isButtonPressed = false;
        }

        if (isButtonPressed){
            if (ID == R.id.actionUp){
                gameView.moveUp();
            } else if (ID == R.id.actionDown){
                gameView.moveDown();
            } else if (ID == R.id.actionLeft){
                gameView.moveLeft();
            } else if (ID == R.id.actionRight){
                gameView.moveRight();
            } else if (ID == R.id.actionBomb){
                gameView.Bomb();
            }
            v.performClick();
            return true;
        }
        return false;
    }



}
