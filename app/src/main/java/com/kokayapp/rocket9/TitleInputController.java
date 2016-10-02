package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Koji on 10/2/2016.
 */

public class TitleInputController {
    private Button playButton;
    private Button continueButton;
    private Button exitButton;
    private Button settingButton;
    private int lastX = -1, lastY = -1;


    public TitleInputController(Context context, Viewport vp) {
        continueButton = new Button(context, vp, R.drawable.continue_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);

        playButton = new Button(context, vp, R.drawable.play_button,
                Viewport.VIEW_WIDTH - 14f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);

        settingButton = new Button(context, vp, R.drawable.settings_button,
                Viewport.VIEW_WIDTH - 9.5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);

        exitButton = new Button(context, vp, R.drawable.exit_button,
                Viewport.VIEW_WIDTH - 5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);
    }

    public void drawPlayingButtons(Canvas canvas, Viewport vp) {
        canvas.drawBitmap(playButton.bitmap, null, playButton.hitBox, null);
        canvas.drawBitmap(exitButton.bitmap, null, exitButton.hitBox, null);
        canvas.drawBitmap(settingButton.bitmap, null, settingButton.hitBox, null);
    }

    public void drawButtonsOnBox(Canvas canvas, Viewport vp, int state) {
        switch (state) {
            case TitleView.SELECT :
                canvas.drawBitmap(continueButton.bitmap, null, continueButton.hitBox, null);
                break;
            case TitleView.SETTING :
                canvas.drawBitmap(continueButton.bitmap, null, continueButton.hitBox, null);
                break;
            default :
                break;
        }
    }

    public int handleInput(MotionEvent motionEvent, int state) {
        for(int i=0;i<motionEvent.getPointerCount();++i) {
            int y = (int) motionEvent.getY(i);
            int x = (int) motionEvent.getX(i);
            switch (state) {
                case TitleView.TITLE   : return handleTitleInput(motionEvent, x, y);
                case TitleView.SELECT  : return handleSelectInput(motionEvent, x, y);
                case TitleView.SETTING : return handleSettingInput(motionEvent, x, y);
            }
        }
        return TitleView.TITLE;
    }

    private int handleTitleInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(playButton.hitBox.contains(lastX, lastY) &&
                        playButton.hitBox.contains(x, y)) {
                    return TitleView.STAGE_CHOSEN;
                }
                if(exitButton.hitBox.contains(lastX, lastY) &&
                        exitButton.hitBox.contains(x, y)) {
                    return TitleView.EXIT;
                }
                if(settingButton.hitBox.contains(lastX, lastY) &&
                        settingButton.hitBox.contains(x, y)) {
                    return TitleView.SETTING;
                }
                break;
        }
        return TitleView.TITLE;
    }

    private int handleSelectInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(continueButton.hitBox.contains(lastX, lastY) &&
                        continueButton.hitBox.contains(x, y)) {
                    return TitleView.TITLE;
                }
                break;
        }
        return TitleView.SELECT;
    }

    private int handleSettingInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(continueButton.hitBox.contains(lastX, lastY) &&
                        continueButton.hitBox.contains(x, y)) {
                    return TitleView.TITLE;
                }
                break;
        }
        return TitleView.SETTING;
    }
}
