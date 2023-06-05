package com.example.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfunctions.MusicService;
import com.example.qbomb.R;

public class FirstPage extends AppCompatActivity implements View.OnClickListener {

    private Button startGame;
    private Button gameTips;
    private Button Rankings;
    private ImageView board;

    private RelativeLayout rl;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        WindowInsetsController controller = decorView.getWindowInsetsController();
        // 隐藏状态栏
        controller.hide(WindowInsets.Type.statusBars());
        // 隐藏导航栏
        controller.hide(WindowInsets.Type.navigationBars());
        controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        // 刘海屏适配
        getWindow().setDecorFitsSystemWindows(false);


        setContentView(R.layout.activity_main);
        initView();
        initData();


    }
    private void initData() {
        musicService = new MusicService();
        musicService.play(this,R.raw.first_bg,true);
        Intent intent = new Intent(FirstPage.this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    board.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });
    }

    private void initView() {
        startGame = findViewById(R.id.start_game);
        startGame.setOnClickListener(this);
        gameTips = findViewById(R.id.game_tips);
        gameTips.setOnClickListener(this);
        rl = findViewById(R.id.page_1);
        board = findViewById(R.id.board);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.start_game){
            Intent intent = new Intent(FirstPage.this, SelectMode.class);
            startActivity(intent);
            FirstPage.this.overridePendingTransition(0, 0);
            finish();
        }else if(id == R.id.game_tips){
            board.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        musicService.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        musicService.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicService.stop();
        unbindService(serviceConnection);
    }


}
