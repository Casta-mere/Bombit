package com.example.Listener;

import com.example.MovingObj.Bomb;
import com.example.MovingObj.Player;

public interface PlayerListener {
    void onPlayerDead(Player player);
    void onSetBomb(Bomb bomb);
}
