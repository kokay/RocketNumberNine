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

    private volatile boolean playing;
    private volatile boolean running;
    private Thread gameThread;

    private Canvas canvas;
    private SurfaceHolder holder;

    private long startFrameTime;
    private long timeOfFrame;
    private long fps;

    private Viewport vp;
    private InputController ic;
    private LevelData ld;

    private Rect topBar = new Rect();
    private Paint topBarPaint = new Paint();
    private Paint textPaint = new Paint();

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
    }

    @Override
    public void run() {
        while (running) {
            startFrameTime = System.currentTimeMillis();
            if(playing) ld.update(fps, vp);

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
        ic.drawButtons(canvas, vp, playing);
    }

    public void resume() {
        running = true;
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        running = false;
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to pause thread");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        playing = ic.handleInput(event, playing, ld.getRocket());
        return true;
    }
}
