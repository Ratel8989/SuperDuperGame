package com.example.superdupergame.anvil.models;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.superdupergame.anvil.GameEngine;

public class AppConstants {

    static BitmapBank bitmapBank;
    static GameEngine gameEngine;

    static int SCREEN_WIDTH, SCREEN_HEIGHT;

    public static void initialization(Context context){ //Initializes Everything in this class
        setScreenSize(context);
        setGameConstants();
        bitmapBank = new BitmapBank(context.getResources());
        gameEngine = new GameEngine();
    }

    //This method will set constants you add to this class
    public static void setGameConstants()
    {

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
}
