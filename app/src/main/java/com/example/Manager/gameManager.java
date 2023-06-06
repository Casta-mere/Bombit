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
import com.example.Listener.PropListener;
import com.example.MovingObj.Player;
import com.example.MovingObj.Bomb;
import com.example.Listener.BombListener;
import com.example.Listener.PlayerListener;
import com.example.MovingObj.Prop;
import com.example.MovingObj.Robot;
import com.example.MovingObj.Wave;
import com.example.Listener.WaveListener;
import com.example.myfunctions.MusicService;
import com.example.qbomb.MapData;
import com.example.qbomb.R;

import java.util.ArrayList;

public class gameManager extends View implements BombListener, WaveListener, PlayerListener, PropListener {
    final int MAP_HEIGHT = MapData.MAP_HEIGHT;
    final int MAP_WIDTH = MapData.MAP_WIDTH;
    private int screenWidth;
    private int screenHeight;
    final int WALL = MapData.WALL;
    final int ROAD = MapData.ROAD;
    final int BLOCK =  MapData.BLOCK;
    final int BOMB = MapData.BOMB;
    final int PROP = MapData.PROP;
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
    private ArrayList<Prop> my_props = new ArrayList<Prop>();
    private GameListener gameListener;
    private int gameTime = 0;
    private Boolean isGameOver = false;
    private MusicService musicService;
    private int LeftTime = 60;
    public gameManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initPlayer();
        initRobots();
//        initProps();
        initTimer();
        initMusic();
    }


    private void initMusic() {
        musicService = new MusicService();
    }
    private void initPlayer() {
        player1 = new Player(this.getContext(), R.drawable.red3,this, this,1,1,gameMap);

    }
    private void initRobots(){
        Robot robot1 = new Robot(this.getContext(), R.drawable.robot1,this, this,13,1,gameMap);
        Robot robot2 = new Robot(this.getContext(), R.drawable.robot2,this, this,13,13,gameMap);
        Robot robot3 = new Robot(this.getContext(), R.drawable.robot3,this, this,1,13,gameMap);
        my_robots.add(robot1);
        my_robots.add(robot2);
        my_robots.add(robot3);
    }
    public void setHard(){
        my_robots.get(0).setState(new int[]{2, 1, 300, 1});
        my_robots.get(1).setState(new int[]{2, 2, 400, 1});
        my_robots.get(2).setState(new int[]{3, 1, 200, 1});
    }
    public void setEasy(){
        player1.setState(new int[]{2, 4, 300, 2});
    }
    public void setTime(int time){
        LeftTime = time;
    }
    public void initProps() {
        for(int i = 3;i<MAP_WIDTH-3;i++){
            for(int j = 3;j<MAP_HEIGHT-3;j++){
                if(gameMap[i][j] == ROAD){
                    int random = (int)(Math.random()*30);
                    if(random < 4){
                        newProp(j,i);
                    }
                }
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isGameOver){
                    try {
                        Thread.sleep(20000);
                        Boolean isHaveProp = true;
                        while(isHaveProp){
                            int randomX = (int)(Math.random()*MAP_WIDTH);
                            int randomY = (int)(Math.random()*MAP_HEIGHT);
                            if(gameMap[randomY][randomX] == ROAD){
                                newProp(randomX,randomY);
                                isHaveProp = false;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void initData(){
        changeMapBitmap(0);
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
    public void changeMapBitmap(int mapType){
        switch(mapType){
            case 0:
                bitmap_wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall_0);
                bitmap_road = BitmapFactory.decodeResource(getResources(), R.drawable.road_0);
                bitmap_block = BitmapFactory.decodeResource(getResources(), R.drawable.block_0);
                break;
            case 1:
                bitmap_wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall_1);
                bitmap_road = BitmapFactory.decodeResource(getResources(), R.drawable.road_1);
                bitmap_block = BitmapFactory.decodeResource(getResources(), R.drawable.block_1);
                break;
            case 2:
                bitmap_wall = BitmapFactory.decodeResource(getResources(), R.drawable.wall_2);
                bitmap_road = BitmapFactory.decodeResource(getResources(), R.drawable.road_2);
                bitmap_block = BitmapFactory.decodeResource(getResources(), R.drawable.block_2);
                break;
        }
    }
    private void initTimer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isGameOver){
                    try {
                        Thread.sleep(1000);
                        gameTime++;
                        LeftTime--;
                        gameListener.onTimeChanged(LeftTime);
                        if(LeftTime <= 0){
                            isGameOver = true;
                            tie();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
        for (int i = 0; i < my_props.size(); i++) {
            my_props.get(i).drawProp(canvas, mPaint, screenWidth, screenHeight);
        }
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
                    case PROP:
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
        musicService.Bomb(getContext());
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
            int rand = (int)(Math.random()*5);
            if(rand == 0)
                newProp(x,y);
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
        gameListener.onDataChanged();
    }
    @Override
    public void onSetBomb(Bomb bomb) {
        my_bombs.add(bomb);
        new Thread(bomb).start();
//        System.out.println("New Bomb");
    }
    @Override
    public boolean PropDetect(Prop prop) {
        int x = prop.getProp_x();
        int y = prop.getProp_y();

//        System.out.println("x:"+x+" y:"+y+" player_x:"+player1.getPlayer_place_x()+" player_y:"+player1.getPlayer_place_y()+"");
        if(player1.getPlayer_place_x()==x&&player1.getPlayer_place_y()==y){
            player1.boost(prop.getPropType());
            my_props.remove(prop);
            gameListener.onDataChanged();
            return false;
        }
        for (int i = 0; i < my_robots.size(); i++) {
            if(my_robots.get(i).getPlayer_place_x()==x&&my_robots.get(i).getPlayer_place_y()==y){
                my_robots.get(i).boost(prop.getPropType());
                my_props.remove(prop);
                gameListener.onDataChanged();
                return false;
            }
        }
        return true;
    }
    private void newProp(int x,int y){
        int propType = (int)(Math.random()*4);
        Prop prop = new Prop(getContext(),x,y,propType,this);
        new Thread(prop).start();
        my_props.add(prop);
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
        isGameOver = true;
        gameListener.onGameTie(gameTime,player1.getLife());
        gameOver();
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
    public Player[] getPlayers(){
        Player[] players = new Player[4];
        players[0]=player1;
        players[1]=my_robots.get(0);
        players[2]=my_robots.get(2);
        players[3]=my_robots.get(1);
        return players;
    }
}