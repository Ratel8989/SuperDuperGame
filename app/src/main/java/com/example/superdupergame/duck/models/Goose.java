package com.example.superdupergame.duck.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.superdupergame.R;
import static com.example.superdupergame.duck.GameView.*;

public class Goose {

    private AppConstant appConstant = new AppConstant();
    private int gooseStartXLocation = 25;
    private boolean wasShot = true;
    private int x = 0, y, width, height, gooseCount = 1;
    private Bitmap goose1, goose2, goose3, goose4, goose5, goose6,goose7, goose8;

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    //Getters and setters
    public int getGooseStartXLocation() {return gooseStartXLocation;}
    public void setGooseStartXLocation(int gooseStartXLocation) {this.gooseStartXLocation = gooseStartXLocation;}

    public boolean isWasShot() {return wasShot;}
    public void setWasShot(boolean wasShot) {this.wasShot = wasShot;}

    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public int getWidth() {return width;}
    public void setWidth(int width) {this.width = width;}

    public int getHeight() {return height;}

    public void setHeight(int height) {this.height = height;}

////////////////////////////////////////////////////////////////////////////////////////////////////

    public Goose(Resources res) {
        //Getting each goose frames for each goose that is created
        goose1 = BitmapFactory.decodeResource(res, R.drawable.goose1);
        goose2 = BitmapFactory.decodeResource(res, R.drawable.goose2);
        goose3 = BitmapFactory.decodeResource(res, R.drawable.goose3);
        goose4 = BitmapFactory.decodeResource(res, R.drawable.goose4);
        goose5 = BitmapFactory.decodeResource(res, R.drawable.goose5);
        goose6 = BitmapFactory.decodeResource(res, R.drawable.goose6);
        goose7 = BitmapFactory.decodeResource(res, R.drawable.goose7);
        goose8 = BitmapFactory.decodeResource(res, R.drawable.goose8);

        width = (int)((goose1.getWidth()/appConstant.getGooseImgScale())*screenRatioX);
        height = (int)((goose1.getHeight()/appConstant.getGooseImgScale())*screenRatioY);

        //Setting each goose width and height
        goose1 = Bitmap.createScaledBitmap(goose1, width, height, false);
        goose2 = Bitmap.createScaledBitmap(goose2, width, height, false);
        goose3 = Bitmap.createScaledBitmap(goose3, width, height, false);
        goose4 = Bitmap.createScaledBitmap(goose4, width, height, false);
        goose5 = Bitmap.createScaledBitmap(goose1, width, height, false);
        goose6 = Bitmap.createScaledBitmap(goose2, width, height, false);
        goose7 = Bitmap.createScaledBitmap(goose3, width, height, false);
        goose8 = Bitmap.createScaledBitmap(goose4, width, height, false);

        y = -height;
    }

    // Returns goose image per frame
    public Bitmap getGoose() {

        if (gooseCount == 1) {
            gooseCount++;
            return goose1;
        }
        if (gooseCount == 2) {
            gooseCount++;
            return goose1;
        }
        if (gooseCount == 3) {
            gooseCount++;
            return goose2;
        }
        if (gooseCount == 4) {
            gooseCount++;
            return goose2;
        }
        if (gooseCount == 5) {
            gooseCount++;
            return goose3;
        }
        if (gooseCount == 6) {
            gooseCount++;
            return goose3;
        }
        if (gooseCount == 7) {
            gooseCount++;
            return goose4;
        }
        if (gooseCount == 8) {
            gooseCount++;
            return goose4;
        }
        if (gooseCount == 9) {
            gooseCount++;
            return goose5;
        }
        if (gooseCount == 10) {
            gooseCount++;
            return goose5;
        }
        if (gooseCount == 11) {
            gooseCount++;
            return goose6;
        }
        if (gooseCount == 12) {
            gooseCount++;
            return goose6;
        }
        if (gooseCount == 13) {
            gooseCount++;
            return goose7;
        }
        if (gooseCount == 14) {
            gooseCount++;
            return goose7;
        }
        if (gooseCount == 15) {
            gooseCount++;
            return goose8;
        }

        gooseCount = 1;
        return goose8;
    }

    public Rect getCollisionShape(){return new Rect(x, y, x + width, y + height);}

}

