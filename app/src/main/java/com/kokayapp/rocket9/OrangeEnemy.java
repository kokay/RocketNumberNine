package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class OrangeEnemy extends Enemy {
    public OrangeEnemy(Context context, Viewport vp, int level) {
        height = 2.2f;
        width = 2.5f;
        this.x = getRandomX();
        this.y = getRandomY();
        velocityX = getRandomVelocityX(3, 4);
        maxVelocity = 2;
        acceleration = getRandomAcceleration(1, 2);
        maxHealthPoint = 3;
        healthPoint = 3;
        point = 30;
        setLevel(level);

        if(bitmaps[ORANGE] == null) {
            bitmaps[ORANGE] = prepareBitmap(context, vp, R.drawable.orange_enemy);
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
                return checkHit(vp, rocket);
            case CRASHED :
                return 0;
            case NONACTIVE :
                reborn();
                return 0;
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
