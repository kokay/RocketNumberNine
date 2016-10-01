package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Koji on 9/28/2016.
 */

public class GameView extends SurfaceView implements Runnable {

    private String TAG = getClass().getSimpleName();
    private boolean debugging = true;
    private long maxFps = 0, minFps = 10000, avgFps = 60;

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
    private DrawingTool dt;


    public GameView(Context context, int screenX, int screenY) {
        super(context);
        gameThread = null;
        holder = getHolder();
        fps = 2000000000;
        vp = new Viewport(screenX, screenY);
        ic = new InputController(context, vp);
        ld = new LevelData(context, vp);
        dt = new DrawingTool(vp);
    }

    @Override
    public void run() {
        while (running) {
            startFrameTime = System.currentTimeMillis();
            if(state == PLYAINTG)
                state = ld.update(fps, vp);
            draw();

            timeOfFrame = System.currentTimeMillis() - startFrameTime;
            if (timeOfFrame >= 1) fps = 1000 / timeOfFrame;
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            ld.draw(canvas, vp);
            drawTopBar();
            ic.drawPlayingButtons(canvas, vp);
            if(state != PLYAINTG) drawInfo();
            ic.drawButtonsOnBox(canvas, vp, state);
            if(debugging) drawDebugging();
            holder.unlockCanvasAndPost(canvas);
        }
        if(state != PLYAINTG) {
        }
    }

    private void drawDebugging() {
        canvas.drawText("FPS : " + fps, 30, 100, dt.smallTextPaint);
        if(fps > 60) canvas.drawText("true", 30, 140, dt.smallTextPaint);
        maxFps = Math.max(maxFps, fps);
        canvas.drawText("MAX : " + maxFps, 30, 180, dt.smallTextPaint);
        minFps = Math.min(minFps, fps);
        canvas.drawText("MIN : " + minFps, 30, 220, dt.smallTextPaint);
        avgFps = (avgFps + fps) / 2;
        canvas.drawText("AVG : " + avgFps, 30, 260, dt.smallTextPaint);
    }

    private void drawTopBar() {
        canvas.drawRect(dt.topBar, dt.darkNavy);
        canvas.drawText("DISTANCE : " + ld.getDistance(),
                (int)(vp.pixelsPerX * 10.5f),
                (int)(vp.pixelsPerY * 0.8f),
                dt.smallTextPaint);
        canvas.drawText("SCORE : " + ld.getScore(),
                (int)(vp.pixelsPerX * 18.5f),
                (int)(vp.pixelsPerY * 0.8f),
                dt.smallTextPaint);
        canvas.drawText("HIGHEST SCORE : " + ld.getScore(),
                (int)(vp.pixelsPerX * 22.5f),
                (int)(vp.pixelsPerY * 0.8f),
                dt.smallTextPaint);
        ld.getRocket().drawHealth(canvas, vp);
    }

    private void drawInfo() {
        canvas.drawColor(Color.argb(50, 0, 0, 0));
        canvas.drawRoundRect(dt.infoBox, 15f, 15f, dt.darkNavy);
        switch (state) {
            case PAUSED : {
                canvas.drawText("PAUSED", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                break;
            }
            case GAMEOVER : {
                canvas.drawText("GAME OVER", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                break;
            }
            case CLEAR : {
                canvas.drawText("GAME CLEAR!!", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                canvas.drawText("SCORE : " + ld.getScore(), vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y + 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                break;
            }
        }
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
