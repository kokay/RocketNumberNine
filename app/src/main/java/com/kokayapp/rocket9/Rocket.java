package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Koji on 9/28/2016.
 */

public class Rocket extends GameObject {

    private boolean goingUp;
    private boolean goingDown;
    public final Bitmap BITMAP;


    public Rocket(Context context, Viewport vp) {
        height = 3;
        width = 5;
        x = 0;
        y = Viewport.VIEW_CENTER_Y - (height / 2.0f);
        velocityX = 0;
        velocityY = 0;
        maxVelocity = 8;
        acceleration = 10;
        healthPoint = 10;
        maxHealthPoint = 10;

        BITMAP = prepareBitmap(context, R.drawable.rocket, vp);
    }
    @Override
    public void update(long fps) {
        if(goingDown) {
            velocityY -= acceleration / fps;
            if(velocityY < -maxVelocity) velocityY = -maxVelocity;
        } else if (goingUp) {
            velocityY += acceleration / fps;
            if(velocityY > maxVelocity) velocityY = maxVelocity;
        }

        y += velocityY / fps;
        if(y < 0) {
            y = 0;
            velocityY = 0;
        } else if (y > (Viewport.VIEW_HEIGHT - height)) {
            y = (Viewport.VIEW_HEIGHT - height);
            velocityY = 0;
        }
    }

}
