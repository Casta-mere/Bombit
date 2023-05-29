package com.example.MovingObj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.example.qbomb.R;

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
                int direction = (int) (Math.random() * 4);
                switch (direction) {
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
                set_Bomb();
                handler.postDelayed(this,100);
            }
        },0);
    }
//    public void run(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (isAlive) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (isAlive) {
//                        int direction = (int) (Math.random() * 4);
//                        switch (direction) {
//                            case 0:
//                                moveUp();
//                                break;
//                            case 1:
//                                moveDown();
//                                break;
//                            case 2:
//                                moveLeft();
//                                break;
//                            case 3:
//                                moveRight();
//                                break;
//                        }
//                    }
//                }
//            }
//        }).start();
//    }


}
