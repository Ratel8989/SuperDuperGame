package com.example.superdupergame.anvil.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.superdupergame.R;

public class BitmapBank {

    Bitmap player;//Stores Player Bitmap
    Bitmap playerSQ1;
    Bitmap playerSQ2;
    Bitmap anvil;
    Bitmap testArea;
    Bitmap[] squish;

    public BitmapBank(Resources res) //BitmapBank Constructor
    {
        player = BitmapFactory.decodeResource(res, R.drawable.player00);
        playerSQ1 = BitmapFactory.decodeResource(res, R.drawable.player01);
        playerSQ2 = BitmapFactory.decodeResource(res, R.drawable.player02);
        anvil = BitmapFactory.decodeResource(res, R.drawable.anvil);
        testArea = BitmapFactory.decodeResource(res, R.drawable.test_area);

        squish = new Bitmap[10];
        squish[0] = BitmapFactory.decodeResource(res, R.drawable.squish01);
        squish[1] = squish[0];
        squish[2] = BitmapFactory.decodeResource(res, R.drawable.squish02);
        squish[3] = squish[2];
        squish[4] = BitmapFactory.decodeResource(res, R.drawable.squish03);
        squish[5] = squish[4];
        squish[6] = BitmapFactory.decodeResource(res, R.drawable.squish04);
        squish[7] = squish[6];
        squish[8] = BitmapFactory.decodeResource(res, R.drawable.squish05);
        squish[9] = squish[8];
        //Initialize all Player Bitmaps in here:

    }

    public void shrinkPlayer(int playerHP){
        if (playerHP == 2){ player = playerSQ1;}
        else if (playerHP == 1){ player = playerSQ2;}
    }

    public Bitmap getSquish(int frame) {
        return squish[frame];
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
