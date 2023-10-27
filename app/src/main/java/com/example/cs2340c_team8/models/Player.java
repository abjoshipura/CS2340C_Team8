package com.example.cs2340c_team8.models;

import com.example.cs2340c_team8.models.elements.Wall;
import com.example.cs2340c_team8.models.enums.PowerUp;
import com.example.cs2340c_team8.models.interfaces.Consumable;
import com.example.cs2340c_team8.models.interfaces.Key;
import com.example.cs2340c_team8.models.interfaces.Level;
import com.example.cs2340c_team8.models.interfaces.Element;
import com.example.cs2340c_team8.models.interfaces.PlayerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements com.example.cs2340c_team8.models.interfaces.PowerUp, Level, Key {
    private static Player instance;
    private static final int SPRITE_SIZE = 16;
    private int health;
    private long time;
    private List<Key> keys;
    private int points;
    private List<com.example.cs2340c_team8.models.interfaces.PowerUp> powerUps;
    private List<Consumable> consumables;
    private int level;
    private List<PlayerObserver> observers;
    private int posX;
    private int posY;

    private Player() {
        health = 100;
        time = 0;
        keys = new ArrayList<>();
        points = 0;
        powerUps = new ArrayList<>();
        consumables = new ArrayList<>();
        observers = new ArrayList<>();
        posX = 25;
        posY = 25;
        level = 1;
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

    public PowerUp getPowerUpType() {
        // Implement power-up type logic
        return PowerUp.HEALTH_BOOST;
    }

    public int getEffect() {
        // Implement power-up effect logic
        return 5;
    }

    public long getDuration() {
        // Implement power-up duration logic
        return 10;
    }

    public int getLevelNumber() {
        // Implement level number logic
        return level;
    }

    public int nextLevel(int level) {
        this.level += 1;
        return level + 1;
    }

    @Override
    public String getLayout() {
        // Implement level layout logic
        if (level < 4) {
            return "Default Layout";
        } else {
            return "Final Level";
        }
    }

    public int getMatchedPoints() {
        // Implement matched points logic
        return 0;
    }

    @Override
    public boolean isMatchedDoor() {
        // Implement matched door logic
        return false;
    }

    public int getX() { //for positioning
        return posX;
    }

    public int getY() { //for positioning
        return posY;
    }

    public void setPosX(int posX) { //for positioning
        this.posX = posX;
    }

    public void setPosY(int posY) { //for positioning
        this.posY = posY;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public static int getSpriteSize() {
        return SPRITE_SIZE;
    }

    public void addObserver(PlayerObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(PlayerObserver observer) {
        this.observers.remove(observer);
    }

    public void updateObservers() {
        for (PlayerObserver observer : observers) {
            observer.update(posX, posY);
        }
    }

    @Override
    public String toString() {
        String rtn = String.valueOf(getLevelNumber() + getHealth()
                + getX() + getY());
        return rtn;
    }

    /**
     * isColliding will check if player and wall collide based on their positions.
     *
     * @param player represents the player (controlled by user).
     * @param wall   represents the list of walls.
     * @return returns if a player is colliding with a Wall
     */
    public static boolean isColliding(Player player, Wall wall) {
        double distanceX = wall.getX() - player.getX();
        double distanceY = wall.getY() - player.getY();
        double distanceXFormula = Math.pow(distanceX, 2);
        double distanceYFormula = Math.pow(distanceY, 2);
        //Below line finds the distance between the player and wall
        double distance = Math.sqrt(distanceXFormula + distanceYFormula);

        //just putting '5' for now b/c '1' might cause player to phase past wall
        return distance < 5;
    } //isColliding

    public void movementInteraction(Element obstacle) {
        Player player = getInstance();
        if (Objects.equals(obstacle.getEffect(), "Damage")) {
            player.setHealth(player.getHealth() - obstacle.getEffectMagnitude()); //deal damage
        } else if (Objects.equals(obstacle.getEffect(), "Knock-back")) {
            player.setPosX(player.getX() + obstacle.getEffectMagnitude());
            player.setPosY(player.getY() - obstacle.getEffectMagnitude());
        } else if (Objects.equals(obstacle.getEffect(), "Door")) {
            player.nextLevel(player.getLevelNumber());
        }
    }
}

