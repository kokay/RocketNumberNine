package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public class Rocket extends MovableObject {
    public static final int MAX_HEALTH_POINT = 10;

    private boolean goingUp = false;
    private boolean goingDown = false;
    private Bitmap bitmap;
    private float currentPlace = 0;
    private Gun gun;

    public Rocket(Context context, Viewport vp, int level, int healthPoint) {
        height = 2.5f;
        width = 3f;
        x = 3;
        y = Viewport.VIEW_CENTER_Y - (height / 2.0f);
        velocityX = 3;
        maxVelocity = 6;
        acceleration = 20;
        this.healthPoint = (float) healthPoint;
        maxHealthPoint = MAX_HEALTH_POINT;
        setLevel(level);
        velocityY = 0;

        gun = new Gun(20 + level, 20);
        bitmap = prepareBitmap(context, vp, R.drawable.rocket);
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

    public void winningRunUpdate(long fps) {
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

    public void completeUpdate(long fps) {
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

}
