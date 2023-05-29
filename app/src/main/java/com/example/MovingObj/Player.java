package com.example.MovingObj;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.myfunctions.bitmapManipulate;
import com.example.qbomb.MapData;

public class Player extends MovingObjects {
    private static final int FRAME_WIDTH_COUNT = 4;
    private static final int FRAME_HEIGHT_COUNT = 4;
    private static int player_FrameWidth;
    private static int player_FrameHeight;
    private static int FRAME_RATE = 200;
    private Bitmap[][] player;
    private PlayerListener playerListener;
    private int mCurrentFrame = 0;
    private int mCurrentHeight = 1;
    private float player_x;
    private int player_place_x;
    private float player_y;
    private int player_place_y;
    private float player_speed = 300f;
    private int player_bomb_power = 1;
    private int bombMax = 1;
    private int bombCurrent = 0;
    private int life = 1;
    private boolean isAlive = true;
    private ValueAnimator playerAnimator=new ValueAnimator();
    public Player(Context context,int player_src,PlayerListener playerListener,int x,int y,int[][] gameMap){
        initPlayerBitmap(context,player_src);
        initData(x,y,gameMap,playerListener);
    }
    private void initData(int x,int y,int[][] gameMap,PlayerListener playerListener){
        this.player_place_x = x;
        this.player_x = x;
        this.player_place_y = y;
        this.player_y = y;
        this.gameMap = gameMap;
        this.playerListener = playerListener;
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
        },0);
    }
    private void initPlayerBitmap(Context context,int player_src){
        Bitmap player_all = BitmapFactory.decodeResource(context.getResources(), player_src);
        player_FrameWidth = player_all.getWidth() / FRAME_WIDTH_COUNT;
        player_FrameHeight = player_all.getHeight() / FRAME_HEIGHT_COUNT;
        player = new Bitmap[FRAME_HEIGHT_COUNT][FRAME_WIDTH_COUNT];

        for(int i=0;i<FRAME_HEIGHT_COUNT;i++){
            for(int j=0;j<FRAME_WIDTH_COUNT;j++){
                int left = j * player_FrameWidth;
                int top = i * player_FrameHeight;
                int right = player_FrameWidth;
                int bottom = player_FrameHeight;

                Bitmap bombFrame = Bitmap.createBitmap(player_all, left, top+2, right, bottom-2);
                player[i][j] = bitmapManipulate.chopInvisible(bombFrame);
            }
        }
    }
    public Bitmap getBitmap(){
        return player[mCurrentHeight][mCurrentFrame];
    }
    private boolean is_not_int(float a){
        return a % 1 != 0;
    }
    public void setPlayer_place(int x, int y){
        player_x = x;
        player_place_x = x;
        player_y = y;
        player_place_y = y;
    }
    public void setPlayer_x(int x){
        player_x = x;
        player_place_x = x;
    }
    public int getPlayer_place_x(){
        return player_place_x;
    }
    public float getPlayer_float_x(){
        return player_x;
    }
    public void setPlayer_y(int y){
        player_y = y;
        player_place_y = y;
    }
    public int getPlayer_place_y(){
        return player_place_y;
    }
    public float getPlayer_float_y(){
        return player_y;
    }
    public int getPlayer_bomb_power(){
        return player_bomb_power;
    }
    public boolean canSetBomb(){
        return bombCurrent < bombMax;
    }
    public void set_Bomb(){bombCurrent++;}
    public void Bomb_explode(){bombCurrent--;}
    public void directionUp(){mCurrentHeight = 3;}
    public void directionDown(){mCurrentHeight = 0;}
    public void directionLeft(){mCurrentHeight = 1;}
    public void directionRight(){mCurrentHeight = 2;}
    public void moveUp(){
//        如果正在移动，就不能移动
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        directionUp();

        int nextPlaceType = gameMap[player_place_y-1][player_place_x];
        if(MapData.walk_able(nextPlaceType)){
            player_place_y--;
            setAnimator((int)player_x,(int)player_y,(int)player_x,(int)player_y-1);
        }
    }
    public void moveDown(){
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        directionDown();

        int nextPlaceType = gameMap[player_place_y+1][player_place_x];
        if (MapData.walk_able(nextPlaceType)) {
            player_place_y++;
            setAnimator((int)player_x,(int)player_y,(int)player_x,(int)player_y+1);
        }
    }
    public void moveLeft(){
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        directionLeft();

        int nextPlaceType = gameMap[player_place_y][player_place_x-1];
        if(MapData.walk_able(nextPlaceType)){
            player_place_x--;
            setAnimator((int)player_x,(int)player_y,(int)player_x-1,(int)player_y);
        }
    }
    public void moveRight(){
        if(is_not_int(player_y)|| is_not_int(player_x))
            return;
        directionRight();

        int nextPlaceType = gameMap[player_place_y][player_place_x+1];
        if(MapData.walk_able(nextPlaceType)){
            player_place_x++;
            setAnimator((int)player_x,(int)player_y,(int)player_x+1,(int)player_y);
        }
    }
    public void setAnimator(int start_x,int start_y,int target_x,int target_y){
        if(playerAnimator!=null)
            playerAnimator.cancel();
        if(start_x == target_x){
            playerAnimator = ValueAnimator.ofFloat(start_y,target_y);
            playerAnimator.setDuration((long) (Math.abs(target_y - start_y) * player_speed));
            playerAnimator.addUpdateListener(animation -> {
                player_y = (float) animation.getAnimatedValue();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_y = target_y;
                }
                @Override
                public void onAnimationCancel(Animator animation) { }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
            playerAnimator.start();
        }else if(start_y == target_y){
            playerAnimator = ValueAnimator.ofFloat(start_x,target_x);
            playerAnimator.setDuration((long) (Math.abs(target_x - start_x) * player_speed));
            playerAnimator.addUpdateListener(animation -> {
                player_x = (float) animation.getAnimatedValue();
            });
            playerAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    player_x = target_x;
                }
                @Override
                public void onAnimationCancel(Animator animation) { }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
            playerAnimator.start();
        }

    }

}
