package com.example.superdupergame.anvil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.superdupergame.anvil.models.*;

import java.util.Random;

public class GameEngine {
    private Player player;
    private Anvil[] anvils;
    private static int gameState;
    private Paint scorePaint;
    private Random rng = new Random();
    private boolean anvilsFalling[];

    public GameEngine() //Constructor initializes variables
    {
        player = new Player();
        anvils = new Anvil[20];
        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(100f);

        anvilsFalling = new boolean[20];
        for(int i = 0; i < anvilsFalling.length; i++)
            anvilsFalling[i] = false;

        for(int i = 0; i < anvils.length; i++) {
            anvils[i] = new Anvil(i);
            //anvils[i].setActive(true);
        }

        // 0 - Not started
        // 1 - Playing
        // 2 - Game Over (Player Died)
        // 3 - Done
        gameState = 1;
        AppConstants.getSoundBank().playBackground01();
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

    public void updateHealthAndTimer(Canvas canvas){
        if (gameState == 1) {
            AppConstants.points++;
            canvas.drawText(AppConstants.points + "", 180, 200, scorePaint);
            if (AppConstants.immunityTimer > 0) {
                AppConstants.immunityTimer--;

                int currentFrame = player.getSquishFrame();
                canvas.drawBitmap(AppConstants.getBitmapBank().getSquish(currentFrame), player.getX() - 50, player.getY() - 30,null);
                if (AppConstants.immunityTimer % 3 == 0) {
                    currentFrame++;
                }
                if (currentFrame > 9) {
                    currentFrame = 0;
                    AppConstants.getBitmapBank().shrinkPlayer(player.getHealth());
                    player.updateY();
                }
                player.setSquishFrame(currentFrame);

            }
            canvas.drawText("Lives: " + player.getHealth(), 180, 300, scorePaint);
            if (player.getHealth() < 1) {
                AppConstants.getSoundBank().stopBackground01();
                gameState = 2;

            }


        } else if (gameState == 2){
            AppConstants.getGameActivity().sendToGameOver();
            gameState = 3;
        }
    }

    public void updateAndDrawAnvil(Canvas canvas){
        if (gameState == 1) {
            for (Anvil anvil : anvils) {//changes anvil falling speed based on points
                if (AppConstants.points % 1000 == 0 && AppConstants.points != 0) {
                    anvil.setSpeed(anvil.getSpeed() + 5);
                    if (AppConstants.anvilSpawnRate > 100) {
                        AppConstants.anvilSpawnRate = AppConstants.anvilSpawnRate - 20;
                    }
                }
            }

            int activeThreshhold = 1;//AppConstants.points / 20;//the chance for an anvil to start falling
            for (int i = 0; i < anvils.length; i++) {
                if (!anvils[i].isActive() && !anvilsFalling[i])//checks that the anvil is valid to fall
                {
                    anvils[i].setActive(rng.nextInt(AppConstants.anvilSpawnRate) + 1 <= activeThreshhold);
                    if (anvils[i].isActive())
                        anvilsFalling[i] = true;
                }
            }
            for (int i = 0; i < anvils.length; i++) {//draws all anvils that are active
                if (anvils[i].isActive()) {
                    updateAnvil(anvils[i], i);
                    if (anvils[i].isActive()) {
                        canvas.drawBitmap(AppConstants.getBitmapBank().getAnvil(), anvils[i].getX(), anvils[i].getY(), null);
                    }
                }
            }
        }

    }

    private void updatePlayer() {
        Log.d("playerX", player.getX() + "");
        Log.d("playableX", AppConstants.playableX + "");
        if (AppConstants.direction == -1 && player.getX() > AppConstants.playableX) {
            player.setX(player.getX() - AppConstants.playerSpeed);
            if (player.getX() < AppConstants.playableX){
                player.setX(AppConstants.playableX);
            }
        } else if (AppConstants.direction == 1 && player.getX() + AppConstants.getBitmapBank().getPlayerWidth() + 10 < AppConstants.playableX + AppConstants.getBitmapBank().getTestAreaWidth()) {
            player.setX(player.getX() + AppConstants.playerSpeed);
        }

    }

    private void updateAnvil(Anvil anvil, int index) {
        if (anvil.getY() + AppConstants.getBitmapBank().getAnvilHeight() < AppConstants.playabley + AppConstants.getBitmapBank().getTestAreaHeight()) {
            anvil.setY(anvil.getY() + anvil.getSpeed());
        }else {
            anvil.setY(AppConstants.playabley);
            anvilsFalling[index] = false;
            anvil.setActive(false);
        }


        //hit detection
        if (anvil.isActive()) {
            int playerWidth = AppConstants.getBitmapBank().getPlayerWidth();
            int playerHeight = AppConstants.getBitmapBank().getPlayerHeight();
            int anvilWidth = AppConstants.getBitmapBank().getAnvilWidth();
            int anvilHeight = AppConstants.getBitmapBank().getAnvilHeight();
            if ((player.getX() > anvil.getX() && player.getX() < anvil.getX() + anvilWidth) ||
                    (player.getX() + playerWidth > anvil.getX() && player.getX() + playerWidth < anvil.getX() + anvilWidth)) {
                if ((player.getY() > anvil.getY() && player.getY() < anvil.getY() + anvilHeight) ||
                        (player.getY() + playerHeight > anvil.getY() && player.getY() + playerHeight < anvil.getY() + anvilHeight)) {
                    if (AppConstants.immunityTimer < 1) {
                        player.setHealth(player.getHealth() - 1);
                        AppConstants.immunityTimer = 30;

                        AppConstants.getSoundBank().playHit();
                    }

                    Log.d("anvils", "Collision detected");
                }
            }
        }

    }

}