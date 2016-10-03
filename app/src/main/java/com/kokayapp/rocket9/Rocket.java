package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public class Rocket extends MovableObject {

    private boolean goingUp;
    private boolean goingDown;
    private Bitmap bitmap;
    private float currentPlace = 0;
    private Gun gun;

    public Rocket(Context context, Viewport vp) {
        height = 2.5f;
        width = 3f;
        x = 3;
        y = Viewport.VIEW_CENTER_Y - (height / 2.0f);
        velocityX = 10;
        velocityY = 0;
        maxVelocity = 8;
        acceleration = 10;
        healthPoint = 10;
        maxHealthPoint = 10;

        goingUp = false;
        goingUp = false;
        bitmap = prepareBitmap(context, vp, R.drawable.rocket);
        gun = new Gun(12, 20);
    }

    public void draw(Canvas canvas, Viewport vp) {
        gun.draw(canvas, vp);
        canvas.drawBitmap(bitmap, null, vp.viewToScreen(this), null);
    }

    public void drawHealth(Canvas canvas, Viewport vp) {
        canvas.drawRoundRect(vp.getHealthBarFrame(this), 5f, 5f, vp.healthBarFramePaint);
        canvas.drawRoundRect(vp.getHealthBar(this), 5f, 5f, vp.healthBarPaint);
    }

    public void update(long fps, Viewport vp) {
        if(goingDown) {
            velocityY -= acceleration / fps;
            if(velocityY < -maxVelocity) velocityY = -maxVelocity;
        } else if (goingUp) {
            velocityY += acceleration / fps;
            if(velocityY > maxVelocity) velocityY = maxVelocity;
        }
        currentPlace += (velocityX / fps) * 10;
        y += velocityY / fps;
        if(y < 0) {
            y = 0;
            velocityY = 0;
        } else if (y > (Viewport.VIEW_HEIGHT - height)) {
            y = (Viewport.VIEW_HEIGHT - height);
            velocityY = 0;
        }
        hitBox.set(vp.viewToScreen(this));
        gun.pullTrigger(this);

        gun.update(vp, fps);
    }

    public void setGoingDown(boolean goingDown) {
        this.goingDown = goingDown;
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    public CopyOnWriteArrayList<Bullet> getBullets() {
        return gun.getBullets();
    }

    public float getCurrentPlace() {
        return currentPlace;
    }

    public void runAway(long fps) {
        if (y > Viewport.VIEW_CENTER_Y) {
            velocityY -= acceleration / fps;
            if(velocityY < -3) velocityY = -3;
        } else if (y < Viewport.VIEW_CENTER_Y) {
            velocityY += acceleration / fps;
            if(velocityY > 3) velocityY = 3;
        }
        velocityX += acceleration / fps;
        x += velocityX / fps;
        y += velocityY / fps;
    }
}
