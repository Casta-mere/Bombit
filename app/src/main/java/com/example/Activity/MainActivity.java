package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

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

        Intent intent = new Intent(MainActivity.this, FirstPage.class);
        startActivity(intent);
    }

}
