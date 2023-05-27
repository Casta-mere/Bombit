package com.example.qbomb;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{
    drawGamemap gameView;
    Button btn_up, btn_down, btn_left, btn_right, btn_bomb;

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
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int ID = v.getId();
        boolean isButtonPressed = false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isButtonPressed = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isButtonPressed = false;
        }

        if (isButtonPressed){
            if (ID == R.id.actionUp){
                gameView.moveUp();
                System.out.println("up");
            } else if (ID == R.id.actionDown){
                gameView.moveDown();
            } else if (ID == R.id.actionLeft){
                gameView.moveLeft();
            } else if (ID == R.id.actionRight){
                gameView.moveRight();
            }
        }
        return false;
    }
}
