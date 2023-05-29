package com.example.qbomb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfunctions.MusicPlayer;

public class FirstPage extends AppCompatActivity implements View.OnClickListener {

    private Button startGame;
    private Button gameTips;

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


        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        startGame = findViewById(R.id.start_game);
        startGame.setOnClickListener(this);
        gameTips = findViewById(R.id.game_tips);
        gameTips.setOnClickListener(this);
        music=new MusicPlayer();
        music.play(this,R.raw.first_bg);
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
        music.play(this,R.raw.first_bg);
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
