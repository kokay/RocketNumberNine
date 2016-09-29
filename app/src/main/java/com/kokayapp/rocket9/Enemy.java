package com.kokayapp.rocket9;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public abstract class Enemy extends GameObject {
    protected static Bitmap[] bitmaps = new Bitmap[3];
    protected static final int YELLOW = 0;
    protected static final int ORANGE = 1;
    protected static final int RED = 2;
    protected boolean active = true;
    protected boolean visible = false;

    public abstract void update(long fps, Viewport vp, Rocket rocket);
    public abstract void draw(Canvas canvas, Viewport vp);
    public void drawHealth(Canvas canvas, Viewport vp) {
        if(visible) {
            canvas.drawRoundRect(vp.getHealthBarFrame(this), 5f, 5f, vp.healthBarFramePaint);
            canvas.drawRoundRect(vp.getHealthBar(this), 5f, 5f, vp.healthBarEnemyPaint);
        }
    }

    public void normalAttack(long fps) {
        x += velocityX / fps;
        if(x + width < 0) active = visible = false;
        else if(x <= Viewport.VIEW_WIDTH) visible = true;

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

        if (x + width < 0) active = visible = false;
        else if (x <= Viewport.VIEW_WIDTH) visible = true;
    }

    public void checkHit(Viewport vp, Rocket rocket) {
        hitBox.set(vp.viewToScreen(this));
        if(hitBox.intersect(rocket.hitBox)) {
            rocket.healthPoint -= 2;
            active = visible = false;
            return;
        }
        for(Bullet bullet : rocket.getBullets()) {
            if (hitBox.intersect(bullet.hitBox)) {
                healthPoint -= 1;
                if (healthPoint <= 0)
                    active = visible = false;
                bullet.hide();
                return;
            }
        }
    }
}
