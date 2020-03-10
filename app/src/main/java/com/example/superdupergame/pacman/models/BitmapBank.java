package com.example.superdupergame.pacman.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.superdupergame.R;

public class BitmapBank
{
    private Bitmap ballPacman;
    private Bitmap[] pacman;
    private Bitmap[] deadPacman;

    private Bitmap[] redGhost;
    private Bitmap[] pinkGhost;
    private Bitmap[] blueGhost;
    private Bitmap[] orangeGhost;

    private Bitmap[] frightGhost;
    private Bitmap[] eatenGhost;

    private Bitmap pellet;
    private Bitmap[] powerPellet;
    private Bitmap[] ptEat;
    private Bitmap ptFruit;
    private Bitmap live;

    private Bitmap defaultMaze;
    private Bitmap neumontMaze;

    private final float SCALE_MULTIPLER;


    public BitmapBank(Resources res)
    {
        SCALE_MULTIPLER = AppConstants.isNeumont? 1.25f: 1.5f;

        pacman = new Bitmap[16]; //Doubled size so each frame goes twice
        fillPacmanArray(res);
        for(int i = 0; i < pacman.length; i++) //Scales bitmap sizes
            pacman[i] = Bitmap.createScaledBitmap(pacman[i], (int)(pacman[i].getWidth() * SCALE_MULTIPLER), (int)(pacman[i].getHeight() * SCALE_MULTIPLER), true);

        ballPacman = BitmapFactory.decodeResource(res, R.drawable.pacman0);
        ballPacman = Bitmap.createScaledBitmap(ballPacman, (int)(ballPacman.getWidth() * SCALE_MULTIPLER), (int)(ballPacman.getHeight() * SCALE_MULTIPLER), true);

        deadPacman = new Bitmap[44];
        fillDeadPacmanArray(res);
        for(int i = 0; i < deadPacman.length; i++)
            deadPacman[i] = Bitmap.createScaledBitmap(deadPacman[i], (int)(deadPacman[i].getWidth() * SCALE_MULTIPLER), (int)(deadPacman[i].getHeight() * SCALE_MULTIPLER), true);


        redGhost = new Bitmap[24];
        fillRedGhostArray(res);
        for(int i = 0; i < redGhost.length; i++)
            redGhost[i] = Bitmap.createScaledBitmap(redGhost[i], (int)(redGhost[i].getWidth() * SCALE_MULTIPLER), (int)(redGhost[i].getHeight() * SCALE_MULTIPLER), true);

        pinkGhost = new Bitmap[24];
        fillPinkGhostArray(res);
        for(int i = 0; i < pinkGhost.length; i++)
            pinkGhost[i] = Bitmap.createScaledBitmap(pinkGhost[i], (int)(pinkGhost[i].getWidth() * SCALE_MULTIPLER), (int)(pinkGhost[i].getHeight() * SCALE_MULTIPLER), true);

        blueGhost = new Bitmap[24];
        fillBlueGhostArray(res);
        for(int i = 0; i < blueGhost.length; i++)
            blueGhost[i] = Bitmap.createScaledBitmap(blueGhost[i], (int)(blueGhost[i].getWidth() * SCALE_MULTIPLER), (int)(blueGhost[i].getHeight() * SCALE_MULTIPLER), true);

        orangeGhost = new Bitmap[24];
        fillOrangeGhostArray(res);
        for(int i = 0; i < orangeGhost.length; i++)
            orangeGhost[i] = Bitmap.createScaledBitmap(orangeGhost[i], (int)(orangeGhost[i].getWidth() * SCALE_MULTIPLER), (int)(orangeGhost[i].getWidth() * SCALE_MULTIPLER), true);

        frightGhost = new Bitmap[12];
        fillFrightGhostArray(res);
        for(int i = 0; i < frightGhost.length; i++)
            frightGhost[i] = Bitmap.createScaledBitmap(frightGhost[i], (int)(frightGhost[i].getWidth() * SCALE_MULTIPLER), (int)(frightGhost[i].getHeight() * SCALE_MULTIPLER), true);


        eatenGhost = new Bitmap[4];
        fillEatenGhostArray(res);
        for(int i= 0; i < eatenGhost.length; i++)
            eatenGhost[i] = Bitmap.createScaledBitmap(eatenGhost[i], (int)(eatenGhost[i].getWidth() * SCALE_MULTIPLER), (int)(eatenGhost[i].getHeight() * SCALE_MULTIPLER), true);



        defaultMaze = BitmapFactory.decodeResource(res, R.drawable.pacman_background);
        defaultMaze = Bitmap.createScaledBitmap(defaultMaze, (int)(defaultMaze.getWidth() * SCALE_MULTIPLER), (int)(defaultMaze.getHeight() * SCALE_MULTIPLER), true);

        neumontMaze = BitmapFactory.decodeResource(res, R.drawable.neumont);
        neumontMaze = Bitmap.createScaledBitmap(neumontMaze, (int)(neumontMaze.getWidth() * SCALE_MULTIPLER), (int)(neumontMaze.getHeight() * SCALE_MULTIPLER), true);

        pellet = BitmapFactory.decodeResource(res, R.drawable.pellet);
        pellet = Bitmap.createScaledBitmap(pellet, (int)(pellet.getWidth() * SCALE_MULTIPLER),(int)(pellet.getHeight() * SCALE_MULTIPLER), true);

        powerPellet = new Bitmap[8];
        fillPowerPelletArray(res);

        live = BitmapFactory.decodeResource(res, R.drawable.pacman3);
        live = Bitmap.createScaledBitmap(live, (int)(live.getWidth() * 1.25f), (int)(live.getHeight() * 1.25f), true);

        ptEat = new Bitmap[4];
        ptEat[0] = BitmapFactory.decodeResource(res, R.drawable.pt_eat1);
        ptEat[1] = BitmapFactory.decodeResource(res, R.drawable.pt_eat2);
        ptEat[2] = BitmapFactory.decodeResource(res, R.drawable.pt_eat3);
        ptEat[3] = BitmapFactory.decodeResource(res, R.drawable.pt_eat4);
        for(int i = 0; i < ptEat.length; i++)
            ptEat[i] = Bitmap.createScaledBitmap(ptEat[i], (int)(ptEat[i].getWidth() * SCALE_MULTIPLER),(int)(ptEat[i].getHeight() * SCALE_MULTIPLER), true);

    }

