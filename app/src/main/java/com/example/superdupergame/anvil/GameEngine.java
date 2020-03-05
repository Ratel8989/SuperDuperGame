package com.example.superdupergame.anvil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.example.superdupergame.anvil.models.*;

public class GameEngine {
    private Player player;
    private Anvil[] anvil;
    static int gameState;

    public GameEngine() //Constructor initializes variables
    {
        player = new Player();
        anvil = new Anvil[10];

        for(int i = 0; i < anvil.length; i++) {
            anvil[i] = new Anvil(i);
            anvil[i].setActive(true);
        }

        // 0 - Not started
        // 1 - Playing
        // 2 - Game Over (Player Died)
        gameState = 1;
    }

    //Called by GameThread every X milliseconds
    public void updateAndDrawPlayer(Canvas canvas) {
        if (gameState == 1) //If Game is Playing
        {
            updatePlayer();
            canvas.drawBitmap(AppConstants.getBitmapBank().getPlayer(), player.getX(), player.getY(), null);
            AppConstants.direction = 0;
            //Movement Logic based on key presses from view
            //Collision Logic Here or With what Collided with
        }

        //Animation Logic
    }

    public void updateAndDrawBackground(Canvas canvas){
        canvas.drawColor(Color.BLUE);
    }

    public void drawPlayableArea(Canvas canvas){
        canvas.drawBitmap(AppConstants.getBitmapBank().getTestArea(), AppConstants.playableX,AppConstants.playabley,null);
    }

    public void anvilBehavior(){
        //maybe takes in an int from the game thread to determine a switch case for how the anvils behave and which ones are on or off
    }

    public void updateAndDrawAnvil(Canvas canvas){
        for (int i = 0; i < anvil.length; i++) {
            if (anvil[i].isActive()) {
                updateAnvil(anvil[i]);
                if (anvil[i].isActive()) {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getAnvil(), anvil[i].getX(), anvil[i].getY(), null);
                }
            }
        }

    }

    private void updatePlayer() {
        if (AppConstants.direction == -1 && player.getX() > AppConstants.playableX) {
            player.setX(player.getX() - AppConstants.playerSpeed);
        } else if (AppConstants.direction == 1 && player.getX() + AppConstants.getBitmapBank().getPlayerWidth() < AppConstants.playableX + AppConstants.getBitmapBank().getTestAreaWidth()) {
            player.setX(player.getX() + AppConstants.playerSpeed);
        }

    }

    private void updateAnvil(Anvil anvil) {
        if (anvil.getY() + AppConstants.getBitmapBank().getAnvilHeight() < AppConstants.playabley + AppConstants.getBitmapBank().getTestAreaHeight()) {
            anvil.setY(anvil.getY() + anvil.getSpeed());
        }else {
            anvil.setY(AppConstants.playabley);
            anvil.setActive(false);
        }


        int playerWidth = AppConstants.getBitmapBank().getPlayerWidth();
        int playerHeight = AppConstants.getBitmapBank().getPlayerHeight();
        int anvilWidth = AppConstants.getBitmapBank().getAnvilWidth();
        int anvilHeight = AppConstants.getBitmapBank().getAnvilHeight();
        if ((player.getX() > anvil.getX() && player.getX() < anvil.getX() + anvilWidth) ||
                (player.getX() + playerWidth > anvil.getX() && player.getX() + playerWidth < anvil.getX() + anvilWidth)){
            if ((player.getY() > anvil.getY() && player.getY() < anvil.getY() + anvilHeight) ||
                    (player.getY() + playerHeight > anvil.getY() && player.getY() + playerHeight < anvil.getY() + anvilHeight)){
                Log.d("anvil", "Collision detected");
            }
        }

    }


    //Desynchronized  anvils
    //make a second Anvil set that can be dropped out of sync from the first set
    //
    //Change difficulty based on points
    //difficulty should increase the number of anvils per row, add more rows, increase speed
    //algorithmic difficulty?
    //Do health and lose conditions
    //
}