package com.example.cs2340c_team8.models;

import com.example.cs2340c_team8.models.enums.PowerUpType;
import com.example.cs2340c_team8.models.interfaces.Consumable;
import com.example.cs2340c_team8.models.interfaces.Key;
import com.example.cs2340c_team8.models.interfaces.Level;
import com.example.cs2340c_team8.models.interfaces.Point;
import com.example.cs2340c_team8.models.interfaces.PowerUp;
import com.example.cs2340c_team8.models.interfaces.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Player implements Weapon, PowerUp, Level, Key, Point {
    private static Player instance;

    private int health;
    private long time;
    private List<Weapon> weapons;
    private List<Key> keys;
    private int points;
    private List<PowerUp> powerUps;
    private List<Consumable> consumables;

    private float x; //for x positioning
    private float y; //for y positioning

    private Player() {
        health = 100;
        time = 0;
        weapons = new ArrayList<>();
        keys = new ArrayList<>();
        points = 0;
        powerUps = new ArrayList<>();
        consumables = new ArrayList<>();
        x = 0; //likely need to change based on grid positioning
        y = 0; //likely need to change based on grid positioning
    }

    public static  Player getInstance() {
        if (instance == null) {
            synchronized (Player.class) {
                if (instance == null) {
                    instance = new Player();
                }
            }
        }
        return instance;
    }

    // Implement methods from the interfaces

    @Override
    public String getType() {
        // Implement weapon type logic
        return "Default";
    }

    @Override
    public int getDamage() {
        // Implement weapon damage logic
        return 10;
    }

    public PowerUpType getPowerUpType() {
        // Implement power-up type logic
        return PowerUpType.HEALTH_BOOST;
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
        return 1;
    }

    public String getLayout() {
        // Implement level layout logic
        return "Default Layout";
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

    @Override
    public int getAmount() {
        // Implement point amount logic
        return points;
    }

    private float getX() { //for positioning
        return x;
    }
    private float getY() { //for positioning
        return y;
    }

    private void setX(float X) { //for positioning
        this.x = x;
    }
    private void setY(float Y) { //for positioning
        this.y = y;
    }

    /**
     * isCollding will check if player and wall collide based on their positions.
     * @param player represents the player (controlled by user).
     * @param wall represents the list of walls.
     */
    public static boolean isColliding(Player player, Wall wall) {
        double distanceX = wall.getX() - player.getX();
        double distanceY = wall.getY() - player.getY();
        double distanceXFormula = Math.pow(distanceX, 2);
        double distanceYFormula = Math.pow(distanceY, 2);
        //Below line finds the distance between the player and wall
        double distance = Math.sqrt(distanceXFormula + distanceYFormula);

        if (distance < wall.getWidth()) { //if distance less wall width, considered a collision
            return true;
        } else {
            return false;
        }
    } //isColliding


    // Implement other methods from the interfaces as needed
}

