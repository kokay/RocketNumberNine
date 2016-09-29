package com.kokayapp.rocket9;

import android.graphics.Bitmap;

/**
 * Created by Koji on 9/28/2016.
 */

public class Bullet extends GameObject {
    private static final float HIDE_PLACE_X = -1;
    private static final float HIDE_PLACE_Y = -1;

    public Bullet() {
        width = .25f;
        height = .05f;
        x = HIDE_PLACE_X;
        y = HIDE_PLACE_Y;
    }

    public void update(long fps) {
        x += velocityX / fps;
    }

    public void set(float x, float y, int velocityX) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
    }

    public void hide() {
        x = HIDE_PLACE_X;
        y = HIDE_PLACE_Y;
        velocityX = 0;
    }
}
