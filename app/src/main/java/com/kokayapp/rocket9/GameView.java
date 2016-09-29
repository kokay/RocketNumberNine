package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
    private Paint debugPaint = new Paint();

    private volatile boolean playing;
    private Thread gameThread;

    private Canvas canvas;
    private SurfaceHolder holder;

    private long startFrameTime;
    private long timeOfFrame;
    private long fps;

    private Viewport vp;
    private InputController ic;
    private LevelData ld;


    public GameView(Context context, int screenX, int screenY) {
        super(context);
        gameThread = null;
        holder = getHolder();
        fps = 2000000000;
        vp = new Viewport(screenX, screenY);
        ic = new InputController(screenX, screenY);
        ld = new LevelData(context, vp);

        debugPaint.setColor(Color.WHITE);
    }

    @Override
    public void run() {
        while (playing) {
            startFrameTime = System.currentTimeMillis();
            ld.update(fps, vp);

            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                ld.draw(canvas, vp);
                drawTools();
                holder.unlockCanvasAndPost(canvas);
            }

            timeOfFrame = System.currentTimeMillis() - startFrameTime;
            if (timeOfFrame >= 1) {
                fps = 1000 / timeOfFrame;
            }
        }
        drawTools();
    }

    private void drawTools() {
        canvas.drawText("" + fps, 30, 50, debugPaint);
        if(fps > 60)
            canvas.drawText("true", 30, 70, debugPaint);

        for(RectF rect : ic.getButtons())
            canvas.drawRoundRect(rect, 15f, 15f, debugPaint);
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to pause thread");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ic.handleInput(event, playing, ld.getRocket());
        return true;
    }
}
