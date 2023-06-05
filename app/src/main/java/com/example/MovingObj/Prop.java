package com.example.MovingObj;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


import com.example.Listener.PropListener;
import com.example.qbomb.MapData;
import com.example.qbomb.R;
import com.example.myfunctions.bitmapManipulate;

public class Prop implements Runnable{
    private static final int PROP_TYPE_NUM = 4;
    private static final int PROP_TYPE_BOMB_NUM = 0;
    private static final int PROP_TYPE_SPEED = 1;
    private static final int PROP_TYPE_RANGE = 2;
    private static final int PROP_TYPE_LIVE = 3;
    private static final int bomb_src = R.drawable.bombnum;
    private static final int speed_src = R.drawable.speed;
    private static final int range_src = R.drawable.power;
    private static final int live_src = R.drawable.heart;
    private static final int MAP_WIDTH = MapData.MAP_WIDTH;
    private static final int MAP_HEIGHT = MapData.MAP_HEIGHT;
    private static Bitmap[] prop;
    private static boolean init = false;
    private int x;
    private int y;
    private int propType;
    private PropListener propListener;
    private int propFrame = 0;
    private boolean flag = true;

    public Prop(Context context,int x,int y,int propType,PropListener propListener){
        if(!init){
            initPropBitmap(context);
            init = true;
        }
        initData(x,y,propType,propListener);
    }
    private void initData(int x, int y, int propType, PropListener propListener) {
        this.x = x;
        this.y = y;
        this.propType = propType;
        this.propListener = propListener;
    }
    private void initPropBitmap(Context context) {
        Bitmap bombNum = BitmapFactory.decodeResource(context.getResources(),bomb_src);
        Bitmap speed = BitmapFactory.decodeResource(context.getResources(),speed_src);
        Bitmap range = BitmapFactory.decodeResource(context.getResources(),range_src);
        Bitmap live = BitmapFactory.decodeResource(context.getResources(),live_src);
        prop = new Bitmap[PROP_TYPE_NUM];
        prop[PROP_TYPE_BOMB_NUM] = bitmapManipulate.chopInvisible(bombNum);
        prop[PROP_TYPE_SPEED] = bitmapManipulate.chopInvisible(speed);
        prop[PROP_TYPE_RANGE] = bitmapManipulate.chopInvisible(range);
        prop[PROP_TYPE_LIVE] = bitmapManipulate.chopInvisible(live);

//        prop[PROP_TYPE_BOMB_NUM] = bombNum;
//        prop[PROP_TYPE_SPEED] = speed;
//        prop[PROP_TYPE_RANGE] = range;
//        prop[PROP_TYPE_LIVE] = live;

    }
    public void drawProp(Canvas canvas, Paint paint,int width,int height){
        int pivotLR = (int) (width / MAP_WIDTH *0.1);
        int pivotTB = (int) (height / MAP_HEIGHT *0.1);
        float left = (width / MAP_WIDTH) * x+pivotLR;
        float top = (height / MAP_HEIGHT) * y-propFrame/5+pivotTB;
        float right = (width / MAP_WIDTH) * (x + 1)-pivotLR;
        float bottom = (height / MAP_HEIGHT) * (y + 1)-propFrame/5-pivotTB;
        if(flag){
            if(propFrame>=50)
                flag = false;
            else
                propFrame++;
        } else {
            if(propFrame<=0)
                flag = true;
            else
                propFrame--;
        }
        Rect dst = new Rect((int)left,(int)top,(int)right,(int)bottom);
        canvas.drawBitmap(prop[propType],null,dst,paint);;
    }
    public int getProp_x(){return x;}
    public int getProp_y(){return y;}
    public int getPropType(){return propType;}

    @Override
    public void run() {
        try {
            while(propListener.PropDetect(this)) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
