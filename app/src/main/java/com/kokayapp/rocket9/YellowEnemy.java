package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class YellowEnemy extends Enemy {

    public YellowEnemy(Context context, Viewport vp, float startPlace, float startY) {
        height = 2.2f;
        width = 2.5f;
        this.startPlace = startPlace;
        this.x = Viewport.VIEW_WIDTH + 1;
        this.y = startY;
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
            case ACTIVE :
                normalAttack(fps);
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
            canvas.drawBitmap(bitmaps[YELLOW], null, vp.viewToScreen(this), null);
    }
}
