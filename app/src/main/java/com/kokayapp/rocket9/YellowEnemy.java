package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class YellowEnemy extends Enemy {

    public YellowEnemy(Context context, Viewport vp, float x, float y) {
        height = 2.2f;
        width = 2.5f;
        this.x = x;
        this.y = y;
        velocityX = -2;
        maxHealthPoint = 3;
        healthPoint = 3;
        point = 10;

        if(bitmaps[YELLOW] == null) {
            bitmaps[YELLOW] = prepareBitmap(context, vp, R.drawable.yellow_enemy);
        }
    }

    @Override
    public int update(long fps, Viewport vp, Rocket rocket) {
        switch (state) {
            case ACTIVE:
                normalAttack(fps);
                return 0;
            case VISIBLE:
                normalAttack(fps);
                return checkHit(vp, rocket);
            default:
                return 0;
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(state == VISIBLE)
            canvas.drawBitmap(bitmaps[YELLOW], null, vp.viewToScreen(this), null);
    }
}
