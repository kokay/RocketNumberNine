package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Koji on 9/28/2016.
 */

public class YellowEnemy extends Enemy {

    public YellowEnemy(Context context, Viewport vp, float x, float y) {
        height = 1.5f;
        width = 2.5f;
        this.x = x;
        this.y = y;
        velocityX = -2;

        if(bitmaps[YELLOW] == null) {
            bitmaps[YELLOW] = prepareBitmap(context, R.drawable.yellow_enemy, vp);
        }
    }

    @Override
    public void update(long fps, Rocket rocket) {
        x += velocityX / fps;
    }

    public Bitmap getBitmap() {
        return bitmaps[YELLOW];
    }
}
