package com.example.superdupergame.anvil.models;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.superdupergame.anvil.GameActivity;
import com.example.superdupergame.anvil.GameEngine;

public class AppConstants {

    private static BitmapBank bitmapBank;
    private static GameEngine gameEngine;
    private static GameActivity gameActivity;
    private static SoundBank soundBank;

    public static int SCREEN_WIDTH, SCREEN_HEIGHT;
    // SCREEN WIDTH:1440
    // SCREEN HEIGHT:2392

    public static int playerSpeed;
    public static int startingAnvilSpeed;
    public static int direction;
    //0 = no movement,
    // 1 = right,
    //-1 = left
    public static int playableX;
    public static int playableMaxX;
    public static int playabley;

    public static int points;
    public static int anvilSpawnRate;

    public static int immunityTimer;



    public static void initialization(Context context, GameActivity activity){ //Initializes Everything in this class
        gameActivity = activity;
        bitmapBank = new BitmapBank(context.getResources());
        soundBank = new SoundBank(context);
        setScreenSize(context);
        setGameConstants();

        gameEngine = new GameEngine();
    }

    //This method will set constants you add to this class
    public static void setGameConstants()
    {
        playerSpeed = 40;
        playableX = 185;
        playableMaxX = playableX + bitmapBank.getTestAreaWidth();
        playabley = 80;
        startingAnvilSpeed = 10;
        immunityTimer = 0;
        points = 0;
        anvilSpawnRate = 300;
    }

    //This Method will set screen variables to current screen's size
    private static void setScreenSize(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        //Gets metrics of screen
        display.getMetrics(metrics);

        //Sets width to screen width pixels
        int width = metrics.widthPixels;
        //Sets height to screen height pixels
        int height = metrics.heightPixels;

        AppConstants.SCREEN_WIDTH = width;
        AppConstants.SCREEN_HEIGHT = height;
    }

    public static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    public static GameActivity getGameActivity() {
        return gameActivity;
    }

    public static SoundBank getSoundBank() {
        return soundBank;
    }
}
