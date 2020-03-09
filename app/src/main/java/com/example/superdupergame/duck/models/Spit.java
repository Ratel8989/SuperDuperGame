package com.example.superdupergame.duck.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.superdupergame.R;

import static com.example.superdupergame.duck.models.GameView.*;

public class Spit {

    int x, y, width, height;
    Bitmap spit;

    Spit(Resources res) {

        spit = BitmapFactory.decodeResource(res, R.drawable.spit);

        //Getting original image width and height
        width = spit.getWidth();
        height = spit.getHeight();
        //Scaling down the spit image
        width /= 6;
        height /= 6;
        //Casting above width and height to int
        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);
        //Creating spit with the above specified attributes
        spit = Bitmap.createScaledBitmap(spit, width, height, false);

    }

    Rect getCollisionShape() {return new Rect(x, y, x + width, y + height);}

}
