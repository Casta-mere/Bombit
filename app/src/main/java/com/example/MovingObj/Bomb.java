package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.qbomb.R;
import com.example.myfunctions.bitmapManipulate;

public class Bomb {
    private Bitmap bomb_all;
    private Bitmap bomb;
    private int x;
    private int y;
    private final int bomb_src = R.drawable.bomb;
    private final int FRAME_HEIGHT_COUNT = 6;
    private final int FRAME_WIDTH_COUNT = 8;
    private int mFrameWidth;
    private int mFrameHeight;

    private int bombFrame = 0;


    public Bomb(Context context,int x,int y,int bombPower){
        initData(context,x,y,bombPower);
    }

    private void initData(Context context, int x, int y,int bombPower){
        bomb_all = BitmapFactory.decodeResource(context.getResources(), bomb_src);
        mFrameWidth = bomb_all.getWidth() / FRAME_WIDTH_COUNT;
        mFrameHeight = bomb_all.getHeight() / FRAME_HEIGHT_COUNT;
        this.x = x;
        this.y = y;

    }

    public Bitmap getBomb(){
        int left = bombFrame * mFrameWidth;
        int top = 0;
        int right =  mFrameWidth;
        int bottom =  mFrameHeight;
        bomb = Bitmap.createBitmap(bomb_all, left, top, right, bottom);
        Bitmap ans =  bitmapManipulate.chopInvisible(bomb);
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
