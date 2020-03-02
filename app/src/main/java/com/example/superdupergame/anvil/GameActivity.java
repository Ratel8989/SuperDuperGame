package com.example.superdupergame.anvil;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.superdupergame.anvil.views.GameView;

public class GameActivity extends Activity
{
    GameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}
