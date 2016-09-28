package com.kokayapp.rocket9;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

/**
 * Created by Koji on 9/28/2016.
 */

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);

        TextView test = new TextView(this);
        test.setText(screen.x + " " + screen.y);
        setContentView(test);
    }
}
