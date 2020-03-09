package com.example.superdupergame.anvil;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.superdupergame.anvil.models.AppConstants;


public class GameThread extends Thread{

    SurfaceHolder surfaceHolder; //Surface Holder Object Reference
    boolean isRunning; //Flag to determine whether the thread is running
    long startTime,loopTime; //Loop start time and loop duration
    long DELAY = 33; //Delay in milliseconds

    public GameThread(SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
        isRunning = true;
    }

    @Override
    public void run()
    {
        while(isRunning)
        {
            startTime = SystemClock.uptimeMillis(); //milliseconds since boot
            Canvas canvas = surfaceHolder.lockCanvas(null);

            if(canvas != null)
            {
                //Synchronized - To make sure no two or more threads try to run the same method
                synchronized (surfaceHolder)
                {
                    /* Put all your updates in here */
                    AppConstants.getGameEngine().updateAndDrawBackground(canvas);
                    AppConstants.getGameEngine().drawPlayableArea(canvas);
                    AppConstants.getGameEngine().updateAndDrawPlayer(canvas);
                    AppConstants.getGameEngine().updateAndDrawAnvil(canvas);
                    AppConstants.getGameEngine().updateHealthAndTimer(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            loopTime = SystemClock.uptimeMillis() - startTime;
            if(loopTime < DELAY)
            {
                try {
                    Thread.sleep(DELAY - loopTime);
                }
                catch(InterruptedException e) {
                    Log.e("Interrupted", "Interrupted while sleeping");
                }
            }
        }
    }

    //Return whether thread is running
    public boolean isRunning()
    {
        return isRunning;
    }

    //Sets the thread state, false = stopped, true = running
    public void setIsRunning(boolean state)
    {
        isRunning = state;
    }
}
