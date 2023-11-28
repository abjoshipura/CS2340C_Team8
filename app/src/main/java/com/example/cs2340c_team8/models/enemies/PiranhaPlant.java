package com.example.cs2340c_team8.models.enemies;

import static com.example.cs2340c_team8.views.enemies.GameView.firstShellExists;
import static com.example.cs2340c_team8.views.enemies.GameView.getFireballX;
import static com.example.cs2340c_team8.views.enemies.GameView.getFireballY;

import android.widget.ImageView;

import com.example.cs2340c_team8.models.GameConfig;
import com.example.cs2340c_team8.models.Player;
import com.example.cs2340c_team8.models.interfaces.Enemy;

public class PiranhaPlant implements Enemy {
    private Player player = Player.getInstance();
    private int playerStartX;
    private int playerStartY;
    private int playerEndX;
    private int playerEndY;

    // Enemy Attributes
    private final int spriteSizeX = 48;
    private final int spriteSizeY = 48;
    private final double pixelsPerFrame = GameConfig.PLAYER_PIXELS_PER_FRAME;
    private ImageView sprite;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final int damage;

    // New
    private int startingY;
    private int endingY;
    private int movementSpeed;

    public PiranhaPlant(int startX, int startY, int endingY, int movementSpeed) {
        this.startX = startX;
        this.endX = startX + spriteSizeX;

        this.startY = startY;
        this.endY = startY + spriteSizeY;

        // New
        this.startingY = startY;
        this.endingY = endingY;
        this.movementSpeed = movementSpeed;

        int startingHealth = GameConfig.getStartingHealth();
        switch (GameConfig.getDifficulty()) {
        case INTERMEDIATE:
            damage = (int) (0.1 * startingHealth);
            break;
        case EXPERT:
            damage = (int) (0.2 * startingHealth);
            break;
        default:
            damage = (int) (0.05 * startingHealth);
        }
    }

    @Override
    public void moveEnemy() {
        setStartY(startY + movementSpeed);
        if (startY >= endingY) {
            startY = startingY;
        }
        if (isCollidingWithFireball()) {
            firstShellExists = false;
        }
        if (isCollidingWithPlayer()) {
            attackPlayer();
        }
    }

    public boolean isCollidingWithFireball() {
        int overlapWidth = Math.min(endX, getFireballX() + 48) - Math.max(startX, getFireballX());
        int overlapHeight = Math.min(endY, getFireballY() + 48) - Math.max(startY, getFireballY());

        return (overlapWidth > 3 && overlapHeight > 3);
    }

    @Override
    public boolean isCollidingWithPlayer() {
        int overlapWidth = Math.min(endX, playerEndX) - Math.max(startX, playerStartX);
        int overlapHeight = Math.min(endY, playerEndY) - Math.max(startY, playerStartY);

        return (overlapWidth > 3 && overlapHeight > 3);
    }

    @Override
    public void updatePlayerPosition(int posX, int posY, int endX, int endY) {
        this.playerStartX = posX;
        this.playerStartY = posY;
        this.playerEndX = endX;
        this.playerEndY = endY;

        if (isCollidingWithPlayer()) {
            attackPlayer();
        }
    }

    @Override
    public void attackPlayer() {
        player.setHealth(player.getHealth() - damage);
        player.setStartY(playerStartY - 40);
    }

    public ImageView getSprite() {
        return sprite;
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

    public int getSpriteSizeX() {
        return spriteSizeX;
    }

    public int getSpriteSizeY() {
        return spriteSizeY;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }

    public void setStartX(int startX) {
        this.startX = startX;
        this.endX = startX + spriteSizeX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
        this.endY = startY + spriteSizeY;
    }
}
