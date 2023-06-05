package com.example.MovingObj;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.example.Activity.GameActivity;
import com.example.Listener.BombListener;
import com.example.Listener.PlayerListener;
import com.example.myfunctions.MusicService;
import com.example.myfunctions.bitmapManipulate;
import com.example.qbomb.MapData;
import com.example.qbomb.R;

public class Player extends MovingObjects {
    private static final int FRAME_WIDTH_COUNT = 4;
    private static final int FRAME_HEIGHT_COUNT = 4;
    private static int player_FrameWidth;
    private static int player_FrameHeight;
    private static int FRAME_RATE = 200;
    private Bitmap[][] player;
    private PlayerListener playerListener;
    private BombListener bombListener;
    private int mCurrentFrame = 0;
    private int mCurrentHeight = 1;
    private float player_x;
    protected int player_place_x;
    private float player_y;
    protected int player_place_y;
    private float player_speed = 400f;
    private int player_bomb_power = 1;
    private int bombMax = 1;
    private int bombCurrent = 0;
    private int life = 1;
    public boolean isAlive = true;
    private ValueAnimator playerAnimator=new ValueAnimator();
    private Context context;
    private MusicService musicService = new MusicService();
    public Player(Context context,int player_src,PlayerListener playerListener,BombListener bombListener,int x,int y,int[][] gameMap){
        initPlayerBitmap(context,player_src);
        initData(x,y,gameMap,playerListener,bombListener);
        this.context = context;
    }
    private void initData(int x,int y,int[][] gameMap,PlayerListener playerListener,BombListener bombListener){
        this.player_place_x = x;
        this.player_x = x;
        this.player_place_y = y;
        this.player_y = y;
        this.gameMap = gameMap;
        this.playerListener = playerListener;
        this.bombListener = bombListener;
        Looper looper = Looper.myLooper();
        if(looper == null){
            looper = Looper.getMainLooper();
        }
        Handler handler = new Handler(looper);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isAlive){
                    handler.removeCallbacksAndMessages(this);
                    return;
                }
                mCurrentFrame++;
                if (mCurrentFrame >= FRAME_WIDTH_COUNT) {
                    mCurrentFrame = 0;
                }
                handler.postDelayed(this, FRAME_RATE);
            }
        },10);
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
    public boolean canSetBomb(){
        boolean flag1 =  bombCurrent < bombMax;
        boolean flag2 = gameMap[player_place_y][player_place_x] == MapData.ROAD;
        return flag1 && flag2;
    }
    public void set_Bomb(){
        if(canSetBomb()){
            Bomb bomb = new Bomb(context,player_place_x,player_place_y,player_bomb_power,bombListener,this);
            bombCurrent++;
            gameMap[player_place_y][player_place_x] = MapData.BOMB;
            playerListener.onSetBomb(bomb);
            musicService.setBomb(context);
        }

    }
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
    public void loseLife(){
        life--;
        if(life == 0){
            isAlive = false;
            playerListener.onPlayerDead(this);
        }
    }
    public void stop(){
        isAlive = false;
    }
    protected void move(int direction){
        switch (direction){
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }
    }
    public int[] getState(){
        int[] state = new int[4];
        state[0] = life;
        state[1] = bombMax;
        state[3] = (int) (400-player_speed)/50+1;
        state[2] = player_bomb_power;
        return state;
    }
    public void setState(int[] state){
        life = state[0];
        bombMax = state[1];
        player_speed = state[2];
        player_bomb_power = state[3];
    }

    public int getLife(){
        return life;
    }
    public void boost(int type){
        switch (type){
            case 0:
                if(bombMax<5)
                    bombMax++;
                break;
            case 1:
                if(player_speed>200)
                    player_speed-=50;
                break;
            case 2:
                if(player_bomb_power<3)
                    player_bomb_power++;
                break;
            case 3:
                if(life<6)
                    life++;
                break;
        }
    }
}
