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
    private int viewCenterX = viewWidth / 2;
    private int viewCenterY = viewHeight / 2;

    private final int screenX;
    private final int screenY;
    private final int screenCenterX;
    private final int screenCenterY;

    private final int pixelsPerX;
    private final int pixelsPerY;
    private Rect convertedRect;

    public Viewport(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        screenCenterX = screenX / 2;
        screenCenterY = screenY / 2;
        pixelsPerX = screenY / viewWidth;
        pixelsPerY = screenY / viewHeight;

        convertedRect = new Rect();
    }
}
