package com.kokayapp.rocket9;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Koji on 9/28/2016.
 */

public class InputController {
    private Paint buttonWhite = new Paint();
    private ArrayList<RectF> buttons;
    private RectF up;
    private RectF down;
    private RectF shoot;


    public InputController(int screenX, int screenY) {
        buttonWhite.setColor(Color.rgb(254, 245, 249));
        int buttonWidth = screenX / 8;
        int buttonHeight = screenY / 7;
        int buttonPadding = screenX / 80;

        up = new RectF(
                buttonPadding,
                screenY - buttonHeight - buttonPadding,
                buttonPadding + buttonWidth,
                screenY - buttonPadding);
        down = new RectF(
                buttonPadding,
                up.top - buttonPadding - buttonHeight,
                buttonPadding + buttonWidth,
                up.top - buttonPadding);

        shoot = new RectF(
                screenX - buttonPadding - buttonWidth,
                screenY - buttonPadding - buttonHeight,
                screenX - buttonPadding,
                screenY - buttonPadding);

        buttons = new ArrayList<>();
        buttons.add(up);
        buttons.add(down);
        buttons.add(shoot);
    }

    public void drawButtons(Canvas canvas) {
        for(RectF rect : buttons)
            canvas.drawRoundRect(rect, 15f, 15f, buttonWhite);
    }

    public ArrayList<RectF> getButtons() {
        return buttons;
    }

    public void handleInput(MotionEvent motionEvent, boolean playing, Rocket rocket) {
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
                        break;
                    case MotionEvent.ACTION_UP:
                        rocket.setGoingDown(false);
                        rocket.setGoingUp(false);
                        break;
                }
            } else {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                }
            }
        }
    }
}
