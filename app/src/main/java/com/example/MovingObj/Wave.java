package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;

import com.example.qbomb.MapData;
import com.example.qbomb.R;

import java.util.ArrayList;

public class Wave {
    private static final int wave_src_up = R.drawable.wave1;
    private static final int wave_src_down = R.drawable.wave2;
    private static final int wave_src_left = R.drawable.wave3;
    private static final int wave_src_right = R.drawable.wave4;
    private static final int wave_src = R.drawable.wave;
    private static final int FRAME_WIDTH_COUNT = 8;
    private static final int MAP_WIDTH = 15;
    private static final int MAP_HEIGHT = 15;
    private static final int[] waveOrder = {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,3,4,5,6,7};

    private static Bitmap[][] wave;
    private static Bitmap[] waveCenter;
    private int[][] gameMap;
    private static int wave_FrameWidth;
    private static int wave_FrameHeight;
    private static boolean init = false;
    private int x;
    private int y;
    private int bombPower;
    private int waveFrame = 0;
    private WaveListener waveListener;
    private boolean isStop = false;


    public Wave(Context context, int x, int y, int bombPower, int[][] gameMap, WaveListener waveListener){
        if(!init){
            initWaveBitmap(context);
            init = true;
        }
        initData(x,y,bombPower,gameMap, waveListener);

    }
    private void initData(int x, int y, int bombPower, int[][] gameMap, WaveListener waveListener){
        this.x = x;
        this.y = y;
        this.bombPower = bombPower;
        this.gameMap = new int[MAP_HEIGHT][MAP_WIDTH];
        System.arraycopy(gameMap,0,this.gameMap,0,gameMap.length);
        this.waveListener = waveListener;
        Looper looper = Looper.myLooper();
        if(looper == null){
            looper=Looper.getMainLooper();
        }
        Handler handler = new Handler(looper);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                waveFrame++;
                if (waveFrame >= waveOrder.length) {
                    finish();
                    handler.removeCallbacksAndMessages(this);
                    return;
                }
                handler.postDelayed(this, 25);
            }
        }, 10);

    }

    private static void initWaveBitmap(Context context){
        Bitmap[] temp_wave = new Bitmap[4];
        temp_wave[0] = BitmapFactory.decodeResource(context.getResources(), wave_src_up);
        temp_wave[1] = BitmapFactory.decodeResource(context.getResources(), wave_src_down);
        temp_wave[2] = BitmapFactory.decodeResource(context.getResources(), wave_src_left);
        temp_wave[3] = BitmapFactory.decodeResource(context.getResources(), wave_src_right);
        Bitmap temp_waveCenter = BitmapFactory.decodeResource(context.getResources(), wave_src);

        wave_FrameWidth = temp_wave[0].getWidth() / FRAME_WIDTH_COUNT;
        wave_FrameHeight = temp_wave[0].getHeight();
        wave = new Bitmap[wave_FrameHeight][wave_FrameWidth];
        waveCenter = new Bitmap[3];

        for(int i=0;i<4;i++){
            for(int j=0;j<FRAME_WIDTH_COUNT;j++){
                int left = j * wave_FrameWidth;
                int top = 0;
                int right = wave_FrameWidth;
                int bottom = wave_FrameHeight;
                wave[i][j] = Bitmap.createBitmap(temp_wave[i],left,top,right,bottom);
            }
        }
        wave_FrameWidth = temp_waveCenter.getWidth() / 3;
        wave_FrameHeight = temp_waveCenter.getHeight();
        for(int i=0;i<3;i++){
            int left = i * wave_FrameWidth;
            int top = 0;
            int right = wave_FrameWidth;
            int bottom = wave_FrameHeight;

            waveCenter[i] = Bitmap.createBitmap(temp_waveCenter,left,top,right,bottom);
        }
    }
    public void drawWave(Canvas mCanvas, Paint mPaint,int width,int height){
        int center_x = x;
        int center_y = y;

        ArrayList<int[]> wave_left = new ArrayList<>();
        for (int i=0;i<bombPower;i++){
            center_x--;
            int Type = gameMap[center_y][center_x];
            if( Type == MapData.ROAD||Type == MapData.BLOCK)
                wave_left.add(new int[]{center_y,center_x});
            if( Type != MapData.ROAD)
                break;
        }

        center_x = x;
        ArrayList<int[]> wave_right = new ArrayList<>();
        for (int i=0;i<bombPower;i++){
            center_x++;
            int Type = gameMap[center_y][center_x];
            if( Type == MapData.ROAD||Type == MapData.BLOCK)
                wave_right.add(new int[]{center_y,center_x});
            if( Type != MapData.ROAD)
                break;
        }

        center_x = x;
        ArrayList<int[]> wave_up = new ArrayList<>();
        for (int i=0;i<bombPower;i++){
            center_y--;
            int Type = gameMap[center_y][center_x];
            if( Type == MapData.ROAD||Type == MapData.BLOCK)
                wave_up.add(new int[]{center_y,center_x});
            if( Type != MapData.ROAD)
                break;
        }

        center_y = y;
        ArrayList<int[]> wave_down = new ArrayList<>();
        for (int i=0;i<bombPower;i++){
            center_y++;
            int Type = gameMap[center_y][center_x];
            if( Type == MapData.ROAD||Type == MapData.BLOCK)
                wave_down.add(new int[]{center_y,center_x});
            if( Type != MapData.ROAD)
                break;
        }



        for (int[] i:wave_left){
            drawOther(mCanvas,mPaint,width,height,2, i[0], i[1]);
        }
        for (int[] i:wave_right){
            drawOther(mCanvas,mPaint,width,height,3, i[0], i[1]);
        }
        for (int[] i:wave_up){
            drawOther(mCanvas,mPaint,width,height,0, i[0], i[1]);
        }
        for (int[] i:wave_down){
            drawOther(mCanvas,mPaint,width,height,1, i[0], i[1]);
        }
        drawCenter(mCanvas,mPaint,width,height);
    }
    public void drawOther(Canvas mCanvas,Paint mPaint,int width,int height,int direction,int y,int x){
        float left = (width/MAP_WIDTH)*x;
        float top = (height/MAP_HEIGHT)*y;
        float right = (width/MAP_WIDTH)*(x+1);
        float bottom = (height/MAP_HEIGHT)*(y+1);
        Bitmap ans = wave[direction][waveOrder[waveFrame]];
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        mCanvas.drawBitmap(ans,null,rect,mPaint);
    }

    public void drawCenter(Canvas mCanvas,Paint mPaint,int width,int height){
        float left = (width/MAP_WIDTH)*x;
        float top = (height/MAP_HEIGHT)*y;
        float right = (width/MAP_WIDTH)*(x+1);
        float bottom = (height/MAP_HEIGHT)*(y+1);
        Bitmap ans = waveCenter[waveOrder[waveFrame%3]];
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        mCanvas.drawBitmap(ans,null,rect,mPaint);
    }

    private void finish(){
        waveListener.onWaveEnd(this);
        isStop = true;
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public int getBombPower() {return bombPower;}
}
