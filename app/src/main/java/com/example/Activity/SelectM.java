package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qbomb.R;

public class SelectM extends AppCompatActivity implements View.OnClickListener{

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


        setContentView(R.layout.select_mode_temp);
        initView();
        initData();
    }

    private void initView() {
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
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.simple){

        } else if (id == R.id.normal) {

        } else if (id == R.id.hard) {
            
        } else if (id == R.id.min1) {

        } else if (id == R.id.min2) {

        } else if (id == R.id.min3) {

        } else if (id == R.id.temp_back) {
            Intent intent = new Intent(SelectM.this, FirstPage.class);
            startActivity(intent);
            SelectM.this.overridePendingTransition(0, 0);
            finish();
        } else if (id == R.id.temp_next) {
            Intent intent = new Intent(SelectM.this, SelectMode.class);
            startActivity(intent);
            SelectM.this.overridePendingTransition(0, 0);
            finish();
        }
    }
}
