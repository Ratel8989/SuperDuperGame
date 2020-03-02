package com.example.superdupergame.anvil.models;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class BitmapBank {

    Bitmap player; //Stores Player Bitmap
    Bitmap anvil;

    public BitmapBank(Resources res) //BitmapBank Constructor
    {
        //Initialize all Player Bitmaps in here:
        //player = BitmapFactory.decodeResource(res, R.drawable.llama);
    }

    public Bitmap getPlayer() //Gets Player Bitmap
    {
        return player;
    }

    public int getPlayerWidth() //Gets Width of Player Bitmap
    {
        return player.getWidth();
    }

    public int getPlayerHeight() //Gets Height of Player Bitmap
    {
        return player.getHeight();
    }

}