    public Bitmap getPacman(int frame)
    {
        return pacman[frame];
    }

    public Bitmap getBallPacman()
    {
        return ballPacman;
    }

    public Bitmap getDeadPacman(int frame){
        return deadPacman[frame];
    }

    public int getPacmanWidth()
    {
        return pacman[0].getWidth();
    }

    public int getPacmanHeight()
    {
        return pacman[0].getHeight();
    }


    public Bitmap getRedGhost(int frame)
    {
        return redGhost[frame];
    }

    public Bitmap getPinkGhost(int frame)
    {
        return pinkGhost[frame];
    }

    public Bitmap getBlueGhost(int frame)
    {
        return blueGhost[frame];
    }

    public Bitmap getOrangeGhost(int frame){return orangeGhost[frame];}

    public int getGhostWidth()
    {
        return redGhost[0].getWidth();
    }

    public int getGhostHeight()
    {
        return redGhost[0].getHeight();
    }


    public Bitmap getFrightGhost(int frame)
    {
        return frightGhost[frame];
    }

    public Bitmap getEatenGhost(int frame)
    {
        return eatenGhost[frame];
    }

    public Bitmap getPellet()
    {
        return pellet;
    }

    public int getPelletWidth()
    {
        return pellet.getWidth();
    }

    public int getPelletHeight()
    {
        return pellet.getHeight();
    }



    public Bitmap getPowerPellet(int frame)
    {
        return powerPellet[frame];
    }

    public int getPowerPelletWidth()
    {
        return powerPellet[0].getWidth();
    }

    public int getPowerPelletHeight()
    {
        return powerPellet[0].getHeight();
    }

    public Bitmap getEatPTS(int frame)
    {
        return ptEat[frame];
    }

    public Bitmap getLive()
    {
        return live;
    }

    public Bitmap getDefaultMaze()
    {
        return defaultMaze;
    }

    public Bitmap getNeumontMaze(){
        return neumontMaze;
    }

    public int getDefaultMazeWidth(){
        return defaultMaze.getWidth();
    }

