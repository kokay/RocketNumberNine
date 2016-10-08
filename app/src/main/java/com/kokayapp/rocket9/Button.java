package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Koji on 9/30/2016.
 */

public class Button extends GameObject {
    public final Bitmap bitmap;
    public Button(Context context, Viewport vp, int imageId, float width, float height, float x, float y) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bitmap = prepareBitmap(context, vp, imageId);
        hitBox.set(vp.viewToScreen(this));
    }
}
