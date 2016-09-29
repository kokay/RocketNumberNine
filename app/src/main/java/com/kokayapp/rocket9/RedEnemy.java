package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/29/2016.
 */

public class RedEnemy extends Enemy {

    private Gun gun;
    public RedEnemy(Context context, Viewport vp, float x, float y) {
        height = 2f;
        width = 3f;
        this.x = x;
        this.y = y;
        velocityX = -4;
        velocityY = 0;
        maxVelocity = 2;
        acceleration = 1;

        gun = new Gun(5);

        if(bitmaps[RED] == null) {
            bitmaps[RED] = prepareBitmap(context, R.drawable.red_enemy, vp);
        }
    }

    @Override
    public void update(long fps, Rocket rocket) {
        if(!active) return;

        if(y > rocket.getY()) {
            velocityY -= acceleration / fps;
            if(velocityY < -maxVelocity) velocityY = -maxVelocity;
        } else if(y < rocket.getY()) {
            velocityY += acceleration / fps;
            if (velocityY > maxVelocity) velocityY = maxVelocity;
        }

        x += velocityX / fps;
        y += velocityY / fps;

        if(x <= Viewport.VIEW_WIDTH) visible = true;
        if(x + width < 0) active = visible = false;

        if(visible) {
            gun.pullTrigger(this, -10);
            for(Bullet bullet : gun.getBullets())
                bullet.update(fps);
        }
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        if(!visible) return;

        for(Bullet bullet : gun.getBullets())
            canvas.drawRect(vp.viewToScreen(bullet), gun.getBulletPaint());
        canvas.drawBitmap(bitmaps[RED], null, vp.viewToScreen(this), null);
    }
}
