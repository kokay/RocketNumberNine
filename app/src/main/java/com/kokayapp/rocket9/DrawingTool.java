package com.kokayapp.rocket9;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.Display;

/**
 * Created by Koji on 9/30/2016.
 */

public class DrawingTool {
    public final Rect topBar = new Rect();
    public final RectF infoBox = new RectF();

    public final Paint darkNavy = new Paint();
    public final Paint smallTextPaint = new Paint();
    public final Paint bigTextPaint = new Paint();

    public DrawingTool(Viewport vp) {
        topBar.set(0, 0, vp.screenX, (int) (1.2 * vp.pixelsPerY));

        infoBox .set(
                (Viewport.VIEW_CENTER_X - 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y - 4.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_X + 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y + 4.5f) * vp.pixelsPerX );

        darkNavy.setColor(Color.rgb(2, 12, 35));

        smallTextPaint.setColor(Color.rgb(254, 245, 249));
        smallTextPaint.setTextSize(vp.pixelsPerX * 0.8f);
        smallTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        smallTextPaint.setTextAlign(Paint.Align.CENTER);
        smallTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        bigTextPaint.setColor(Color.rgb(254, 245, 249));
        bigTextPaint.setTextSize(vp.pixelsPerX * 2);
        bigTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        bigTextPaint.setTextAlign(Paint.Align.CENTER);
        bigTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }
}
