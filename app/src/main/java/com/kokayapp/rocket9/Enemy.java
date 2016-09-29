package com.kokayapp.rocket9;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public abstract class Enemy extends GameObject {
    protected static Bitmap[] bitmaps = new Bitmap[3];
    protected static final int YELLOW = 0;
    protected static final int ORANGE = 1;
    protected static final int RED = 2;

    public abstract void update(long fps, Rocket rocket);
    public abstract Bitmap getBitmap();
}
