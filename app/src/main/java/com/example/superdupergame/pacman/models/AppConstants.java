package com.example.superdupergame.pacman.models;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.core.content.res.ResourcesCompat;

import com.example.superdupergame.R;
import com.example.superdupergame.pacman.GameActivity;
import com.example.superdupergame.pacman.GameEngine;

public class AppConstants
{
    private static BitmapBank bitmapBank;
    private static GameEngine gameEngine;
    private static GameActivity gameActivity;
    private static SoundBank soundBank;

    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    public static int mazeX;
    public static int mazeY;

    //Power Pellet Frames
    public static int pp_currentFrame;
    public final static int PP_MAXFRAME = 7;

    public static int pacmanSpeed;
    public static int ghostSpeed;

    public static int score;
    public static Typeface typeface;

    /**
     * <pre>
     *   0 - No Movement
     *   1 - Up
     *   2 - Right
     *   3 - Down
     *   4 - Left
     * </pre>
     */
    public static int pacmanDirection;

    //Constants to make code more readable for both Pacman and Ghosts
    public static final int UP_DIRECTION = 1;
    public static final int RIGHT_DIRECTION = 2;
    public static final int DOWN_DIRECTION = 3;
    public static final int LEFT_DIRECTION = 4;

    //Constants to for ghost speeds
    public static final int SLOW_SPEED = 3;
    public static final int NORMAL_SPEED = 6;
    public static final int FAST_SPEED = 30;

    public static boolean isNeumont;

    private static int TILE_SIZE;

    public static void initialization(Context context, GameActivity activity)
    {
        isNeumont = activity.getIntent().getBooleanExtra("neumont", false);
        setScreenSize(context);
        setGameConstants();
        typeface = ResourcesCompat.getFont(context, R.font.emulogic);
        soundBank = new SoundBank(context);
        bitmapBank = new BitmapBank(context.getResources());
        gameEngine = new GameEngine();
        gameActivity = activity;
    }

    private static void setGameConstants()
    {
        pacmanDirection = LEFT_DIRECTION;

        if(!isNeumont) {
            mazeX = SCREEN_WIDTH / 10;
            mazeY = SCREEN_HEIGHT / 10;
            TILE_SIZE = 42;
        }else {
            mazeX = 0;
            mazeY = SCREEN_HEIGHT/4;
            TILE_SIZE = 35;
        }

        pacmanSpeed = 8;
        ghostSpeed = 6;

        pp_currentFrame = 0;

        score = 0;
    }

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

    public static BitmapBank getBitmapBank()
    {
        return bitmapBank;
    }

    public static GameEngine getGameEngine()
    {
        return gameEngine;
    }

    public static GameActivity getGameActivity()
    {
        return gameActivity;
    }

    public static SoundBank getSoundBank()
    {
        return soundBank;
    }

    public static int getTileSize()
    {
        return TILE_SIZE;
    }

    public static int getScreenWidth()
    {
        return SCREEN_WIDTH;
    }

    public static int getSCreenHeight()
    {
        return SCREEN_HEIGHT;
    }
}
