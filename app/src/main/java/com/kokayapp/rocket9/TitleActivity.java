package com.kokayapp.rocket9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void clickPlay(View view) {
        //Intent intent = new Intent(this, GameActivity.class);
        //startActivity(intent);
    }
}
