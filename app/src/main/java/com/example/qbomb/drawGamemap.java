package com.example.qbomb;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.example.MovingObj.Player;


public class drawGamemap extends View {
    final int MAP_HEIGHT = 11;
    final int MAP_WIDTH = 10;

    private int width;
    private int height;

    final int wall = 1;
    final int road = 0;
    final int player = 2;

    Bitmap bitmap_wall ;
    Bitmap bitmap_road ;
    private static final int FRAME_RATE = 80;

    private Player player1 = new Player(this.getContext());

    Paint mPaint = new Paint();
    Canvas mCanvas = new Canvas();

    int player_x = 4;
    int player_y = 1;

    int [ ][ ]  gameMap= {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,2,0,0,0,0,1},
            {1,0,1,0,1,0,1,1,0,1},
            {1,0,1,0,1,0,0,0,0,1},
            {1,0,1,0,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,1,0,1},
            {1,0,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,1,0,1},
            {1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };


    public drawGamemap (Context context, AttributeSet attrs) {
        super(context, attrs);
        initdata();
    }

    private void initdata(){
        bitmap_wall = BitmapFactory.decodeResource(getResources(), R.drawable.mushroom_down);
        bitmap_road = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, FRAME_RATE);
            }
        }, FRAME_RATE);

    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSper){
        super.onMeasure(widthMeasureSpec, heightMeasureSper);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSper);
        width = specWidthSize;
        height = specHeightSize;
        setMeasuredDimension(specWidthSize, specHeightSize);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        this.mCanvas = canvas;
        paintMap();
        drawPlayer(player_x, player_y);
    }

    private void paintMap() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                int bitmap_point = gameMap[i][j];
                Rect rect = new Rect
                        ((width/MAP_WIDTH)*j, (height/MAP_HEIGHT)*i,(width/MAP_WIDTH)*(j+1),(height/MAP_HEIGHT)*(i+1));

                switch (bitmap_point) {
                    case wall:
                        mCanvas.drawBitmap(bitmap_wall, null, rect, mPaint);
                        break;
                    case road:
                        mCanvas.drawBitmap(bitmap_road, null, rect, mPaint);
                        break;
                    case player:
                        player_y = i;
                        player_x = j;
                        gameMap[i][j] = road;
                        mCanvas.drawBitmap(bitmap_road, null, rect, mPaint);
                        break;
                }
            }
        }
    }

    private void drawPlayer(int x, int y){
        Rect rect = new Rect
                ((width/MAP_WIDTH)*x, (height/MAP_HEIGHT)*y,(width/MAP_WIDTH)*(x+1),(height/MAP_HEIGHT)*(y+1));
        Bitmap frameBitmap = player1.getBitmap();
        mCanvas.drawBitmap(frameBitmap, null, rect, mPaint);
    }

    public void setMap(int[][] mapData){
        System.arraycopy(mapData, 0, gameMap, 0, mapData.length);
        invalidate();  // 刷新画布
    }



    public void moveUp(){
        System.out.println("moveUp");
        if (gameMap[player_y-1][player_x] == road){
            player_y--;
            invalidate();
        }
        invalidate();
    }
    public void moveDown(){
        System.out.println("moveDown");
        if (gameMap[player_y+1][player_x] == road){
            player_y++;
            invalidate();
        }
    }
    public void moveLeft(){
        System.out.println("moveLeft");
        if (gameMap[player_y][player_x-1] == road){
            player_x--;
            invalidate();
        }
    }
    public void moveRight(){
        System.out.println("moveRight");
        if (gameMap[player_y][player_x+1] == road){
            player_x++;
            invalidate();
        }
    }
    public void Bomb(){
        System.out.println("Bomb");
    }

}
