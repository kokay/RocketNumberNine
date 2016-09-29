package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Koji on 9/28/2016.
 */

abstract public class GameObject {

    protected float x = 0;
    protected float y = 0;
    protected float width = 0;
    protected float height = 0;
    protected float velocityX = 0;
    protected float velocityY = 0;
    protected float maxVelocity = 0;
    protected float acceleration = 0;
    protected int healthPoint = 0;
    protected int maxHealthPoint = 0;

    protected Bitmap prepareBitmap(Context context, int imageId, Viewport vp) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (width * vp.pixelsPerX), (int) (height * vp.pixelsPerY), false);
        return bitmap;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

    public int getMaxHealthPoint() {
        return maxHealthPoint;
    }

    public int getHealthPoint() {
        return healthPoint;
    }
}
