package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Koji on 9/29/2016.
 */

public class RedEnemy extends Enemy {

    public RedEnemy(Context context, Viewport vp, float x, float y) {
        height = 1.5f;
        width = 2.5f;
        this.x = x;
        this.y = y;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 1;

        if(bitmaps[RED] == null) {
            bitmaps[RED] = prepareBitmap(context, R.drawable.red_enemy, vp);
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
        return bitmaps[RED];
    }
}
