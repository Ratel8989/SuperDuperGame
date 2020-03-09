package com.example.superdupergame.anvil.models;

import com.example.superdupergame.R;

import java.util.Random;
import java.util.prefs.AbstractPreferences;

public class Anvil {
    private int xPosition;
    private int yPosition;
    private int speed;
    private boolean isActive;
    public Anvil(int startingPos) {
        startingPos = startingPos % 10;
        this.xPosition = AppConstants.playableX + (AppConstants.getBitmapBank().getAnvilWidth() * startingPos);
        this.yPosition = AppConstants.playabley;
        this.speed = AppConstants.startingAnvilSpeed;
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getX() {
        return xPosition;
    }

    public void setX(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getY() {
        return yPosition;
    }

    public void setY(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
