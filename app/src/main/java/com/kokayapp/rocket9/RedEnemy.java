package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/29/2016.
 */

public class RedEnemy extends Enemy {

    private Gun gun;
    public RedEnemy(Context context, Viewport vp, float startPlace, float startY) {
        height = 2.2f;
        width = 2.5f;
        this.startPlace = startPlace;
        this.x = Viewport.VIEW_WIDTH + 1;
        this.y = startY;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 1;
        maxHealthPoint = 5;
        healthPoint = 5;
        point = 50;

        gun = new Gun(5, -10);

        if(bitmaps[RED] == null) {
            bitmaps[RED] = prepareBitmap(context, vp, R.drawable.red_enemy);
        }
    }

    @Override
    public int update(long fps, Viewport vp, Rocket rocket) {
        switch (state) {
            case ACTIVE :
                followAttack(fps, rocket);
                gun.pullTrigger(this);
                for (Bullet bullet : gun.getBullets()) {
                    bullet.update(vp, fps);
                    if (bullet.hitBox.intersect(rocket.hitBox)) {
                        rocket.healthPoint -= 1;
                        bullet.hide();
                    }
                }
                return checkHit(vp, rocket);
            case NONACTIVE :
                if(rocket.getCurrentPlace() >= startPlace)
                    state = ACTIVE;
                return 0;
            default:
                return 0;
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(state == ACTIVE) {
            gun.draw(canvas, vp);
            canvas.drawBitmap(bitmaps[RED], null, vp.viewToScreen(this), null);
        }
    }
}
