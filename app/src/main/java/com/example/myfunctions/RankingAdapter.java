package com.example.myfunctions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qbomb.R;

public class RankingAdapter extends RecyclerView.Adapter<RankingHolder> {
    private String[] items;

    public RankingAdapter(String[] items) {
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
            String temp = "    "+items[position];
            holder.itemTs.setText(temp);
            String temp1 = "    "+items[position+1]+"    ";
            holder.itemTu.setText(temp1);
            String temp2 = items[position+2]+"    ";
            holder.itemSc.setText(temp2);
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}