package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.qbomb.R;

public class Player {
    Bitmap bimap_player;
    private int mFrameWidth;
    private int mFrameHeight;

    private final int FRAME_WIDTH_COUNT = 6;
    private final int FRAME_HEIGHT_COUNT = 4;

    //  change frame_rate when you want to change the speed of the player
    private static int FRAME_RATE = 80;
    private int mCurrentFrame = 0;

    private final int player_src= R.drawable.player_gray;

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
        int top = mFrameHeight;
        int right =  mFrameWidth;
        int bottom =  mFrameHeight;
        return Bitmap.createBitmap(bimap_player, left, top, right, bottom);

    }

}
