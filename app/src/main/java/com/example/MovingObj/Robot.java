package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.qbomb.R;

public class Robot {
    private Bitmap bitmap_robot;
    private int mFrameWidth;
    private int mFrameHeight;
    private final int FRAME_WIDTH_COUNT = 6;
    private final int FRAME_HEIGHT_COUNT = 4;
    private static int FRAME_RATE = 80;
    private int mCurrentFrame = 0;
    private int robot_x = 1;
    private int robot_y = 1;
    private final int robot_src= R.drawable.player_gray;
    public Robot(Context context){
        initdata(context);
    }

    private void initdata(Context context){
        bitmap_robot = BitmapFactory.decodeResource(context.getResources(), robot_src);
        mFrameWidth = bitmap_robot.getWidth() / FRAME_WIDTH_COUNT;
        mFrameHeight = bitmap_robot.getHeight() / FRAME_HEIGHT_COUNT;

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
        return Bitmap.createBitmap(bitmap_robot, left, top, right, bottom);

    }

    public void setRobot_place(int x, int y){
        robot_x = x;
        robot_y = y;
    }

    public void start_robot(){
        while (true){

        }

    }

}
