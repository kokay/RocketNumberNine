package com.kokayapp.rocket9;

import android.content.Context;
import android.content.SyncAdapterType;
import android.graphics.Bitmap;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public class Rocket extends GameObject {

    private boolean goingUp;
    private boolean goingDown;
    public final Bitmap bitmap;

    private CopyOnWriteArrayList<Bullet> bullets;
    private int maxBullets;
    private int bulletsIdx;
    private int fireRate;
    private long lastShotTime;


    public Rocket(Context context, Viewport vp) {
        height = 3;
        width = 5;
        x = 3;
        y = Viewport.VIEW_CENTER_Y - (height / 2.0f);
        velocityX = 0;
        velocityY = 0;
        maxVelocity = 8;
        acceleration = 10;
        healthPoint = 10;
        maxHealthPoint = 10;

        goingUp = false;
        goingUp = false;
        bitmap = prepareBitmap(context, R.drawable.rocket, vp);

        bullets = new CopyOnWriteArrayList<>();
        maxBullets = 15;
        for(int i=0; i < maxBullets; ++i) {
            bullets.add(new Bullet());
        }
        bulletsIdx = 0;
        fireRate = 3;
        lastShotTime = -1;
    }

    @Override
    public void update(long fps) {
        if(goingDown) {
            velocityY -= acceleration / fps;
            if(velocityY < -maxVelocity) velocityY = -maxVelocity;
        } else if (goingUp) {
            velocityY += acceleration / fps;
            if(velocityY > maxVelocity) velocityY = maxVelocity;
        }

        y += velocityY / fps;
        if(y < 0) {
            y = 0;
            velocityY = 0;
        } else if (y > (Viewport.VIEW_HEIGHT - height)) {
            y = (Viewport.VIEW_HEIGHT - height);
            velocityY = 0;
        }

        for (Bullet bullet : bullets)
            bullet.update(fps);
    }

    public void setGoingDown(boolean goingDown) {
        this.goingDown = goingDown;
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    public void shoot() {
        if(System.currentTimeMillis() - lastShotTime > 1000 / fireRate) {
            bullets.get(bulletsIdx).set(x + width * 0.75f, y + height * 0.5f, 15);
            bulletsIdx = (bulletsIdx + 1) % bullets.size();
            lastShotTime = System.currentTimeMillis();
        }
    }

    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }
}
