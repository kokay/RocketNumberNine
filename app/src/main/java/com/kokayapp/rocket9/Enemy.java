package com.kokayapp.rocket9;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Koji on 9/28/2016.
 */

public abstract class Enemy extends MovableObject {
    protected static Bitmap[] bitmaps = new Bitmap[3];
    protected static final int YELLOW = 0, ORANGE = 1, RED = 2;
    protected static final int ACTIVE = 0, VISIBLE = 1, CRASHED = 2, NONACTIVE = 3;
    protected static final Random gen = new Random();
    protected int state = ACTIVE;
    protected int point = 10;
    protected int level = 0;

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
        else if(x < Viewport.VIEW_WIDTH) state = VISIBLE;
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
        else if(x < Viewport.VIEW_WIDTH) state = VISIBLE;
    }

    public int checkHit(Viewport vp, Rocket rocket) {
        hitBox.set(vp.viewToScreen(this));
        if(hitBox.intersect(rocket.hitBox)) {
            rocket.healthPoint -= 2;
            state = NONACTIVE;
            //state = CRASHED;
            return point;
        }
        for(Bullet bullet : rocket.getBullets()) {
            if (hitBox.intersect(bullet.hitBox)) {
                bullet.hide();
                healthPoint -= 1;
                if (healthPoint <= 0) {
                    state = NONACTIVE;
                    //state = CRASHED;
                    return point;
                }
            }
        }
        return 0;
    }

    protected float getRandomX() {
        return gen.nextInt(Viewport.VIEW_WIDTH * 2) + gen.nextFloat() + Viewport.VIEW_WIDTH;
    }

    protected float getRandomY() {
        return gen.nextInt(Viewport.VIEW_HEIGHT) + gen.nextFloat();
    }

    protected float getRandomVelocityX(int min, int max) {
        if(max - min - 1 <= 0) return -(gen.nextFloat() + min);
        return -(gen.nextInt(max - min - 1) + gen.nextFloat() + min);
    }

    protected float getRandomAcceleration(int min, int max) {
        if(max - min - 1 <= 0) return gen.nextFloat() + min;
        return gen.nextInt(max - min - 1) + gen.nextFloat() + min;
    }

    protected void reborn() {
        state = ACTIVE;
        x = getRandomX();
        y = getRandomY();
        healthPoint = maxHealthPoint;
    }
}
