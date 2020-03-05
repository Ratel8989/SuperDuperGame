package com.example.superdupergame.anvil.views;

import android.content.Context;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.superdupergame.anvil.GameThread;
import com.example.superdupergame.anvil.models.AppConstants;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    GameThread gameThread;

    public GameView(Context context) {
        super(context);
        initView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(gameThread.isRunning()) {
            gameThread = new GameThread(holder);
            gameThread.start();
        }
        else
            gameThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

    void initView() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(holder);
    }

    @Override //Called when key Pressed Down
    public boolean onKeyDown(int keyCode, KeyEvent key)
    {
        switch (keyCode)//If presses key W
        {
            case KeyEvent.KEYCODE_A:
                AppConstants.direction = -1;
                break;
            case KeyEvent.KEYCODE_D:
                AppConstants.direction = 1;
                break;
            case KeyEvent.KEYCODE_SPACE:

                break;

        }
        return true;
    }
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent key){
//        switch (keyCode){
//
//        }
//    }
}
