package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Koji on 9/28/2016.
 */

abstract public class GameObject {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float velocityX;
    protected float velocityY;
    protected float maxVelocity;
    protected float acceleration;
    protected int healthPoint;
    protected int maxHealthPoint;
    protected Rect convertedRect;

    abstract public void update(long fps);

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
}
