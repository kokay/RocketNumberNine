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

    Rocket rocket;
    ArrayList<Enemy> enemies;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        gameThread = null;
        holder = getHolder();
        fps = 1000000000;
        vp = new Viewport(screenX, screenY);
        ic = new InputController(screenX, screenY);

        debugPaint.setColor(Color.WHITE);
        rocket = new Rocket(context, vp);
        enemies = new ArrayList<>();
        enemies.add(new YellowEnemy(context, vp, 32, 10));
        enemies.add(new OrangeEnemy(context, vp, 50, 15));
        enemies.add(new RedEnemy(context, vp, 40, 5));
    }

    @Override
    public void run() {
        while (playing) {
            startFrameTime = System.currentTimeMillis();
            rocket.update(fps);
            for(Enemy e : enemies) {
                e.update(fps, rocket);
            }
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

            for(Bullet bullet : rocket.getBullets())
                canvas.drawRect(vp.viewToScreen(bullet), debugPaint);
            canvas.drawBitmap(rocket.getBitmap(), null, vp.viewToScreen(rocket), null);
            for(Enemy e : enemies)
                canvas.drawBitmap(e.getBitmap(), null, vp.viewToScreen(e), null);

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
