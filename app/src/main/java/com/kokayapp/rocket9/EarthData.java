package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Vibrator;

import java.util.ArrayList;

/**
 * Created by Koji on 10/2/2016.
 */

public class EarthData extends LevelData {
    private ArrayList<Star> stars = new ArrayList<>();

    public EarthData(Context context, Viewport vp, Vibrator vib, int level, int score, int healthPoint) {
        super(context, vp, vib, level, score, healthPoint);

        distance = 500 + (level * 100);
        enemies.add(new YellowEnemy(context, vp, level));
        enemies.add(new OrangeEnemy(context, vp, level));
        enemies.add(new YellowEnemy(context, vp, level));
        enemies.add(new OrangeEnemy(context, vp, level));
        enemies.add(new YellowEnemy(context, vp, level));
        enemies.add(new    RedEnemy(context, vp, level));

        background.setShader(new LinearGradient(0, 0, 0, vp.screenY, Color.rgb(22, 29, 56), Color.rgb(79, 64, 90), Shader.TileMode.CLAMP));
        background.setColor(Color.rgb(39, 38, 67));
        backgrounds.add(new Background(context,
                vp, R.drawable.middleground, Viewport.VIEW_HEIGHT - 5, Viewport.VIEW_HEIGHT, rocket.velocityX / 10));
        foregrounds.add(new Background(context,
                vp, R.drawable.foreground, Viewport.VIEW_HEIGHT - 3, Viewport.VIEW_HEIGHT, rocket.velocityX));
        for(int i=0; i < 200;++i) stars.add(new Star(vp));
    }

    @Override
    public void openingUpdate(long fps) {
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
    }

    @Override
    public int playingUpdate(long fps) {
        rocket.update(fps, vp);
        for(Enemy e : enemies) score += e.update(fps, vp, rocket);
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
        if(rocket.getHealthPoint() <= 0) {
            if(score > highScore) setHighScore(score);
            return GameView.GAME_OVER;
        }
        if(getDistance() <= 0) return GameView.CLEAR;
        return GameView.PLAYING;
    }

    @Override
    public void clearUpdate(long fps) {
        rocket.completeUpdate(fps);
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
    }

    @Override
    public int winningRunUpdate(long fps) {
        rocket.winningRunUpdate(fps);
        if(rocket.getX() > Viewport.VIEW_WIDTH + 10)
            return GameView.GO_NEXT_LEVEL;
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
        return GameView.WINNING_RUN;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPaint(background);
        for(Background bg : backgrounds) bg.draw(canvas, vp);
        for(Star s : stars) s.draw(canvas, vp);
        for(Enemy enemy : enemies) enemy.draw(canvas, vp);
        rocket.draw(canvas, vp);
        for(Background fg : foregrounds) fg.draw(canvas, vp);
        for(Enemy enemy : enemies) enemy.drawHealth(canvas, vp);
    }
}
