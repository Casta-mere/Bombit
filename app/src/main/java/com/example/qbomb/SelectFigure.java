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

public class SelectFigure extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private Button next;
    private Button char1;
    private Button char2;
    private Button char3;
    private Button char4;
    private ImageView select1;
    private ImageView select2;
    private ImageView select3;
    private ImageView select4;


    private MusicPlayer music;

    private int selectedFigure=1;
    private int selectedMode;

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

        setContentView(R.layout.select_figure);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        selectedMode = intent.getIntExtra("map",0);
        music = new MusicPlayer();
        music.play(this, R.raw.mode_fig_bg,true);
    }

    private void initView() {
        back = findViewById(R.id.figure_back);
        back.setOnClickListener(this);
        next = findViewById(R.id.figure_next);
        next.setOnClickListener(this);
        char1 = findViewById(R.id.char1);
        char1.setOnClickListener(this);
        char2 = findViewById(R.id.char2);
        char2.setOnClickListener(this);
        char3 = findViewById(R.id.char3);
        char3.setOnClickListener(this);
        char4 = findViewById(R.id.char4);
        char4.setOnClickListener(this);
        select1 = findViewById(R.id.arrow_select1);
        select1.setVisibility(View.VISIBLE);
        select2 = findViewById(R.id.arrow_select2);
        select2.setVisibility(View.INVISIBLE);
        select3 = findViewById(R.id.arrow_select3);
        select3.setVisibility(View.INVISIBLE);
        select4 = findViewById(R.id.arrow_select4);
        select4.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.figure_back){
            System.out.println("figure back");
            Intent intent = new Intent(SelectFigure.this, SelectMode.class);
            startActivity(intent);
            SelectFigure.this.overridePendingTransition(0, 0);
            finish();
        }
        else if(id == R.id.figure_next){
            System.out.println("figure next");
            Intent intent = new Intent(SelectFigure.this,GameActivity.class);
            intent.putExtra("map",selectedMode);
            intent.putExtra("figure",selectedFigure);
            startActivity(intent);
            SelectFigure.this.overridePendingTransition(0, 0);
            finish();
        }
        else if(id == R.id.char1){
            select1.setVisibility(View.VISIBLE);
            select2.setVisibility(View.INVISIBLE);
            select3.setVisibility(View.INVISIBLE);
            select4.setVisibility(View.INVISIBLE);
            selectedFigure = 1;
        }
        else if(id == R.id.char2){
            select1.setVisibility(View.INVISIBLE);
            select2.setVisibility(View.VISIBLE);
            select3.setVisibility(View.INVISIBLE);
            select4.setVisibility(View.INVISIBLE);
            selectedFigure = 2;
        }
        else if(id == R.id.char3){
            select1.setVisibility(View.INVISIBLE);
            select2.setVisibility(View.INVISIBLE);
            select3.setVisibility(View.VISIBLE);
            select4.setVisibility(View.INVISIBLE);
            selectedFigure = 3;
        }
        else if(id == R.id.char4){
            select1.setVisibility(View.INVISIBLE);
            select2.setVisibility(View.INVISIBLE);
            select3.setVisibility(View.INVISIBLE);
            select4.setVisibility(View.VISIBLE);
            selectedFigure = 4;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
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
