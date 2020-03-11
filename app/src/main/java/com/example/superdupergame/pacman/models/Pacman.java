package com.example.superdupergame.pacman.models;

public class Pacman
{
    private int pacmanX, pacmanY;
    private int currentFrame;
    private int lives;
    private int pacmanRow, pacmanCol;


    private final int STARTING_X;
    private final int STARTING_Y;

    public Pacman()
    {
        //42 = AppConstants.getGameEngine().getMaze().TILE_SIZE
        //35 = AppConstants.getGameEngine().getMaze().TILE_SIZE
        if(AppConstants.isNeumont)
        {
            STARTING_X = (25 * 35);
            STARTING_Y = (14 * 35) + 20;
        }else {
            STARTING_X = (13 * 42);
            STARTING_Y = (23 * 42) - 10;
        }
        pacmanX = STARTING_X;
        pacmanY = STARTING_Y;
        currentFrame = 0;
        lives = 3;
        updatePacmanOnGrid();
    }

    public int getX() {
        return pacmanX;
    }

    public void setX(int pacmanX) {
        this.pacmanX = pacmanX;
        this.pacmanY = pacmanRow * AppConstants.getTileSize() - AppConstants.getTileSize() + (AppConstants.isNeumont ? 10 : -10);
    }

    public int getY() {
        return pacmanY;
    }

    public void setY(int pacmanY) {
        this.pacmanY = pacmanY;
        this.pacmanX = pacmanCol * AppConstants.getTileSize() - 10;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void updatePacmanOnGrid()
    {
            int x = pacmanX + AppConstants.getBitmapBank().getPacmanWidth() / 2;
            int y = pacmanY + AppConstants.getBitmapBank().getPacmanHeight() / 2 + (AppConstants.isNeumont ? 0: AppConstants.getTileSize());
            //This last part is because of invisible tile row above top of maze

            pacmanRow = (int) Math.ceil(y / AppConstants.getTileSize());
            pacmanCol = (int) Math.ceil(x / AppConstants.getTileSize());
    }

    public int getRow()
    {
        return pacmanRow;
    }

    public int getColumn()
    {
        return pacmanCol;
    }

    public void resetPosition()
    {
        pacmanX = STARTING_X;
        pacmanY = STARTING_Y;
        updatePacmanOnGrid();
        AppConstants.pacmanDirection = 0;
    }
}
