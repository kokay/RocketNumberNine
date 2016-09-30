package com.kokayapp.rocket9;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Koji on 9/29/2016.
 */

public class Star extends MovableObject {
    private Paint starPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect rect = new Rect();
    private Random gen = new Random();

    public Star(Viewport vp) {
        x = gen.nextInt(Viewport.VIEW_WIDTH * 2) + gen.nextFloat();
        y = gen.nextInt((int)(Viewport.VIEW_HEIGHT * 0.7)) + gen.nextFloat();
        width = .075f;
        height = .075f;
        velocityX = -gen.nextFloat();
        rect.set(vp.viewToScreen(this));
        starPaint.setColor(Color.argb(gen.nextInt(130), 138, 135, 107));
    }

    public void update(long fps) {
        x += velocityX / fps;
        if(x < -10) {
            x = gen.nextInt(Viewport.VIEW_WIDTH) + gen.nextFloat() + Viewport.VIEW_WIDTH;
            y = gen.nextInt((int)(Viewport.VIEW_HEIGHT * 0.7)) + gen.nextFloat();
        }
    }

    public void draw(Canvas canvas, Viewport vp) {
        canvas.drawRect(vp.viewToScreen(this), starPaint);
    }
}
