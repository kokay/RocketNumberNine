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

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Ckreated by Koji on 9/28/2016.
 */

public class GameView extends SurfaceView implements Runnable {
    public static final int OPENING = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;
    public static final int CLEAR = 3;
    public static final int GAME_OVER = 4;
    public static final int WINNING_RUN = 5;
    public static final int GO_NEXT_LEVEL = 6;
    public static final int GO_EXIT = 7;

    private String TAG = getClass().getSimpleName();
    private boolean debugging = true;
    private long maxFps = 0, minFps = 10000, avgFps = 60;

    private volatile boolean running;

    private volatile int state = OPENING;
    private Thread gameThread = null;
    private int level = 0;

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


    public GameView(Context context, int screenX, int screenY, int level, int score, int healthPoint) {
        super(context);
        this.context = context;
        this.level = level;
        holder = getHolder();

        vp = new Viewport(screenX, screenY);
        Vibrator vib = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if(vib == null) Log.w(TAG, "No vibration service");
        dt = new DrawingTool(context, vp);
        ld = new EarthData(context, vp, vib, level, score, healthPoint);
        ic = new InputController(vib, dt, ld.getRocket());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        state = ic.gameHandleInput(event, state);
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
            case OPENING       : ld.openingUpdate(fps); break;
            case PLAYING       : state = ld.playingUpdate(fps); break;
            case CLEAR         : ld.clearUpdate(fps); break;
            case WINNING_RUN   : state = ld.winningRunUpdate(fps); break;
            case GO_NEXT_LEVEL : startNextLevel(); break;
            case GO_EXIT       : goBackToTitle(); break;
            default            : break;
        }
    }

    private void startNextLevel() {
        Intent nextLevelIntent = new Intent(context, GameActivity.class);
        nextLevelIntent.putExtra("Level", level + 1);
        nextLevelIntent.putExtra("Score", ld.getScore() + 100);
        nextLevelIntent.putExtra("HealthPoint", (int) ld.getRocket().getHealthPoint());
        context.startActivity(nextLevelIntent);
        ((Activity)(context)).finish();
    }

    private void goBackToTitle() {
        Intent backHomeIntent = new Intent(context, TitleActivity.class);
        backHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(backHomeIntent);
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            ld.draw(canvas);
            dt.drawGameTopBar(canvas, ld);
            dt.drawGameButtons(canvas);
            if(state != PLAYING) drawMessage();
            dt.drawGameButtonsOnBox(canvas, state);
            if(debugging) drawDebugging();
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawMessage() {
        canvas.drawColor(Color.argb(50, 0, 0, 0));
        switch (state) {
            case OPENING   : dt.showOpeningMessage(canvas, level); break;
            case PAUSED    : dt.showPausedMessage(canvas); break;
            case GAME_OVER : dt.showGameOverMessage(canvas, level, ld.getScore()); break;
            case CLEAR     : dt.showCompleteMessage(canvas, level, ld.getScore()); break;
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
