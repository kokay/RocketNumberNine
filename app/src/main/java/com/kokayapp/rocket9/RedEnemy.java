package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/29/2016.
 */

public class RedEnemy extends Enemy {

    private Gun gun;
    public RedEnemy(Context context, Viewport vp, float x, float y) {
        height = 2.2f;
        width = 2.5f;
        this.x = x;
        this.y = y;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 1;

        maxHealthPoint = 5;
        healthPoint = 5;

        gun = new Gun(5);

        if(bitmaps[RED] == null) {
            bitmaps[RED] = prepareBitmap(context, vp, R.drawable.red_enemy);
        }
    }

    @Override
    public void update(long fps, Viewport vp, Rocket rocket) {
        if (active) {
            followAttack(fps, rocket);
        } else {
            return;
        }

        if (visible) {
            checkHit(vp, rocket);
            gun.pullTrigger(this, -10);
            for (Bullet bullet : gun.getBullets()) {
                bullet.update(fps, vp);
                if (bullet.hitBox.intersect(rocket.hitBox)) {
                    rocket.healthPoint -= 1;
                    bullet.hide();
                    return;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(visible) {
            for(Bullet bullet : gun.getBullets())
                canvas.drawRect(vp.viewToScreen(bullet), gun.getBulletPaint());
            canvas.drawBitmap(bitmaps[RED], null, vp.viewToScreen(this), null);
        }
    }
}
