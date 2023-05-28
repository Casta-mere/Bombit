package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;

import com.example.qbomb.R;
import com.example.myfunctions.bitmapManipulate;


public class Bomb {
    private Bitmap bomb_all;
    private Bitmap bomb;
    private int x;
    private int y;
    private final int bomb_src = R.drawable.custom_bubble_96;
    private final int FRAME_WIDTH_COUNT = 3;
    private int bomb_FrameWidth;
    private int bomb_FrameHeight;

    private int bombFrame = 0;


    public Bomb(Context context,int x,int y,int bombPower){
        initData(context,x,y,bombPower);
    }

    private void initData(Context context, int x, int y,int bombPower){
        bomb= BitmapFactory.decodeResource(context.getResources(), bomb_src);
        bomb_FrameWidth = bomb.getWidth() / FRAME_WIDTH_COUNT;
        bomb_FrameHeight = bomb.getHeight();
        this.x = x;
        this.y = y;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bombFrame++;
                if (bombFrame >= FRAME_WIDTH_COUNT) {
                    bombFrame = 0;
                }
                handler.postDelayed(this, 200);
            }
        }, 200);

    }

    public Bitmap getBomb(){
        int left = bombFrame * bomb_FrameWidth;
        int top = 0;
        int right = bomb_FrameWidth;
        int bottom = bomb_FrameHeight;
        Bitmap ans =  Bitmap.createBitmap(bomb, left, top, right, bottom);
//        ans = bitmapManipulate.chopInvisible(ans);
        bitmapManipulate.findCropSize(ans);
        ans = bitmapManipulate.cropBitmap(ans,30, 53, 142, 158);
        return ans;
    }

    public void drawBomb(Canvas mCanvas, Paint mPaint,int  width, int height, int MAP_WIDTH, int MAP_HEIGHT){
        float left = (width/MAP_WIDTH)*x;
        float top = (height/MAP_HEIGHT)*y;
        float right = (width/MAP_WIDTH)*(x+1);
        float bottom = (height/MAP_HEIGHT)*(y+1);
        Bitmap ans = getBomb();
        Rect rect = new Rect((int)left, (int)top, (int)right, (int)bottom);
        mCanvas.drawBitmap(ans, null, rect, mPaint);
    }

}
