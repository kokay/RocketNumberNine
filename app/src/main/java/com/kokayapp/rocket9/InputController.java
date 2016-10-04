package com.kokayapp.rocket9;

import android.os.Vibrator;
import android.view.MotionEvent;

/**
 * Created by Koji on 9/28/2016.
 */

public class InputController {
    private DrawingTool dt;
    private Vibrator vib;
    private Rocket rocket;

    private int lastX = -1, lastY = -1;
    private long vibDuration = 40;

    public InputController(Vibrator vib, DrawingTool dt, Rocket rocket) {
        this.dt = dt;
        this.vib = vib;
        this.rocket = rocket;
    }

    public int titleHandleInput(MotionEvent motionEvent, int state) {
        for(int i=0;i<motionEvent.getPointerCount();++i) {
            int y = (int) motionEvent.getY(i);
            int x = (int) motionEvent.getX(i);
            switch (state) {
                case TitleView.TITLE       : return handleTitleInput(motionEvent, x, y);
                case TitleView.SELECT      : return handleSelectInput(motionEvent, x, y);
                case TitleView.SETTING     : return handleSettingInput(motionEvent, x, y);
                //case GameView.CHECK_SCORE : return handleCheckScoreInput(motionEvent, x, y);
                default: return state;
            }
        }
        return state;
    }

    private int handleTitleInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(dt.titlePlay.hitBox.contains(lastX, lastY) &&
                        dt.titlePlay.hitBox.contains(x, y)) {
                    return TitleView.START_GAME;
                }
                if(dt.titleExit.hitBox.contains(lastX, lastY) &&
                        dt.titleExit.hitBox.contains(x, y)) {
                    return TitleView.EXIT;
                }
                if(dt.titleSetting.hitBox.contains(lastX, lastY) &&
                        dt.titleSetting.hitBox.contains(x, y)) {
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
                if(dt.titleContinue.hitBox.contains(lastX, lastY) &&
                        dt.titleContinue.hitBox.contains(x, y)) {
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
                if(dt.titleContinue.hitBox.contains(lastX, lastY) &&
                        dt.titleContinue.hitBox.contains(x, y)) {
                    return TitleView.TITLE;
                }
                break;
        }
        return TitleView.SETTING;
    }

    public int gameHandleInput(MotionEvent motionEvent, int state) {
        for(int i=0;i<motionEvent.getPointerCount();++i) {
            int y = (int) motionEvent.getY(i);
            int x = (int) motionEvent.getX(i);
            switch (state) {
                case GameView.OPENING   : return handleOpeningInput(motionEvent, x, y);
                case GameView.PLAYING  : return handlePlayingInput(motionEvent, x, y);
                case GameView.PAUSED    : return handlePausedInput(motionEvent, x, y);
                case GameView.CLEAR     : return handleClearInput(motionEvent, x, y);
                case GameView.GAME_OVER  : return handleGameOverInput(motionEvent, x, y);
                default: return state;
            }
        }
        return state;
    }

    private int handleOpeningInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                return GameView.PLAYING;
        }
        return GameView.OPENING;
    }

    private int handlePlayingInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                if(dt.gameUp.hitBox.contains(x, y)) {
                    rocket.setGoingDown(true);
                    rocket.setGoingUp(false);
                    vib.vibrate(vibDuration);
                }
                if(dt.gameDown.hitBox.contains(x, y)) {
                    rocket.setGoingDown(false);
                    rocket.setGoingUp(true);
                    vib.vibrate(vibDuration);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(dt.gamePause.hitBox.contains(lastX, lastY) &&
                   dt.gamePause.hitBox.contains(x, y)) {
                    vib.vibrate(vibDuration);
                    return GameView.PAUSED;
                } else {
                    rocket.setGoingDown(false);
                    rocket.setGoingUp(false);
                }
                break;
        }
        return GameView.PLAYING;
    }

    private int handlePausedInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(dt.gameContinue.hitBox.contains(lastX, lastY) &&
                   dt.gameContinue.hitBox.contains(x, y)) {
                    vib.vibrate(vibDuration);
                    return GameView.PLAYING;
                }
                break;
        }
        return GameView.PAUSED;
    }

    private int handleGameOverInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if(dt.gameExitGameOver.hitBox.contains(lastX, lastY) &&
                   dt.gameExitGameOver.hitBox.contains(x, y)) {
                    if(vib != null) vib.vibrate(vibDuration);
                    return GameView.GO_EXIT;
                }
                break;
        }
        return GameView.GAME_OVER;
    }

    private int handleClearInput(MotionEvent motionEvent, int x, int y) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                if(dt.gameUp.hitBox.contains(x, y)) {
                    rocket.setGoingDown(true);
                    rocket.setGoingUp(false);
                    if(vib != null) vib.vibrate(vibDuration);
                }
                if(dt.gameDown.hitBox.contains(x, y)) {
                    rocket.setGoingDown(false);
                    rocket.setGoingUp(true);
                    if(vib != null) vib.vibrate(vibDuration);
                }
                break;
            case MotionEvent.ACTION_UP:
                rocket.setGoingDown(false);
                rocket.setGoingUp(false);
                if(dt.gamePlay.hitBox.contains(lastX, lastY) &&
                        dt.gamePlay.hitBox.contains(x, y)) {
                    if(vib != null) vib.vibrate(vibDuration);
                    return GameView.WINNING_RUN;
                }
                if(dt.gameExitComplete.hitBox.contains(lastX, lastY) &&
                        dt.gameExitComplete.hitBox.contains(x, y)) {
                    if(vib != null) vib.vibrate(vibDuration);
                    return GameView.GO_EXIT;
                }
                break;
        }
        return GameView.CLEAR;
    }
}
