package com.kokayapp.rocket9;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public abstract class Enemy extends MovableObject {
    protected static Bitmap[] bitmaps = new Bitmap[3];
    protected static final int YELLOW = 0, ORANGE = 1, RED = 2;
    protected static final int NONACTIVE = 0, ACTIVE = 1, CRASHED = 2;
    protected int state = NONACTIVE;
    protected float startPlace = 0;
    protected int point = 10;

    public abstract int update(long fps, Viewport vp, Rocket rocket);
    public abstract void draw(Canvas canvas, Viewport vp);

    public void drawHealth(Canvas canvas, Viewport vp) {
        if(state == ACTIVE) {
            canvas.drawRoundRect(vp.getHealthBarFrame(this), 5f, 5f, vp.healthBarFramePaint);
            canvas.drawRoundRect(vp.getHealthBar(this), 5f, 5f, vp.healthBarEnemyPaint);
        }
    }

    public void normalAttack(long fps) {
        x += velocityX / fps;
        if(x + width < 0) state = NONACTIVE;
    }

    public void followAttack(long fps, Rocket rocket) {
        if (y > rocket.getY()) {
            velocityY -= acceleration / fps;
            if (velocityY < -maxVelocity) velocityY = -maxVelocity;
        } else if (y < rocket.getY()) {
            velocityY += acceleration / fps;
            if (velocityY > maxVelocity) velocityY = maxVelocity;
        }
        x += velocityX / fps;
        y += velocityY / fps;

        if(x + width < 0) state = NONACTIVE;
    }

    public int checkHit(Viewport vp, Rocket rocket) {
        hitBox.set(vp.viewToScreen(this));
        if(hitBox.intersect(rocket.hitBox)) {
            rocket.healthPoint -= 2;
            state = CRASHED;
            return point;
        }
        for(Bullet bullet : rocket.getBullets()) {
            if (hitBox.intersect(bullet.hitBox)) {
                bullet.hide();
                healthPoint -= 1;
                if (healthPoint <= 0) {
                    state = CRASHED;
                    return point;
                }
            }
        }
        return 0;
    }
}
