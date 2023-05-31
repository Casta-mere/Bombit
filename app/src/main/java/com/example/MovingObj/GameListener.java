package com.example.MovingObj;

public interface GameListener {
    void onGameLose(int time,int live);
    void onGameWin(int time,int live);
    void onGameTie(int time,int live);
}
