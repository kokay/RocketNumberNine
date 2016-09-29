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
    private Rect convertedRect;
    private Rect fromRect;
    private Rect toRect;

    public Viewport(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        screenCenterX = screenX / 2;
        screenCenterY = screenY / 2;
        pixelsPerX = screenX / VIEW_WIDTH;
        pixelsPerY = screenY / VIEW_HEIGHT;

        convertedRect = new Rect();
        fromRect = new Rect();
        toRect = new Rect();
    }

    public Rect viewToScreen(GameObject go) {
        int left   = (int)(screenCenterX - ((VIEW_CENTER_X - go.getX())  * pixelsPerX));
        int top    = (int)(screenCenterY - ((VIEW_CENTER_Y - go.getY())  * pixelsPerY));
        int right  = (int)(left + (go.getWidth() * pixelsPerX));
        int bottom = (int)(top + (go.getHeight() * pixelsPerY));
        convertedRect.set(left, top, right, bottom);
        return convertedRect;
    }

    public Rect getFromRect1(Background bg) {
        fromRect.set(0, 0, bg.getBitmapWidth() - bg.getXClip(), bg.getBitmapHeight());
        return fromRect;
    }

    public Rect getToRect1(Background bg) {
        toRect.set(bg.getXClip(), 0, bg.getBitmapWidth(), screenY);
        return toRect;
    }

    public Rect getFromRect2(Background bg) {
        fromRect.set(bg.getBitmapWidth() - bg.getXClip(), 0, bg.getBitmapWidth(), bg.getBitmapHeight());
        return fromRect;
    }

    public Rect getToRect2(Background bg) {
        toRect.set(0, 0, bg.getXClip(), screenY);
        return toRect;
    }
}
