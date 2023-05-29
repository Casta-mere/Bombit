package com.example.MovingObj;

public interface GameListener {
    void onGameLose(int time);
    void onGameWin(int time);
    void onGameTie(int time);
}
