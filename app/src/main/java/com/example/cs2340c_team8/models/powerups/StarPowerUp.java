package com.example.cs2340c_team8.models.powerups;

import com.example.cs2340c_team8.models.enums.PowerUpType;
import com.example.cs2340c_team8.models.interfaces.PowerUp;

public class StarPowerUp implements PowerUp {
    private final PowerUpType powerUpType = PowerUpType.STAR;

    @Override
    public PowerUpType getType() {
        return powerUpType;
    }

    @Override
    public int getEffect() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }
}