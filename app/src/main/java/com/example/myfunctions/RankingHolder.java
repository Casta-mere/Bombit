package com.example.myfunctions;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qbomb.R;

public class RankingHolder extends RecyclerView.ViewHolder {
    public TextView itemTs;
    public TextView itemTu;
    public TextView itemSc;

    public RankingHolder(View itemView) {
        super(itemView);
        itemTs = itemView.findViewById(R.id.itemTs);
        itemTu = itemView.findViewById(R.id.itemTu);
        itemSc = itemView.findViewById(R.id.itemSc);
    }
}
