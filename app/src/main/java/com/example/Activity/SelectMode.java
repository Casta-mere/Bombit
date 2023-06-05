package com.example.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfunctions.MusicService;
import com.example.qbomb.R;

public class SelectMode extends AppCompatActivity implements View.OnClickListener{
    private TextView text_classic;
    private TextView text_kill;
    private ImageView modeClassic;
    private ImageView modeKill;
    private Button simple;
    private Button normal;
    private Button hard;
    private Button min1;
    private Button min2;
    private Button min3;
    private Button tempBack;
    private Button tempNext;
    private int selectedMode;
    private int selectedTime;
    private int selectedDiff;
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


        setContentView(R.layout.select_mode);
        initView();
        initData();
        initMusic();
    }

    private void initMusic() {
        musicService = new MusicService();
        musicService.play(this,R.raw.mode_fig_bg,true);
        Intent intent=new Intent(SelectMode.this, MusicService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private void initView() {
        text_classic = findViewById(R.id.text_classic);
        text_kill = findViewById(R.id.text_kill);
        modeClassic = findViewById(R.id.mode_classic);
        modeKill = findViewById(R.id.mode_kill);
        simple = findViewById(R.id.simple);
        simple.setOnClickListener(this);
        normal = findViewById(R.id.normal);
        normal.setOnClickListener(this);
        hard = findViewById(R.id.hard);
        hard.setOnClickListener(this);
        min1 = findViewById(R.id.min1);
        min1.setOnClickListener(this);
        min2 = findViewById(R.id.min2);
        min2.setOnClickListener(this);
        min3 = findViewById(R.id.min3);
        min3.setOnClickListener(this);
        tempBack = findViewById(R.id.temp_back);
        tempBack.setOnClickListener(this);
        tempNext = findViewById(R.id.temp_next);
        tempNext.setOnClickListener(this);
    }

    private void initData() {
        min1.setSelected(true);
        simple.setSelected(true);
        modeClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeKill.setImageResource(R.drawable.map_demo3);
                text_kill.setVisibility(View.INVISIBLE);
                modeClassic.setImageResource(R.drawable.mask_mode_classic);
                text_classic.setVisibility(View.VISIBLE);
                selectedMode=0;
            }
        });
        modeKill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeClassic.setImageResource(R.drawable.map_demo2);
                text_classic.setVisibility(View.INVISIBLE);
                modeKill.setImageResource(R.drawable.mask_mode_kill);
                text_kill.setVisibility(View.VISIBLE);
                selectedMode=1;
            }
        });

        selectedMode = 0;
        selectedTime = 0;
        selectedDiff = 0;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.simple){
            selectedDiff=0;
            simple.setSelected(true);
            normal.setSelected(false);
            hard.setSelected(false);
        } else if (id == R.id.normal) {
            selectedDiff=1;
            simple.setSelected(false);
            normal.setSelected(true);
            hard.setSelected(false);
        } else if (id == R.id.hard) {
            selectedDiff=2;
            simple.setSelected(false);
            normal.setSelected(false);
            hard.setSelected(true);
        } else if (id == R.id.min1) {
            selectedTime=1;
            min1.setSelected(true);
            min2.setSelected(false);
            min3.setSelected(false);
        } else if (id == R.id.min2) {
            selectedTime=2;
            min1.setSelected(false);
            min2.setSelected(true);
            min3.setSelected(false);
        } else if (id == R.id.min3) {
            selectedTime=3;
            min1.setSelected(false);
            min2.setSelected(false);
            min3.setSelected(true);
        } else if (id == R.id.temp_back) {
            Intent intent = new Intent(SelectMode.this, FirstPage.class);
            startActivity(intent);
            SelectMode.this.overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.temp_next) {
            Intent intent = new Intent(SelectMode.this, SelectMap.class);
            intent.putExtra("time",selectedTime);
            intent.putExtra("difficulty",selectedDiff);
            intent.putExtra("mode",selectedMode);
            startActivity(intent);
            SelectMode.this.overridePendingTransition(0, 0);
            finish();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        musicService.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicService.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

}
