package com.example.superdupergame.duck.models;

public class AppConstant {

    private int spitSpeed = 80;
    private int gooseSpeed = 22;
    private int duckVerticalSpeed = 30;
    private int backgroundSpeed = 15;
    private float duckImgScale = 1.6f;
    private float gooseImgScale = 2.5f;
    private int perUpdateCounter = 0;

    private static int amountOfFramesForUpdate = 180;
    private static int frameBackgroundSpeed = 0;
    private static int frameGooseSpeed = 0;
    private static int frameDuckVerticalSpeed = 0;
    private static int frameSpitSpeed = 0;

    public int getSpitSpeed() {
        return spitSpeed;
    }

    public void setSpitSpeed(int spitSpeed) {
        this.spitSpeed = spitSpeed;
    }

    public int getGooseSpeed() {
        return gooseSpeed;
    }

    public void setGooseSpeed(int gooseSpeed) {
        this.gooseSpeed = gooseSpeed;
    }

    public int getDuckVerticalSpeed() {
        return duckVerticalSpeed;
    }

    public void setDuckVerticalSpeed(int duckVerticalSpeed) {
        this.duckVerticalSpeed = duckVerticalSpeed;
    }

    public int getBackgroundSpeed() {
        return backgroundSpeed;
    }

    public void setBackgroundSpeed(int backgroundSpeed) {
        this.backgroundSpeed = backgroundSpeed;
    }

    public float getDuckImgScale() {
        return duckImgScale;
    }

    public void setDuckImgScale(float duckImgScale) {
        this.duckImgScale = duckImgScale;
    }

    public float getGooseImgScale() {
        return gooseImgScale;
    }

    public void setGooseImgScale(float gooseImgScale) {
        this.gooseImgScale = gooseImgScale;
    }

    public int getPerUpdateCounter() {
        return perUpdateCounter;
    }

    public void setPerUpdateCounter(int perUpdateCounter) {
        this.perUpdateCounter = perUpdateCounter;
    }

    public static int getAmountOfFramesForUpdate() {
        return amountOfFramesForUpdate;
    }

    public static void setAmountOfFramesForUpdate(int amountOfFramesForUpdate) {
        AppConstant.amountOfFramesForUpdate = amountOfFramesForUpdate;
    }

    public static int getFrameBackgroundSpeed() {
        return frameBackgroundSpeed;
    }

    public static void setFrameBackgroundSpeed(int frameBackgroundSpeed) {
        AppConstant.frameBackgroundSpeed = frameBackgroundSpeed;
    }

    public static int getFrameGooseSpeed() {
        return frameGooseSpeed;
    }

    public static void setFrameGooseSpeed(int frameGooseSpeed) {
        AppConstant.frameGooseSpeed = frameGooseSpeed;
    }

    public static int getFrameDuckVerticalSpeed() {
        return frameDuckVerticalSpeed;
    }

    public static void setFrameDuckVerticalSpeed(int frameDuckVerticalSpeed) {
        AppConstant.frameDuckVerticalSpeed = frameDuckVerticalSpeed;
    }

    public static int getFrameSpitSpeed() {
        return frameSpitSpeed;
    }

    public static void setFrameSpitSpeed(int frameSpitSpeed) {
        AppConstant.frameSpitSpeed = frameSpitSpeed;
    }

    public void modeEgg(){
        setFrameBackgroundSpeed(2);
        setFrameGooseSpeed(3);
        setFrameDuckVerticalSpeed(3);
        setFrameSpitSpeed(3);
        setAmountOfFramesForUpdate(120); // Increase every 2 seconds
    }

    public void modeDuckling(){
        setFrameBackgroundSpeed(8);
        setFrameGooseSpeed(5);
        setFrameDuckVerticalSpeed(10);
        setFrameSpitSpeed(8);
        setAmountOfFramesForUpdate(180); // Increase every 3 seconds
    }

    public void modeAdult(){
        setFrameBackgroundSpeed(10);
        setFrameGooseSpeed(8);
        setFrameDuckVerticalSpeed(15);
        setFrameSpitSpeed(10);
        setAmountOfFramesForUpdate(240); // Increase every 4 seconds
    }
}
