package com.example.cs2340c_team8.models;

import com.example.cs2340c_team8.models.elements.Wall;
import com.example.cs2340c_team8.models.enums.PowerUpType;
import com.example.cs2340c_team8.models.interfaces.Consumable;
import com.example.cs2340c_team8.models.interfaces.Key;
import com.example.cs2340c_team8.models.interfaces.PlayerObserver;
import com.example.cs2340c_team8.models.interfaces.PowerUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
    private static volatile Player instance;
    private static double pixelsPerFrame = GameConfig.PLAYER_PIXELS_PER_FRAME;
    private final int spriteSizeX = 25;
    private final int spriteSizeY = 25;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int health;
    private List<PlayerObserver> observers;
    private HashMap<PowerUpType, PowerUp> powerUps;
    private List<Consumable> consumables;
    private List<Key> keys;

    private Player() {
        startX = 25;
        startY = 25;
        endX = startX + spriteSizeX;
        endY = startY + spriteSizeY;

        observers = new ArrayList<>();
        powerUps = new HashMap<>();
        consumables = new ArrayList<>();
        keys = new ArrayList<>();
    }

    public static Player getInstance() {
        if (instance == null) {
            synchronized (Player.class) {
                if (instance == null) {
                    instance = new Player();
                }
            }
        }
        return instance;
    }

    public void addObserver(PlayerObserver observer) {
        observers.add(observer);
    }

    public void clearObservers() {
        observers = new ArrayList<>();
    }

    public void updateObservers() {
        for (PlayerObserver observer : observers) {
            observer.updatePlayerPosition(startX, startY, endX, endY);
        }
    }

    public HashMap<PowerUpType, PowerUp> getPowerUps() {
        return powerUps;
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.put(powerUp.getType(), powerUp);
    }

    public void removePowerUp(PowerUp powerUp) {
        powerUps.remove(powerUp.getType());
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getPixelsPerFrame() {
        return pixelsPerFrame;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setPixelsPerFrame(double pixelsPerFrame) {
        Player.pixelsPerFrame = pixelsPerFrame;
    }

    public void setStartX(int startX) {
        this.startX = startX;
        endX = startX + spriteSizeX;
        updateObservers();
    }

    public void setStartY(int startY) {
        this.startY = startY;
        endY = startY + spriteSizeY;
        updateObservers();
    }

    public static boolean isColliding(Player player, Wall wall) {
        double distanceX = wall.getX() - player.getStartX();
        double distanceY = wall.getY() - player.getStartY();
        double distanceXFormula = Math.pow(distanceX, 2);
        double distanceYFormula = Math.pow(distanceY, 2);
        double distance = Math.sqrt(distanceXFormula + distanceYFormula);
        return distance < 5;
    }
}

