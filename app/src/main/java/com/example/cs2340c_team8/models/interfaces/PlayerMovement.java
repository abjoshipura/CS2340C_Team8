package com.example.cs2340c_team8.models.interfaces;

import android.graphics.Bitmap;

public interface PlayerMovement {
    Bitmap movePlayer();
    boolean canPlayerMove();
    int checkLevelCompleted(int currentMap);
}
