package com.example.superdupergame.anvil.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.superdupergame.R;

public class BitmapBank {

    Bitmap player; //Stores Player Bitmap
    Bitmap anvil;
    Bitmap testArea;

    public BitmapBank(Resources res) //BitmapBank Constructor
    {
        player = BitmapFactory.decodeResource(res, R.drawable.player);
        anvil = BitmapFactory.decodeResource(res, R.drawable.anvil);
        testArea = BitmapFactory.decodeResource(res, R.drawable.test_area);
        //Initialize all Player Bitmaps in here:

    }
    public int getTestAreaWidth(){
        return testArea.getWidth();
    }
    public int getTestAreaHeight(){
        return testArea.getHeight();
    }

    public Bitmap getTestArea() {
        return testArea;
    }

    public Bitmap getAnvil() //Gets Player Bitmap
    {
        return anvil;
    }

    public int getAnvilWidth() //Gets Width of Player Bitmap
    {
        return anvil.getWidth();
    }

    public int getAnvilHeight() //Gets Height of Player Bitmap
    {
        return anvil.getHeight();
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
