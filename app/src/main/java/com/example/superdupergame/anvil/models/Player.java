package com.example.superdupergame.anvil.models;

public class Player {
    private int xPosition;
    private int yPosition;
    private int velocity;
    private int health;

    public Player()
    {
        health = 3;
        //Set Starting XPosition
        //Set Starting YPosition

        velocity = 0;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
