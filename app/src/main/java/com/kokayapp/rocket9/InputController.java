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
    private Paint white = new Paint();
    private RectF up;
    private RectF down;
    private Button pauseButton;
    private Button playButton;


    public InputController(Context context, Viewport vp) {
        int buttonWidth = vp.screenX / 8;
        int buttonHeight = vp.screenY / 7;
        int buttonPadding = vp.screenX / 80;

        up = new RectF(
                buttonPadding,
                vp.screenY - buttonHeight - buttonPadding,
                buttonPadding + buttonWidth,
                vp.screenY - buttonPadding);
        down = new RectF(
                buttonPadding,
                up.top - buttonPadding - buttonHeight,
                buttonPadding + buttonWidth,
                up.top - buttonPadding);


        pauseButton = new Button(context, vp, R.drawable.pause_button,
                Viewport.VIEW_WIDTH - 3.2f, 1.3f, 3f, 3f);
        playButton = new Button(context, vp, R.drawable.play_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);
    }

    public void drawPlayingButtons(Canvas canvas, Viewport vp) {
        canvas.drawRoundRect(up, 15f, 15f, white);
        canvas.drawRoundRect(down, 15f, 15f, white);
        canvas.drawBitmap(pauseButton.bitmap, null, pauseButton.hitBox, null);
    }

    public void drawButtonsOnBox(Canvas canvas, Viewport vp, int state) {
        switch (state) {
            case GameView.PAUSED :
                canvas.drawBitmap(playButton.bitmap, null, playButton.hitBox, null);
                break;
            default :
                break;
        }
    }

    public int handleInput(MotionEvent motionEvent, int state, Rocket rocket) {
        for(int i=0;i<motionEvent.getPointerCount();++i) {
            int y = (int) motionEvent.getY(i);
            int x = (int) motionEvent.getX(i);

            if(state == GameView.PLYAINTG) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(down.contains(x, y)) {
                            rocket.setGoingDown(true);
                            rocket.setGoingUp(false);
                        }
                        if(up.contains(x, y)) {
                            rocket.setGoingDown(false);
                            rocket.setGoingUp(true);
                        }
                        if(pauseButton.hitBox.contains(x, y)) {
                            return GameView.PAUSED;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        rocket.setGoingDown(false);
                        rocket.setGoingUp(false);
                        break;
                }
            } else if (state == GameView.PAUSED){
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(playButton.hitBox.contains(x, y)) {
                            return GameView.PLYAINTG;
                        }
                        break;
                }
                return GameView.PAUSED;

            } else if (state == GameView.GAMEOVER){
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(playButton.hitBox.contains(x, y)) {
                            return GameView.GAMEOVER;
                        }
                        break;
                }
                return GameView.GAMEOVER;
            } else if (state == GameView.CLEAR){
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(playButton.hitBox.contains(x, y)) {
                            return GameView.CLEAR;
                        }
                        break;
                }
                return GameView.CLEAR;
            }
        }
        return GameView.PLYAINTG;
    }
}
