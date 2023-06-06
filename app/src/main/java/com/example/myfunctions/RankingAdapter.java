package com.example.myfunctions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qbomb.R;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingHolder> {
    private ArrayList<Record> items;

    public RankingAdapter(ArrayList<Record> items) {
        this.items = items;
    }

    public void setData(ArrayList<Record> data) {
        this.items = data;
        notifyDataSetChanged();
    }

    @Override
    public RankingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_items, parent, false);
        return new RankingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingHolder holder, int position) {
        Record record = items.get(position);
        holder.itemTs.setText("   "+record.getTimes()+"  ");
        holder.itemTu.setText("    "+record.getTimeu()+"    ");
        holder.itemSc.setText("  "+record.getScore()+"  ");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}