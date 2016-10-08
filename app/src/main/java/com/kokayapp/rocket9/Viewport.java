package com.kokayapp.rocket9;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Koji on 9/28/2016.
 */

public class Viewport {
    public static final int VIEW_WIDTH = 30;
    public static final int VIEW_HEIGHT = 20;
    public static final int VIEW_WIDTH_TO_DRAW = VIEW_WIDTH + 2;
    public static final int VIEW_HEIGHT_TO_DRAW = VIEW_HEIGHT + 2;
    public static final int VIEW_CENTER_X = VIEW_WIDTH / 2;
    public static final int VIEW_CENTER_Y = VIEW_HEIGHT / 2;

    public final int screenX;
    public final int screenY;
    public final int screenCenterX;
    public final int screenCenterY;

    public final int pixelsPerX;
    public final int pixelsPerY;

    private Rect rect1 = new Rect();
    private Rect rect2 = new Rect();
    private RectF rectF = new RectF();
    public final Paint healthBarFramePaint = new Paint();
    public final Paint healthBarPaint = new Paint();
    public final Paint healthBarEnemyPaint = new Paint();

    public final double pixelsPerBox;
    public final int screenStartX;
    public final int screenStartY;
    public final int screenEndX;
    public final int screenEndY;

    //public final Paint healthBarEmergencyPaint = new Paint();
    //healthBarEmergencyPaint.setColor(Color.rgb(255, 200, 80));
    public Viewport(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        screenCenterX = screenX / 2;
        screenCenterY = screenY / 2;
        //pixelsPerX = screenX / VIEW_WIDTH;
        //pixelsPerY = screenY / VIEW_HEIGHT;

        double pixelsPerX = (double)screenX / VIEW_WIDTH;
        double pixelsPerY = (double)screenY / VIEW_HEIGHT;
        if(pixelsPerX > pixelsPerY) {
            pixelsPerBox = this.pixelsPerX = this.pixelsPerY = (int)pixelsPerY;
            int remainPixels = (int)(screenX - (pixelsPerBox * VIEW_WIDTH));
            screenStartX = remainPixels / 2;
            screenStartY = 0;
            screenEndX = screenStartX + (int)(pixelsPerBox * VIEW_WIDTH);
            screenEndY = screenY;
        } else {
            pixelsPerBox = this.pixelsPerY = this.pixelsPerX = (int)pixelsPerX;
            int remainPixels = (int)(screenY - (pixelsPerBox * VIEW_HEIGHT));
            screenStartX = 0;
            screenStartY = remainPixels / 2;
            screenEndX = screenX;
            screenEndY = screenStartY + (int)(pixelsPerBox * VIEW_HEIGHT);
        }


        healthBarFramePaint.setColor(Color.rgb(254, 245, 249));
        healthBarPaint.setColor(Color.rgb(71, 183, 73));
        healthBarEnemyPaint.setColor(Color.rgb(217, 57, 51));
    }

    public int getScreenX(float x) {
        return (int)((x * pixelsPerBox) + screenStartX);
    }

    public int getScreenY(float y) {
        return (int)((y * pixelsPerBox) + screenStartY);
    }

    public Rect viewToScreen(GameObject go) {
        int left   = (int)(screenCenterX - ((VIEW_CENTER_X - go.getX())  * pixelsPerX));
        int top    = (int)(screenCenterY - ((VIEW_CENTER_Y - go.getY())  * pixelsPerY));
        int right  = (int)(left + (go.getWidth() * pixelsPerX));
        int bottom = (int)(top + (go.getHeight() * pixelsPerY));
        rect1.set(left, top, right, bottom);
        return rect1;
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

    public Rect getFromRect2(Background bg) {
        rect1.set(bg.getBitmapWidth() - bg.getXClip(), 0, bg.getBitmapWidth(), bg.getBitmapHeight());
        return rect1;
    }

    public Rect getToRect1(Background bg) {
        rect2.set(
                bg.getXClip() + screenStartX,
                (int) (bg.getStartY() * pixelsPerY),
                bg.getBitmapWidth() + screenStartX,
                (int) (bg.getEndY()* pixelsPerY));
        return rect2;
    }


    public Rect getToRect2(Background bg) {
        rect2.set(
                screenStartX,
                (int)(bg.getStartY() * pixelsPerY),
                bg.getXClip() + screenStartX,
                (int)(bg.getEndY() * pixelsPerY));
        return rect2;
    }
}
