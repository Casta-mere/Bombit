package com.example.qbomb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameTie extends AppCompatActivity implements View.OnClickListener {
    private Button back;
    private Button next;

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

        setContentView(R.layout.result_tie);
        initView();
    }

    private void initView() {
        back = findViewById(R.id.tie_back);
        back.setOnClickListener(this);
        next = findViewById(R.id.tie_next);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id ==R.id.tie_back){
            Intent intent = new Intent(GameTie.this,FirstPage.class);
            startActivity(intent);
            GameTie.this.overridePendingTransition(0, 0);
            finish();
        }
        else if(id ==R.id.tie_next){
            Intent intent = new Intent(GameTie.this, GameActivity.class);
            startActivity(intent);
            GameTie.this.overridePendingTransition(0, 0);
            finish();
        }
    }
}