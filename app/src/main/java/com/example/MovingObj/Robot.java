package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.qbomb.R;

public class Robot extends Player{

    public Robot(Context context, int player_src, PlayerListener playerListener, int x, int y, int[][] gameMap) {
        super(context, player_src, playerListener, x, y, gameMap);
    }

}
