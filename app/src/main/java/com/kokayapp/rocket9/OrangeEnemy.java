package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class OrangeEnemy extends Enemy {
    public OrangeEnemy(Context context, Viewport vp, float x, float y) {
        height = 2.2f;
        width = 2.5f;
        this.x = x;
        this.y = y;
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
            case ACTIVE:
                followAttack(fps, rocket);
                return 0;
            case VISIBLE:
                followAttack(fps, rocket);
                return checkHit(vp, rocket);
            default:
                return 0;
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(state == VISIBLE)
            canvas.drawBitmap(bitmaps[ORANGE], null, vp.viewToScreen(this), null);
    }
}
