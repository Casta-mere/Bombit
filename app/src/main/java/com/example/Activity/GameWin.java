package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Manager.dbManager;
import com.example.myfunctions.MusicPlayer;
import com.example.qbomb.R;

public class GameWin extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private Button next;
    private TextView timeView;
    private TextView liveView;
    private dbManager dbm;
    private MusicPlayer music= new MusicPlayer();

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

        setContentView(R.layout.result_win);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        int live = intent.getIntExtra("live",0);
        String times = intent.getStringExtra("startTime");
        int score = intent.getIntExtra("score",0);
        timeView.setText(time);
        liveView.setText(String.valueOf(live));
        dbm.insert_record(times,time,String.valueOf(score));
    }

    private void initView() {
        back = findViewById(R.id.win_back);
        back.setOnClickListener(this);
        next = findViewById(R.id.win_next);
        next.setOnClickListener(this);
        timeView = findViewById(R.id.win_time);
        liveView = findViewById(R.id.win_live);
        music=new MusicPlayer();
        music.play(this,R.raw.win_bg,false);
        dbm = new dbManager(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.win_back){
            Intent intent = new Intent(GameWin.this,FirstPage.class);
            startActivity(intent);
            GameWin.this.overridePendingTransition(0, 0);
            finish();
        }
        else if(id == R.id.win_next) {
            Intent intent = new Intent(GameWin.this, GameActivity.class);
            startActivity(intent);
            GameWin.this.overridePendingTransition(0, 0);
            finish();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        music.play(this,R.raw.win_bg,false);
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
