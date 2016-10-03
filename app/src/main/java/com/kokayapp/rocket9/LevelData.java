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
import android.os.Vibrator;

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

    protected Viewport vp;
    protected Vibrator vib;

    public LevelData(Context context, Viewport vp, Vibrator vib, int level, int score, int healthPoint) {
        this.vp = vp;
        this.vib = vib;
        prefs = context.getSharedPreferences("BestScoreFile", context.MODE_PRIVATE);
        highScore = prefs.getInt("BestScore", 0);
        editor = prefs.edit();
        this.rocket = new Rocket(context, vp, vib, level, healthPoint);
        this.score = score;
    }

    public abstract void openingUpdate(long fps);
    public abstract int playingUpdate(long fps);
    public abstract void clearUpdate(long fps);
    public abstract int winningRunUpdate(long fps);
    public abstract void draw(Canvas canvas);

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

