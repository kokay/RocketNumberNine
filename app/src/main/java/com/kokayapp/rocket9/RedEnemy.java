package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/29/2016.
 */

public class RedEnemy extends Enemy {

    private Gun gun;
    public RedEnemy(Context context, Viewport vp, int level) {
        height = 2.2f;
        width = 2.5f;
        this.x = getRandomX();
        this.y = getRandomY();
        velocityX = getRandomVelocityX(3, 4);
        acceleration = getRandomAcceleration(1, 2);
        maxVelocity = 2;
        maxHealthPoint = 2;
        healthPoint = 2;
        point = 50;
        setLevel(level);
        gun = new Gun(5 + level, -10);

        if(bitmaps[RED] == null) {
            bitmaps[RED] = prepareBitmap(context, vp, R.drawable.red_enemy);
        }
    }

    @Override
    public int update(long fps, Viewport vp, Rocket rocket) {
        switch (state) {
            case ACTIVE :
                followAttack(fps, rocket);
                return 0;
            case VISIBLE :
                followAttack(fps, rocket);
                gun.pullTrigger(this);
                for (Bullet bullet : gun.getBullets()) {
                    bullet.update(vp, fps);
                    if (bullet.hitBox.intersect(rocket.hitBox)) {
                        rocket.hitBullet();
                        bullet.hide();
                    }
                }
                return checkHit(vp, rocket);
            case NONACTIVE :
                reborn();
                return 0;
            default:
                return 0;
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(state == VISIBLE) {
            gun.draw(canvas, vp);
            canvas.drawBitmap(bitmaps[RED], null, vp.viewToScreen(this), null);
        }
    }
}
