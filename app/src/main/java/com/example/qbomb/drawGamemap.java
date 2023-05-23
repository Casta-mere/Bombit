package com.example.qbomb;


import android.animation.Animator;
import android.animation.ValueAnimator;
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
import android.view.animation.LinearInterpolator;

import com.example.MovingObj.Player;

public class drawGamemap extends View {
    final int MAP_HEIGHT =15;
    final int MAP_WIDTH = 15;
    private int width;
    private int height;
    final int wall = 1;
    final int road = 0;
    final int player = 2;
    Bitmap bitmap_wall ;
    Bitmap bitmap_road ;
    private static final int FRAME_RATE = 80;
    private static final float PLAYER_SPEED = 300f;
    private Player player1 = new Player(this.getContext());
    Paint mPaint = new Paint();
    Canvas mCanvas = new Canvas();
    float player_x = 4;
    float player_y = 1;
    int [ ][ ]  gameMap= {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,1,1,1,1,1,0,1},
            {1,0,1,0,0,0,0,0,0,0,0,0,1,0,1},
            {1,0,1,0,1,1,1,1,1,1,1,0,1,0,1},
            {1,0,1,0,1,0,0,0,0,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
    private ValueAnimator playerAnimator = new ValueAnimator();
    public drawGamemap (Context context, AttributeSet attrs) {
        super(context, attrs);
        initdata();
    }
    private void initdata(){
        bitmap_wall = BitmapFactory.decodeResource(getResources(), R.drawable.block_3_n);
        bitmap_road = BitmapFactory.decodeResource(getResources(), R.drawable.path_1);

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
    private void drawPlayer(float x, float y){
        float left = (width/MAP_WIDTH)*x;
        float top = (height/MAP_HEIGHT)*y;
        float right = (width/MAP_WIDTH)*(x+1);
        float bottom = (height/MAP_HEIGHT)*(y+1);

        Rect rect = new Rect((int)left, (int)top, (int)right, (int)bottom);
        Bitmap frameBitmap = player1.getBitmap();
        mCanvas.drawBitmap(frameBitmap, null, rect, mPaint);
    }
    public void setMap(int[][] mapData){
        System.arraycopy(mapData, 0, gameMap, 0, mapData.length);
        invalidate();  // 刷新画布
    }
    public void moveUp() {
        int x = (int) player_x;
        int y = (int) player_y;
        if (gameMap[y - 1][x] == road) {
            float startY = player_y;
            float targetY = y - 1;
            if (playerAnimator != null) {
                playerAnimator.cancel(); // 取消前一个动画
            }
            playerAnimator = ValueAnimator.ofFloat(startY, targetY);
            playerAnimator.setDuration((long) Math.abs((targetY - startY) * PLAYER_SPEED));
            playerAnimator.setInterpolator(new LinearInterpolator()); // 线性插值器，使动画速度保持恒定
            playerAnimator.addUpdateListener(animation -> {
                player_y = (float) animation.getAnimatedValue();
                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_y = targetY;
                    invalidate();
                }
                @Override
                public void onAnimationCancel(Animator animation) { }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
            playerAnimator.start();
        }
    }
    public void moveDown(){
        int x = (int)player_x;
        int y = (int)player_y;
        if (gameMap[y+1][x] == road) {
            float startY = player_y;
            float targetY = y + 1;
            if (playerAnimator != null) {
                playerAnimator.cancel(); // 取消前一个动画
            }
            playerAnimator = ValueAnimator.ofFloat(startY, targetY);
            playerAnimator.setDuration((long) Math.abs((targetY - startY) * PLAYER_SPEED));
            playerAnimator.setInterpolator(new LinearInterpolator()); // 线性插值器，使动画速度保持恒定
            playerAnimator.addUpdateListener(animation -> {
                player_y = (float) animation.getAnimatedValue();
                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    player_y = targetY;
                    invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            playerAnimator.start();
        }

    }
    public void moveLeft(){
        int x = (int)player_x;
        int y = (int)player_y;
        if (gameMap[y][x-1] == road){
            float startX = player_x;
            float targetX = x - 1;
            if (playerAnimator != null) {
                playerAnimator.cancel(); // 取消前一个动画
            }
            playerAnimator = ValueAnimator.ofFloat(startX, targetX);
            playerAnimator.setDuration((long) Math.abs((targetX - startX) * PLAYER_SPEED));
            playerAnimator.setInterpolator(new LinearInterpolator()); // 线性插值器，使动画速度保持恒定
            playerAnimator.addUpdateListener(animation -> {
                player_x = (float) animation.getAnimatedValue();
                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_x = targetX;
                    invalidate();
                }
                @Override
                public void onAnimationCancel(Animator animation) { }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
            playerAnimator.start();
        }
    }
    public void moveRight(){
        int x = (int)player_x;
        int y = (int)player_y;
        if (gameMap[y][x+1] == road){
            float startX = player_x;
            float targetX = x + 1;
            if (playerAnimator != null) {
                playerAnimator.cancel(); // 取消前一个动画
            }
            playerAnimator = ValueAnimator.ofFloat(startX, targetX);
            playerAnimator.setDuration((long) Math.abs((targetX - startX) * PLAYER_SPEED));
            playerAnimator.setInterpolator(new LinearInterpolator()); // 线性插值器，使动画速度保持恒定
            playerAnimator.addUpdateListener(animation -> {
                player_x = (float) animation.getAnimatedValue();
                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_x = targetX;
                    invalidate();
                }
                @Override
                public void onAnimationCancel(Animator animation) { }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
            playerAnimator.start();
        }
    }
    public void Bomb(){
        System.out.println("Bomb");
    }
}