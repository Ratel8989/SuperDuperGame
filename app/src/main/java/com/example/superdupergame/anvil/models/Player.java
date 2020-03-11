package com.example.superdupergame.anvil.models;

public class Player {
    private int xPosition;
    private int yPosition;
    private int health;
    private int squishFrame;


    public Player()
    {
        health = 3;
        //Set Starting XPosition
        xPosition = (AppConstants.getBitmapBank().getTestAreaWidth() / 2) + AppConstants.playableX - (AppConstants.getBitmapBank().getPlayerWidth() / 2);
        //Set Starting YPosition
        yPosition = AppConstants.playabley + AppConstants.getBitmapBank().getTestAreaHeight() - AppConstants.getBitmapBank().getPlayerHeight();
    }

    public int getX() {
        return xPosition;
    }

    public void setX(int xPosition) {
        this.xPosition = xPosition;

    }

    public void updateY(){
        yPosition = AppConstants.playabley + AppConstants.getBitmapBank().getTestAreaHeight() - AppConstants.getBitmapBank().getPlayerHeight();
    }

    public int getY() {
        return yPosition;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSquishFrame() {
        return squishFrame;
    }

    public void setSquishFrame(int squishFrame) {
        this.squishFrame = squishFrame;
    }
}
