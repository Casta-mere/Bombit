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
import com.example.MovingObj.Bomb;
import com.example.MovingObj.BombListener;
import com.example.MovingObj.PlayerListener;
import com.example.MovingObj.Wave;
import com.example.MovingObj.WaveListener;

import java.util.ArrayList;

public class gameManager extends View implements BombListener, WaveListener {
    final int MAP_HEIGHT = MapData.MAP_HEIGHT;
    final int MAP_WIDTH = MapData.MAP_WIDTH;
    private int screenWidth;
    private int screenHeight;
    final int wall = MapData.WALL;
    final int road = MapData.ROAD;
    final int block =  MapData.BLOCK;
    final int bomb = MapData.BOMB;
    final int prop = MapData.PROP;
    Bitmap bitmap_wall ;
    Bitmap bitmap_road ;
    Bitmap bitmap_block;
    private static final int FRAME_RATE = 16;
    private Player player1;
    Paint mPaint = new Paint();
    Canvas mCanvas = new Canvas();
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
    private ArrayList<Bomb> my_bombs = new ArrayList<Bomb>();
    private ArrayList<Wave> my_waves = new ArrayList<Wave>();
    public gameManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initPlayer();
    }
    private void initPlayer() {
        player1 = new Player(this.getContext(), R.drawable.red3, new PlayerListener() {
            @Override
            public void onPlayerDead(Player player) {

            }
        },1,1,gameMap);
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
        screenWidth = specWidthSize;
        screenHeight = specHeightSize;
        setMeasuredDimension(specWidthSize, specHeightSize);
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        this.mCanvas = canvas;
        paintMap();
        for (int i = 0; i < my_bombs.size(); i++) {
            my_bombs.get(i).drawBomb(canvas, mPaint, screenWidth, screenHeight);
        }
        for (int i = 0; i < my_waves.size(); i++) {
            my_waves.get(i).drawWave(canvas, mPaint, screenWidth, screenHeight);
        }
        draw_Player(player1);

    }
    private void paintMap() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                int bitmap_point = gameMap[i][j];
                Rect rect = new Rect
                        ((screenWidth /MAP_WIDTH)*j, (screenHeight /MAP_HEIGHT)*i,(screenWidth /MAP_WIDTH)*(j+1),(screenHeight /MAP_HEIGHT)*(i+1));

                mCanvas.drawBitmap(bitmap_road, null, rect, mPaint);
                switch (bitmap_point) {
                    case wall:
                        mCanvas.drawBitmap(bitmap_wall, null, rect, mPaint);
                        break;
                    case block:
                        mCanvas.drawBitmap(bitmap_block, null, rect, mPaint);
                        break;
                }
            }
        }
    }
    private void draw_Player(Player p){
        float x=p.getPlayer_float_x();
        float y=p.getPlayer_float_y();
        float left = (screenWidth /MAP_WIDTH)*x;
        float top = (screenHeight /MAP_HEIGHT)*y;
        float right = (screenWidth /MAP_WIDTH)*(x+1);
        float bottom = (screenHeight /MAP_HEIGHT)*(y+1);

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
        player1.moveUp();
    }
    public void moveDown() {
        player1.moveDown();
    }
    public void moveLeft() {
        player1.moveLeft();
    }
    public void moveRight() {
        player1.moveRight();
    }
    public void newBomb(int x, int y, int bombPower,Player player){
        gameMap[y][x] = MapData.BOMB;
        Bomb bomb = new Bomb(getContext(),x,y,bombPower,this,player);
        player.set_Bomb();
        my_bombs.add(bomb);
        new Thread(bomb).start();
        System.out.println("New Bomb");
    }
    public void playerSetBomb() {
        int x = player1.getPlayer_place_x();
        int y = player1.getPlayer_place_y();
        if(gameMap[y][x] == MapData.ROAD && player1.canSetBomb()){
            int bombPower = player1.getPlayer_bomb_power();
            newBomb(x,y,bombPower,player1);
        }
    }
    @Override
    public void onBombExplode(Bomb bomb,Player player) {
        gameMap[bomb.getBomb_y()][bomb.getBomb_x()] = MapData.ROAD;
        player.Bomb_explode();
        int [][] map = new int[MAP_HEIGHT][MAP_WIDTH];
        for(int i =0;i<MAP_HEIGHT;i++){
            System.arraycopy(gameMap[i],0,map[i],0,MAP_WIDTH);
        }
        Wave wave = new Wave(getContext(),bomb.getBomb_x(),bomb.getBomb_y(),bomb.getBombPower(),map,this);
        my_waves.add(wave);

        bombBlock(wave.getX(),wave.getY(),wave.getBombPower());
        my_bombs.remove(bomb);
        System.out.println("New Wave");
    }
    @Override
    public void onWaveEnd(Wave wave) {
        my_waves.remove(wave);
    }
    public void bombBlock(int x,int y,int power){
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
        }else if(player1.getPlayer_place_x()== x && player1.getPlayer_place_y() == y){
            player1.setPlayer_x(1);
            player1.setPlayer_y(1);
        } else if(gameMap[y][x] == wall){
            flag = true;
        }
        return flag;
    }
}