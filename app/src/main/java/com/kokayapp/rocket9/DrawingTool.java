package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

/**
 * Created by Koji on 9/30/2016.
 */

public class DrawingTool {
    private Viewport vp;
    private final Rect topBar = new Rect();
    private final RectF smallBox = new RectF();
    private final RectF bigBox = new RectF();
    private final RectF healthBarFrame = new RectF();
    private RectF healthBar = new RectF();

    public final Paint darkNavy = new Paint();
    public final Paint smallText = new Paint();
    public final Paint mediumText = new Paint();
    public final Paint bigText = new Paint();

    public final Button titlePlay;
    public final Button titleScore;
    public final Button titleSetting;
    public final Button titleExit;
    public final Button titleContinue;
    public final Button rocketImage;
    public final Button logo;

    public final Button gameUp;
    public final Button gameDown;
    public final Button gamePause;
    public final Button gameContinue;
    public final Button gamePlay;
    public final Button gameExitComplete;
    public final Button gameExitGameOver;

    public DrawingTool(Context context, Viewport vp) {
        this.vp = vp;
        topBar.set(0, 0, vp.screenX, (int) (1.2 * vp.pixelsPerY));

        smallBox.set(
                (Viewport.VIEW_CENTER_X - 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y - 4.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_X + 8) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y + 4.5f) * vp.pixelsPerX );

        bigBox .set(
                (Viewport.VIEW_CENTER_X - 9.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y - 6f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_X + 9.5f) * vp.pixelsPerX,
                (Viewport.VIEW_CENTER_Y + 6f) * vp.pixelsPerX );

        healthBarFrame.set(
                (int)(0.2f * vp.pixelsPerX), (int)(0.2f * vp.pixelsPerY),
                (int)((0.2f + 8) * vp.pixelsPerX), (int)(1.0f * vp.pixelsPerY));

        healthBar.set(
                (int)(0.3f * vp.pixelsPerX), (int)(0.3f * vp.pixelsPerY),
                (int)((0.3f + 8 - 0.2f) * vp.pixelsPerX), (int)(0.9f * vp.pixelsPerY ));

        darkNavy.setColor(Color.rgb(2, 12, 35));

        smallText.setColor(Color.rgb(254, 245, 249));
        smallText.setTextSize(vp.pixelsPerX * 0.8f);
        smallText.setTypeface(Typeface.DEFAULT_BOLD);
        smallText.setTextAlign(Paint.Align.CENTER);
        smallText.setFlags(Paint.ANTI_ALIAS_FLAG);

        mediumText.setColor(Color.rgb(254, 245, 249));
        mediumText.setTextSize(vp.pixelsPerX * 1.5f);
        mediumText.setTypeface(Typeface.DEFAULT_BOLD);
        mediumText.setTextAlign(Paint.Align.CENTER);
        mediumText.setFlags(Paint.ANTI_ALIAS_FLAG);

        bigText.setColor(Color.rgb(254, 245, 249));
        bigText.setTextSize(vp.pixelsPerX * 2);
        bigText.setTypeface(Typeface.DEFAULT_BOLD);
        bigText.setTextAlign(Paint.Align.CENTER);
        bigText.setFlags(Paint.ANTI_ALIAS_FLAG);

        titlePlay = new Button(context, vp, R.drawable.play_button,
                Viewport.VIEW_CENTER_X, Viewport.VIEW_CENTER_Y + 1f, 3f, 3f);

        titleSetting = new Button(context, vp, R.drawable.settings_button,
                Viewport.VIEW_CENTER_X + 4.5f, Viewport.VIEW_CENTER_Y + 1f, 3f, 3f);

        titleExit = new Button(context, vp, R.drawable.exit_button,
                Viewport.VIEW_CENTER_X + 9f, Viewport.VIEW_CENTER_Y + 1f, 3f, 3f);

        titleScore = new Button(context, vp, R.drawable.down_button,
                Viewport.VIEW_CENTER_X + 2f, Viewport.VIEW_CENTER_Y + 1.5f, 3f, 3f);

        titleContinue = new Button(context, vp, R.drawable.continue_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y + 1.5f, 3f, 3f);

        rocketImage = new Button(context, vp, R.drawable.rocket_titile, 3, 2.5f, 10, 13);
        logo = new Button(context, vp, R.drawable.intro,
                Viewport.VIEW_CENTER_X - 2f, 3.5f, 16, 5);


        gameUp = new Button(context, vp, R.drawable.up_button,
                0.1f, Viewport.VIEW_HEIGHT - 6f - 0.2f, 3f, 3f);

        gameDown = new Button(context, vp, R.drawable.down_button,
                0.1f, Viewport.VIEW_HEIGHT - 3f - 0.1f, 3f, 3f);

        gamePause = new Button(context, vp, R.drawable.pause_button,
                Viewport.VIEW_WIDTH - 3.1f, 1.3f, 3f, 3f);

        gameContinue = new Button(context, vp, R.drawable.continue_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y - 0.5f, 3f, 3f);

        gameExitGameOver = new Button(context, vp, R.drawable.exit_button,
                Viewport.VIEW_CENTER_X - 1.5f, Viewport.VIEW_CENTER_Y + 1.5f, 3f, 3f);

        gamePlay = new Button(context, vp, R.drawable.play_button,
                Viewport.VIEW_CENTER_X - 5f, Viewport.VIEW_CENTER_Y + 1.5f, 3f, 3f);

