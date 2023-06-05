package com.example.Listener;

import com.example.MovingObj.Bomb;
import com.example.MovingObj.Player;

public interface BombListener {
    void onBombExplode(Bomb bomb, Player player);
}
