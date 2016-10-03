package com.kokayapp.rocket9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;

import java.util.ArrayList;

/**
 * Created by Koji on 10/2/2016.
 */

public class TitleData extends LevelData {
    private ArrayList<Star> stars = new ArrayList<>();
    private Button rocketImage;

    public TitleData(Context context, Viewport vp) {
        super(context, vp);
        rocketImage = new Button(context, vp, R.drawable.rocket_titile,
                4, 2.5f, 10, 13);
        enemies.add(new YellowEnemy(context, vp, 50, 10));
        enemies.add(new YellowEnemy(context, vp, 50, 10));
        enemies.add(new YellowEnemy(context, vp, 80, 10));
        enemies.add(new YellowEnemy(context, vp, 100, 10));
        enemies.add(new OrangeEnemy(context, vp, 60, 15));
        enemies.add(new OrangeEnemy(context, vp, 100, 15));
        enemies.add(new OrangeEnemy(context, vp, 130, 15));
        enemies.add(new RedEnemy(context, vp, 60, 5));
        enemies.add(new RedEnemy(context, vp, 100, 5));
        enemies.add(new RedEnemy(context, vp, 150, 5));
        background.setShader(new LinearGradient(0, 0, 0, vp.screenY, Color.rgb(22, 29, 56),
                Color.rgb(79, 64, 90), Shader.TileMode.CLAMP));
        background.setColor(Color.rgb(39, 38, 67));
        backgrounds.add(new Background(context,
                vp, R.drawable.middleground, Viewport.VIEW_HEIGHT - 5, Viewport.VIEW_HEIGHT, rocket.velocityX / 20));
        foregrounds.add(new Background(context,
                vp, R.drawable.foreground, Viewport.VIEW_HEIGHT - 3, Viewport.VIEW_HEIGHT, rocket.velocityX / 2));
        for(int i=0; i < 200;++i) stars.add(new Star(vp));
    }

    @Override
    public void openingUpdate(long fps, Viewport vp) {
        for(Enemy e : enemies) e.update(fps, vp, rocket);
        for(Background bg : backgrounds) bg.update(fps);
        for(Background fg : foregrounds) fg.update(fps);
        for(Star s : stars) s.update(fps);
    }

    @Override
    public int playingUpdate(long fps, Viewport vp) {
        return 0;
    }

    @Override
    public void clearUpdate(long fps, Viewport vp) {

    }

    @Override
    public int winningRunUpdate(long fps, Viewport vp) {
        return 0;
    }

    @Override
    public void draw(Canvas canvas, Viewport vp) {
        canvas.drawPaint(background);
        for(Background bg : backgrounds) bg.draw(canvas, vp);
        for(Star s : stars) s.draw(canvas, vp);
        for(Enemy enemy : enemies) enemy.draw(canvas, vp);
        for(Background fg : foregrounds) fg.draw(canvas, vp);
        canvas.drawBitmap(rocketImage.bitmap, null, rocketImage.hitBox, null);
    }
}
