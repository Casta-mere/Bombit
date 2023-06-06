package com.example.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfunctions.RankingAdapter;
import com.example.qbomb.R;

public class GameRank extends AppCompatActivity implements View.OnClickListener {

    private Button rank_back;
    private RecyclerView recyclerView;

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


        setContentView(R.layout.game_rank);
        initView();
        getFromDB();
        initData();
    }

    private void initData() {
        try {
            recyclerView = findViewById(R.id.ranking_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(GameRank.this));
            RankingAdapter adapter = new RankingAdapter(new int[] {1,2,3});
            recyclerView.setAdapter(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void getFromDB() {

    }

    private void initView() {
        rank_back = findViewById(R.id.rank_back);
        rank_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.rank_back){
            Intent intent = new Intent(GameRank.this,FirstPage.class);
            startActivity(intent);
            GameRank.this.overridePendingTransition(0, 0);
            finish();
        }
    }
}
