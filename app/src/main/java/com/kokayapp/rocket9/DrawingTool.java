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
    private final Rect leftSpace = new Rect();
    private final Rect topSpace = new Rect();
    private final Rect rightSpace = new Rect();
    private final Rect bottomSpace = new Rect();
    private final Rect topBar = new Rect();
    private final RectF smallBox = new RectF();
    private final RectF bigBox = new RectF();
    private final RectF rocketHealthBarFrame = new RectF();
    private RectF rocketHealthBar = new RectF();

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

    private final float distanceX;
    private final float distanceY;
    private final float scoreX;
    private final float scoreY;
    private final float highScoreX;
    private final float highScoreY;

    public DrawingTool(Context context, Viewport vp) {
        this.vp = vp;

        leftSpace.set(0, 0, vp.screenStartX, vp.screenY);
        topSpace.set(0, 0, vp.screenX, vp.screenStartY);
        rightSpace.set(vp.screenEndX, 0, vp.screenX, vp.screenY);
        bottomSpace.set(0, vp.screenEndY, vp.screenX, vp.screenY);

        topBar.set(
                vp.screenStartX,
                vp.screenStartY,
                vp.screenEndX,
                (int) (1.2 * vp.pixelsPerBox)
        );

        smallBox.set(
                vp.getScreenX(Viewport.VIEW_CENTER_X - 8),
                vp.getScreenY(Viewport.VIEW_CENTER_Y - 4.5f),
                vp.getScreenX(Viewport.VIEW_CENTER_X + 8),
                vp.getScreenY(Viewport.VIEW_CENTER_Y + 4.5f)
        );

        bigBox .set(
                vp.getScreenX(Viewport.VIEW_CENTER_X - 9.5f),
                vp.getScreenY(Viewport.VIEW_CENTER_Y - 6f),
                vp.getScreenX(Viewport.VIEW_CENTER_X + 9.5f),
                vp.getScreenY(Viewport.VIEW_CENTER_Y + 6f)
        );

        rocketHealthBarFrame.set(
                vp.getScreenX(0.2f),
                vp.getScreenY(0.2f),
                vp.getScreenX(8.2f),
                vp.getScreenY(1f)
        );

        rocketHealthBar.set(
                vp.getScreenX(0.3f),
                vp.getScreenY(0.3f),
                vp.getScreenX(8.1f),
                vp.getScreenY(0.9f)
        );


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

        titlePlay = new Button(context, vp, R.drawable.play_button, 3f, 3f,
                Viewport.VIEW_CENTER_X,
                Viewport.VIEW_CENTER_Y + 1f
        );

        titleSetting = new Button(context, vp, R.drawable.settings_button, 3f, 3f,
                Viewport.VIEW_CENTER_X + 4.5f,
                Viewport.VIEW_CENTER_Y + 1f
        );

        titleExit = new Button(context, vp, R.drawable.exit_button, 3f, 3f,
                Viewport.VIEW_CENTER_X + 9f,
                Viewport.VIEW_CENTER_Y + 1f
        );

        titleScore = new Button(context, vp, R.drawable.down_button, 3f, 3f,
                Viewport.VIEW_CENTER_X + 2f,
                Viewport.VIEW_CENTER_Y + 1.5f
        );

        titleContinue = new Button(context, vp, R.drawable.continue_button, 3f, 3f,
                Viewport.VIEW_CENTER_X - 1.5f,
                Viewport.VIEW_CENTER_Y + 1.5f
        );

        rocketImage = new Button(context, vp, R.drawable.rocket_titile, 10, 13,
                3,
                2.5f
        );

        logo = new Button(context, vp, R.drawable.intro, 16, 5,
                Viewport.VIEW_CENTER_X - 2f,
                3.5f
        );

        gameUp = new Button(context, vp, R.drawable.up_button, 3f, 3f,
                0.1f,
                Viewport.VIEW_HEIGHT - 6f - 0.2f
        );

        gameDown = new Button(context, vp, R.drawable.down_button, 3f, 3f,
                0.1f,
                Viewport.VIEW_HEIGHT - 3f - 0.1f
        );

        gamePause = new Button(context, vp, R.drawable.pause_button, 3f, 3f,
                Viewport.VIEW_WIDTH - 3.1f, 1.3f
        );

        gameContinue = new Button(context, vp, R.drawable.continue_button, 3f, 3f,
                Viewport.VIEW_CENTER_X - 1.5f,
                Viewport.VIEW_CENTER_Y - 0.5f
        );

        gameExitGameOver = new Button(context, vp, R.drawable.exit_button, 3f, 3f,
                Viewport.VIEW_CENTER_X - 1.5f,
                Viewport.VIEW_CENTER_Y + 1.5f
        );

        gamePlay = new Button(context, vp, R.drawable.play_button, 3f, 3f,
                Viewport.VIEW_CENTER_X - 5f,
                Viewport.VIEW_CENTER_Y + 1.5f
        );

        gameExitComplete = new Button(context, vp, R.drawable.exit_button, 3f, 3f,
                Viewport.VIEW_CENTER_X + 2f,
                Viewport.VIEW_CENTER_Y + 1.5f
        );

        distanceX = vp.getScreenX(12f);
        distanceY = vp.getScreenY(0.9f);
        scoreX = vp.getScreenX(19f);
        scoreY = vp.getScreenY(0.9f);
        highScoreX = vp.getScreenX(26f);
        highScoreY = vp.getScreenY(0.9f);
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
                distanceX, distanceY, smallText);
        canvas.drawText("SCORE : " + String.format("%1$05d", ld.getScore()),
                scoreX, scoreY, smallText);
        canvas.drawText("HIGH SCORE : " + String.format("%1$05d", ld.getHighScore()),
                highScoreX, highScoreY, smallText);
        canvas.drawRoundRect(rocketHealthBarFrame, 5f, 5f, vp.healthBarFramePaint);

        rocketHealthBar.right = (int)(vp.getScreenX(8.1f) *
                ld.getRocket().getHealthPoint() / ld.getRocket().getMaxHealthPoint());
        canvas.drawRoundRect(rocketHealthBar, 5f, 5f, vp.healthBarPaint);
    }

    public void drawSpace(Canvas canvas) {
        canvas.drawRect(leftSpace, darkNavy);
        canvas.drawRect(topSpace, darkNavy);
        canvas.drawRect(rightSpace, darkNavy);
        canvas.drawRect(bottomSpace, darkNavy);
    }
}
