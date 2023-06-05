package com.example.MovingObj;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.Listener.BombListener;
import com.example.Listener.PlayerListener;
import com.example.qbomb.MapData;

public class Robot extends Player {
    public Robot(Context context, int player_src, PlayerListener playerListener, BombListener bombListener, int x, int y, int[][] gameMap) {
        super(context, player_src, playerListener, bombListener, x, y, gameMap);
        initAi();
    }

    private void initAi(){
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
                if(BombAble())
                    set_Bomb();
                int direction = (int) (Math.random() * 4);
                switch (direction) {
                    case 0:
                        goUp();
                        break;
                    case 1:
                        goDown();
                        break;
                    case 2:
                        goLeft();
                        break;
                    case 3:
                        goRight();
                        break;
                }
                handler.postDelayed(this,100);
            }
        },0);
    }

    private boolean is_dangerous(int x , int y){
        if(MapData.walk_able(gameMap[y][x])){
            return is_bomb(x-1, y)||is_bomb(x+1, y)||is_bomb(x, y-1)||is_bomb(x, y+1);}
        return true;
    }

    private boolean is_bomb(int x , int y){
        return gameMap[y][x] == MapData.BOMB;
    }
    private boolean is_block(int x , int y){
        return gameMap[y][x] == MapData.BLOCK;
    }

    private void goUp(){
        if(is_bomb(player_place_x, player_place_y)||!is_dangerous(player_place_x, player_place_y-1))
            moveUp();
    }
    private void goDown(){
        if(is_bomb(player_place_x, player_place_y)||!is_dangerous(player_place_x, player_place_y+1))
            moveDown();
    }
    private void goLeft(){
        if(is_bomb(player_place_x, player_place_y)||!is_dangerous(player_place_x-1, player_place_y))
            moveLeft();
    }
    private void goRight(){
        if(is_bomb(player_place_x, player_place_y)||!is_dangerous(player_place_x+1, player_place_y))
            moveRight();
    }
    private boolean BombAble(){
        return is_block(player_place_x, player_place_y-1)||is_block(player_place_x, player_place_y+1)||is_block(player_place_x-1, player_place_y)||is_block(player_place_x+1, player_place_y);
    }


}