        gameExitComplete = new Button(context, vp, R.drawable.exit_button,
                Viewport.VIEW_CENTER_X + 2f, Viewport.VIEW_CENTER_Y + 1.5f, 3f, 3f);

    }

    public void showSetting(Canvas canvas) {
        canvas.drawColor(Color.argb(50, 0, 0, 0));
        canvas.drawRoundRect(bigBox, 15f, 15f, darkNavy);
        canvas.drawText("SETTING", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, bigText);
    }

    public void showOpeningMessage(Canvas canvas, int level) {
        canvas.drawRoundRect(smallBox, 15f, 15f, darkNavy);
        canvas.drawText("EARTH", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 2f) * vp.pixelsPerY, bigText);
        canvas.drawText("LEVEL " + level, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y ) * vp.pixelsPerY, bigText);
        canvas.drawText("Touch the screen to start!", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y + 2.5f) * vp.pixelsPerY, smallText);
    }

    public void showPausedMessage(Canvas canvas) {
        canvas.drawRoundRect(smallBox, 15f, 15f, darkNavy);
        canvas.drawText("PAUSED", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, bigText);
    }

    public void showCompleteMessage(Canvas canvas, int level, int score) {
        canvas.drawRoundRect(bigBox, 15f, 15f, darkNavy);
        canvas.drawText("LEVEL " + level, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 3.5f) * vp.pixelsPerY, bigText);
        canvas.drawText("COMPLETE", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, bigText);
        canvas.drawText("SCORE : " + score + " + 100", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y + 0.5f) * vp.pixelsPerY, smallText);
    }

    public void showGameOverMessage(Canvas canvas, int level, int score) {
        canvas.drawRoundRect(bigBox, 15f, 15f, darkNavy);
        canvas.drawText("GAME OVER", vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 3.5f) * vp.pixelsPerY, bigText);
        canvas.drawText("LEVEL : " + level, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y - 1.5f) * vp.pixelsPerY, mediumText);
        canvas.drawText("SCORE : " + score, vp.screenCenterX,
                (Viewport.VIEW_CENTER_Y + 0.5f) * vp.pixelsPerY, mediumText);
    }

    public void drawTitleButtons(Canvas canvas) {
        canvas.drawBitmap(logo.bitmap, null, logo.hitBox, null);
        canvas.drawBitmap(rocketImage.bitmap, null, rocketImage.hitBox, null);
        canvas.drawBitmap(titlePlay.bitmap, null, titlePlay.hitBox, null);
        canvas.drawBitmap(titleSetting.bitmap, null, titleSetting.hitBox, null);
        canvas.drawBitmap(titleExit.bitmap, null, titleExit.hitBox, null);
    }

    public void drawGameButtons(Canvas canvas) {
        canvas.drawBitmap(gameUp.bitmap, null, gameUp.hitBox, null);
        canvas.drawBitmap(gameDown.bitmap, null, gameDown.hitBox, null);
        canvas.drawBitmap(gamePause.bitmap, null, gamePause.hitBox, null);
    }

    public void drawGameButtonsOnBox(Canvas canvas, int state) {
        switch (state) {
            case GameView.PAUSED :
                canvas.drawBitmap(gameContinue.bitmap, null, gameContinue.hitBox, null);
                break;
            case GameView.GAME_OVER :
                canvas.drawBitmap(gameExitGameOver.bitmap, null, gameExitGameOver.hitBox, null);
                break;
            case GameView.CLEAR :
                canvas.drawBitmap(gamePlay.bitmap, null, gamePlay.hitBox, null);
                canvas.drawBitmap(gameExitComplete.bitmap, null, gameExitComplete.hitBox, null);
            default :
                break;
        }
    }

    public void drawTitleButtonsOnBox(Canvas canvas, int state) {
        switch (state) {
            case TitleView.SETTING :
                canvas.drawBitmap(titleContinue.bitmap, null, titleContinue.hitBox, null);
                break;
        }
    }

    public void drawTitleTopBar(Canvas canvas) {
        canvas.drawRect(topBar, darkNavy);
    }

    public void drawGameTopBar(Canvas canvas, LevelData ld) {
        canvas.drawRect(topBar, darkNavy);
        canvas.drawText("DISTANCE : " + String.format("%1$05d", ld.getDistance()) + " M",
                (int)(vp.pixelsPerX * 12.5f),
                (int)(vp.pixelsPerY * 0.9f),
                smallText);
        canvas.drawText("SCORE : " + String.format("%1$05d", ld.getScore()),
                (int)(vp.pixelsPerX * 20f),
                (int)(vp.pixelsPerY * 0.9f),
                smallText);
        canvas.drawText("HIGH SCORE : " + String.format("%1$05d", ld.getHighScore()),
                (int)(vp.pixelsPerX * 28f),
                (int)(vp.pixelsPerY * 0.9f),
                smallText);
        healthBar.right = (int)((0.3f + 8 - 0.2f) * vp.pixelsPerX) *
                ld.getRocket().getHealthPoint() / ld.getRocket().getMaxHealthPoint();
        canvas.drawRoundRect(healthBarFrame, 5f, 5f, vp.healthBarFramePaint);
        canvas.drawRoundRect(healthBar, 5f, 5f, vp.healthBarPaint);
    }
}
