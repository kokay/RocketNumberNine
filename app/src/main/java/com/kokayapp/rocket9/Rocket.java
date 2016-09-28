package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Koji on 9/28/2016.
 */

public class Rocket extends GameObject {

    private boolean goingUp;
    private boolean goingDown;
    private Bitmap bitmap;


    public Rocket(Context context) {
        x = 0;
        y = 18;
        height = 3;
        width = 5;
        velocityX = 0;
        velocityY = 0;
        maxVelocity = 8;
        acceleration = 10;
        healthPoint = 10;
        maxHealthPoint = 10;

        bitmap = prepareBitmap(context, R.drawable.rocket, 1000);
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
        } else if (y > 17) {
            y = 17;
            velocityY = 0;
        }
    }
}
