package com.example.Manager;

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

import com.example.Listener.GameListener;
import com.example.MovingObj.Player;
import com.example.MovingObj.Bomb;
import com.example.Listener.BombListener;
import com.example.Listener.PlayerListener;
import com.example.MovingObj.Robot;
import com.example.MovingObj.Wave;
import com.example.Listener.WaveListener;
import com.example.qbomb.MapData;
import com.example.qbomb.R;

import java.util.ArrayList;

public class gameManager extends View implements BombListener, WaveListener, PlayerListener {
    final int MAP_HEIGHT = MapData.MAP_HEIGHT;
    final int MAP_WIDTH = MapData.MAP_WIDTH;
    private int screenWidth;
    private int screenHeight;
    final int WALL = MapData.WALL;
    final int ROAD = MapData.ROAD;
    final int BLOCK =  MapData.BLOCK;
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
            {1,0,0,2,2,2,2,2,2,2,2,2,0,0,1},
            {1,0,1,2,1,2,1,2,1,2,1,2,1,0,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
            {1,0,1,2,1,2,1,2,1,2,1,2,1,0,1},
            {1,0,0,2,2,2,2,2,2,2,2,2,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
    private ArrayList<Bomb> my_bombs = new ArrayList<Bomb>();
    private ArrayList<Wave> my_waves = new ArrayList<Wave>();
    private ArrayList<Robot> my_robots = new ArrayList<Robot>();
    private GameListener gameListener;
    private int gameTime = 0;
    private Boolean isGameOver = false;
    public gameManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initPlayer();
        initRobots();
        initTimer();
    }
    private void initPlayer() {
        player1 = new Player(this.getContext(), R.drawable.red3,this, this,1,1,gameMap);
        player1.setState(new int[]{2, 4, 300, 2});
    }
    private void initRobots(){
        Robot robot1 = new Robot(this.getContext(), R.drawable.robot1,this, this,13,1,gameMap);
        Robot robot2 = new Robot(this.getContext(), R.drawable.robot2,this, this,13,13,gameMap);
        Robot robot3 = new Robot(this.getContext(), R.drawable.robot3,this, this,1,13,gameMap);
        my_robots.add(robot1);
        my_robots.add(robot2);
        my_robots.add(robot3);
        robot1.setState(new int[]{1, 3, 300, 1});
        robot2.setState(new int[]{1, 2, 400, 1});
        robot3.setState(new int[]{3, 3, 200, 2});
    }
    private void initData(){
        bitmap_wall = BitmapFactory.decodeResource(getResources(), R.drawable.f_block_01);
        bitmap_road = BitmapFactory.decodeResource(getResources(), R.drawable.path_1);
        bitmap_block = BitmapFactory.decodeResource(getResources(), R.drawable.desert_grass_b_layer);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isGameOver){
                    handler.removeCallbacksAndMessages(this);
                    return;
                }
                invalidate();
                handler.postDelayed(this, FRAME_RATE);
            }
        }, FRAME_RATE);
    }
    private void initTimer(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameTime++;
                handler.postDelayed(this, 1000);
            }
        }, 0);
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
        for (int i = 0; i < my_robots.size(); i++) {
            draw_Player(my_robots.get(i));
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
                    case WALL:
                        mCanvas.drawBitmap(bitmap_wall, null, rect, mPaint);
                        break;
                    case BLOCK:
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
    public void setGameListener(GameListener gameListener){
        this.gameListener = gameListener;
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
    public void playerSetBomb() {
        player1.set_Bomb();
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
//        System.out.println("New Wave");
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
        if(gameMap[y][x] == BLOCK){
            gameMap[y][x] = ROAD;
            flag = true;
        }else if(gameMap[y][x] == WALL){
            flag = true;
        }
        if(player1.getPlayer_place_x()==x&&player1.getPlayer_place_y()==y){
            player1.loseLife();
        }
        for (int i = 0; i < my_robots.size(); i++) {
            if(my_robots.get(i).getPlayer_place_x()==x&&my_robots.get(i).getPlayer_place_y()==y){
                my_robots.get(i).loseLife();
            }
        }
        return flag;
    }
    @Override
    public void onPlayerDead(Player player) {
        if(player == player1){
            lose();
            gameOver();
        }
        else{
            my_robots.remove(player);
            if(my_robots.size()==0){
                win();
                gameOver();
            }
        }
    }
    @Override
    public void onSetBomb(Bomb bomb) {
        my_bombs.add(bomb);
        new Thread(bomb).start();
//        System.out.println("New Bomb");
    }
    private void win(){
        if(!isGameOver) {
            isGameOver = true;
            gameListener.onGameWin(gameTime,player1.getLife());
        }
    }
    private void lose(){
        if(!isGameOver) {
            isGameOver = true;
            gameListener.onGameLose(gameTime,0);
        }
    }
    private void tie() {
        if(!isGameOver) {
            isGameOver = true;
            gameListener.onGameTie(gameTime,player1.getLife());
        }
    }
    private void gameOver(){
        player1.stop();
        for (int i = 0; i < my_robots.size(); i++) {
            my_robots.get(i).stop();
        }
    }
    public int[][] getStates(){
        int [][] states = new int[4][4];
        states[0]=player1.getState();
        states[1]=my_robots.get(0).getState();
        states[2]=my_robots.get(2).getState();
        states[3]=my_robots.get(1).getState();
        return states;
    }
}