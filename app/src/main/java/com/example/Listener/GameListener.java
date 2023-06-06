package com.example.Listener;

public interface GameListener {
    void onGameLose(int time,int live,int stateScore);
    void onGameWin(int time,int live,int stateScore);
    void onGameTie(int time,int live,int stateScore);
    void onDataChanged();
    void onTimeChanged(int time);
}
