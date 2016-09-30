package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

import java.util.ArrayList;

/**
 * Created by Koji on 9/29/2016.
 */

public class LevelData {
    private Rocket rocket;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Paint background = new Paint();
    private ArrayList<Background> backgrounds = new ArrayList<>();
    private ArrayList<Background> foregrounds = new ArrayList<>();
    private ArrayList<Star> stars = new ArrayList<>();

    public LevelData(Context context, Viewport vp) {
        rocket = new Rocket(context, vp);
        enemies.add(new YellowEnemy(context, vp, 50, 10));
        enemies.add(new YellowEnemy(context, vp, 50, 10));
        enemies.add(new OrangeEnemy(context, vp, 60, 15));
        enemies.add(new YellowEnemy(context, vp, 80, 10));
        enemies.add(new YellowEnemy(context, vp, 90, 10));
        enemies.add(new OrangeEnemy(context, vp, 100, 15));
        enemies.add(new YellowEnemy(context, vp, 100, 10));
        enemies.add(new YellowEnemy(context, vp, 110, 10));
        enemies.add(new OrangeEnemy(context, vp, 130, 15));
        enemies.add(new RedEnemy(context, vp, 50, 5));
        background.setShader(new LinearGradient(0, 0, 0, vp.screenY, Color.rgb(22, 29, 56), Color.rgb(79, 64, 90), Shader.TileMode.CLAMP));
        background.setColor(Color.rgb(39, 38, 67));
        backgrounds.add(new Background(context,
                vp, R.drawable.middleground, Viewport.VIEW_HEIGHT - 5, Viewport.VIEW_HEIGHT, 30));
        foregrounds.add(new Background(context,
                vp, R.drawable.foreground, Viewport.VIEW_HEIGHT - 3, Viewport.VIEW_HEIGHT, 300));
        for(int i=0; i < 200;++i) stars.add(new Star(vp));
    }

    public void update(long fps, Viewport vp) {
        rocket.update(fps, vp);
        for(Enemy e : enemies) e.update(fps, vp, rocket);
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
    }

    public void draw(Canvas canvas, Viewport vp) {
        canvas.drawPaint(background);
        for(Background bg : backgrounds) bg.draw(canvas, vp);
        for(Star s : stars) s.draw(canvas, vp);
        for(Enemy enemy : enemies) enemy.draw(canvas, vp);
        rocket.draw(canvas, vp);
        for(Background fg : foregrounds) fg.draw(canvas, vp);
        for(Enemy enemy : enemies) enemy.drawHealth(canvas, vp);
    }

    public Rocket getRocket() {
        return rocket;
    }
}
