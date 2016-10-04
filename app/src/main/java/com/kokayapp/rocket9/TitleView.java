package com.kokayapp.rocket9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.content.ContentValues.TAG;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Koji on 10/2/2016.
 */

public class TitleView extends SurfaceView implements Runnable {
    public static final int TITLE = 0;
    public static final int SELECT = 1;
    public static final int SETTING = 2;
    public static final int EXIT = 3;
    public static final int STAGE_CHOSEN = 4;
    public static final int CHECK_SCORE = 5;
    public static final int START_GAME = 6;

    private String TAG = getClass().getSimpleName();
    private boolean debugging = true;
    private long maxFps = 0, minFps = 10000, avgFps = 60;

    private volatile boolean running;

    private volatile int state = TITLE;
    private Thread titleThread = null;

    private Canvas canvas;
    private SurfaceHolder holder;

    private long startFrameTime;
    private long timeOfFrame;
    private long fps = 2000000000;

    private Context context;
    private Viewport vp;
    private InputController ic;
    private LevelData ld;
    private DrawingTool dt;

    public TitleView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        holder = getHolder();

        vp = new Viewport(screenX, screenY);
        Vibrator vib = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if(vib == null) Log.w(TAG, "No vibration service");
        dt = new DrawingTool(context, vp);
        ld = new TitleData(context, vp, vib, 0, 0, 0);
        ic = new InputController(vib, dt, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        state = ic.titleHandleInput(event, state);
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
            case EXIT : ((Activity) context).finish(); break;
            case STAGE_CHOSEN  : ld.winningRunUpdate(fps); break;
            case START_GAME : startGame(); break;
            default: ld.openingUpdate(fps); break;
        }
    }

    private void startGame() {
        Intent gameIntent = new Intent(context, GameActivity.class);
        //gameIntent.putExtra("Stage", "somename");
        context.startActivity(gameIntent);
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            ld.draw(canvas);
            dt.drawTitleTopBar(canvas);
            dt.drawTitleButtons(canvas);
            if(state != TITLE) drawMessage();
            dt.drawTitleButtonsOnBox(canvas, state);
            if(debugging) drawDebugging();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawMessage() {
        canvas.drawColor(Color.argb(50, 0, 0, 0));
        switch (state) {
            case SETTING   : dt.showSetting(canvas); break;
            default        : break;
        }
    }

    private void drawDebugging() {
        canvas.drawText("FPS : " + fps, 80, 100, dt.smallText);
        if(fps > 60) canvas.drawText("true", 80, 140, dt.smallText);
        maxFps = Math.max(maxFps, fps);
        canvas.drawText("MAX : " + maxFps, 80, 180, dt.smallText);
        minFps = Math.min(minFps, fps);
        canvas.drawText("MIN : " + minFps, 80, 220, dt.smallText);
        avgFps = (avgFps + fps) / 2;
        canvas.drawText("AVG : " + avgFps, 80, 260, dt.smallText);
    }

    public void resume() {
        running = true;
        state = TITLE;
        titleThread = new Thread(this);
        titleThread.start();
    }

    public void pause() {
        running = false;
        try {
            titleThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Failed to pause thread");
        }
    }
}
