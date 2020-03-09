package com.example.superdupergame.anvil.models;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.superdupergame.R;

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
        
    }
    //.setLooping(true)

    public void playHit(){
        hit.start();
    }
}
