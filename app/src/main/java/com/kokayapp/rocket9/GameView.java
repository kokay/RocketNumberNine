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
    Rocket rocket;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        gameThread = null;
        holder = getHolder();
        fps = 1000 / 60;
        vp = new Viewport(screenX, screenY);
        ic = new InputController(screenX, screenY);

        debugPaint.setColor(Color.WHITE);
        rocket = new Rocket(context, vp);
    }

    @Override
    public void run() {
        while (playing) {
            startFrameTime = System.currentTimeMillis();
            rocket.update(fps);
            draw();
            timeOfFrame = System.currentTimeMillis() - startFrameTime;
            if (timeOfFrame >= 1) {
                fps = 1000 / timeOfFrame;
            }
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawText("" + fps, 10, 10, debugPaint);
            if(fps >= 60)
                canvas.drawText("true", 10, 30, debugPaint);
            canvas.drawBitmap(rocket.bitmap, null, vp.viewToScreen(rocket), null);
            for(Bullet bullet : rocket.getBullets())
                canvas.drawRect(vp.viewToScreen(bullet), debugPaint);



                for(RectF rect : ic.getButtons())
                canvas.drawRoundRect(rect, 15f, 15f, debugPaint);
            holder.unlockCanvasAndPost(canvas);
        }
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
        ic.handleInput(event, playing, rocket);
        return true;
    }
}
