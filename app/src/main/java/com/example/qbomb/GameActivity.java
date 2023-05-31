package com.example.qbomb;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MovingObj.GameListener;
import com.example.myfunctions.MusicPlayer;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener, GameListener {
    private MusicPlayer music;
    gameManager gameView;
    MapData map = new MapData();
    Button btn_up, btn_down, btn_left, btn_right, btn_bomb;
    boolean isButtonPressed = false;

    private ImageView selfFigure;
    private TextView char1_live;
    private TextView char2_live;
    private TextView char3_live;
    private TextView char4_live;

    private TextView char1_bomb;
    private TextView char2_bomb;
    private TextView char3_bomb;
    private TextView char4_bomb;

    private TextView char1_power;
    private TextView char2_power;
    private TextView char3_power;
    private TextView char4_power;

    private TextView char1_speed;
    private TextView char2_speed;
    private TextView char3_speed;
    private TextView char4_speed;

    private final String tempLive = "❤";
    private final String tempBomb = "💣";
    private final String tempPower = "💥";
    private final String tempSpeed = "\uD83D\uDCA8";

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏状态栏和导航栏
        View decorView = getWindow().getDecorView();
        WindowInsetsController controller = decorView.getWindowInsetsController();
        // 隐藏状态栏
        controller.hide(WindowInsets.Type.statusBars());
        // 隐藏导航栏
        controller.hide(WindowInsets.Type.navigationBars());
        controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        // 刘海屏适配
        getWindow().setDecorFitsSystemWindows(false);

        setContentView(R.layout.gamemap);
        initData();
        initView();
        setInfo(
                gameView.getStates()
        );
    }

    private void initView(){
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

        gameView.setGameListener(this);
        gameView.setMap(map.mapDataList.get(5));



        music = new MusicPlayer();
        music.play(this, R.raw.gaming,true);
    }
    private void initData(){
        Intent tempIntent = getIntent();
        int figureID = tempIntent.getIntExtra("figure", 0);
        int [] figureIDList = {
                R.drawable.slot_bazzi,
                R.drawable.slot_dao,
                R.drawable.slot_marid,
                R.drawable.slot_uni
        };
        selfFigure = findViewById(R.id.char1_slot);
        selfFigure.setImageResource(figureIDList[figureID-1]);
        char1_live = findViewById(R.id.char1_live);
        char2_live = findViewById(R.id.char2_live);
        char3_live = findViewById(R.id.char3_live);
        char4_live = findViewById(R.id.char4_live);

        char1_bomb = findViewById(R.id.char1_bomb);
        char2_bomb = findViewById(R.id.char2_bomb);
        char3_bomb = findViewById(R.id.char3_bomb);
        char4_bomb = findViewById(R.id.char4_bomb);

        char1_power = findViewById(R.id.char1_power);
        char2_power = findViewById(R.id.char2_power);
        char3_power = findViewById(R.id.char3_power);
        char4_power = findViewById(R.id.char4_power);

        char1_speed = findViewById(R.id.char1_speed);
        char2_speed = findViewById(R.id.char2_speed);
        char3_speed = findViewById(R.id.char3_speed);
        char4_speed = findViewById(R.id.char4_speed);

    }


    private void setInfo(int[][] info){
        char1_live.setText(tempLive +   "   " + info[0][0]);
        char2_live.setText(tempLive +   "   " + info[1][0]);
        char3_live.setText(tempLive +   "   " + info[2][0]);
        char4_live.setText(tempLive +   "   " + info[3][0]);
        char1_bomb.setText(tempBomb +   "   " + info[0][1]);
        char2_bomb.setText(tempBomb +   "   " + info[1][1]);
        char3_bomb.setText(tempBomb +   "   " + info[2][1]);
        char4_bomb.setText(tempBomb +   "   " + info[3][1]);
        char1_power.setText(tempPower + "   " + info[0][2]);
        char2_power.setText(tempPower + "   " + info[1][2]);
        char3_power.setText(tempPower + "   " + info[2][2]);
        char4_power.setText(tempPower + "   " + info[3][2]);
        char1_speed.setText(tempSpeed + "   " + info[0][3]);
        char2_speed.setText(tempSpeed + "   " + info[1][3]);
        char3_speed.setText(tempSpeed + "   " + info[2][3]);
        char4_speed.setText(tempSpeed + "   " + info[3][3]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int ID = v.getId();

        if (ID == R.id.actionBomb) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setBomb();
            }
            return true;
        }

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
                    }
                    // 循环执行
                    handler.postDelayed(this, 100);
                }
            }
        }, 0);
    }
    // 在 ACTION_UP 中停止定时器
    private void stopTimer() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void setBomb(){
        gameView.playerSetBomb();
    }

    @Override
    public void onGameLose(int time,int life) {
        Intent intent = new Intent(GameActivity.this, GameLose.class);
        intent.putExtra("time", time);
        intent.putExtra("life", life);
        startActivity(intent);
        finish();
    }

    @Override
    public void onGameWin(int time,int life) {
        Intent intent = new Intent(GameActivity.this, GameWin.class);
        intent.putExtra("time", time);
        intent.putExtra("life", life);
        startActivity(intent);
        finish();
    }

    @Override
    public void onGameTie(int time,int life) {
        Intent intent = new Intent(GameActivity.this, GameTie.class);
        intent.putExtra("time", time);
        intent.putExtra("life", life);
        startActivity(intent);
        finish();
    }
    @Override
    public void onResume(){
        super.onResume();
        music.play(this, R.raw.gaming,true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(music!=null){
            music.stop();
            music.release();
        }
    }
}
