package com.kokayapp.rocket9;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Koji on 9/29/2016.
 */

public class Star extends GameObject{
    private Paint starPaint = new Paint();
    private Rect rect = new Rect();

    public Star(Viewport vp) {
        Random gen = new Random();
        x = gen.nextInt(Viewport.VIEW_WIDTH) + gen.nextFloat();
        y = gen.nextInt((int)(Viewport.VIEW_HEIGHT * 0.7)) + gen.nextFloat();
        width = .05f;
        height = .05f;
        rect.set(vp.viewToScreen(this));
        starPaint.setColor(Color.argb(gen.nextInt(130), 138, 135, 107));
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, starPaint);
    }
}
