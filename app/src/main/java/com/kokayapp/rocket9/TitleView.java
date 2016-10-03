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

import static android.content.ContentValues.TAG;

/**
 * Created by Koji on 10/2/2016.
 */

public class TitleView extends SurfaceView implements Runnable {

    private String TAG = getClass().getSimpleName();
    private boolean debugging = true;
    private long maxFps = 0, minFps = 10000, avgFps = 60;

    private volatile boolean running;
    public static final int TITLE = 0, SELECT = 1, SETTING = 2, EXIT = 3, STAGE_CHOSEN = 4;
    private volatile int state = TITLE;
    private Thread thread;

    private Canvas canvas;
    private SurfaceHolder holder;

    private long startFrameTime;
    private long timeOfFrame;
    private long fps;

    private Context context;
    private Viewport vp;
    private TitleInputController ic;
    private LevelData ld;
    private DrawingTool dt;


    public TitleView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        thread = null;
        holder = getHolder();
        fps = 2000000000;
        vp = new Viewport(screenX, screenY);
        ic = new TitleInputController(context, vp);
        ld = new TitleData(context, vp);
        dt = new DrawingTool(vp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        state = ic.handleInput(event, state);
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
            case EXIT :
                ((Activity) context).finish();
                break;
            case STAGE_CHOSEN :
                Intent intent = new Intent(context, GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                break;
            default:
                ld.openingUpdate(fps, vp);
                break;
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            ld.draw(canvas, vp);
            drawTopBar();
            ic.drawPlayingButtons(canvas, vp);
            if(state != TITLE) drawInfo();
            ic.drawButtonsOnBox(canvas, vp, state);
            if(debugging) drawDebugging();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawTopBar() {
        canvas.drawRect(dt.topBar, dt.darkNavy);
    }

    private void drawInfo() {
        switch (state) {
            case SELECT : {
                canvas.drawColor(Color.argb(50, 0, 0, 0));
                canvas.drawRoundRect(dt.infoBox, 15f, 15f, dt.darkNavy);
                canvas.drawText("SELECT STAGE", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                break;
            }
            case SETTING : {
                canvas.drawColor(Color.argb(50, 0, 0, 0));
                canvas.drawRoundRect(dt.infoBox, 15f, 15f, dt.darkNavy);
                canvas.drawText("SETTING", vp.screenCenterX,
                        (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, dt.bigTextPaint);
                break;
            }
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

    public void resume() {
        running = true;
        state = TITLE;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to pause thread");
        }
    }
}
