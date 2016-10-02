package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import java.util.ArrayList;

/**
 * Created by Koji on 10/2/2016.
 */

public class EarthData extends LevelData {
    private ArrayList<Star> stars = new ArrayList<>();

    public EarthData(Context context, Viewport vp) {
        super(context, vp);
        distance = 5500;
        enemies.add(new YellowEnemy(context, vp, 0, 2));
        enemies.add(new YellowEnemy(context, vp, 500, 2));
        enemies.add(new OrangeEnemy(context, vp, 1000, 15));
        enemies.add(new YellowEnemy(context, vp, 1500, 4));
        enemies.add(new YellowEnemy(context, vp, 2000, 10));
        enemies.add(new OrangeEnemy(context, vp, 2500, 15));
        enemies.add(new YellowEnemy(context, vp, 3000, 10));
        enemies.add(new YellowEnemy(context, vp, 4500, 10));
        enemies.add(new OrangeEnemy(context, vp, 5000, 15));
        enemies.add(new RedEnemy(context, vp, 50, 5));
        background.setShader(new LinearGradient(0, 0, 0, vp.screenY, Color.rgb(22, 29, 56), Color.rgb(79, 64, 90), Shader.TileMode.CLAMP));
        background.setColor(Color.rgb(39, 38, 67));
        backgrounds.add(new Background(context,
                vp, R.drawable.middleground, Viewport.VIEW_HEIGHT - 5, Viewport.VIEW_HEIGHT, rocket.velocityX / 10));
        foregrounds.add(new Background(context,
                vp, R.drawable.foreground, Viewport.VIEW_HEIGHT - 3, Viewport.VIEW_HEIGHT, rocket.velocityX));
        for(int i=0; i < 200;++i) stars.add(new Star(vp));
    }

    @Override
    public void updateOpening(long fps, Viewport vp) {
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
    }

    @Override
    public int update(long fps, Viewport vp) {
        rocket.update(fps, vp);
        for(Enemy e : enemies) score += e.update(fps, vp, rocket);
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
        if(rocket.getHealthPoint() <= 0) return GameView.GAMEOVER;
        if(getDistance() <= 0) {
            if(score > highScore) setHighScore(score);
            return GameView.CLEAR;
        }
        return GameView.PLYAINTG;
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        canvas.drawPaint(background);
        for(Background bg : backgrounds) bg.draw(canvas, vp);
        for(Star s : stars) s.draw(canvas, vp);
        for(Enemy enemy : enemies) enemy.draw(canvas, vp);
        rocket.draw(canvas, vp);
        for(Background fg : foregrounds) fg.draw(canvas, vp);
        for(Enemy enemy : enemies) enemy.drawHealth(canvas, vp);
    }
}
