package com.example.cs2340c_team8.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.cs2340c_team8.models.Player;

import androidx.annotation.NonNull;

public class DisplayMap extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Bitmap bitmap;
    private int displayedWidth = 51;
    private int displayedHeight = 51;
    private int centerX;
    private int centerY;
    private int topX;
    private int topY;

    public DisplayMap(Context context) {
        super(context);
    }

    private int getTopX() {
        Player test = new Player();
        topX = test.getX();
        return topX;
    }

    private int getTopY() {
        Player test = new Player();
        topX = test.getY();
        return topY;
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);

        // Load your 800x800 bitmap here
        // bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.your_image);
        // Make sure to handle bitmap loading properly

        // Set initial topX and topY values

        topX = getTopX();
        topY = getTopY();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            Rect srcRect = new Rect(topX, topY, topX + displayedWidth, topY + displayedHeight);
            Rect dstRect = new Rect(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }


}
