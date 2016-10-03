package com.kokayapp.rocket9;

import android.content.Context;
import android.content.SharedPreferences;
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

public abstract class LevelData {
    protected Rocket rocket;
    protected ArrayList<Enemy> enemies = new ArrayList<>();
    protected Paint background = new Paint();
    protected ArrayList<Background> backgrounds = new ArrayList<>();
    protected ArrayList<Background> foregrounds = new ArrayList<>();

    protected float distance = 0;
    protected int score = 0;
    protected int highScore = 0;

    protected SharedPreferences prefs;
    protected SharedPreferences.Editor editor;


    public LevelData(Context context, Viewport vp, int level, Rocket rocket) {
        prefs = context.getSharedPreferences("BestScoreFile", context.MODE_PRIVATE);
        highScore = prefs.getInt("BestScore", 0);
        editor = prefs.edit();
        this.rocket = new Rocket(context, vp, level);
    }

    public abstract void openingUpdate(long fps, Viewport vp);
    public abstract int playingUpdate(long fps, Viewport vp);
    public abstract void clearUpdate(long fps, Viewport vp);
    public abstract int winningRunUpdate(long fps, Viewport vp);
    public abstract void draw(Canvas canvas, Viewport vp);

    public Rocket getRocket() {
        return rocket;
    }

    public int getDistance() {
        return (int) (distance - rocket.getCurrentPlace());
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    protected void setHighScore(int score) {
        editor.putInt("BestScore", score);
        editor.commit();
    }
}

