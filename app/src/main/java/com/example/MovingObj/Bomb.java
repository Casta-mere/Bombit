package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;

import com.example.qbomb.R;
import com.example.myfunctions.bitmapManipulate;

public class Bomb implements Runnable{
    private static final int bomb_src = R.drawable.custom_bubble_96;
    private static final int FRAME_WIDTH_COUNT = 3;
    private static final int MAP_WIDTH = 15;
    private static final int MAP_HEIGHT = 15;
    private static Bitmap[] bomb;
    private static int bomb_FrameWidth;
    private static int bomb_FrameHeight;
    private static boolean init = false;
    private BombListener bombListener;
    private boolean isExplode = false;
    private int x;
    private int y;
    private int bombPower;
    private int bombFrame = 0;
    private Player player;

    public Bomb(Context context,int x,int y,int bombPower,BombListener bombListener,Player player){
        if(!init){
            initBombBitmap(context);
            init = true;
        }
        initData(x,y,bombPower,bombListener);
        this.player = player;
    }
    private void initData(int x, int y,int bombPower, BombListener bombListener){
        this.x = x;
        this.y = y;
        this.bombPower = bombPower;
        this.bombListener = bombListener;
        Looper looper = Looper.myLooper();
        if(looper == null){
            looper=Looper.getMainLooper();
        }
        Handler handler = new Handler(looper);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isExplode){
                    handler.removeCallbacksAndMessages(this);
                    return;
                }
                bombFrame++;
                if (bombFrame >= FRAME_WIDTH_COUNT) {
                    bombFrame = 0;
                }

//                每200ms换一帧
                handler.postDelayed(this, 200);
            }
        }, 0);

    }

    public Bitmap getBomb(){
        return bomb[bombFrame];
    }
    private static void initBombBitmap(Context context){
        Bitmap bomb_all = BitmapFactory.decodeResource(context.getResources(), bomb_src);
        bomb_FrameWidth = bomb_all.getWidth() / FRAME_WIDTH_COUNT;
        bomb_FrameHeight = bomb_all.getHeight();
        System.out.println(String.format("bomb_FrameWidth is %d", bomb_FrameWidth));
        System.out.println(String.format("bomb_FrameHeight is %d", bomb_FrameHeight));
        bomb = new Bitmap[FRAME_WIDTH_COUNT];
        for(int i=0;i<FRAME_WIDTH_COUNT;i++){
            int left = i * bomb_FrameWidth;
            int top = 0;
            int right = bomb_FrameWidth;
            int bottom = bomb_FrameHeight;

            Bitmap bombFrame = Bitmap.createBitmap(bomb_all, left, top, right, bottom);
//            bitmapManipulate.findCropSize(bombFrame);
//            bomb[i] = bitmapManipulate.cropBitmap(bombFrame,30, 53, 142, 158);
//            可能受到设备分辨率影响
            bomb[i] = bitmapManipulate.cropBitmap(bombFrame,12,21,53,61);
        }

    }

    public void drawBomb(Canvas mCanvas, Paint mPaint,int  width, int height){
        float left = (width/MAP_WIDTH)*x;
        float top = (height/MAP_HEIGHT)*y;
        float right = (width/MAP_WIDTH)*(x+1);
        float bottom = (height/MAP_HEIGHT)*(y+1);
        Bitmap ans = getBomb();
        Rect rect = new Rect((int)left, (int)top, (int)right, (int)bottom);
        mCanvas.drawBitmap(ans, null, rect, mPaint);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            explode();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void explode(){
        isExplode = true;
        bombListener.onBombExplode(this, player);
    }

    public int getBomb_x() {return x;}
    public int getBomb_y() {return y;}
    public int getBombPower() {return bombPower;}
}
