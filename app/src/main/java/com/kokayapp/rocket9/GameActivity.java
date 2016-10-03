package com.kokayapp.rocket9;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

/**
 * Created by Koji on 9/28/2016.
 */

public class GameActivity extends Activity {

    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);

        int level = getIntent().getIntExtra("Level", 0);
        gameView = new GameView(this, screen.x, screen.y, level, null);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
}
