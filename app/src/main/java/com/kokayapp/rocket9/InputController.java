package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public class InputController {
    private Button upButton;
    private Button downButton;
    private Button pauseButton;
    private Button continueButton;
    private Button playButton;
    private Button exitButton;
    private Button exitButton2;
    private int lastX = -1, lastY = -1;


    public InputController(Context context, Viewport vp) {
        upButton = new Button(context, vp, R.drawable.up_button,
                0.1f, Viewport.VIEW_HEIGHT - 6f - 0.2f, 3f, 3f);

        downButton = new Button(context, vp, R.drawable.down_button,
                0.1f, Viewport.VIEW_HEIGHT - 3f - 0.1f, 3f, 3f);

        pauseButton = new Button(context, vp, R.drawable.pause_button,
                Viewport.VIEW_WIDTH - 3.1f, 1.3f, 3f, 3f);

        continueButton = new Button(context, vp, R.drawable.continue_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);

        exitButton = new Button(context, vp, R.drawable.exit_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);

        playButton = new Button(context, vp, R.drawable.play_button,
                Viewport.VIEW_CENTER_X - 5f, Viewport.VIEW_CENTER_Y + 1.5f, 3f, 3f);

        exitButton2 = new Button(context, vp, R.drawable.exit_button,
                Viewport.VIEW_CENTER_X + 2f, Viewport.VIEW_CENTER_Y + 1.5f, 3f, 3f);

    }

    public void drawPlayingButtons(Canvas canvas, Viewport vp) {
        canvas.drawBitmap(upButton.bitmap, null, upButton.hitBox, null);
        canvas.drawBitmap(downButton.bitmap, null, downButton.hitBox, null);
        canvas.drawBitmap(pauseButton.bitmap, null, pauseButton.hitBox, null);
    }

    public void drawButtonsOnBox(Canvas canvas, Viewport vp, int state) {
        switch (state) {
            case GameView.PAUSED :
                canvas.drawBitmap(continueButton.bitmap, null, continueButton.hitBox, null);
                break;
            case GameView.GAME_OVER :
                canvas.drawBitmap(exitButton.bitmap, null, exitButton.hitBox, null);
                break;
            case GameView.CLEAR :
                canvas.drawBitmap(playButton.bitmap, null, playButton.hitBox, null);
                canvas.drawBitmap(exitButton2.bitmap, null, exitButton2.hitBox, null);
            default :
                break;
        }
    }


    public int handleInput(MotionEvent motionEvent, int state, Rocket rocket) {
        for(int i=0;i<motionEvent.getPointerCount();++i) {
            int y = (int) motionEvent.getY(i);
            int x = (int) motionEvent.getX(i);
            switch (state) {
                case GameView.OPENING   : return handleOpeningInput(motionEvent, x, y, rocket);
                case GameView.PLAYING  : return handlePlayingInput(motionEvent, x, y, rocket);
                case GameView.PAUSED    : return handlePausedInput(motionEvent, x, y, rocket);
                case GameView.CLEAR     : return handleClearInput(motionEvent, x, y, rocket);
                case GameView.GAME_OVER  : return handleGameOverInput(motionEvent, x, y, rocket);
                default: return state;
            }
        }
        return state;
    }

    private int handleOpeningInput(MotionEvent motionEvent, int x, int y, Rocket rocket) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                return GameView.PLAYING;
        }
        return GameView.OPENING;
    }

    private int handlePlayingInput(MotionEvent motionEvent, int x, int y, Rocket rocket) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                if(upButton.hitBox.contains(x, y)) {
                    rocket.setGoingDown(true);
                    rocket.setGoingUp(false);
                }
                if(downButton.hitBox.contains(x, y)) {
                    rocket.setGoingDown(false);
                    rocket.setGoingUp(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(pauseButton.hitBox.contains(lastX, lastY) &&
                   pauseButton.hitBox.contains(x, y)) {
                    return GameView.PAUSED;
                } else {
                    rocket.setGoingDown(false);
                    rocket.setGoingUp(false);
                }
                break;
        }
        return GameView.PLAYING;
    }

    private int handlePausedInput(MotionEvent motionEvent, int x, int y, Rocket rocket) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(continueButton.hitBox.contains(lastX, lastY) &&
                   continueButton.hitBox.contains(x, y)) {
                    return GameView.PLAYING;
                }
                break;
        }
        return GameView.PAUSED;
    }

    private int handleGameOverInput(MotionEvent motionEvent, int x, int y, Rocket rocket) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(exitButton.hitBox.contains(lastX, lastY) &&
                   exitButton.hitBox.contains(x, y)) {
                    return GameView.GO_EXIT;
                }
                break;
        }
        return GameView.GAME_OVER;
    }

    private int handleClearInput(MotionEvent motionEvent, int x, int y, Rocket rocket) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                if(upButton.hitBox.contains(x, y)) {
                    rocket.setGoingDown(true);
                    rocket.setGoingUp(false);
                }
                if(downButton.hitBox.contains(x, y)) {
                    rocket.setGoingDown(false);
                    rocket.setGoingUp(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                rocket.setGoingDown(false);
                rocket.setGoingUp(false);
                if(playButton.hitBox.contains(lastX, lastY) &&
                        playButton.hitBox.contains(x, y)) {
                    return GameView.WINNING_RUN;
                }
                if(exitButton2.hitBox.contains(lastX, lastY) &&
                        exitButton2.hitBox.contains(x, y)) {
                    return GameView.GO_EXIT;
                }
                break;
        }
        return GameView.CLEAR;
    }
}
