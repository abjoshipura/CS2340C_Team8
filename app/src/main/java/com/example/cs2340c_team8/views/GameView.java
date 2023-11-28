package com.example.cs2340c_team8.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.example.cs2340c_team8.R;

public class GameView extends View {

    private int currentMap;
    private int player_x; // Top left X coordinate of player
    private int player_y; // Top left Y coordinate of player
    private Bitmap player; // Player character
    private Bitmap map; // Map
    private Bitmap enemy1; // Enemy #1 (Bullet Bill)
    private int enemy1_x; // Top left X coordinate of enemy1
    private int enemy1_y; //Top left Y coordinate of enemy1
    private int enemy1_start_y, enemy1_end_y;
    private int enemy1_speed; // Movement speed for enemy1
    private boolean movingDown;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Initialize variables here
        currentMap = 1;
        map = BitmapFactory.decodeResource(getResources(), R.drawable.map1);
        enemy1 = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_bill);
        enemy1_x = 550;
        enemy1_y = 48;
        enemy1_start_y = enemy1_y;
        enemy1_end_y = 123;
        enemy1_speed = 1;
        movingDown = true;
    }

    // Method to create enemy
    public void createEnemy(Bitmap enemy, int x, int y) {

    }

    // Someone needs to make methods for enemy position that gets updated
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentMap == 1) {
            float scale = Math.min((float) getWidth() / map.getWidth(), (float) getHeight() / map.getHeight());
            Matrix mapMatrix = new Matrix();
            mapMatrix.setScale(scale, scale);

            canvas.drawBitmap(map, mapMatrix, null); // Loads map1

            Matrix enemyMatrix = new Matrix();
            enemyMatrix.setScale(scale, scale);
            enemyMatrix.postTranslate(enemy1_x * scale, enemy1_y * scale);

            canvas.drawBitmap(enemy1, enemyMatrix, null); // Loads enemy1

            if (movingDown) {
                enemy1_y = enemy1_y + enemy1_speed;
                if (enemy1_y >= enemy1_end_y) {
                    movingDown = false;
                }
            } else {
                enemy1_y = enemy1_y - enemy1_speed;
                if (enemy1_y <= enemy1_start_y) {
                    movingDown = true;
                }
            }
        }
        invalidate();
    }

    // This method sets View object to 800x800 pixels, do not change it!
import com.example.cs2340c_team8.models.GameConfig;
import com.example.cs2340c_team8.models.Player;
import com.example.cs2340c_team8.models.enemies.BulletBill;
import com.example.cs2340c_team8.models.enemies.Goomba;
import com.example.cs2340c_team8.models.enemies.KoopaTroopa;
import com.example.cs2340c_team8.models.enemies.PiranhaPlant;
import com.example.cs2340c_team8.models.levels.Level;

