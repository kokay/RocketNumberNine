package com.kokayapp.rocket9;

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
    private Rect rect1;
    private Rect rect2;

    public Viewport(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        screenCenterX = screenX / 2;
        screenCenterY = screenY / 2;
        pixelsPerX = screenX / VIEW_WIDTH;
        pixelsPerY = screenY / VIEW_HEIGHT;

        rect1 = new Rect();
        rect2 = new Rect();
    }

    public Rect viewToScreen(GameObject go) {
        int left   = (int)(screenCenterX - ((VIEW_CENTER_X - go.getX())  * pixelsPerX));
        int top    = (int)(screenCenterY - ((VIEW_CENTER_Y - go.getY())  * pixelsPerY));
        int right  = (int)(left + (go.getWidth() * pixelsPerX));
        int bottom = (int)(top + (go.getHeight() * pixelsPerY));
        rect1.set(left, top, right, bottom);
        return rect1;
    }

    public Rect getFromRect1(Background bg) {
        rect1.set(0, 0, bg.getBitmapWidth() - bg.getXClip(), bg.getBitmapHeight());
        return rect1;
    }

    public Rect getToRect1(Background bg) {
        rect2.set(bg.getXClip(), 0, bg.getBitmapWidth(), screenY);
        return rect2;
    }

    public Rect getFromRect2(Background bg) {
        rect1.set(bg.getBitmapWidth() - bg.getXClip(), 0, bg.getBitmapWidth(), bg.getBitmapHeight());
        return rect1;
    }

    public Rect getToRect2(Background bg) {
        rect2.set(0, 0, bg.getXClip(), screenY);
        return rect2;
    }
}
