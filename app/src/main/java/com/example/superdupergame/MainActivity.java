package com.example.superdupergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.superdupergame.anvil.GameActivity;
import com.example.superdupergame.duck.Activities.StartActivity;


public class MainActivity extends AppCompatActivity {

    /**
     * <pre>
     *     0 = Duck
     *     1 = Anvil
     *     2 = Pacman
     * </pre>
     */
    private int gameCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void playGame(View v)
    {
        Intent intent = new Intent(this, MainActivity.class); //To not get mad
        switch(gameCount)
        {
            case 0: //Duck Game
                intent = new Intent(this, StartActivity.class);
            break;
            case 1:
                intent = new Intent(this, com.example.superdupergame.anvil.GameActivity.class);
            break;
            case 2:
                intent = new Intent(this, com.example.superdupergame.pacman.GameActivity.class);
            break;
        }
        startActivity(intent);
        finish();
    }

    public void nextGame(View v)
    {
        gameCount = gameCount + 1 > 2 ? 0: gameCount + 1;

    }

    public void lastGame(View v)
    {
        gameCount = gameCount -1 < 0 ? 2: gameCount - 1;
    }

    public void pacman(View v){
        Intent intent = new Intent(this, com.example.superdupergame.pacman.GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void anvil(View v)
    {
        Intent intent = new Intent(this, com.example.superdupergame.anvil.GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void duck(View v)
    {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
