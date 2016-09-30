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
    private Paint pauseNavy = new Paint();
    private RectF up;
    private RectF down;
    private RectF shoot;
    private RectF pausedBox;
    private Button pauseButton;
    private Button playButton;
    private Button pausedSign;


    public InputController(Context context, Viewport vp) {
        white.setColor(Color.rgb(254, 245, 249));
        white.setTextSize(vp.pixelsPerX * 2);
        white.setTypeface(Typeface.DEFAULT_BOLD);
        white.setTextAlign(Paint.Align.CENTER);
        white.setFlags(Paint.ANTI_ALIAS_FLAG);
        pauseNavy.setColor(Color.rgb(2, 12, 35));
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

        shoot = new RectF(
                vp.screenX - buttonPadding - buttonWidth,
                vp.screenY - buttonPadding - buttonHeight,
                vp.screenX - buttonPadding,
                vp.screenY - buttonPadding);

        pausedBox = new RectF(
                (Viewport.VIEW_CENTER_X - 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y - 4.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_X + 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y + 4.5f) * vp.pixelsPerX
        );

        pauseButton = new Button(context, vp, R.drawable.pause_button,
                Viewport.VIEW_WIDTH - 3.2f, 0.2f, 3f, 3f);
        pausedSign = new Button(context, vp, R.drawable.paused_sign,
                Viewport.VIEW_CENTER_X - 8, Viewport.VIEW_CENTER_Y - 4.5f, 16f, 9f);
        playButton = new Button(context, vp, R.drawable.play_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);
    }

    public void drawButtons(Canvas canvas, Viewport vp, boolean playing) {
        canvas.drawRoundRect(up, 15f, 15f, white);
        canvas.drawRoundRect(down, 15f, 15f, white);
        canvas.drawRoundRect(shoot, 15f, 15f, white);
        canvas.drawBitmap(pauseButton.bitmap, null, pauseButton.hitBox, null);
        if(!playing) {
            canvas.drawColor(Color.argb(50, 0, 0, 0));
            canvas.drawRoundRect(pausedBox, 15f, 15f, pauseNavy);
            canvas.drawText("PAUSED", vp.screenCenterX, (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, white);
            canvas.drawBitmap(playButton.bitmap, null, playButton.hitBox, null);
        }
    }

    public boolean handleInput(MotionEvent motionEvent, boolean playing, Rocket rocket) {
        for(int i=0;i<motionEvent.getPointerCount();++i) {
            int y = (int) motionEvent.getY(i);
            int x = (int) motionEvent.getX(i);

            if(playing) {
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
                        if(shoot.contains(x, y)) {
                            rocket.shoot();
                        }
                        if(pauseButton.hitBox.contains(x, y)) {
                            return false;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        rocket.setGoingDown(false);
                        rocket.setGoingUp(false);
                        break;
                }
            } else {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        if(playButton.hitBox.contains(x, y)) {
                            return true;
                        }
                        break;
                }
                return false;
            }
        }
        return true;
    }
}
