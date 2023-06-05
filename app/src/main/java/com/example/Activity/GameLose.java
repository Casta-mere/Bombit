package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfunctions.MusicPlayer;
import com.example.qbomb.R;

public class GameLose extends AppCompatActivity implements View.OnClickListener {
    private Button back;
    private Button next;
    private TextView timeView;
    private TextView liveView;
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

        setContentView(R.layout.result_lose);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        int time = intent.getIntExtra("time",0);
        int live = intent.getIntExtra("live",0);
        timeView.setText(String.valueOf(time)+"s");
        liveView.setText(String.valueOf(live));
    }

    private void initView() {
        back = findViewById(R.id.lose_back);
        back.setOnClickListener(this);
        next = findViewById(R.id.lose_next);
        next.setOnClickListener(this);
        timeView = findViewById(R.id.lose_time);
        liveView = findViewById(R.id.lose_live);
        music=new MusicPlayer();
        music.play(this,R.raw.lose_bg,false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id ==R.id.lose_back){
            Intent intent = new Intent(GameLose.this,FirstPage.class);
            startActivity(intent);
            GameLose.this.overridePendingTransition(0, 0);
            finish();
        }
        else if(id ==R.id.lose_next){
            Intent intent = new Intent(GameLose.this, GameActivity.class);
            startActivity(intent);
            GameLose.this.overridePendingTransition(0, 0);
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
        music.play(this,R.raw.lose_bg,false);
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
