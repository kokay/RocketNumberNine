package com.kokayapp.rocket9;

import android.graphics.Canvas;
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
    private Viewport vp;
    public final Rect topBar = new Rect();
    public final RectF infoBox = new RectF();
    public final RectF bigBox = new RectF();

    public final Paint darkNavy = new Paint();
    public final Paint smallTextPaint = new Paint();
    public final Paint bigTextPaint = new Paint();

    public DrawingTool(Viewport vp) {
        this.vp = vp;
        topBar.set(0, 0, vp.screenX, (int) (1.2 * vp.pixelsPerY));

        infoBox .set(
                (Viewport.VIEW_CENTER_X - 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y - 4.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_X + 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y + 4.5f) * vp.pixelsPerX );

        bigBox .set(
                (Viewport.VIEW_CENTER_X - 9.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y - 6f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_X + 9.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y + 6f) * vp.pixelsPerX );

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

    public void showOpeningMessage(Canvas canvas, int level) {
        canvas.drawRoundRect(infoBox, 15f, 15f, darkNavy);
        canvas.drawText("EARTH", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 2f) * vp.pixelsPerY, bigTextPaint);
        canvas.drawText("LEVEL " + level, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y ) * vp.pixelsPerY, bigTextPaint);
        canvas.drawText("Touch the screen to start!", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y + 2.5f) * vp.pixelsPerY, smallTextPaint);
    }

    public void showPausedMessage(Canvas canvas) {
        canvas.drawRoundRect(infoBox, 15f, 15f, darkNavy);
        canvas.drawText("PAUSED", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, bigTextPaint);
    }

    public void showCompleteMessage(Canvas canvas, int level, int score) {
        canvas.drawRoundRect(bigBox, 15f, 15f, darkNavy);
        canvas.drawText("LEVEL " + level, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 3.5f) * vp.pixelsPerY, bigTextPaint);
        canvas.drawText("COMPLETE", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, bigTextPaint);
        canvas.drawText("SCORE : " + score + " + 100", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y + 0.5f) * vp.pixelsPerY, smallTextPaint);
    }

    public void showGameOverMessage(Canvas canvas, int level, int score) {
        canvas.drawRoundRect(infoBox, 15f, 15f, darkNavy);
        canvas.drawText("GAME OVER", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, bigTextPaint);
        canvas.drawText("LEVEL : " + level, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y + 0.5f) * vp.pixelsPerY, smallTextPaint);
        canvas.drawText("SCORE : " + score, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y + 0.5f) * vp.pixelsPerY, smallTextPaint);
    }
}
