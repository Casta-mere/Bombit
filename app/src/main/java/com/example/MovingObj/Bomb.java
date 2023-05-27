package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.qbomb.R;

public class Bomb {
    private Bitmap bomb;
    private int x;
    private int y;
    private final int bomb_src = R.drawable.bomb;

    public Bomb(Context context,int x,int y){
        initData(context);
    }

    private void initData(Context context){
        bomb = BitmapFactory.decodeResource(context.getResources(), bomb_src);
    }


}
