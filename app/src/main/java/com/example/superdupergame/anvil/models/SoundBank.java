package com.example.superdupergame.anvil.models;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.superdupergame.R;

import java.net.PortUnreachableException;

public class SoundBank {
    private MediaPlayer hit;
    private MediaPlayer background01;
    private MediaPlayer background02;
    private MediaPlayer background03;
    private MediaPlayer background04;
    private MediaPlayer levelUpSound;
    private MediaPlayer moveLeftSound;
    private MediaPlayer moveRightSound;


    public SoundBank(Context context) {
        hit = MediaPlayer.create(context, R.raw.terrible_sound);
        hit.setVolume(1f,1f);
        background01 = MediaPlayer.create(context, R.raw.anvil_music_meme03);
        background01.setLooping(true);
        background02 = MediaPlayer.create(context, R.raw.amvil_music_meme2);

    }
    //.setLooping(true)

    public void playHit(){
        hit.start();
    }

    public void playBackground01(){ background01.start(); }

    public void stopBackground01(){background01.stop();}
}
