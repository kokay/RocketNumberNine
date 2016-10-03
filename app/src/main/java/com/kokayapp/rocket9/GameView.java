package com.kokayapp.rocket9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    public static final int OPENING = 0, PLAYING = 1, PAUSED = 2, CLEAR = 3, GAME_OVER = 4, WINNING_RUN = 5, GO_NEXT_LEVEL = 6, GO_EXIT = 7;
    private volatile int state = PLAYING;
    private Thread gameThread;
    private int level = 0;

    private Canvas canvas;
    private SurfaceHolder holder;

    private long startFrameTime;
    private long timeOfFrame;
    private long fps;

    private Context context;
    private Viewport vp;
    private InputController ic;
    private LevelData ld;
    private DrawingTool dt;


    public GameView(Context context, int screenX, int screenY, int level, Rocket rocket) {
        super(context);
        this.context = context;
        this.level = level;
        gameThread = null;
        holder = getHolder();
        fps = 2000000000;
        vp = new Viewport(screenX, screenY);
        ic = new InputController(context, vp);
        ld = new EarthData(context, vp, level, rocket);
        dt = new DrawingTool(vp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        state = ic.handleInput(event, state, ld.getRocket());
        return true;
    }

    @Override
    public void run() {
        while (running) {
            startFrameTime = System.currentTimeMillis();
            update();
            draw();

            timeOfFrame = System.currentTimeMillis() - startFrameTime;
            if (timeOfFrame >= 1) fps = 1000 / timeOfFrame;
        }
    }
    private void update() {
        switch (state) {
            case OPENING :
                ld.openingUpdate(fps, vp);
                break;
            case PLAYING :
                state = ld.playingUpdate(fps, vp);
                break;
            case CLEAR :
                ld.clearUpdate(fps, vp);
                break;
            case WINNING_RUN :
                state = ld.winningRunUpdate(fps, vp);
                break;
            case GO_NEXT_LEVEL :
                Intent nextLevelIntent = new Intent(context, GameActivity.class);
                nextLevelIntent.putExtra("Level", level + 1);
                context.startActivity(nextLevelIntent);
                ((Activity)(context)).finish();
                break;
            case GO_EXIT :
                Intent backHomeIntent = new Intent(context, TitleActivity.class);
                backHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(backHomeIntent);
                break;
            default:
                break;
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            ld.draw(canvas, vp);
            drawTopBar();
            ic.drawPlayingButtons(canvas, vp);
            if(state != PLAYING) drawInfo();
            ic.drawButtonsOnBox(canvas, vp, state);
            if(debugging) drawDebugging();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawDebugging() {
        canvas.drawText("FPS : " + fps, 80, 100, dt.smallTextPaint);
        if(fps > 60) canvas.drawText("true", 80, 140, dt.smallTextPaint);
        maxFps = Math.max(maxFps, fps);
        canvas.drawText("MAX : " + maxFps, 80, 180, dt.smallTextPaint);
        minFps = Math.min(minFps, fps);
        canvas.drawText("MIN : " + minFps, 80, 220, dt.smallTextPaint);
        avgFps = (avgFps + fps) / 2;
        canvas.drawText("AVG : " + avgFps, 80, 260, dt.smallTextPaint);
    }

    private void drawTopBar() {
        canvas.drawRect(dt.topBar, dt.darkNavy);
        canvas.drawText("DISTANCE : " + String.format("%1$05d", ld.getDistance()) + " M",
                (int)(vp.pixelsPerX * 12.5f),
                (int)(vp.pixelsPerY * 0.9f),
                dt.smallTextPaint);
        canvas.drawText("SCORE : " + String.format("%1$05d", ld.getScore()),
                (int)(vp.pixelsPerX * 20f),
                (int)(vp.pixelsPerY * 0.9f),
                dt.smallTextPaint);
        canvas.drawText("HIGH SCORE : " + String.format("%1$05d", ld.getHighScore()),
                (int)(vp.pixelsPerX * 28f),
                (int)(vp.pixelsPerY * 0.9f),
                dt.smallTextPaint);
        ld.getRocket().drawHealth(canvas, vp);
    }

    private void drawInfo() {
        canvas.drawColor(Color.argb(50, 0, 0, 0));
        switch (state) {
            case OPENING : {
                canvas.drawRoundRect(dt.infoBox, 15f, 15f, dt.darkNavy);
                canvas.drawText("EARTH", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 2f) * vp.pixelsPerY, dt.bigTextPaint);
                canvas.drawText("LEVEL " + level, vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y ) * vp.pixelsPerY, dt.bigTextPaint);
                canvas.drawText("Touch the screen to start!", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y + 2.5f) * vp.pixelsPerY, dt.smallTextPaint);
                break;
            }
            case PAUSED : {
                canvas.drawRoundRect(dt.infoBox, 15f, 15f, dt.darkNavy);
                canvas.drawText("PAUSED", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                break;
            }
            case GAME_OVER : {
                canvas.drawRoundRect(dt.infoBox, 15f, 15f, dt.darkNavy);
                canvas.drawText("GAME OVER", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                break;
            }
            case CLEAR : {
                canvas.drawRoundRect(dt.bigBox, 15f, 15f, dt.darkNavy);
                canvas.drawText("LEVEL " + level, vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 3.5f) * vp.pixelsPerY, dt.bigTextPaint);
                canvas.drawText("COMPLETE", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                canvas.drawText("SCORE : " + ld.getScore(), vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y + 0.5f) * vp.pixelsPerY, dt.smallTextPaint);
                break;
            }
            default :
                break;
        }
    }

    public void resume() {
        running = true;
        state = OPENING;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to pause thread");
        }
    }
}
