package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/28/2016.
 */

public class OrangeEnemy extends Enemy {
    public OrangeEnemy(Context context, Viewport vp, float x, float y) {
        height = 2f;
        width = 3f;
        this.x = x;
        this.y = y;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 2;

        maxHealthPoint = 4;
        healthPoint = 4;

        if(bitmaps[ORANGE] == null) {
            bitmaps[ORANGE] = prepareBitmap(context, R.drawable.orange_enemy, vp);
        }
    }

    @Override
    public void update(long fps, Viewport vp, Rocket rocket) {
        if(active){
            if(y > rocket.getY()) {
                velocityY -= acceleration / fps;
                if(velocityY < -maxVelocity) velocityY = -maxVelocity;
            } else if(y < rocket.getY()) {
                velocityY += acceleration / fps;
                if (velocityY > maxVelocity) velocityY = maxVelocity;
            }
            x += velocityX / fps;
            y += velocityY / fps;

            if(x + width < 0) active = visible = false;
            else if(x <= Viewport.VIEW_WIDTH) visible = true;
        } else  {
            return;
        }

        if(visible) {
            hitBox.set(vp.viewToScreen(this));
            if(hitBox.intersect(rocket.hitBox)) {
                rocket.healthPoint -= 2;
                active = visible = false;
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(visible)
            canvas.drawBitmap(bitmaps[ORANGE], null, vp.viewToScreen(this), null);
    }
}
