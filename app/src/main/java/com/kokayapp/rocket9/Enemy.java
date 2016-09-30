package com.kokayapp.rocket9;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public abstract class Enemy extends MovableObject {
    protected static Bitmap[] bitmaps = new Bitmap[3];
    protected static final int YELLOW = 0, ORANGE = 1, RED = 2;
    protected static final int ACTIVE = 0, VISIBLE = 1, NONACTIVE = 2;
    protected int state = ACTIVE;
    protected int point = 10;

    public abstract int update(long fps, Viewport vp, Rocket rocket);
    public abstract void draw(Canvas canvas, Viewport vp);

    public void drawHealth(Canvas canvas, Viewport vp) {
        if(state == VISIBLE) {
            canvas.drawRoundRect(vp.getHealthBarFrame(this), 5f, 5f, vp.healthBarFramePaint);
            canvas.drawRoundRect(vp.getHealthBar(this), 5f, 5f, vp.healthBarEnemyPaint);
        }
    }

    public void normalAttack(long fps) {
        x += velocityX / fps;
        if(x + width < 0) state = NONACTIVE;
        else if(x <= Viewport.VIEW_WIDTH) state = VISIBLE;

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
        else if(x <= Viewport.VIEW_WIDTH) state = VISIBLE;
    }

    public int checkHit(Viewport vp, Rocket rocket) {
        hitBox.set(vp.viewToScreen(this));
        if(hitBox.intersect(rocket.hitBox)) {
            rocket.healthPoint -= 2;
            state = NONACTIVE;
            return point;
        }
        for(Bullet bullet : rocket.getBullets()) {
            if (hitBox.intersect(bullet.hitBox)) {
                bullet.hide();
                healthPoint -= 1;
                if (healthPoint <= 0) {
                    state = NONACTIVE;
                    return point;
                }
            }
        }
        return 0;
    }
}
