package com.example.superdupergame.pacman.models;

import android.content.Context;
import android.media.SoundPool;

import com.example.superdupergame.R;

public class SoundBank
{
    private SoundPool soundPool;
    private int beginningTuneID;
    private int sirenID, sirenPlayID;
    private int powerPelletID, powerPelletPlayID;
    private int ghostRetreatID, ghostRetreatPlayID;

    private int eatGhostID;
    private int[] munchID;
    private int[] deathID;
    private int deathPlayID;

    //Boolean to keep track of whether munch1 sound or munch2 should play
    private boolean isMunch1;

    //To make sure sounds don't repeat or overlap
    private boolean playedSirenSound = false;
    private boolean playedGhostRetreatSound = false;
    private boolean playedDeathSound = false;
    private boolean playedPowerPelletSound = false;

    public SoundBank(Context context)
    {
        SoundPool.Builder sb = new SoundPool.Builder();
        sb.setMaxStreams(4);
        soundPool = sb.build();

        beginningTuneID = soundPool.load(context, R.raw.pacman_beginning, 1);
        sirenID = soundPool.load(context, R.raw.pacman_siren, 1);
        powerPelletID = soundPool.load(context, R.raw.pacman_powerpellet, 1);
        ghostRetreatID = soundPool.load(context, R.raw.pacman_ghostretreat, 1);

        eatGhostID = soundPool.load(context, R.raw.pacman_eatghost, 1);

        isMunch1 = true;
        munchID = new int[2];
        munchID[0] = soundPool.load(context, R.raw.pacman_munch1, 1);
        munchID[1] = soundPool.load(context, R.raw.pacman_munch2, 1);

        deathID = new int[2];
        deathID[0] = soundPool.load(context, R.raw.pacman_death1, 1);
        deathID[1] = soundPool.load(context, R.raw.pacman_death2, 1);

    }

    public void playBeginningTune()
    {
        soundPool.play(beginningTuneID, 1, 1, 1, 0, 1);
    }


    public void playSiren()
    {
        if(!playedSirenSound)
            sirenPlayID = soundPool.play(sirenID, 1, 1, 1, -1, 1 );
        playedSirenSound = true;
    }

    public void stopSiren()
    {
        soundPool.stop(sirenPlayID);
        playedSirenSound = false;
    }

    public void playMunch()
    {
        if(isMunch1)
            soundPool.play(munchID[0], 1, 1, 1, 0, 1);
        else
            soundPool.play(munchID[1], 1, 1, 1, 0, 1);
        isMunch1 = !isMunch1;
    }

    public void playEatGhost()
    {
        soundPool.play(eatGhostID, 1, 1, 1, 0, 1);
    }


    public void playGhostRetreat()
    {
        if(!playedGhostRetreatSound)
            ghostRetreatPlayID = soundPool.play(ghostRetreatID, 1,1, 1, -1, 1);
        playedGhostRetreatSound = true;
    }

    public void stopGhostRetreat()
    {
        soundPool.stop(ghostRetreatPlayID);
        playedGhostRetreatSound = false;
    }

    public void playDeath()
    {
        if(!playedDeathSound)
            deathPlayID = soundPool.play(deathID[0], 1, 1, 1, 0, 1);

        //Do funny stuff if neumont
        if(!AppConstants.isNeumont)
            playedDeathSound = true;
    }

    public void finishDeath()
    {
        soundPool.stop(deathPlayID);
        soundPool.play(deathID[1], 1, 1, 1, 0, 1);
        playedDeathSound = false;
    }

    public void playPowerPellet()
    {
        if(!playedPowerPelletSound)
            powerPelletPlayID = soundPool.play(powerPelletID, 1, 1, 1, -1, 1);
        playedPowerPelletSound = true;
    }

    public void pausePowerPellet()
    {
        soundPool.stop(powerPelletPlayID);
        playedPowerPelletSound = false;
    }

    //To Stop SoundPool for next time playing
    public void releaseSoundPool()
    {
        soundPool.release();
    }

}
