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

    public Bitmap prepareBitmap(Context context, int imageId, int pixelsPerMeter) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (width * pixelsPerMeter), (int) (height * pixelsPerMeter), false);
        return bitmap;
    }
}
