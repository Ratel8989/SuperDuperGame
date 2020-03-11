package com.example.superdupergame.duck.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.superdupergame.R;

import static com.example.superdupergame.duck.GameView.*;

public class Spit {

    private int x, y, width, height;
    private Bitmap spit;

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    //Getters and setters
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public Bitmap getSpit() {return spit;}

////////////////////////////////////////////////////////////////////////////////////////////////////

    public Spit(Resources res) {

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

    public Rect getCollisionShape() {return new Rect(x, y, x + width, y + height);}

}
