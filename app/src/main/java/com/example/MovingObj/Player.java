package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.qbomb.R;

public class Player {
    private Bitmap bimap_player;
    private int mFrameWidth;
    private int mFrameHeight;
    private final int FRAME_WIDTH_COUNT = 6;
    private final int FRAME_HEIGHT_COUNT = 4;
    private static int FRAME_RATE = 80;
    private int mCurrentFrame = 0;
    private int mCurrentHeight = 1;
    private int player_x = 1;
    private int player_y = 1;
    private final int player_src= R.drawable.player_red;
    public Player(Context context){
        initdata(context);
    }
    private void initdata(Context context){
        bimap_player = BitmapFactory.decodeResource(context.getResources(), player_src);
        mFrameWidth = bimap_player.getWidth() / FRAME_WIDTH_COUNT;
        mFrameHeight = bimap_player.getHeight() / FRAME_HEIGHT_COUNT;

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
    public Bitmap getBitmap(){
        int left = mCurrentFrame * mFrameWidth;
        int top = mFrameHeight*mCurrentHeight;
        int right =  mFrameWidth;
        int bottom =  mFrameHeight;
        return Bitmap.createBitmap(bimap_player, left, top, right, bottom);

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
    public void goUp(){mCurrentHeight = 0;}
    public void goDown(){mCurrentHeight = 1;}
    public void goLeft(){mCurrentHeight = 2;}
    public void goRight(){mCurrentHeight = 3;}

}
