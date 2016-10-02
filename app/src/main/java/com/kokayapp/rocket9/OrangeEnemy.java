package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class OrangeEnemy extends Enemy {
    public OrangeEnemy(Context context, Viewport vp, float startPlace, float startY) {
        height = 2.2f;
        width = 2.5f;
        this.startPlace = startPlace;
        this.x = Viewport.VIEW_WIDTH + 1;
        this.y = startY;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 2;
        maxHealthPoint = 4;
        healthPoint = 4;
        point = 30;

        if(bitmaps[ORANGE] == null) {
            bitmaps[ORANGE] = prepareBitmap(context, vp, R.drawable.orange_enemy);
        }
    }

    @Override
    public int update(long fps, Viewport vp, Rocket rocket) {
        switch (state) {
            case ACTIVE :
                followAttack(fps, rocket);
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
        if(state == ACTIVE)
            canvas.drawBitmap(bitmaps[ORANGE], null, vp.viewToScreen(this), null);
    }
}
