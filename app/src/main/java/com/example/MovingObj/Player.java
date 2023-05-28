package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.myfunctions.bitmapManipulate;

public class Player {
    private static final int FRAME_WIDTH_COUNT = 4;
    private static final int FRAME_HEIGHT_COUNT = 4;
    private static int player_FrameWidth;
    private static int player_FrameHeight;
    private static int FRAME_RATE = 200;
    private Bitmap[][] player;
    private PlayerListener playerListener;
    private int mCurrentFrame = 0;
    private int mCurrentHeight = 1;
    private int player_x = 1;
    private int player_y = 1;
    private int player_src ;
    private int player_speed = 1;
    private int player_bomb_power = 1;

    public Player(Context context,int player_src,PlayerListener playerListener){
        initPlayerBitmap(context,player_src);
        initData();
    }
    private void initData(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCurrentFrame++;
                if (mCurrentFrame >= FRAME_WIDTH_COUNT) {
                    mCurrentFrame = 0;
                }
                handler.postDelayed(this, FRAME_RATE);
            }
        }, FRAME_RATE);
    }
    private void initPlayerBitmap(Context context,int player_src){
        Bitmap player_all = BitmapFactory.decodeResource(context.getResources(), player_src);
        player_FrameWidth = player_all.getWidth() / FRAME_WIDTH_COUNT;
        player_FrameHeight = player_all.getHeight() / FRAME_HEIGHT_COUNT;
        player = new Bitmap[FRAME_HEIGHT_COUNT][FRAME_WIDTH_COUNT];

        for(int i=0;i<FRAME_HEIGHT_COUNT;i++){
            for(int j=0;j<FRAME_WIDTH_COUNT;j++){
                int left = j * player_FrameWidth;
                int top = i * player_FrameHeight;
                int right = player_FrameWidth;
                int bottom = player_FrameHeight;

                Bitmap bombFrame = Bitmap.createBitmap(player_all, left, top+2, right, bottom-2);
                player[i][j] = bitmapManipulate.chopInvisible(bombFrame);
            }
        }
    }
    public Bitmap getBitmap(){
        return player[mCurrentHeight][mCurrentFrame];
    }

    public void setPlayer_place(int x, int y){
        player_x = x;
        player_y = y;
    }
    public void setPlayer_x(int x){
        player_x = x;
    }
    public int getPlayer_x(){
        return player_x;
    }
    public void setPlayer_y(int y){
        player_y = y;
    }
    public int getPlayer_y(){
        return player_y;
    }
    public void set_Bomb(){}
    public void goUp(){mCurrentHeight = 3;}
    public void goDown(){mCurrentHeight = 0;}
    public void goLeft(){mCurrentHeight = 1;}
    public void goRight(){mCurrentHeight = 2;}

}
