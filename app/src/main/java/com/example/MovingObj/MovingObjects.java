package com.example.MovingObj;

import android.graphics.Bitmap;

public abstract class MovingObjects {
    protected static final int MAP_WIDTH = 15;
    protected static final int MAP_HEIGHT = 15;
    protected int [][] gameMap = new int[MAP_HEIGHT][MAP_WIDTH];

    protected void loadGameMap(int [][] gameMap){
        for (int i=0;i<MAP_HEIGHT;i++){
            System.arraycopy(gameMap[i],0,this.gameMap[i],0,MAP_WIDTH);
        }
    }

}
