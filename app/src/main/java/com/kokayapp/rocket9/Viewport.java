package com.kokayapp.rocket9;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Koji on 9/28/2016.
 */

public class Viewport {
    public static final int VIEW_WIDTH = 32;
    public static final int VIEW_HEIGHT = 18;
    public static final int VIEW_WIDTH_TO_DRAW = VIEW_WIDTH + 2;
    public static final int VIEW_HEIGHT_TO_DRAW = VIEW_HEIGHT + 2;
    public static final int VIEW_CENTER_X = VIEW_WIDTH / 2;
    public static final int VIEW_CENTER_Y = VIEW_HEIGHT / 2;

    public final int screenX;
    public final int screenY;
    private final int screenCenterX;
    private final int screenCenterY;

    public final int pixelsPerX;
    public final int pixelsPerY;
    private Rect rect1 = new Rect();
    private Rect rect2 = new Rect();
    private RectF healthBarFrame = new RectF();
    private RectF healthBar = new RectF();
    private RectF rectF = new RectF();
    public final Paint healthBarFramePaint = new Paint();
    public final Paint healthBarPaint = new Paint();
    public final Paint healthBarEnemyPaint = new Paint();

    public Viewport(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        screenCenterX = screenX / 2;
        screenCenterY = screenY / 2;
        pixelsPerX = screenX / VIEW_WIDTH;
        pixelsPerY = screenY / VIEW_HEIGHT;

        healthBarFrame.set(5, 5, 10 * pixelsPerY, (int) 1.5 * pixelsPerY - 5);
        healthBar.set(8, 8, 10 * pixelsPerY - 8, (int) 1.5 * pixelsPerY - 8);

        healthBarFramePaint.setColor(Color.rgb(254, 245, 249));
        healthBarPaint.setColor(Color.rgb(71, 183, 73));
        healthBarEnemyPaint.setColor(Color.rgb(217, 57, 51));
    }

    public Rect viewToScreen(GameObject go) {
        int left   = (int)(screenCenterX - ((VIEW_CENTER_X - go.getX())  * pixelsPerX));
        int top    = (int)(screenCenterY - ((VIEW_CENTER_Y - go.getY())  * pixelsPerY));
        int right  = (int)(left + (go.getWidth() * pixelsPerX));
        int bottom = (int)(top + (go.getHeight() * pixelsPerY));
        rect1.set(left, top, right, bottom);
        return rect1;
    }
    public RectF getHealthBarFrame(Rocket rocket) {
        return healthBarFrame;
    }

    public RectF getHealthBar(Rocket rocket) {
        healthBar.set(
                8,
                8,
                (10 * pixelsPerY - 3) * (rocket.getHealthPoint() / rocket.getMaxHealthPoint()),
                (int) 1.5 * pixelsPerY - 8);
        return healthBar;
    }

    public RectF getHealthBarFrame(Enemy enemy) {
        int left   = (int)(screenCenterX - ((VIEW_CENTER_X - enemy.getX())  * pixelsPerX));
        int top    = (int)(screenCenterY - ((VIEW_CENTER_Y - (enemy.getY() - 0.3))  * pixelsPerY));
        int right  = (int)(left + (enemy.getWidth()  * pixelsPerX));
        int bottom = (int)(screenCenterY - ((VIEW_CENTER_Y - (enemy.getY()))  * pixelsPerY));
        rectF.set(left, top, right, bottom);
        return rectF;
    }

    public RectF getHealthBar(Enemy enemy) {
        int left   = (int)(screenCenterX - ((VIEW_CENTER_X - (enemy.getX() + 0.1))  * pixelsPerX));
        int top    = (int)(screenCenterY - ((VIEW_CENTER_Y - (enemy.getY() - 0.2))  * pixelsPerY));
        int right  = (int)(left + (((enemy.getWidth() - 0.2)  * pixelsPerX)) * enemy.getHealthPoint() / enemy.getMaxHealthPoint());
        int bottom = (int)(screenCenterY - ((VIEW_CENTER_Y - (enemy.getY() - 0.1))  * pixelsPerY));
        rectF.set(left, top, right, bottom);
        return rectF;
    }

    public Rect getFromRect1(Background bg) {
        rect1.set(0, 0, bg.getBitmapWidth() - bg.getXClip(), bg.getBitmapHeight());
        return rect1;
    }

    public Rect getToRect1(Background bg) {
        rect2.set(bg.getXClip(), (int) (bg.getStartY() * pixelsPerY), bg.getBitmapWidth(), (int) (bg.getEndY()* pixelsPerY));
        return rect2;
    }

    public Rect getFromRect2(Background bg) {
        rect1.set(bg.getBitmapWidth() - bg.getXClip(), 0, bg.getBitmapWidth(), bg.getBitmapHeight());
        return rect1;
    }

    public Rect getToRect2(Background bg) {
        rect2.set(0, (int)(bg.getStartY() * pixelsPerY), bg.getXClip(), (int)(bg.getEndY() * pixelsPerY));
        return rect2;
    }
}
