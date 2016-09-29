package com.kokayapp.rocket9;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

abstract public class Enemy extends GameObject {
    protected static Bitmap[] bitmaps = new Bitmap[3];
    protected static final int YELLOW = 0;
    protected static final int ORANGE = 1;
    protected static final int RED = 2;

    abstract public Bitmap getBitmap();
}
