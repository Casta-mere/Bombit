package com.example.qbomb;



import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    drawGamemap gameView;
    MapData map = new MapData();
    Button btn_up, btn_down, btn_left, btn_right, btn_bomb;
    boolean isButtonPressed = false;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 开始循环
                isButtonPressed = true;
                startTimer(ID);
                return true;
            case MotionEvent.ACTION_UP:
                // 停止循环
                isButtonPressed = false;
                stopTimer();
                return true;
            case MotionEvent.ACTION_MOVE:
                // 这里可以添加一些代码来避免多次触发
                return true;
        }

        return false;
    }

    // 在 ACTION_DOWN 中启动一个定时器，在定时器中循环执行相应操作直到 ACTION_UP
    private void startTimer(int buttonId) {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 执行操作
                if(isButtonPressed){
                    if (buttonId == R.id.actionUp) {
                        gameView.moveUp();
                    } else if (buttonId == R.id.actionDown) {
                        gameView.moveDown();
                    } else if (buttonId == R.id.actionLeft) {
                        gameView.moveLeft();
                    } else if (buttonId == R.id.actionRight) {
                        gameView.moveRight();
                    } else if (buttonId == R.id.actionBomb) {
                        gameView.Bomb();
                    }
                    // 循环执行
                    handler.postDelayed(this, 100);
                }
            }
        }, 20);
    }

    // 在 ACTION_UP 中停止定时器
    private void stopTimer() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }



}
