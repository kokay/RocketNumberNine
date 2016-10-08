package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Koji on 9/29/2016.
 */

public class Background {
    private Bitmap bitmap;
    private final float startY;
    private final float endY;
    private float speed;
    private int xClip;
    private final int pixelsPerX;

    public Background(Context context, Viewport vp, int imageId, float startY, float endY, float speed) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
        bitmap = Bitmap.createScaledBitmap(bitmap, (vp.screenEndX - vp.screenStartX),
                (int)((endY - startY) * vp.pixelsPerBox), true);

        this.startY = startY;
        this.endY = endY;
        this.speed = speed;
        xClip = 0;
        pixelsPerX = vp.pixelsPerX;
    }

    public void update(float fps) {
        xClip -= (speed * pixelsPerX) / fps;
        if(xClip >= bitmap.getWidth()) {
            xClip = 0;
        } else if (xClip <= 0) {
            xClip = bitmap.getWidth();
        }
    }

    public void draw(Canvas canvas, Viewport vp) {
        canvas.drawBitmap(bitmap, vp.getFromRect1(this), vp.getToRect1(this), null);
        canvas.drawBitmap(bitmap, vp.getFromRect2(this), vp.getToRect2(this), null);
    }

    public int getXClip() {
        return xClip;
    }

    public int getBitmapWidth() {
        return bitmap.getWidth();
    }

    public int getBitmapHeight() {
        return bitmap.getHeight();
    }

    public float getStartY() {
        return startY;
    }

    public float getEndY() {
        return endY;
    }
}
