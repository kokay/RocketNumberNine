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

        if(bitmaps[YELLOW] == null) {
            bitmaps[YELLOW] = prepareBitmap(context, R.drawable.yellow_enemy, vp);
        }
    }

    @Override
    public void update(long fps, Rocket rocket) {
        if(!active) return;

        x += velocityX / fps;
        if(x <= Viewport.VIEW_WIDTH) visible = true;
        if(x + width < 0) active = visible = false;
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(!visible) return;
        canvas.drawBitmap(bitmaps[YELLOW], null, vp.viewToScreen(this), null);
    }
}
