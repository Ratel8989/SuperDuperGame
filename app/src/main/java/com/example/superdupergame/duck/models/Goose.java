package com.example.superdupergame.duck.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.superdupergame.R;
import static com.example.superdupergame.duck.models.GameView.*;

public class Goose {

    public int gooseStartXLocation = 25;
    public boolean wasShot = true;
    int x = 0, y, width, height, gooseCount = 1;
    Bitmap goose1, goose2, goose3, goose4, goose5, goose6,goose7, goose8;

    private AppConstant appConstant = new AppConstant();

    Goose(Resources res) {
        goose1 = BitmapFactory.decodeResource(res, R.drawable.goose1);
        goose2 = BitmapFactory.decodeResource(res, R.drawable.goose2);
        goose3 = BitmapFactory.decodeResource(res, R.drawable.goose3);
        goose4 = BitmapFactory.decodeResource(res, R.drawable.goose4);
        goose5 = BitmapFactory.decodeResource(res, R.drawable.goose5);
        goose6 = BitmapFactory.decodeResource(res, R.drawable.goose6);
        goose7 = BitmapFactory.decodeResource(res, R.drawable.goose7);
        goose8 = BitmapFactory.decodeResource(res, R.drawable.goose8);

        width = (int)((goose1.getWidth()/appConstant.gooseImgScale)*screenRatioX);
        height = (int)((goose1.getHeight()/appConstant.gooseImgScale)*screenRatioY);

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
    Bitmap getGoose() {

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

    Rect getCollisionShape(){return new Rect(x, y, x + width, y + height);}

}

