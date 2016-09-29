package com.kokayapp.rocket9;

import android.graphics.Rect;

/**
 * Created by Koji on 9/28/2016.
 */

public class Viewport {
    private final int viewWidth = 32;
    private final int viewHeight = 18;
    private final int viewWidthToDraw = viewWidth + 2;
    private final int viewHeightToDraw = viewHeight + 2;
    public final int viewCenterX = viewWidth / 2;
    public final int viewCenterY = viewHeight / 2;

    private final int screenX;
    private final int screenY;
    private final int screenCenterX;
    private final int screenCenterY;

    public final int pixelsPerX;
    public final int pixelsPerY;
    private Rect convertedRect;

    public Viewport(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        screenCenterX = screenX / 2;
        screenCenterY = screenY / 2;
        pixelsPerX = screenX / viewWidth;
        pixelsPerY = screenY / viewHeight;

        convertedRect = new Rect();
    }

    public Rect viewToScreen(GameObject go) {
        int left   = (int)(screenCenterX - ((viewCenterX - go.getX())  * pixelsPerX));
        int top    = (int)(screenCenterY - ((viewCenterY - go.getY())  * pixelsPerY));
        int right  = (int)(left + (go.getWidth() * pixelsPerX));
        int bottom = (int)(top + (go.getHeight() * pixelsPerY));
        convertedRect.set(left, top, right, bottom);
        return convertedRect;
    }
}
