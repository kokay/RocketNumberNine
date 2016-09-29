package com.kokayapp.rocket9;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Koji on 9/29/2016.
 */

public class Gun {
    private CopyOnWriteArrayList<Bullet> bullets;
    private int maxBullets;
    private int bulletsIdx;
    private int fireRate;
    private long lastShotTime;

    public Gun(int fireRate) {
        maxBullets = 15;
        bulletsIdx = 0;
        this.fireRate = fireRate;
        lastShotTime = -1;
        bullets = new CopyOnWriteArrayList<>();
        for(int i=0; i < maxBullets; ++i) {
            bullets.add(new Bullet());
        }
    }

    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void pullTrigger(GameObject go, int velocityX) {
        if(System.currentTimeMillis() - lastShotTime > 1000 / fireRate) {
            bullets.get(bulletsIdx).set(go.getX() + go.getWidth() * 0.75f, go.getY() + go.getHeight() * 0.5f, velocityX);
            bulletsIdx = (bulletsIdx + 1) % bullets.size();
            lastShotTime = System.currentTimeMillis();
        }
    }
}