    public int getDefaultMazeHeight(){
        return defaultMaze.getHeight();
    }





    private void fillPacmanArray(Resources res)
    {
        pacman[0] = BitmapFactory.decodeResource(res, R.drawable.pacman1);
        pacman[1] = pacman[0];
        pacman[2] = BitmapFactory.decodeResource(res, R.drawable.pacman2);
        pacman[3] = pacman[2];

        pacman[4] = BitmapFactory.decodeResource(res, R.drawable.pacman3);
        pacman[5] = pacman[4];
        pacman[6] = BitmapFactory.decodeResource(res, R.drawable.pacman4);
        pacman[7] = pacman[6];

        pacman[8] = BitmapFactory.decodeResource(res, R.drawable.pacman5);
        pacman[9] = pacman[8];
        pacman[10] = BitmapFactory.decodeResource(res, R.drawable.pacman6);
        pacman[11] = pacman[10];

        pacman[12] = BitmapFactory.decodeResource(res, R.drawable.pacman7);
        pacman[13] = pacman[12];
        pacman[14] = BitmapFactory.decodeResource(res, R.drawable.pacman8);
        pacman[15] = pacman[14];
    }

    private void fillDeadPacmanArray(Resources res)
    {
        deadPacman[0] = BitmapFactory.decodeResource(res, R.drawable.death1);
        deadPacman[1] = deadPacman[0];
        deadPacman[2] = deadPacman[0];
        deadPacman[3] = deadPacman[0];
        deadPacman[4] = BitmapFactory.decodeResource(res, R.drawable.death2);
        deadPacman[5] = deadPacman[4];
        deadPacman[6] = deadPacman[4];
        deadPacman[7] = deadPacman[4];
        deadPacman[8] = BitmapFactory.decodeResource(res, R.drawable.death3);
        deadPacman[9] = deadPacman[8];
        deadPacman[10] = deadPacman[8];
        deadPacman[11] = deadPacman[8];
        deadPacman[12] = BitmapFactory.decodeResource(res, R.drawable.death4);
        deadPacman[13] = deadPacman[12];
        deadPacman[14] = deadPacman[12];
        deadPacman[15] = deadPacman[12];
        deadPacman[16] = BitmapFactory.decodeResource(res, R.drawable.death5);
        deadPacman[17] = deadPacman[16];
        deadPacman[18] = deadPacman[16];
        deadPacman[19] = deadPacman[16];
        deadPacman[20] = BitmapFactory.decodeResource(res, R.drawable.death6);
        deadPacman[21] = deadPacman[20];
        deadPacman[22] = deadPacman[20];
        deadPacman[23] = deadPacman[20];
        deadPacman[24] = BitmapFactory.decodeResource(res, R.drawable.death7);
        deadPacman[25] = deadPacman[24];
        deadPacman[26] = deadPacman[24];
        deadPacman[27] = deadPacman[24];
        deadPacman[28] = BitmapFactory.decodeResource(res, R.drawable.death8);
        deadPacman[29] = deadPacman[28];
        deadPacman[30] = deadPacman[28];
        deadPacman[31] = deadPacman[28];
        deadPacman[32] = BitmapFactory.decodeResource(res, R.drawable.death9);
        deadPacman[33] = deadPacman[32];
        deadPacman[34] = deadPacman[32];
        deadPacman[35] = deadPacman[32];
        deadPacman[36] = BitmapFactory.decodeResource(res, R.drawable.death10);
        deadPacman[37] = deadPacman[36];
        deadPacman[38] = deadPacman[36];
        deadPacman[39] = deadPacman[36];
        deadPacman[40] = BitmapFactory.decodeResource(res, R.drawable.death11);
        deadPacman[41] = deadPacman[40];
        deadPacman[42] = deadPacman[40];
        deadPacman[43] = deadPacman[40];
    }

