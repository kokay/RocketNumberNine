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
    protected Rect hitBox = new Rect();

    protected Bitmap prepareBitmap(Context context, Viewport vp, int imageId) {
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
