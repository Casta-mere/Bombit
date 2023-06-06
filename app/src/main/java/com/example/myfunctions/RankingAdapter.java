package com.example.myfunctions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qbomb.R;

public class RankingAdapter extends RecyclerView.Adapter<RankingHolder> {
    private int[] items;

    public RankingAdapter(int[] items) {
        this.items = items;
    }

    @Override
    public RankingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_items, parent, false);
        return new RankingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingHolder holder, int position) {
        if (position %3 == 0) {
            holder.itemTs.setText(String.valueOf(items[position]));
            holder.itemTu.setText(String.valueOf(items[position+1]));
            holder.itemSc.setText(String.valueOf(items[position+2]));
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}