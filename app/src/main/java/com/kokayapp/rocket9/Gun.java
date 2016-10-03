package com.kokayapp.rocket9;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Koji on 9/29/2016.
 */

public class Gun {
    private CopyOnWriteArrayList<Bullet> bullets;
    private int maxBullets;
    private int bulletsIdx;
    private int fireRate; // per 10 seconds
    private long lastShotTime;
    private Paint bulletPaint;
    private float velocityX;

    public Gun(int fireRate, float velocityX) {
        maxBullets = 15;
        bulletsIdx = 0;
        this.fireRate = fireRate;
        this.velocityX = velocityX;
        lastShotTime = -1;
        bullets = new CopyOnWriteArrayList<>();
        for(int i=0; i < maxBullets; ++i) {
            bullets.add(new Bullet());
        }
        bulletPaint = new Paint();
        bulletPaint.setColor(Color.WHITE);
    }



    public void pullTrigger(GameObject go) {
        if(System.currentTimeMillis() - lastShotTime > 10000/ fireRate) {
            bullets.get(bulletsIdx).set(go.getX() + go.getWidth() * 0.75f, go.getY() + go.getHeight() * 0.5f, velocityX);
            bulletsIdx = (bulletsIdx + 1) % bullets.size();
            lastShotTime = System.currentTimeMillis();
        }
    }

    public void update(Viewport vp, long fps) {
        for(Bullet bullet : bullets)
            bullet.update(vp, fps);
    }

    public void draw(Canvas canvas, Viewport vp) {
        for(Bullet bullet : bullets)
            canvas.drawRect(vp.viewToScreen(bullet), bulletPaint);
    }

    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }
}
