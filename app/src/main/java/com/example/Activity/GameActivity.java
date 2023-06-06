package com.example.Activity;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Listener.GameListener;
import com.example.Manager.gameManager;
import com.example.MovingObj.Player;
import com.example.myfunctions.MusicService;
import com.example.qbomb.MapData;
import com.example.qbomb.R;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener, GameListener {
    private MusicService musicService;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder= (MusicService.MusicBinder) service;
            musicService=binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
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
    private TextView timeView;

    private final String LiveIcon = "❤";
    private final String BombIcon = "💣";
    private final String PowerIcon = "💥";
    private final String SpeedIcon = "💨";

    private Handler handler;
    private Player[] players;
    private boolean[] isDead = new boolean[4];
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
        int mode = getIntent().getIntExtra("mode",0);
        initData();
        initView(mode);
        initMusic();
        initPlayer();
        initConfig();
        setInfo(getStates());
    }

    private void initConfig() {
        int map = getIntent().getIntExtra("map", 1);
        int time = getIntent().getIntExtra("time", 1);
        int mode = getIntent().getIntExtra("mode", 1);
        int diff = getIntent().getIntExtra("diff", 1);
        System.out.println("map: " + map + " time: " + time + " mode: " + mode + " diff: " + diff);
        gameView.changeMapBitmap(map);
        switch (time){
            case 1:
                gameView.setTime(60);
                break;
            case 2:
                gameView.setTime(120);
                break;
            case 3:
                gameView.setTime(180);
                break;
        }
        switch (diff){
            case 0:
                gameView.setEasy();
                break;
            case 1:
                break;
            case 2:
                gameView.setHard();
                break;
        }
    }

    private void initPlayer() {
        players = new Player[4];
        players = gameView.getPlayers();
        isDead = new boolean[]{false, false, false, false};
    }

    private int[][] getStates(){
        int [][] states = new int[4][4];
        for(int i = 0; i < 4; i++){
            if(!isDead[i]){
                if(!players[i].isAlive){
                    isDead[i] = true;
                    playerDeadImage(i);
                    setInfo(getStates());
                } else {
                    states[i] = players[i].getState();
                }
            } else
                states[i]=new int[]{0,0,0,0};
        }
        return states;
    }
    private void initMusic() {
        musicService = new MusicService();
        musicService.play(this,R.raw.gaming,true);
        Intent intent=new Intent(GameActivity.this, MusicService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private void initView(int mode){
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
        if(mode==1)
            gameView.setMap(map.mapDataList.get(7));
        else
            gameView.setMap(map.mapDataList.get((int) (Math.random()*6)));
//        gameView.setMap(map.mapDataList.get(6));
        gameView.initProps();
    }
    private void initData(){
        Intent tempIntent = getIntent();
        int figureID = tempIntent.getIntExtra("figure", 1);
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

        timeView = findViewById(R.id.game_timer);
    }

    private void playerDeadImage(int playerID){

        runOnUiThread(new Runnable() {
            public void run() {
                switch (playerID){
                    case 1:
                        ImageView temp = findViewById(R.id.char2_slot);
                        temp.setImageResource(R.drawable.slot_robot1_dead);
                        break;
                    case 2:
                        temp = findViewById(R.id.char3_slot);
                        temp.setImageResource(R.drawable.slot_robot3_dead);
                        break;
                    case 3:
                        temp = findViewById(R.id.char4_slot);
                        temp.setImageResource(R.drawable.slot_robot2_dead);
                        break;
                }
            }
        });
    }


    private void setInfo(int[][] info){
        runOnUiThread(new Runnable() {
            public void run() {
                char1_live.setText(LiveIcon + "   " + info[0][0]);
                char2_live.setText(LiveIcon + "   " + info[1][0]);
                char3_live.setText(LiveIcon + "   " + info[2][0]);
                char4_live.setText(LiveIcon + "   " + info[3][0]);
                char1_bomb.setText(BombIcon + "   " + info[0][1]);
                char2_bomb.setText(BombIcon + "   " + info[1][1]);
                char3_bomb.setText(BombIcon + "   " + info[2][1]);
                char4_bomb.setText(BombIcon + "   " + info[3][1]);
                char1_power.setText(PowerIcon + "   " + info[0][2]);
                char2_power.setText(PowerIcon + "   " + info[1][2]);
                char3_power.setText(PowerIcon + "   " + info[2][2]);
                char4_power.setText(PowerIcon + "   " + info[3][2]);
                char1_speed.setText(SpeedIcon + "   " + info[0][3]);
                char2_speed.setText(SpeedIcon + "   " + info[1][3]);
                char3_speed.setText(SpeedIcon + "   " + info[2][3]);
                char4_speed.setText(SpeedIcon + "   " + info[3][3]);
            }
        });
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
    public void onDataChanged() {
        setInfo(getStates());
    }

    @Override
    public void onTimeChanged(int time) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(time<30)
                    timeView.setTextColor(Color.RED);
                String timeString = String.format("%01d:%02d", time / 60, time % 60);
                timeView.setText(timeString);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        musicService.play(this, R.raw.gaming,true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicService.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicService.stop();
    }
}
