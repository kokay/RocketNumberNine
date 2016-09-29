package com.kokayapp.rocket9;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public abstract class Enemy extends GameObject {
    protected static Bitmap[] bitmaps = new Bitmap[3];
    protected static final int YELLOW = 0;
    protected static final int ORANGE = 1;
    protected static final int RED = 2;
    protected boolean active = true;
    protected boolean visible = false;

    public abstract void update(long fps, Rocket rocket);
    public abstract void draw(Canvas canvas, Viewport vp);
    public void drawHealth(Canvas canvas, Viewport vp) {
        canvas.drawRoundRect(vp.getHealthBarFrame(this), 5f, 5f, vp.healthBarFramePaint);
        canvas.drawRoundRect(vp.getHealthBar(this), 5f, 5f, vp.healthBarEnemyPaint);
    }
}
