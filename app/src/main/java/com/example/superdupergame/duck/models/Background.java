package com.example.superdupergame.duck.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.superdupergame.R;

public class Background {

    private int x = 0, y = 0;
    private Bitmap background;

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    //Getters and setters
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}

////////////////////////////////////////////////////////////////////////////////////////////////////

    public Background (int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }

    public Bitmap getBackground() {return background;}
}
