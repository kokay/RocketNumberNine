package com.kokayapp.rocket9;

import android.graphics.Canvas;

/**
 * Created by Koji on 9/30/2016.
 */

public abstract class MovableObject extends GameObject {
    protected float velocityX = 0;
    protected float velocityY = 0;
    protected float maxVelocity = 0;
    protected float acceleration = 0;
    protected float healthPoint = 0;
    protected float maxHealthPoint = 0;

    public float getMaxHealthPoint() {
        return maxHealthPoint;
    }

    public float getHealthPoint() {
        return healthPoint;
    }

    protected void setLevel(int level) {
        velocityX += level;
        velocityY += level;
        maxVelocity += level;
        acceleration += level;
    }
}
