package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Koji on 9/29/2016.
 */

public class LevelData {
    private Rocket rocket;
    private ArrayList<Enemy> enemies;
    private ArrayList<Background> backgrounds;

    public LevelData(Context context, Viewport vp) {
        rocket = new Rocket(context, vp);
        enemies = new ArrayList<>();
        enemies.add(new YellowEnemy(context, vp, 50, 10));
        enemies.add(new OrangeEnemy(context, vp, 60, 15));
        enemies.add(new RedEnemy(context, vp, 50, 5));
        backgrounds = new ArrayList<>();
        backgrounds.add(new Background(context, vp, R.drawable.middleground, 0, 32, 30));
        backgrounds.add(new Background(context, vp, R.drawable.foreground, 0, 32, 300));
    }

    public void update(long fps) {
        rocket.update(fps);
        for(Enemy e : enemies) e.update(fps, rocket);
        for(Background bg : backgrounds) bg.update(fps);
    }

    public void draw(Canvas canvas, Viewport vp) {
        for(Enemy enemy : enemies) enemy.draw(canvas, vp);
        rocket.draw(canvas, vp);
        for(Background bg : backgrounds) bg.draw(canvas, vp);

        for(Enemy enemy : enemies) enemy.drawHealth(canvas, vp);
        rocket.drawHealth(canvas, vp);
    }

    public Rocket getRocket() {
        return rocket;
    }
}
