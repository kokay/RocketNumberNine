package com.kokayapp.rocket9;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class TitleActivity extends Activity {

    private TitleView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);
        titleView = new TitleView(this, screen.x, screen.y, 0, null);
        setContentView(titleView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        titleView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        titleView.pause();
    }
}
