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

import com.example.Manager.dbManager;
import com.example.myfunctions.RankingAdapter;
import com.example.myfunctions.Record;
import com.example.qbomb.R;

import java.util.ArrayList;

public class GameRank extends AppCompatActivity implements View.OnClickListener {

    private Button rank_back;
    private RecyclerView recyclerView;
    private dbManager dbm;
    private ArrayList<Record> RecordList;

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
            ArrayList<Record> temp2d = new ArrayList<>();
            for(int j=0;j<RecordList.size();j++) {
                temp2d.add(RecordList.get(j));
            }
            RankingAdapter adapter = new RankingAdapter(new ArrayList<>());
            adapter.setData(temp2d);
            recyclerView.setAdapter(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void getFromDB() {
        dbm.resetDB();
        dbm.insert_record("13:02","01:09","5000");
        dbm.insert_record("15:15","02:54","3600");
        dbm.insert_record("16:24","01:13","2200");
        dbm.insert_record("17:18","03:00","1000");
        dbm.insert_record("18:44","02:13","4700");
        dbm.insert_record("18:44","02:13","4700");
        RecordList = dbm.select_record();
    }

    private void initView() {
        rank_back = findViewById(R.id.rank_back);
        rank_back.setOnClickListener(this);
        dbm = new dbManager(this);
        RecordList = new ArrayList<Record>();
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
