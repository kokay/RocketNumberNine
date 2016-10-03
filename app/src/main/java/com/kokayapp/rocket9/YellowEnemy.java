package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class YellowEnemy extends Enemy {

    public YellowEnemy(Context context, Viewport vp, int level) {
        height = 2.2f;
        width = 2.5f;
        x = getRandomX();
        y = getRandomY();
        velocityX = getRandomVelocityX(3, 4);
        maxHealthPoint = 2;
        healthPoint = 2;
        point = 10;
        setLevel(level);

        if(bitmaps[YELLOW] == null)
            bitmaps[YELLOW] = prepareBitmap(context, vp, R.drawable.yellow_enemy);
    }

    @Override
    public int update(long fps, Viewport vp, Rocket rocket) {
        switch (state) {
            case ACTIVE :
                normalAttack(fps);
                return checkHit(vp, rocket);
            case VISIBLE :
                normalAttack(fps);
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
            canvas.drawBitmap(bitmaps[YELLOW], null, vp.viewToScreen(this), null);
    }
}
