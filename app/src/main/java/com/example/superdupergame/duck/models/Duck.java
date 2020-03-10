package com.example.superdupergame.duck.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.superdupergame.R;
import com.example.superdupergame.duck.GameView;

import static com.example.superdupergame.duck.GameView.*;


public class Duck {

    public int toShoot = 0;
    public boolean isGoingUp = false;
    private int x, y, width, height, duckCount = 0, shootCounter = 1;
    public Bitmap duck1, duck2,duck3, duck4, duckshoot1, duckshoot2, duckshoot3, duckshoot4, kiaduck;
    private GameView gameView;
    private AppConstant appConstant = new AppConstant();

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    public Duck(GameView gameView, int screenY, Resources res) {

        this.gameView = gameView;

        duck1 = BitmapFactory.decodeResource(res, R.drawable.duck1);
        duck2 = BitmapFactory.decodeResource(res, R.drawable.duck2);
        duck3 = BitmapFactory.decodeResource(res, R.drawable.duck3);
        duck4 = BitmapFactory.decodeResource(res, R.drawable.duck4);

        width = (int)((duck1.getWidth()/appConstant.getDuckImgScale())*screenRatioX);
        height = (int)((duck1.getHeight()/appConstant.getDuckImgScale())*screenRatioY);

        duck1 = Bitmap.createScaledBitmap(duck1, width, height, false);
        duck2 = Bitmap.createScaledBitmap(duck2, width, height, false);
        duck3 = Bitmap.createScaledBitmap(duck3, width, height, false);
        duck4 = Bitmap.createScaledBitmap(duck4, width, height, false);

        duckshoot1 = BitmapFactory.decodeResource(res, R.drawable.duckshoot1);
        duckshoot2 = BitmapFactory.decodeResource(res, R.drawable.duckshoot2);
        duckshoot3 = BitmapFactory.decodeResource(res, R.drawable.duckshoot3);
        duckshoot4 = BitmapFactory.decodeResource(res, R.drawable.duckshoot4);

        duckshoot1 = Bitmap.createScaledBitmap(duckshoot1, width, height, false);
        duckshoot2 = Bitmap.createScaledBitmap(duckshoot2, width, height, false);
        duckshoot3 = Bitmap.createScaledBitmap(duckshoot3, width, height, false);
        duckshoot4 = Bitmap.createScaledBitmap(duckshoot4, width, height, false);

        kiaduck = BitmapFactory.decodeResource(res, R.drawable.kiaduck);
        kiaduck = Bitmap.createScaledBitmap(kiaduck, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    //Getters and setters
    public int getX() {return x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public int getWidth() {return width;}

    public int getHeight() {return height; }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public Bitmap getFlight () {

        if (toShoot != 0) {

            if (shootCounter == 1) {
                shootCounter++;
                return duckshoot1;
            }
            if (shootCounter == 2) {
                shootCounter++;
                return duckshoot2;
            }
            if (shootCounter == 3) {
                shootCounter++;
                return duckshoot3;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newSpit();

            return duckshoot4;
        }

        if (duckCount == 0) {
            duckCount++;
            return duck1;
        }
        if (duckCount == 1) {
            duckCount++;
            return duck1;
        }
        if (duckCount == 2) {
            duckCount++;
            return duck2;
        }
        if (duckCount == 3) {
            duckCount++;
            return duck2;
        }if (duckCount == 4) {
            duckCount++;
            return duck3;
        }if (duckCount == 5) {
            duckCount++;
            return duck3;
        }
        if (duckCount == 6) {
            duckCount++;
            return duck4;
        }
        duckCount =0;
        return duck4;
    }

    public Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    public Bitmap duckDead() {
        return kiaduck;
    }

}
