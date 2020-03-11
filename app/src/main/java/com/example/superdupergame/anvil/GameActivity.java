package com.example.superdupergame.anvil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.superdupergame.anvil.models.AppConstants;
import com.example.superdupergame.anvil.views.GameView;

public class GameActivity extends Activity
{
    GameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppConstants.initialization(this.getApplicationContext(), this);
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }

    public void sendToGameOver(){
        Intent intent = new Intent(this, AnvilGameOverActivity.class);
        intent.putExtra("Points", AppConstants.points);
        startActivity(intent);
        finish();

    }
}
