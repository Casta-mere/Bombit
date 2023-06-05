package com.example.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qbomb.R;

public class SelectM extends AppCompatActivity implements View.OnClickListener{

    private Button modeSimple;

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
    }

    private void initData() {
    }

    @Override
    public void onClick(View view) {

    }
}
