package com.example.superdupergame.pacman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.superdupergame.MainActivity;
import com.example.superdupergame.pacman.models.AppConstants;
import com.example.superdupergame.pacman.views.GameView;

public class GameActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppConstants.initialization(this.getApplicationContext(), this);
        setContentView(new GameView(this));
    }

    public void sendToMainMenu()
    {
        AppConstants.getSoundBank().releaseSoundPool();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
