package com.example.superdupergame.anvil;

import android.graphics.Canvas;

import com.example.superdupergame.anvil.models.*;

public class GameEngine {
    private Player player;
    static int gameState;

    public GameEngine() //Constructor initializes variables
    {
        player = new Player();

        // 0 - Not started
        // 1 - Playing
        // 2 - Game Over (Player Died)
        gameState = 1;
    }

    //Called by GameThread every X milliseconds
    public void updateAndDrawPlayer(Canvas canvas)
    {
        if(gameState == 1) //If Game is Playing
        {
            canvas.drawBitmap(AppConstants.getBitmapBank().getPlayer(), player.getxPosition(), player.getyPosition(), null);

            //Movement Logic based on key presses from view
            //Collision Logic Here or With what Collided with
        }
        //Animation Logic
    }
}
