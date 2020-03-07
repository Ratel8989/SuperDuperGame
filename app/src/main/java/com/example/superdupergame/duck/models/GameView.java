package com.example.superdupergame.duck.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.superdupergame.R;
import com.example.superdupergame.duck.models.*;
import com.example.superdupergame.duck.models.AppConstant;
import com.example.superdupergame.duck.models.Background;
import com.example.superdupergame.duck.models.Duck;
import com.example.superdupergame.duck.models.Goose;
import com.example.superdupergame.duck.models.Spit;
import com.example.superdupergame.duck.views.GameActivity;
import com.example.superdupergame.duck.views.StartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean gameIsPlaying, gameIsOver = false;
    private int screenX, screenY, geeseEliminated = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Goose[] gooseList;
    private SharedPreferences prefs;
    private Random rng;
    private SoundPool soundPool;
    private List<Spit> duckSpit;
    private int sound;
    private Duck flight;
    private GameActivity activity;
    private Background background1, background2;

    ///
    AppConstant appConstant = new AppConstant();


    public GameView(GameActivity activity, int screenX, int screenY) {



        super(activity);
        setFocusable(true);
        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        // Duck Spit sound stuff
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = soundPool.load(activity, R.raw.quack, 1);

        // Makes it so app can be scaled to any screen size
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        flight = new Duck(this, screenY, getResources());

        duckSpit = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        gooseList = new Goose[4];
        for (int i = 0;i < 4;i++) {
            Goose bird = new Goose(getResources());
            gooseList[i] = bird;
        }

        rng = new Random();

    }

    @Override
    public void run() {
        while (gameIsPlaying) {
            update ();
            draw ();
            sleep ();
        }
    }

    private void update () {
        //Makes the moving background
        background1.x -= appConstant.backgroundSpeed;
        background2.x -= appConstant.backgroundSpeed;
        if (background1.x + background1.background.getWidth() < 0) { background1.x = screenX; }
        if (background2.x + background2.background.getWidth() < 0) { background2.x = screenX; }

        ////////////////////////////////////////////////////////////////////////////////////////////

        // Moves vertically if isGoingUp is true
        if (flight.isGoingUp)
            flight.y -= appConstant.duckVerticalSpeed * screenRatioY;
        else
            flight.y += appConstant.duckVerticalSpeed * screenRatioY;

        if (flight.y < 0)
            flight.y = 0;

        if (flight.y >= screenY - flight.height)
            flight.y = screenY - flight.height;

        ////////////////////////////////////////////////////////////////////////////////////////////

        List<Spit> spitBucket = new ArrayList<>();
        for (Spit spit : duckSpit) {
            if (spit.x > screenX){spitBucket.add(spit);}
            //What gooseStartXLocation the duck spits at
            spit.x += appConstant.spitSpeed * screenRatioX;

            for (Goose goose : gooseList) {
                //If spit collides with a bird geeseEliminated goes up and returns that the bird was shot.
                if (Rect.intersects(goose.getCollisionShape(), spit.getCollisionShape())) {
                    geeseEliminated++;
                    goose.x = -500;
                    spit.x = screenX + 500;
                    goose.wasShot = true;
                }
            }
        }
        for (Spit spatSpit : spitBucket) {duckSpit.remove(spatSpit);}

        ////////////////////////////////////////////////////////////////////////////////////////////

        //Checking game over cases for each gooseList
        for (Goose goose : gooseList) {
            //Speed of the gooseList
            goose.x -= appConstant.gooseSpeed;

            //If the gooseList hits the x-axis
            if (goose.x + goose.width < 0) {
                if (!goose.wasShot) {
                    gameIsOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                goose.gooseStartXLocation = rng.nextInt(bound);

                if (goose.gooseStartXLocation < 10 * screenRatioX)
                    goose.gooseStartXLocation = (int) (10 * screenRatioX);

                goose.x = screenX;
                //Places gooseList randomly on the y-axis
                goose.y = rng.nextInt(screenY - goose.height);

                goose.wasShot = false;
            }

            if (Rect.intersects(goose.getCollisionShape(), flight.getCollisionShape())) {
                gameIsOver = true;
                return;
            }

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void draw () {
        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Goose goose : gooseList){
                canvas.drawBitmap(goose.getGoose(), goose.x, goose.y, paint);
            }

            canvas.drawText(geeseEliminated + "", screenX / 2f, 164, paint);

            if (gameIsOver) {
                gameIsPlaying = false;
                canvas.drawBitmap(flight.duckDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;
            }

            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);

            for (Spit spit : duckSpit)
                canvas.drawBitmap(spit.spit, spit.x, spit.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void waitBeforeExiting() {

        try {
            Thread.sleep(1500);
            activity.startActivity(new Intent(activity, StartActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < geeseEliminated) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", geeseEliminated);
            editor.apply();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume () {

        gameIsPlaying = true;
        thread = new Thread(this);
        thread.start();

    }
    public void pause () {

        try {
            gameIsPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Keyboard and screen touch functions

    //If left or right side of screen is touched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    flight.toShoot++;
                break;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_W:
                Log.d("KEY: ", keyCode + "");
                flight.isGoingUp = true;
                break;
            case KeyEvent.KEYCODE_SPACE:
                flight.toShoot++;
                break;
        }
        return true;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_W:
                flight.isGoingUp = false;
                break;
        }
        return true;
    }


    public void newSpit() {
        if(!prefs.getBoolean("isMute", false)) {
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }
        Spit spit = new Spit(getResources());
        spit.x = flight.x + flight.width;
        spit.y = flight.y + (flight.height / 2);
        duckSpit.add(spit);

    }
}