    private void fillRedGhostArray(Resources res)
    {
        redGhost[0] = BitmapFactory.decodeResource(res, R.drawable.redghost1);
        redGhost[1] = redGhost[0];
        redGhost[2] = redGhost[0];
        redGhost[3] = BitmapFactory.decodeResource(res, R.drawable.redghost2);
        redGhost[4] = redGhost[3];
        redGhost[5] = redGhost[3];
        redGhost[6] = BitmapFactory.decodeResource(res, R.drawable.redghost3);
        redGhost[7] = redGhost[6];
        redGhost[8] = redGhost[6];
        redGhost[9] = BitmapFactory.decodeResource(res, R.drawable.redghost4);
        redGhost[10] = redGhost[9];
        redGhost[11] = redGhost[9];
        redGhost[12] = BitmapFactory.decodeResource(res, R.drawable.redghost5);
        redGhost[13] = redGhost[12];
        redGhost[14] = redGhost[12];
        redGhost[15] = BitmapFactory.decodeResource(res, R.drawable.redghost6);
        redGhost[16] = redGhost[15];
        redGhost[17] = redGhost[15];
        redGhost[18] = BitmapFactory.decodeResource(res, R.drawable.redghost7);
        redGhost[19] = redGhost[18];
        redGhost[20] = redGhost[18];
        redGhost[21] = BitmapFactory.decodeResource(res, R.drawable.redghost8);
        redGhost[22] = redGhost[21];
        redGhost[23] = redGhost[21];
    }

    private void fillPinkGhostArray(Resources res)
    {
        pinkGhost[0] = BitmapFactory.decodeResource(res, R.drawable.pinkghost1);
        pinkGhost[1] = pinkGhost[0];
        pinkGhost[2] = pinkGhost[0];
        pinkGhost[3] = BitmapFactory.decodeResource(res, R.drawable.pinkghost2);
        pinkGhost[4] = pinkGhost[3];
        pinkGhost[5] = pinkGhost[3];
        pinkGhost[6] = BitmapFactory.decodeResource(res, R.drawable.pinkghost3);
        pinkGhost[7] = pinkGhost[6];
        pinkGhost[8] = pinkGhost[6];
        pinkGhost[9] = BitmapFactory.decodeResource(res, R.drawable.pinkghost4);
        pinkGhost[10] = pinkGhost[9];
        pinkGhost[11] = pinkGhost[9];
        pinkGhost[12] = BitmapFactory.decodeResource(res, R.drawable.pinkghost5);
        pinkGhost[13] = pinkGhost[12];
        pinkGhost[14] = pinkGhost[12];
        pinkGhost[15] = BitmapFactory.decodeResource(res, R.drawable.pinkghost6);
        pinkGhost[16] = pinkGhost[15];
        pinkGhost[17] = pinkGhost[15];
        pinkGhost[18] = BitmapFactory.decodeResource(res, R.drawable.pinkghost7);
        pinkGhost[19] = pinkGhost[18];
        pinkGhost[20] = pinkGhost[18];
        pinkGhost[21] = BitmapFactory.decodeResource(res, R.drawable.pinkghost8);
        pinkGhost[22] = pinkGhost[21];
        pinkGhost[23] = pinkGhost[21];
    }

    private void fillBlueGhostArray(Resources res)
    {
        blueGhost[0] = BitmapFactory.decodeResource(res, R.drawable.blueghost1);
        blueGhost[1] = blueGhost[0];
        blueGhost[2] = blueGhost[0];
        blueGhost[3] = BitmapFactory.decodeResource(res, R.drawable.blueghost2);
        blueGhost[4] = blueGhost[3];
        blueGhost[5] = blueGhost[3];
        blueGhost[6] = BitmapFactory.decodeResource(res, R.drawable.blueghost3);
        blueGhost[7] = blueGhost[6];
        blueGhost[8] = blueGhost[6];
        blueGhost[9] = BitmapFactory.decodeResource(res, R.drawable.blueghost4);
        blueGhost[10] = blueGhost[9];
        blueGhost[11] = blueGhost[9];
        blueGhost[12] = BitmapFactory.decodeResource(res, R.drawable.blueghost5);
        blueGhost[13] = blueGhost[12];
        blueGhost[14] = blueGhost[12];
        blueGhost[15] = BitmapFactory.decodeResource(res, R.drawable.blueghost6);
        blueGhost[16] = blueGhost[15];
        blueGhost[17] = blueGhost[15];
        blueGhost[18] = BitmapFactory.decodeResource(res, R.drawable.blueghost7);
        blueGhost[19] = blueGhost[18];
        blueGhost[20] = blueGhost[18];
        blueGhost[21] = BitmapFactory.decodeResource(res, R.drawable.blueghost8);
        blueGhost[22] = blueGhost[21];
        blueGhost[23] = blueGhost[21];
    }

