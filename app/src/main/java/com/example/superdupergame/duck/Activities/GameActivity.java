package com.example.superdupergame.duck.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.superdupergame.duck.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    // Makes screen full screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameView(this, point.x, point.y);
        setContentView(gameView);
    }

    // When paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // When un-paused
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
