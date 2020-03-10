package com.example.superdupergame.pacman;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.superdupergame.pacman.models.AppConstants;

public class GameThread extends Thread
{
    private final SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private long startTime, loopTime;
    private long DELAY = 33;

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
            startTime = SystemClock.uptimeMillis();
            Canvas canvas = surfaceHolder.lockCanvas(null);
            if(canvas != null)
            {
                synchronized (surfaceHolder)
                {
                    AppConstants.getGameEngine().updateGUIAndDrawMaze(canvas);
                    AppConstants.getGameEngine().updateAndDrawPellets(canvas);
                    AppConstants.getGameEngine().updateAndDrawPacman(canvas);
                    AppConstants.getGameEngine().updateAndDrawRedGhost(canvas);
                    AppConstants.getGameEngine().updateAndDrawPinkGhost(canvas);
                    AppConstants.getGameEngine().updateAndDrawBlueGhost(canvas);
                    AppConstants.getGameEngine().updateAndDrawOrangeGhost(canvas);
                    AppConstants.getGameEngine().updateTimers(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            loopTime = SystemClock.uptimeMillis() - startTime;
            if(loopTime < DELAY)
            {
                try {
                    Thread.sleep(DELAY - loopTime);
                } catch(InterruptedException e) {
                    Log.e("Interrupted", "Interrupted while sleeping");
                }
            }
        }
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void setIsRunning(boolean state)
    {
        isRunning = state;
    }

}
