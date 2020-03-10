package com.example.superdupergame.pacman.views;

import android.content.Context;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.superdupergame.pacman.GameThread;
import com.example.superdupergame.pacman.models.AppConstants;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread gameThread;

    public GameView(Context context)
    {
        super(context);
        initView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        if(gameThread.isRunning()) {
            gameThread = new GameThread(holder);
            gameThread.start();
        }
        else {
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        if(gameThread.isRunning()) {
            gameThread.setIsRunning(false);
            boolean retry = false;
            while(retry) {
                try {
                    gameThread.join();
                    retry = false;
                }
                catch(InterruptedException e) {}
            }
        }
    }

    private void initView()
    {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(holder);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent key)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_DPAD_UP:
                //These if's are to make it so you can't try to move towards a wall and just stop in place
                if(!AppConstants.getGameEngine().getMaze().isTopTileWall(AppConstants.getGameEngine().getPacman().getRow(), AppConstants.getGameEngine().getPacman().getColumn()))
                    AppConstants.pacmanDirection = 1;

                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(!AppConstants.getGameEngine().getMaze().isLeftTileWall(AppConstants.getGameEngine().getPacman().getRow(), AppConstants.getGameEngine().getPacman().getColumn()))
                    AppConstants.pacmanDirection = 4;

                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(!AppConstants.getGameEngine().getMaze().isBottomTileWall(AppConstants.getGameEngine().getPacman().getRow(), AppConstants.getGameEngine().getPacman().getColumn()))
                    AppConstants.pacmanDirection = 3;

                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(!AppConstants.getGameEngine().getMaze().isRightTileWall(AppConstants.getGameEngine().getPacman().getRow(), AppConstants.getGameEngine().getPacman().getColumn()))
                    AppConstants.pacmanDirection = 2;

                break;
        }
        return true;
    }
}
