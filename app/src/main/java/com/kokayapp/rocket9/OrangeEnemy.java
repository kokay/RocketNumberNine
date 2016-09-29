package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Koji on 9/28/2016.
 */

public class OrangeEnemy extends Enemy {
    public OrangeEnemy(Context context, Viewport vp, float x, float y) {
        height = 1.5f;
        width = 2.5f;
        this.x = x;
        this.y = y;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 2;

        if(bitmaps[ORANGE] == null) {
            bitmaps[ORANGE] = prepareBitmap(context, R.drawable.orange_enemy, vp);
        }
    }

    @Override
    public void update(long fps, Rocket rocket) {
        if(y > rocket.getY()) {
            velocityY -= acceleration / fps;
            if(velocityY < -maxVelocity) velocityY = -maxVelocity;
        } else if(y < rocket.getY()) {
            velocityY += acceleration / fps;
            if (velocityY > maxVelocity) velocityY = maxVelocity;
        }

        x += velocityX / fps;
        y += velocityY / fps;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmaps[ORANGE];
    }
}