public class GameView extends View {
    private Level level;
    private int currentMap = 1;
    private static Bitmap map;
    private Bitmap goomba;
    private Bitmap koopaTroopa;
    private Bitmap bulletBill;
    private Bitmap shell;
    private Bitmap playerBitmap;
    private Goomba firstGoomba;
    private KoopaTroopa firstKoopaTroopa;
    private BulletBill firstBulletBill;
    private PiranhaPlant firstShell;
    private static Player player;
    private static float scale;
    private static int tileColor1 = Color.rgb(87, 140, 170);
    private static int tileColor2 = Color.rgb(107, 68, 150);
    private static int tileColor3 = Color.rgb(255, 119, 0);
    private int calls = 0;
    private boolean gameCompleted;
    private static boolean levelChanged;
    private double scalar = 0.5;
    private int keyOneX = (int) (176 * (scalar));
    private int keyOneY = (int) (464 * (scalar));
    private int keyTwoX = (int) (320 * (scalar));
    private int keyTwoY = (int) (32 * (scalar));
    private int keyThreeX = (int) (400 * (scalar));
    private int keyThreeY = (int) (384 * (scalar));
    private Bitmap fireball = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
    public static boolean isFireballThrown = false;
    private static int fireballX;
    private static int fireballY;
    public static boolean firstGoombaExists;
    public static boolean firstShellExists;
    public static boolean firstBulletBillExists;
    public static boolean firstKoopaTroopaExists;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameCompleted = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentMap == 1 && calls == 0) {
            createMapOne();
            calls++;
        } else if (currentMap == 2 && calls == 0) {
            createMapTwo();
            calls++;
        } else if (currentMap == 3 && calls == 0) {
            createMapThree();
            calls++;
        }

        if (currentMap == 1) {
            canvas.drawBitmap(map, scaleMap(), null);
            if (firstGoombaExists) {
                canvas.drawBitmap(goomba, scaleGoomba(firstGoomba), null);
            } else {
                firstGoomba = new Goomba(goomba, 0, 500, true, 0);
            }
            if (firstKoopaTroopaExists) {
                canvas.drawBitmap(koopaTroopa, scaleKoopa(firstKoopaTroopa), null);
            } else {
                firstKoopaTroopa = new KoopaTroopa(koopaTroopa, 700, 0, true, 0);
            }
            if (firstBulletBillExists) {
                canvas.drawBitmap(bulletBill, scaleBullet(firstBulletBill), null);
            } else {
                firstBulletBill = new BulletBill(bulletBill, 800, 0, 10, 0);
            }
            if (firstShellExists) {
                canvas.drawBitmap(shell, scaleShell(firstShell), null);
            } else {
                firstShell = new PiranhaPlant(0, 400, 410, 0);
            }
            canvas.drawBitmap(playerBitmap, scalePlayer(player), null);
            firstGoomba.moveEnemy();
            firstKoopaTroopa.moveEnemy();
            firstBulletBill.moveEnemy();
            firstShell.moveEnemy();
        } else if (currentMap == 2) {
            canvas.drawBitmap(map, scaleMap(), null);
            if (firstGoombaExists) {
                canvas.drawBitmap(goomba, scaleGoomba(firstGoomba), null);
            } else {
                firstGoomba = new Goomba(goomba, 0, 500, true, 0);
            }
            if (firstKoopaTroopaExists) {
                canvas.drawBitmap(koopaTroopa, scaleKoopa(firstKoopaTroopa), null);
            } else {
                firstKoopaTroopa = new KoopaTroopa(koopaTroopa, 700, 0, true, 0);
            }
            if (firstBulletBillExists) {
                canvas.drawBitmap(bulletBill, scaleBullet(firstBulletBill), null);
            } else {
                firstBulletBill = new BulletBill(bulletBill, 800, 0, 10, 0);
            }
            if (firstShellExists) {
                canvas.drawBitmap(shell, scaleShell(firstShell), null);
            } else {
                firstShell = new PiranhaPlant(0, 400, 410, 0);
            }
            canvas.drawBitmap(playerBitmap, scalePlayer(player), null);
            firstGoomba.moveEnemy();
            firstKoopaTroopa.moveEnemy();
            firstBulletBill.moveEnemy();
            firstShell.moveEnemy();
        } else if (currentMap == 3) {
            canvas.drawBitmap(map, scaleMap(), null);
            if (firstGoombaExists) {
                canvas.drawBitmap(goomba, scaleGoomba(firstGoomba), null);
            } else {
                firstGoomba = new Goomba(goomba, 0, 500, true, 0);
            }
            if (firstKoopaTroopaExists) {
                canvas.drawBitmap(koopaTroopa, scaleKoopa(firstKoopaTroopa), null);
            } else {
                firstKoopaTroopa = new KoopaTroopa(koopaTroopa, 700, 0, true, 0);
            }
            if (firstBulletBillExists) {
                canvas.drawBitmap(bulletBill, scaleBullet(firstBulletBill), null);
            } else {
                firstBulletBill = new BulletBill(bulletBill, 800, 0, 10, 0);
            }
            if (firstShellExists) {
                canvas.drawBitmap(shell, scaleShell(firstShell), null);
            } else {
                firstShell = new PiranhaPlant(0, 400, 410, 0);
            }
            canvas.drawBitmap(playerBitmap, scalePlayer(player), null);
            firstGoomba.moveEnemy();
            firstKoopaTroopa.moveEnemy();
            firstBulletBill.moveEnemy();
            firstShell.moveEnemy();
        }

        if (isFireballThrown) {
            if (fireballX < player.getStartX() + 160) {
                canvas.drawBitmap(fireball, scaleFireball(fireballX, fireballY), null);
                fireballX++;
            } else {
                isFireballThrown = false;
            }
        }

        if (isLevelOver(player.getStartX(), player.getStartY())) {
            GameConfig.incrementLevel();
            levelChanged = true;

            if (currentMap == 3) {
                gameCompleted = true;
            }
            currentMap++;
            calls = 0;
        }
        invalidate();
    }

    public void createMapThree() {
        firstBulletBillExists = true;
        firstKoopaTroopaExists = true;
        firstShellExists = true;
        firstGoombaExists = true;
        map = BitmapFactory.decodeResource(getResources(), R.drawable.map3);
        goomba = BitmapFactory.decodeResource(getResources(), R.drawable.goomba);
        koopaTroopa = BitmapFactory.decodeResource(getResources(), R.drawable.koopa_troopa);
        bulletBill = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_bill);
        shell = BitmapFactory.decodeResource(getResources(), R.drawable.shell);
        playerBitmap =
                BitmapFactory.decodeResource(getResources(), GameConfig.fetchCharacterSprite());

        firstGoomba = new Goomba(goomba, 550, 48, true, 1);
        firstKoopaTroopa =
                new KoopaTroopa(koopaTroopa, 700, 60, true, 1);
        firstBulletBill =
                new BulletBill(bulletBill, 550, 100, 200, 1);
        firstShell = new PiranhaPlant(920, 60, 100, 1);

        player = Player.getInstance();
        player.setStartX(100);
        player.setStartY(100);

        player.addObserver(firstGoomba);
        player.addObserver(firstKoopaTroopa);
        player.addObserver(firstBulletBill);
        player.addObserver(firstShell);
    }

    public void createMapTwo() {
        firstBulletBillExists = true;
        firstKoopaTroopaExists = true;
        firstShellExists = true;
        firstGoombaExists = true;
        map = BitmapFactory.decodeResource(getResources(), R.drawable.map2);
        goomba = BitmapFactory.decodeResource(getResources(), R.drawable.goomba);
        koopaTroopa = BitmapFactory.decodeResource(getResources(), R.drawable.koopa_troopa);
        bulletBill = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_bill);
        shell = BitmapFactory.decodeResource(getResources(), R.drawable.shell);
        playerBitmap =
                BitmapFactory.decodeResource(getResources(), GameConfig.fetchCharacterSprite());

        firstGoomba = new Goomba(goomba, 500, 80, true, 1);
        firstKoopaTroopa = new KoopaTroopa(koopaTroopa, 400, 90, true, 1);
        firstBulletBill = new BulletBill(bulletBill, 550, 610, 200, 1);
        firstShell = new PiranhaPlant(650, 650, 1200, 1);

        player = Player.getInstance();
        player.setStartX(100);
        player.setStartY(100);

        player.addObserver(firstGoomba);
        player.addObserver(firstKoopaTroopa);
        player.addObserver(firstBulletBill);
        player.addObserver(firstShell);
    }

    public void createMapOne() {
        firstBulletBillExists = true;
        firstKoopaTroopaExists = true;
        firstShellExists = true;
        firstGoombaExists = true;
        map = BitmapFactory.decodeResource(getResources(), R.drawable.map1);
        goomba = BitmapFactory.decodeResource(getResources(), R.drawable.goomba);
        koopaTroopa = BitmapFactory.decodeResource(getResources(), R.drawable.koopa_troopa);
        bulletBill = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_bill);
        shell = BitmapFactory.decodeResource(getResources(), R.drawable.shell);
        playerBitmap =
                BitmapFactory.decodeResource(getResources(), GameConfig.fetchCharacterSprite());

        firstGoomba = new Goomba(goomba, 550, 48, true, 1);
        firstKoopaTroopa = new KoopaTroopa(koopaTroopa, 800, 48, true, 1);
        firstBulletBill = new BulletBill(bulletBill, 550, 550, 50, 1);
        firstShell = new PiranhaPlant(650, 650, 700, 1);

        player = Player.getInstance();
        player.setStartX(100);
        player.setStartY(100);

        player.addObserver(firstGoomba);
        player.addObserver(firstKoopaTroopa);
        player.addObserver(firstBulletBill);
        player.addObserver(firstShell);
    }

    public static boolean isLevelOver(int playerX, int playerY) {
        int mapLeftX = 0;
        int mapLeftY = 0;
        int mapLength = 85;
        int mapHeight = 85;
        int playerWidth = 16;
        int playerHeight = 16;

        return (playerX + playerWidth > mapLeftX)
                && (playerY + playerHeight > mapLeftY)
                && (playerX < mapLeftX + mapLength)
                && (playerY < mapLeftY + mapHeight);
    }

    public static boolean isValidMoveX(double actuatorX) {
        if (actuatorX > 0) {
            int xth = player.getStartX() + 48;
            for (int y = player.getStartY(); y < player.getStartY() + 16; y++) {
                if (map.getPixel(xth, y) != tileColor1 && map.getPixel(xth, y)
                        != tileColor2 && map.getPixel(xth, y) != tileColor3) {
                    return false;
                }
            }
            return true;
        } else if (actuatorX < 0) {
            int xth = player.getStartX() - 1;
            for (int y = player.getStartY(); y < player.getStartY() + 16; y++) {
                if (map.getPixel(xth, y) != tileColor1 && map.getPixel(xth, y)
                        != tileColor2 && map.getPixel(xth, y) != tileColor3) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isValidMoveY(double actuatorY) {
        if (actuatorY > 0) {
            int yth = player.getStartY() + 48;
            for (int x = player.getStartX(); x < player.getStartX() + 16; x++) {
                if (map.getPixel(x, yth) != tileColor1 && map.getPixel(x, yth)
                        != tileColor2 && map.getPixel(x, yth) != tileColor3) {
                    return false;
                }
            }
            return true;
        } else if (actuatorY < 0) {
            int yth = player.getStartY() - 1;
            for (int x = player.getStartX(); x < player.getStartX() + 16; x++) {
                if (map.getPixel(x, yth) != tileColor1 && map.getPixel(x, yth)
                        != tileColor2 && map.getPixel(x, yth) != tileColor3) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Matrix scaleMap() {
        this.scale = Math.min((float) getWidth() / map.getWidth(),
                (float) getHeight() / map.getHeight());
        Matrix mapMatrix = new Matrix();
        mapMatrix.setScale(scale, scale);
        return mapMatrix;
    }

    public Matrix scaleGoomba(Goomba test) {
        Matrix enemyMatrix = new Matrix();
        enemyMatrix.setScale(scale, scale);
        enemyMatrix.postTranslate(test.getStartX() * scale, test.getStartY() * scale);
        return enemyMatrix;
    }

    public Matrix scalePlayer(Player test) {
        Matrix enemyMatrix = new Matrix();
        enemyMatrix.setScale(scale, scale);
        enemyMatrix.postTranslate(test.getStartX() * scale, test.getStartY() * scale);
        return enemyMatrix;
    }

    public Matrix scaleKoopa(KoopaTroopa test) {
        Matrix enemyMatrix = new Matrix();
        enemyMatrix.setScale(scale, scale);
        enemyMatrix.postTranslate(test.getStartX() * scale, test.getStartY() * scale);
        return enemyMatrix;
    }

    public Matrix scaleBullet(BulletBill test) {
        Matrix enemyMatrix = new Matrix();
        enemyMatrix.setScale(scale, scale);
        enemyMatrix.postTranslate(test.getStartX() * scale, test.getStartY() * scale);
        return enemyMatrix;
    }

    public Matrix scaleShell(PiranhaPlant test) {
        Matrix enemyMatrix = new Matrix();
        enemyMatrix.setScale(scale, scale);
        enemyMatrix.postTranslate(test.getStartX() * scale, test.getStartY() * scale);
        return enemyMatrix;
    }

    public Matrix scaleFireball(int x, int y) {
        Matrix enemyMatrix = new Matrix();
        enemyMatrix.setScale(scale, scale);
        enemyMatrix.postTranslate(x * scale, y * scale);
        return enemyMatrix;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(800, widthMeasureSpec);
        int height = resolveSize(800, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    public static boolean bulletBillKB() {
        int yth = player.getStartY() - 1;
        for (int x = player.getStartX(); x < player.getStartX() + 16; x++) {
            if (map.getPixel(x, yth) != tileColor1 && map.getPixel(x, yth)
                    != tileColor2 && map.getPixel(x, yth) != tileColor3) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public static boolean isLevelChanged() {
        return levelChanged;
    }

    public static void setLevelChanged(boolean levelChanged1) {
        levelChanged = levelChanged1;
    }

    public static void setFireballPosition() {
        fireballX = player.getStartX() + 60;
        fireballY = player.getStartY();
    }

    public static int getFireballX() {
        return fireballX;
    }

    public static int getFireballY() {
        return fireballY;
    }
}
