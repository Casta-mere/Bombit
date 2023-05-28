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
import com.example.MovingObj.Bomb;
import com.example.MovingObj.BombListener;
import com.example.MovingObj.PlayerListener;
import com.example.MovingObj.Wave;
import com.example.MovingObj.WaveListener;

import java.util.ArrayList;

public class drawGameMap extends View implements BombListener, WaveListener {
    final int MAP_HEIGHT =15;
    final int MAP_WIDTH = 15;
    private int width;
    private int height;
    final int wall = 1;
    final int road = 0;
    final int block = 2;
    Bitmap bitmap_wall ;
    Bitmap bitmap_road ;
    Bitmap bitmap_block;
    private static final int FRAME_RATE = 16;
    private static final float PLAYER_SPEED = 300f;
    private Player player1 = new Player(this.getContext(), R.drawable.red3, new PlayerListener() {
        @Override
        public void onPlayerDead(Player player) {

        }
    });
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
    private ArrayList<Bomb> my_bombs = new ArrayList<Bomb>();
    private ArrayList<Wave> my_waves = new ArrayList<Wave>();
    public drawGameMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }
    private void initData(){
        bitmap_wall = BitmapFactory.decodeResource(getResources(), R.drawable.block_3_n);
        bitmap_road = BitmapFactory.decodeResource(getResources(), R.drawable.path_1);
        bitmap_block = BitmapFactory.decodeResource(getResources(), R.drawable.desert_grass_b_layer);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, FRAME_RATE);
            }
        }, FRAME_RATE);

    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
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
        for (int i = 0; i < my_bombs.size(); i++) {
            my_bombs.get(i).drawBomb(canvas, mPaint, width, height);
        }
        for (int i = 0; i < my_waves.size(); i++) {
            my_waves.get(i).drawWave(canvas, mPaint, width, height);
        }
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
                    case block:
                        mCanvas.drawBitmap(bitmap_road, null, rect, mPaint);
                        mCanvas.drawBitmap(bitmap_block, null, rect, mPaint);
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
    private void draw_Player(Player p){
        int x=p.getPlayer_x();
        int y=p.getPlayer_y();
        float left = (width/MAP_WIDTH)*x;
        float top = (height/MAP_HEIGHT)*y;
        float right = (width/MAP_WIDTH)*(x+1);
        float bottom = (height/MAP_HEIGHT)*(y+1);

        Rect rect = new Rect((int)left, (int)top, (int)right, (int)bottom);
        Bitmap frameBitmap = p.getBitmap();
        mCanvas.drawBitmap(frameBitmap, null, rect, mPaint);
    }

    public void setMap(int[][] mapData){
        System.arraycopy(mapData, 0, gameMap, 0, mapData.length);
        invalidate();  // 刷新画布
    }
    public boolean is_not_int(float a){
        return a % 1 != 0;
    }
    public void moveUp() {
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        int x = (int) player_x;
        int y = (int) player_y;
        player1.goUp();

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
//                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_y = targetY;
                    player1.setPlayer_y((int) player_y);
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
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        int x = (int) player_x;
        int y = (int) player_y;
        player1.goDown();
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
//                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    player_y = targetY;
                    player1.setPlayer_y((int)targetY);
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
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        int x = (int)player_x;
        int y = (int)player_y;
        player1.goLeft();
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
//                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_x = targetX;
                    player1.setPlayer_x((int) player_x);
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
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        int x = (int)player_x;
        int y = (int)player_y;
        player1.goRight();
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
//                invalidate();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_x = targetX;
                    player1.setPlayer_x((int) player_x);
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
    public void setBomb(int x,int y,int bombPower){
        Bomb bomb = new Bomb(getContext(),x,y,bombPower,this);
        my_bombs.add(bomb);
        new Thread(bomb).start();
        System.out.println("New Bomb");
    }
    public void playerSetBomb() {
        int x = (int) player_x;
        int y = (int) player_y;
        int bombPower = 2;
        setBomb(x,y,bombPower);
    }
    @Override
    public void onBombExplode(Bomb bomb) {
        my_bombs.remove(bomb);


        Wave wave = new Wave(getContext(),bomb.getBomb_x(),bomb.getBomb_y(),bomb.getBombPower(),gameMap,this);
        my_waves.add(wave);
        System.out.println("New Wave");

        bombBlock(bomb);
    }
    @Override
    public void onWaveEnd(Wave wave) {
        my_waves.remove(wave);
    }

    public void bombBlock(Bomb bomb){
        int power = bomb.getBombPower();
        int x = bomb.getBomb_x();
        int y = bomb.getBomb_y();
        bombResult(x,y);
        for(int i = 1;i<=power;i++){
            if(bombResult(x+i,y))
                break;
        }
        for(int i = 1;i<=power;i++){
            if(bombResult(x-i,y))
                break;
        }
        for(int i = 1;i<=power;i++){
            if(bombResult(x,y+i))
                break;
        }
        for(int i = 1;i<=power;i++){
            if(bombResult(x,y-i))
                break;
        }

    }
    private boolean bombResult(int x,int y){
        boolean flag = false;
        if(gameMap[y][x] == block){
            gameMap[y][x] = road;
            flag = true;
        }else if((int)player_x == x && (int)player_y == y){
            player1.setPlayer_x(1);
            player1.setPlayer_y(1);
            player_x = 1;
            player_y = 1;
        } else if(gameMap[y][x] == wall){
            flag = true;
        }
        return flag;
    }
}