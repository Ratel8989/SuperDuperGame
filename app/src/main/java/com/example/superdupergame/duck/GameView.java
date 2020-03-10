package com.example.superdupergame.duck;

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

import androidx.core.content.res.ResourcesCompat;

import com.example.superdupergame.R;
import com.example.superdupergame.duck.Activities.GameActivity;
import com.example.superdupergame.duck.Activities.StartActivity;
import com.example.superdupergame.duck.models.AppConstant;
import com.example.superdupergame.duck.models.Background;
import com.example.superdupergame.duck.models.Duck;
import com.example.superdupergame.duck.models.Goose;
import com.example.superdupergame.duck.models.Spit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    AppConstant appConstant = new AppConstant();
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
    private Duck flyingDuck;
    private GameActivity activity;
    private Background background1, background2;

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

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

        //Getting the background
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        flyingDuck = new Duck(this, screenY, getResources());
        duckSpit = new ArrayList<>();

        background2.setX(screenX);

        //Uses the attributes below when called to draw something
        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.parseColor("#FF9800"));
        paint.setTypeface(ResourcesCompat.getFont(getContext(), R.font.duckfont));

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

        //Speed increase after x frames to make the game harder
        appConstant.setPerUpdateCounter(appConstant.getPerUpdateCounter()+1);;
        if(appConstant.getPerUpdateCounter() > appConstant.getAmountOfFramesForUpdate()) {
            appConstant.setPerUpdateCounter(0);

            appConstant.setBackgroundSpeed(appConstant.getBackgroundSpeed() + appConstant.getFrameBackgroundSpeed());
            appConstant.setGooseSpeed(appConstant.getGooseSpeed() + appConstant.getFrameGooseSpeed());
            appConstant.setDuckVerticalSpeed(appConstant.getDuckVerticalSpeed() + appConstant.getFrameDuckVerticalSpeed()) ;
            appConstant.setSpitSpeed(appConstant.getSpitSpeed() + appConstant.getFrameSpitSpeed());
        }




        //Makes the moving background
        background1.setX(background1.getX() - appConstant.getBackgroundSpeed());
        background2.setX(background2.getX() - appConstant.getBackgroundSpeed());

        if (background1.getX() + background1.getBackground().getWidth() < 0) { background1.setX(screenX); }
        if (background2.getX() + background2.getBackground().getWidth() < 0) { background2.setX(screenX); }

        ////////////////////////////////////////////////////////////////////////////////////////////

        // Moves vertically if isGoingUp is true
        if (flyingDuck.isGoingUp)
            flyingDuck.setY(flyingDuck.getY() - (int)(appConstant.getDuckVerticalSpeed() * screenRatioY));
        else
            flyingDuck.setY(flyingDuck.getY() + (int)(appConstant.getDuckVerticalSpeed() * screenRatioY));

        if (flyingDuck.getY() < 0)
            flyingDuck.setY(0);
        if (flyingDuck.getY() >= screenY - flyingDuck.getHeight())
            flyingDuck.setY(screenY - flyingDuck.getHeight());

        ////////////////////////////////////////////////////////////////////////////////////////////

        List<Spit> spitBucket = new ArrayList<>();
        for (Spit spit : duckSpit) {
            if (spit.getX() > screenX){spitBucket.add(spit);}
            //What gooseStartXLocation the duck spits at
            spit.setX(spit.getX() + (int)(appConstant.getSpitSpeed() * screenRatioX));

            for (Goose goose : gooseList) {
                //If spit collides with a bird geeseEliminated goes up and returns that the bird was shot.
                if (Rect.intersects(goose.getCollisionShape(), spit.getCollisionShape())) {
                    geeseEliminated++;
                    goose.setX(-500);
                    spit.setX(screenX + 500);
                    goose.setWasShot(true);
                }
            }
        }
        for (Spit spatSpit : spitBucket) {duckSpit.remove(spatSpit);}

        ////////////////////////////////////////////////////////////////////////////////////////////

        //Checking game over cases for each goose
        for (Goose goose : gooseList) {
            //Speed of the goose
            goose.setX(goose.getX() - appConstant.getGooseSpeed());

            //If the goose hits the x-axis
            if (goose.getX() + goose.getWidth() < 0) {
                if (!goose.isWasShot()) {
                    gameIsOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                goose.setGooseStartXLocation(rng.nextInt(bound));

                if (goose.getGooseStartXLocation() < 10 * screenRatioX)
                    goose.setGooseStartXLocation((int) (10 * screenRatioX));

                goose.setX(screenX);
                //Places goose randomly on the y-axis
                goose.setY(rng.nextInt(screenY - goose.getHeight()));

                goose.setWasShot(false);
            }

            if (Rect.intersects(goose.getCollisionShape(), flyingDuck.getCollisionShape())) {
                gameIsOver = true;
                return;
            }

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void draw () {
        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.getBackground(), background1.getX(), background1.getY(), paint);
            canvas.drawBitmap(background2.getBackground(), background2.getX(), background2.getY(), paint);

            for (Goose goose : gooseList){
                canvas.drawBitmap(goose.getGoose(), goose.getX(), goose.getY(), paint);
            }

            canvas.drawText(geeseEliminated + "", screenX / 2f, 164, paint);

            if (gameIsOver) {
                gameIsPlaying = false;
                canvas.drawBitmap(flyingDuck.duckDead(), flyingDuck.getX(), flyingDuck.getY(), paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();

                return;
            }

            canvas.drawBitmap(flyingDuck.getFlight(), flyingDuck.getX(), flyingDuck.getY(), paint);

            for (Spit spit : duckSpit)
                canvas.drawBitmap(spit.getSpit(), spit.getX(), spit.getY(), paint);

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

    //Saves highscore somehow
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

    //If bottom left or bottom right side of screen is touched
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2 && event.getY() > screenY /2) {
                    flyingDuck.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flyingDuck.isGoingUp = false;
                if (event.getX() > screenX / 2 && event.getY() > screenY /2)
                    flyingDuck.toShoot++;
                break;
        }
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_W:
                Log.d("KEY: ", keyCode + "");
                flyingDuck.isGoingUp = true;
                break;
            case KeyEvent.KEYCODE_SPACE:
                flyingDuck.toShoot++;
                break;
        }
        return true;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_W:
                flyingDuck.isGoingUp = false;
                break;
        }
        return true;
    }

    public void newSpit() {
        if(!prefs.getBoolean("isMute", false)) {
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }
        Spit spit = new Spit(getResources());
        spit.setX(flyingDuck.getX() + flyingDuck.getWidth());
        spit.setY(flyingDuck.getY() + (flyingDuck.getHeight() / 2));
        duckSpit.add(spit);
    }
}

