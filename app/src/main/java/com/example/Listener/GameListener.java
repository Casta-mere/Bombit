package com.example.Listener;

public interface GameListener {
    void onGameLose(int time,int live);
    void onGameWin(int time,int live);
    void onGameTie(int time,int live);
    void onDataChanged();
    void onTimeChanged(int time);
}
