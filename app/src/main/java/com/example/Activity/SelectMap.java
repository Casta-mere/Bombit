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

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfunctions.MusicService;
import com.example.qbomb.R;

public class SelectMap extends AppCompatActivity implements View.OnClickListener {
    private Button back;
    private Button next;
    private Button leftMap;
    private Button rightMap;
    private ImageView showMap;
    private int selectedMap=0;
    private final int[] seriesImages = {R.drawable.map_demo0,R.drawable.map_demo1,R.drawable.map_demo3};
    private MusicService musicService;
    private int tempTime;
    private int tempMode;
    private int tempDiff;
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


        setContentView(R.layout.select_map);
        initView();
        initData();
    }

    private void initData() {
        Intent tempIntent = getIntent();
        tempTime = tempIntent.getIntExtra("time",-1);
        tempMode = tempIntent.getIntExtra("mode",-1);
        tempDiff = tempIntent.getIntExtra("difficulty",-1);
//        System.out.println(tempMode);
//        System.out.println(tempTime);
//        System.out.println(tempDiff);
        musicService = new MusicService();
        musicService.play(this,R.raw.mode_fig_bg,true);
        Intent intent=new Intent(SelectMap.this, MusicService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private void initView() {
        back = findViewById(R.id.mode_back);
        back.setOnClickListener(this);
        next = findViewById(R.id.mode_next);
        next.setOnClickListener(this);
        leftMap = findViewById(R.id.mode_left);
        leftMap.setOnClickListener(this);
        rightMap = findViewById(R.id.mode_right);
        rightMap.setOnClickListener(this);
        showMap = findViewById(R.id.inner_map);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id== R.id.mode_back){
            Intent intent = new Intent(SelectMap.this, SelectMode.class);
            startActivity(intent);
            SelectMap.this.overridePendingTransition(0, 0);
            finish();
         }
         else if(id ==  R.id.mode_next){
            musicService.pause();
            Intent intent = new Intent(SelectMap.this,SelectFigure.class);
            intent.putExtra("map",selectedMap);
            intent.putExtra("time",tempTime);
            intent.putExtra("mode",tempMode);
            intent.putExtra("diff",tempDiff);

            startActivity(intent);
            SelectMap.this.overridePendingTransition(0, 0);

            finish();
        } else if (id == R.id.mode_left) {
            selectedMap = (selectedMap + 2) % 3;
            showMap.setImageResource(seriesImages[selectedMap]);

        } else if (id == R.id.mode_right) {
            selectedMap = (selectedMap + 1) % 3;
            showMap.setImageResource(seriesImages[selectedMap]);
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
