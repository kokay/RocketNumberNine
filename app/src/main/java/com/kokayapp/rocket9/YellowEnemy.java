package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class YellowEnemy extends Enemy {

    public YellowEnemy(Context context, Viewport vp, float x, float y) {
        height = 2f;
        width = 3f;
        this.x = x;
        this.y = y;
        velocityX = -2;
        maxHealthPoint = 3;
        healthPoint = 3;

        if(bitmaps[YELLOW] == null) {
            bitmaps[YELLOW] = prepareBitmap(context, R.drawable.yellow_enemy, vp);
        }
    }

    @Override
    public void update(long fps, Viewport vp, Rocket rocket) {
        if(active){
            normalAttack(fps);
        } else  {
            return;
        }

        if(visible)
            checkHit(vp, rocket);
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(visible)
            canvas.drawBitmap(bitmaps[YELLOW], null, vp.viewToScreen(this), null);
    }
}
