package com.kokayapp.rocket9;

import android.graphics.Rect;

/**
 * Created by Koji on 9/28/2016.
 */

abstract public class GameObject {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float velocityX;
    protected float velocityY;
    protected int healthPoint;
    protected Rect convertedRect;

    abstract public void update(long fps);
}
