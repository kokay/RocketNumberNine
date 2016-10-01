package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public class GameView extends SurfaceView implements Runnable {

    private String TAG = getClass().getSimpleName();

    private volatile boolean running;

    public static final int OPENING = 0, PLYAINTG = 1, PAUSED = 2, CLEAR = 3, GAMEOVER = 4;
    private volatile int state = PLYAINTG;
    private Thread gameThread;

    private Canvas canvas;
    private SurfaceHolder holder;

    private long startFrameTime;
    private long timeOfFrame;
    private long fps;

    private Viewport vp;
    private InputController ic;
    private LevelData ld;

    private RectF pausedBox;
    private Rect topBar = new Rect();
    private Paint topBarPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint white = new Paint();
    private Paint pauseNavy = new Paint();

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        gameThread = null;
        holder = getHolder();
        fps = 2000000000;
        vp = new Viewport(screenX, screenY);
        ic = new InputController(context, vp);
        ld = new LevelData(context, vp);
        topBar.set(0, 0, vp.screenX, (int) (1.2 * vp.pixelsPerY));
        topBarPaint.setColor(Color.rgb(2, 12, 35));
        textPaint.setColor(Color.rgb(254, 245, 249));
        textPaint.setTextSize(vp.pixelsPerX * 0.8f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        pausedBox = new RectF(
                (Viewport.VIEW_CENTER_X - 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y - 4.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_X + 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y + 4.5f) * vp.pixelsPerX
        );
        white.setColor(Color.rgb(254, 245, 249));
        white.setTextSize(vp.pixelsPerX * 2);
        white.setTypeface(Typeface.DEFAULT_BOLD);
        white.setTextAlign(Paint.Align.CENTER);
        white.setFlags(Paint.ANTI_ALIAS_FLAG);
        pauseNavy.setColor(Color.rgb(2, 12, 35));
    }

    @Override
    public void run() {
        while (running) {
            startFrameTime = System.currentTimeMillis();
            if(state == PLYAINTG)
                state = ld.update(fps, vp);

            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                ld.draw(canvas, vp);
                drawTools();
                holder.unlockCanvasAndPost(canvas);
            }

            timeOfFrame = System.currentTimeMillis() - startFrameTime;
            if (timeOfFrame >= 1) {
                fps = 1000 / timeOfFrame;
            }
        }
    }

    private void drawTools() {
        canvas.drawText("" + fps, 30, 100, textPaint);
        if(fps > 60) canvas.drawText("true", 30, 140, textPaint);
        canvas.drawRect(topBar, topBarPaint);
        canvas.drawText("DISTANCE : " + ld.getDistance(),
                (int)(vp.pixelsPerX * 10.5f),
                (int)(vp.pixelsPerY * 0.8f),
                textPaint);
        canvas.drawText("SCORE : " + ld.getScore(),
                (int)(vp.pixelsPerX * 18.5f),
                (int)(vp.pixelsPerY * 0.8f),
                textPaint);
        ld.getRocket().drawHealth(canvas, vp);
        if(state != PLYAINTG) {
            canvas.drawColor(Color.argb(50, 0, 0, 0));
            canvas.drawRoundRect(pausedBox, 15f, 15f, pauseNavy);
            switch (state) {
                case PAUSED :
                    canvas.drawText("PAUSED", vp.screenCenterX, (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, white);
                    break;
                case GAMEOVER :
                    canvas.drawText("GAME OVER", vp.screenCenterX, (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, white);
                    break;
                case CLEAR :
                    canvas.drawText("GAME CLEAR!!", vp.screenCenterX, (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, white);
                    canvas.drawText("SCORE : " + ld.getScore(), vp.screenCenterX, (Viewport.VIEW_CENTER_Y + 1.5f) * vp.pixelsPerY, white);
                    break;
            }
        }
        ic.drawButtons(canvas, vp, state);
    }

    public void resume() {
        running = true;
        state = PLYAINTG;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        running = false;
        state = PAUSED;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to pause thread");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        state = ic.handleInput(event, state, ld.getRocket());
        return true;
    }
}
