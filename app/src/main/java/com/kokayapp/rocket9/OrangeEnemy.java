package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class OrangeEnemy extends Enemy {
    public OrangeEnemy(Context context, Viewport vp, float x, float y) {
        height = 2f;
        width = 3f;
        this.x = x;
        this.y = y;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 2;

        maxHealthPoint = 4;
        healthPoint = 4;

        if(bitmaps[ORANGE] == null) {
            bitmaps[ORANGE] = prepareBitmap(context, R.drawable.orange_enemy, vp);
        }
    }

    @Override
    public void update(long fps, Viewport vp, Rocket rocket) {
        if(active){
            followAttack(fps, rocket);
        } else  {
            return;
        }

        if(visible) {
            checkHit(vp, rocket);
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(visible)
            canvas.drawBitmap(bitmaps[ORANGE], null, vp.viewToScreen(this), null);
    }
}