    private void fillOrangeGhostArray(Resources res)
    {
        orangeGhost[0] = BitmapFactory.decodeResource(res, R.drawable.orangeghost1);
        orangeGhost[1] = orangeGhost[0];
        orangeGhost[2] = orangeGhost[0];
        orangeGhost[3] = BitmapFactory.decodeResource(res, R.drawable.orangeghost2);
        orangeGhost[4] = orangeGhost[3];
        orangeGhost[5] = orangeGhost[3];
        orangeGhost[6] = BitmapFactory.decodeResource(res, R.drawable.orangeghost3);
        orangeGhost[7] = orangeGhost[6];
        orangeGhost[8] = orangeGhost[6];
        orangeGhost[9] = BitmapFactory.decodeResource(res, R.drawable.orangeghost4);
        orangeGhost[10] = orangeGhost[9];
        orangeGhost[11] = orangeGhost[9];
        orangeGhost[12] = BitmapFactory.decodeResource(res, R.drawable.orangeghost5);
        orangeGhost[13] = orangeGhost[12];
        orangeGhost[14] = orangeGhost[12];
        orangeGhost[15] = BitmapFactory.decodeResource(res, R.drawable.orangeghost6);
        orangeGhost[16] = orangeGhost[15];
        orangeGhost[17] = orangeGhost[15];
        orangeGhost[18] = BitmapFactory.decodeResource(res, R.drawable.orangeghost7);
        orangeGhost[19] = orangeGhost[18];
        orangeGhost[20] = orangeGhost[18];
        orangeGhost[21] = BitmapFactory.decodeResource(res, R.drawable.orangeghost8);
        orangeGhost[22] = orangeGhost[21];
        orangeGhost[23] = orangeGhost[21];
    }

    private void fillFrightGhostArray(Resources res)
    {
        frightGhost[0] = BitmapFactory.decodeResource(res, R.drawable.frightghost1);
        frightGhost[1] = frightGhost[0];
        frightGhost[2] = frightGhost[0];
        frightGhost[3] = BitmapFactory.decodeResource(res, R.drawable.frightghost2);
        frightGhost[4] = frightGhost[3];
        frightGhost[5] = frightGhost[3];
        frightGhost[6] = BitmapFactory.decodeResource(res, R.drawable.frightghost3);
        frightGhost[7] = frightGhost[6];
        frightGhost[8] = frightGhost[6];
        frightGhost[9] = BitmapFactory.decodeResource(res, R.drawable.frightghost4);
        frightGhost[10] = frightGhost[9];
        frightGhost[11] = frightGhost[9];

    }

    private void fillEatenGhostArray(Resources res)
    {
        eatenGhost[0] = BitmapFactory.decodeResource(res, R.drawable.eaten1);
        eatenGhost[1] = BitmapFactory.decodeResource(res, R.drawable.eaten2);
        eatenGhost[2] = BitmapFactory.decodeResource(res, R.drawable.eaten3);
        eatenGhost[3] = BitmapFactory.decodeResource(res, R.drawable.eaten4);
    }

    private void fillPowerPelletArray(Resources res)
    {
        powerPellet[0] = BitmapFactory.decodeResource(res, R.drawable.power_pellet);
        powerPellet[0] = Bitmap.createScaledBitmap(powerPellet[0], (int)(powerPellet[0].getWidth() * SCALE_MULTIPLER), (int)(powerPellet[0].getHeight() * SCALE_MULTIPLER), true);
        powerPellet[1] = powerPellet[0];
        powerPellet[2] = powerPellet[0];
        powerPellet[3] = powerPellet[0];

        powerPellet[4] = BitmapFactory.decodeResource(res, R.drawable.power_pellet2);
        powerPellet[4] = Bitmap.createScaledBitmap(powerPellet[4], (int)(powerPellet[4].getWidth() * SCALE_MULTIPLER), (int)(powerPellet[4].getHeight() * SCALE_MULTIPLER), true);
        powerPellet[5] = powerPellet[4];
        powerPellet[6] = powerPellet[4];
        powerPellet[7] = powerPellet[4];
    }
}
