package com.example.qbomb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfunctions.MusicPlayer;

public class SelectMode extends AppCompatActivity implements View.OnClickListener {
    private Button back;
    private Button next;
    private Button leftMap;
    private Button rightMap;
    private static MusicPlayer music ;
    private ImageView showMap;
    private int selectedMap;

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
    }

    private void initData() {
        music = new MusicPlayer();
        music.play(this, R.raw.mode_fig_bg,true);
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

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id== R.id.mode_back){
            Intent intent = new Intent(SelectMode.this,FirstPage.class);
            startActivity(intent);
            SelectMode.this.overridePendingTransition(0, 0);
            finish();
         }
         else if(id ==  R.id.mode_next){
            Intent intent = new Intent(SelectMode.this,SelectFigure.class);
            intent.putExtra("map",selectedMap);
            startActivity(intent);
            SelectMode.this.overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.mode_left) {
            selectedMap = (selectedMap - 1) % 3;

        } else if (id == R.id.mode_right) {
            selectedMap = (selectedMap + 1) % 3;
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        music.resume();
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
