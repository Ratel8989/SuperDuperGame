package com.example.superdupergame.pacman.models;

public abstract class Maze
{
    protected Tile[][] grid;
    private int pelletCount;

    public final int ROWS;
    public final int COLS;
    public final int GHOST_HOME_ROW;
    public final int GHOST_HOME_COL;
    public final int TELEPORT_LEFTX;
    public final int TELEPORT_RIGHTX;


    protected Maze(int rows, int cols, int pelletCount)
    {
        ROWS = rows;
        COLS = cols;
        this.pelletCount = pelletCount;

        if(AppConstants.isNeumont)
        {
            TELEPORT_LEFTX = 10;
            TELEPORT_RIGHTX = 2300;
            GHOST_HOME_ROW = 4;
            GHOST_HOME_COL = 26;
        }else
            {
            TELEPORT_LEFTX = 10;
            TELEPORT_RIGHTX = 1090;
            GHOST_HOME_ROW = 12;
            GHOST_HOME_COL = 12;
        }
    }

    public boolean checkForPellets(int row, int col)
    {
        if(grid[row][col] == Tile.P) {
            grid[row][col] = Tile.E;
            pelletCount--;
            AppConstants.score += 10;
            AppConstants.getSoundBank().playMunch();
        }
        else if(grid[row][col] == Tile.R)
        {
            grid[row][col] = Tile.E;
            pelletCount--;
            AppConstants.score += 50;
            AppConstants.getSoundBank().playMunch();
            AppConstants.getSoundBank().stopSiren();
            AppConstants.getSoundBank().playPowerPellet();
            return true;
        }
        return false;
    }

    protected abstract void initializeMaze();

    public Tile getTile(int row, int col)
    {
        return grid[row][col];
    }

    public boolean isLeftTileWall(int row, int col)
    {
        return grid[row][col - 1] == Tile.W;
    }

    public boolean isRightTileWall(int row, int col)
    {
        return grid[row][col + 1] == Tile.W;
    }

    public boolean isTopTileWall(int row, int col)
    {
        return grid[row - 1][col] == Tile.W;
    }

    public boolean isBottomTileWall(int row, int col)
    {
        return grid[row + 1][col] == Tile.W;
    }

    public int getPelletCount()
    {
        return pelletCount;
    }
}
