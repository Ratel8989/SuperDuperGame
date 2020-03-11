package com.example.superdupergame.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;

import com.example.superdupergame.pacman.models.*;

public class GameEngine
{
    /**
     * <pre>
     * -1 - Not Started Yet
     *  0 - Start Up
     *  1 - Playing
     *  2 - Eaten Ghost
     *  3 - Dying
     *  4 - Game Over
     *  5 - Leaving
     *  </pre>
     */
    private int gameState;
    private Pacman pacman;
    private Ghost redGhost, pinkGhost, blueGhost, orangeGhost;
    private Maze maze;

    private Paint scorePaint;
    private Paint readyPaint;

    private boolean hasEatenPowerUp;
    private boolean startBlinkingGhost;

    private int swapModeCount;
    private boolean stopUntilSwap;
    private int eatCount;


    private long frightTimer;
    private long chaseTimer;
    private long scatterTimer;

    //Setup Timers
    private long firstStartTimer;
    private long startUpTimer;

    //Ghost Eaten Pause Timer
    private long eatTimer;

    //Reset Timer
    private long resetTimer;

    public GameEngine()
    {
        if(AppConstants.isNeumont)
            maze = new NeumontMaze();
        else
            maze = new DefaultMaze();

        pacman = new Pacman();
        redGhost = new Ghost(); //Blinky
        pinkGhost = new Ghost(); //Pinky
        blueGhost = new Ghost(); //Inky
        orangeGhost = new Ghost(); //Clyde

        hasEatenPowerUp = false;
        startBlinkingGhost = false;
        swapModeCount = 0;
        stopUntilSwap = false;

        gameState = -1;

        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40f);
        scorePaint.setTypeface(AppConstants.typeface);

        readyPaint = new Paint();
        readyPaint.setColor(Color.YELLOW);
        readyPaint.setTextSize(40f);
        readyPaint.setTypeface(AppConstants.typeface);

        AppConstants.getSoundBank().playBeginningTune();
        firstStartTimer = SystemClock.uptimeMillis() + 2000;
        eatCount = -1;
    }

    public void updateAndDrawPacman(Canvas canvas)
    {
        int currentFrame = pacman.getCurrentFrame();

        if(gameState == 1)
        {
            //Movement Logic and Frame Logic
            switch (AppConstants.pacmanDirection) {
                case 1: //Up Direction
                    if (!maze.isTopTileWall(pacman.getRow(), pacman.getColumn())) {
                        pacman.setY(pacman.getY() - AppConstants.pacmanSpeed);
                        currentFrame = currentFrame + 1 > 11 || currentFrame < 8 ? 8 : currentFrame + 1;
                    }
                    break;
                case 2: //Right Direction
                    if (!maze.isRightTileWall(pacman.getRow(), pacman.getColumn())) {
                        pacman.setX(pacman.getX() + AppConstants.pacmanSpeed);
                        currentFrame = currentFrame + 1 > 3 ? 0 : currentFrame + 1;
                    }
                    break;
                case 3: //Down Direction
                    if (!maze.isBottomTileWall(pacman.getRow(), pacman.getColumn())) {
                        pacman.setY(pacman.getY() + AppConstants.pacmanSpeed);
                        currentFrame = currentFrame + 1 > 15 || currentFrame < 12 ? 12 : currentFrame + 1;
                    }
                    break;
                case 4: //Left Direction
                    if (!maze.isLeftTileWall(pacman.getRow(), pacman.getColumn())) {
                        pacman.setX(pacman.getX() - AppConstants.pacmanSpeed);
                        currentFrame = currentFrame + 1 > 7 || currentFrame < 4 ? 4 : currentFrame + 1;
                    }
                    break;
                default:
                    AppConstants.pacmanDirection = 0;
            }

            //Side Teleport Logic
            if(pacman.getX() > maze.TELEPORT_RIGHTX)
            {
                pacman.setX(maze.TELEPORT_LEFTX);
            }else if(pacman.getX() < maze.TELEPORT_LEFTX)
            {
                pacman.setX(maze.TELEPORT_RIGHTX);
            }

            //Translates Pacman's position on screen to position in array
            pacman.updatePacmanOnGrid();

            canvas.drawBitmap(AppConstants.getBitmapBank().getPacman(currentFrame), pacman.getX()  + AppConstants.mazeX, pacman.getY() + AppConstants.mazeY, null);
        }
        else if(gameState == 3)
        {
            if(SystemClock.uptimeMillis() < resetTimer + 1500)
                canvas.drawBitmap(AppConstants.getBitmapBank().getPacman(currentFrame), pacman.getX()  + AppConstants.mazeX, pacman.getY() + AppConstants.mazeY, null);
            else {
                AppConstants.getSoundBank().playDeath();
                if (currentFrame < 43)
                    currentFrame++;
                canvas.drawBitmap(AppConstants.getBitmapBank().getDeadPacman(currentFrame), pacman.getX() + AppConstants.mazeX, pacman.getY() + AppConstants.mazeY, null);
            }
        }
        else if(gameState == 0 || gameState == -1)
        {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBallPacman(), pacman.getX() + AppConstants.mazeX, pacman.getY() + AppConstants.mazeY, null);
        }
        pacman.setCurrentFrame(currentFrame);
    }


    public void updateAndDrawRedGhost(Canvas canvas) {
        int currentFrame = redGhost.getCurrentFrame();

        if (gameState == 1) {
            //Movement
            switch (redGhost.getState()) {
                case Ghost.SCATTER_STATE:
                    //Turn 180 when entering state from another
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;
                    redGhost.trackTarget(maze, 0, maze.COLS);
                    break;

                case Ghost.CHASE_STATE:
                    //Turn 180 when entering state (will do in timer)
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;
                    redGhost.trackTarget(maze, pacman.getRow(), pacman.getColumn());
                    break;

                case Ghost.FRIGHTENED_STATE:
                    //Turn 180 when entering state
                    AppConstants.ghostSpeed = AppConstants.SLOW_SPEED;
                    redGhost.targetRandom(maze);
                    break;

                case Ghost.EATEN_STATE:
                    AppConstants.ghostSpeed = AppConstants.FAST_SPEED;
                    redGhost.trackTarget(maze, maze.GHOST_HOME_ROW, maze.GHOST_HOME_COL);
                    if (redGhost.getRow() == maze.GHOST_HOME_ROW && redGhost.getColumn() == maze.GHOST_HOME_COL) {
                        if(hasEatenPowerUp)
                            AppConstants.getSoundBank().playPowerPellet();
                        else
                            AppConstants.getSoundBank().playSiren();
                        AppConstants.getSoundBank().stopGhostRetreat();
                        redGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE : Ghost.CHASE_STATE);
                    }
                    break;
            }
            redGhost.updateGhostOnGrid();

            //Side Teleport Logic
            checkAndDoGhostSideTeleporter(redGhost);


            //Collision Logic
            checkAndDoCollision(redGhost);


            //Animation Logic
            redGhost.setCurrentFrame(calculateNextFrame(redGhost));
            switch(redGhost.getState())
            {
                case Ghost.SCATTER_STATE:
                case Ghost.CHASE_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getRedGhost(redGhost.getCurrentFrame()), redGhost.getX() + AppConstants.mazeX, redGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.FRIGHTENED_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(redGhost.getCurrentFrame()), redGhost.getX() + AppConstants.mazeX, redGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.EATEN_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(redGhost.getCurrentFrame()), redGhost.getX() + AppConstants.mazeX, redGhost.getY() + AppConstants.mazeY, null);
                    break;
            }

        } else if (gameState == 3) {
            if (SystemClock.uptimeMillis() < resetTimer + 1500) {
                currentFrame = (currentFrame + 1) % 6 == 0 ? currentFrame - 5 : currentFrame + 1;
                canvas.drawBitmap(AppConstants.getBitmapBank().getRedGhost(currentFrame), redGhost.getX() + AppConstants.mazeX, redGhost.getY() + AppConstants.mazeY, null);
                redGhost.setCurrentFrame(currentFrame);
            }
        } else if (gameState == 0 || gameState == 2) {
            if(!redGhost.isBeingEaten())
            {
                if(redGhost.getState() == Ghost.FRIGHTENED_STATE) {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(currentFrame), redGhost.getX() + AppConstants.mazeX, redGhost.getY() + AppConstants.mazeY, null);
                }
                else if(redGhost.getState() == Ghost.EATEN_STATE) {
                    if(currentFrame > 3)
                        currentFrame = 0;
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(currentFrame), redGhost.getX() + AppConstants.mazeX, redGhost.getY() + AppConstants.mazeY, null);
                }
                else {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getRedGhost(currentFrame), redGhost.getX() + AppConstants.mazeX, redGhost.getY() + AppConstants.mazeY, null);
                }
            }
            redGhost.setCurrentFrame(currentFrame);
        }


    }

    public void updateAndDrawPinkGhost(Canvas canvas)
    {
        int currentFrame = pinkGhost.getCurrentFrame();

        if(gameState == 1)
        {
            //Movement
            switch(pinkGhost.getState())
            {
                case Ghost.SCATTER_STATE:
                    //Turn 180 when entering state from another
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;
                    pinkGhost.trackTarget(maze, 0, 0);
                    break;

                case Ghost.CHASE_STATE:
                    //Turn 180 when entering state (will do in timer)
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;

                    switch(AppConstants.pacmanDirection)
                    {
                        case AppConstants.UP_DIRECTION:
                            pinkGhost.trackTarget(maze, pacman.getRow() - 4 , pacman.getColumn() - 4);
                            break;
                        case AppConstants.RIGHT_DIRECTION:
                            pinkGhost.trackTarget(maze, pacman.getRow(), pacman.getColumn() + 4);
                            break;
                        case AppConstants.DOWN_DIRECTION:
                            pinkGhost.trackTarget(maze, pacman.getRow() + 4, pacman.getColumn());
                            break;
                        case AppConstants.LEFT_DIRECTION:
                            pinkGhost.trackTarget(maze, pacman.getRow(), pacman.getColumn() - 4);
                            break;
                    }
                    break;

                case Ghost.FRIGHTENED_STATE:
                    //Turn 180 when entering state
                    AppConstants.ghostSpeed = AppConstants.SLOW_SPEED;
                    pinkGhost.targetRandom(maze);
                    break;

                case Ghost.EATEN_STATE:
                    AppConstants.ghostSpeed = AppConstants.FAST_SPEED;
                    pinkGhost.trackTarget(maze, maze.GHOST_HOME_ROW, maze.GHOST_HOME_COL);
                    if(pinkGhost.getRow() == maze.GHOST_HOME_ROW && pinkGhost.getColumn() == maze.GHOST_HOME_COL){
                        if(hasEatenPowerUp)
                            AppConstants.getSoundBank().playPowerPellet();
                        else
                            AppConstants.getSoundBank().playSiren();
                        AppConstants.getSoundBank().stopGhostRetreat();
                        pinkGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE: Ghost.CHASE_STATE);
                    }
                    break;
            }
            pinkGhost.updateGhostOnGrid();

            //Side Teleport Logic
            checkAndDoGhostSideTeleporter(pinkGhost);



            //Collision Logic
            checkAndDoCollision(pinkGhost);


            //Animation Logic
            pinkGhost.setCurrentFrame(calculateNextFrame(pinkGhost));
            switch(pinkGhost.getState())
            {
                case Ghost.SCATTER_STATE:
                case Ghost.CHASE_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getPinkGhost(pinkGhost.getCurrentFrame()), pinkGhost.getX() + AppConstants.mazeX, pinkGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.FRIGHTENED_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(pinkGhost.getCurrentFrame()), pinkGhost.getX() + AppConstants.mazeX, pinkGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.EATEN_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(pinkGhost.getCurrentFrame()), pinkGhost.getX() + AppConstants.mazeX, pinkGhost.getY() + AppConstants.mazeY, null);
                    break;
            }

        }else if(gameState == 3)
        {
            if(SystemClock.uptimeMillis() < resetTimer + 1500){
                currentFrame = (currentFrame + 1) % 6 == 0? currentFrame - 5: currentFrame + 1;
                canvas.drawBitmap(AppConstants.getBitmapBank().getPinkGhost(currentFrame), pinkGhost.getX() + AppConstants.mazeX, pinkGhost.getY() + AppConstants.mazeY, null);
                pinkGhost.setCurrentFrame(currentFrame);
            }
        }else if (gameState == 0 || gameState == 2) {
            if(!pinkGhost.isBeingEaten())
            {
                if(pinkGhost.getState() == Ghost.FRIGHTENED_STATE) {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(currentFrame), pinkGhost.getX() + AppConstants.mazeX, pinkGhost.getY() + AppConstants.mazeY, null);
                }
                else if(pinkGhost.getState() == Ghost.EATEN_STATE) {
                    if(currentFrame > 3)
                        currentFrame = 0;
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(currentFrame), pinkGhost.getX() + AppConstants.mazeX, pinkGhost.getY() + AppConstants.mazeY, null);
                }
                else {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getPinkGhost(currentFrame), pinkGhost.getX() + AppConstants.mazeX, pinkGhost.getY() + AppConstants.mazeY, null);
                }
                pinkGhost.setCurrentFrame(currentFrame);
            }
        }

    }

    public void updateAndDrawBlueGhost(Canvas canvas)
    {
        int currentFrame = blueGhost.getCurrentFrame();

        if(gameState == 1)
        {
            //Movement
            switch(blueGhost.getState())
            {
                case Ghost.SCATTER_STATE:
                    //Turn 180 when entering state from another
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;
                    blueGhost.trackTarget(maze, maze.ROWS, maze.COLS);
                    break;

                case Ghost.CHASE_STATE:
                    //Turn 180 when entering state (will do in timer)
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;

                    int targetRow;
                    int targetCol;
                    int redCol = redGhost.getColumn();
                    int redRow = redGhost.getRow();
                    int pacCol;
                    int pacRow;

                    switch(AppConstants.pacmanDirection)
                    {
                        case AppConstants.UP_DIRECTION:
                            pacCol = pacman.getColumn() - 2;
                            pacRow = pacman.getRow() - 2;

                            if(redCol > pacCol) //If red is to right of pacman vector
                               targetCol = pacCol - Math.abs(pacCol - redCol);
                            else if(redCol < pacCol) //If red is left of pacman vector
                               targetCol = pacCol + Math.abs(pacCol - redCol);
                            else //Otherwise on vector
                                targetCol = pacCol;

                            if(redRow < pacRow) //If red is above pacman vector
                                targetRow = pacRow - Math.abs(pacRow - redRow);
                            else if(redRow > pacRow) //If red is below pacman vector
                                targetRow = pacRow + Math.abs(pacRow - redRow);
                            else
                                targetRow = pacRow;

                            blueGhost.trackTarget(maze, targetRow, targetCol);
                            break;
                        case AppConstants.RIGHT_DIRECTION:
                            pacCol = pacman.getColumn() + 2;
                            pacRow = pacman.getRow();

                            if(redCol > pacCol) //If red is to right of pacman vector
                                targetCol = pacCol - Math.abs(pacCol - redCol);
                            else if(redCol < pacCol) //If red is left of pacman vector
                                targetCol = pacCol + Math.abs(pacCol - redCol);
                            else //Otherwise on vector
                                targetCol = pacCol;

                            if(redRow < pacRow) //If red is above pacman vector
                                targetRow = pacRow - Math.abs(pacRow - redRow);
                            else if(redRow > pacRow) //If red is below pacman vector
                                targetRow = pacRow + Math.abs(pacRow - redRow);
                            else
                                targetRow = pacRow;

                            blueGhost.trackTarget(maze, targetRow, targetCol);
                            break;
                        case AppConstants.DOWN_DIRECTION:
                            pacCol = pacman.getColumn();
                            pacRow = pacman.getRow() + 2;

                            if(redCol > pacCol) //If red is to right of pacman vector
                                targetCol = pacCol - Math.abs(pacCol - redCol);
                            else if(redCol < pacCol) //If red is left of pacman vector
                                targetCol = pacCol + Math.abs(pacCol - redCol);
                            else //Otherwise on vector
                                targetCol = pacCol;

                            if(redRow < pacRow) //If red is above pacman vector
                                targetRow = pacRow - Math.abs(pacRow - redRow);
                            else if(redRow > pacRow) //If red is below pacman vector
                                targetRow = pacRow + Math.abs(pacRow - redRow);
                            else
                                targetRow = pacRow;

                            blueGhost.trackTarget(maze, targetRow, targetCol);
                            break;
                        case AppConstants.LEFT_DIRECTION:
                            pacCol = pacman.getColumn() - 2;
                            pacRow = pacman.getRow();

                            if(redCol > pacCol) //If red is to right of pacman vector
                                targetCol = pacCol - Math.abs(pacCol - redCol);
                            else if(redCol < pacCol) //If red is left of pacman vector
                                targetCol = pacCol + Math.abs(pacCol - redCol);
                            else //Otherwise on vector
                                targetCol = pacCol;

                            if(redRow < pacRow) //If red is above pacman vector
                                targetRow = pacRow - Math.abs(pacRow - redRow);
                            else if(redRow > pacRow) //If red is below pacman vector
                                targetRow = pacRow + Math.abs(pacRow - redRow);
                            else
                                targetRow = pacRow;

                            blueGhost.trackTarget(maze, targetRow, targetCol);
                            break;
                    }
                    break;

                case Ghost.FRIGHTENED_STATE:
                    //Turn 180 when entering state
                    AppConstants.ghostSpeed = AppConstants.SLOW_SPEED;
                    blueGhost.targetRandom(maze);
                    break;

                case Ghost.EATEN_STATE:
                    AppConstants.ghostSpeed = AppConstants.FAST_SPEED;
                    blueGhost.trackTarget(maze, maze.GHOST_HOME_ROW, maze.GHOST_HOME_COL);
                    if(blueGhost.getRow() == maze.GHOST_HOME_ROW && blueGhost.getColumn() == maze.GHOST_HOME_COL) {
                        if(hasEatenPowerUp)
                            AppConstants.getSoundBank().playPowerPellet();
                        else
                            AppConstants.getSoundBank().playSiren();
                        AppConstants.getSoundBank().stopGhostRetreat();
                        blueGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE : Ghost.CHASE_STATE);
                    }
                    break;
            }
            blueGhost.updateGhostOnGrid();

            //Side Teleport Logic
            checkAndDoGhostSideTeleporter(blueGhost);



            //Collision Logic
            checkAndDoCollision(blueGhost);


            //Animation Logic
            blueGhost.setCurrentFrame(calculateNextFrame(blueGhost));
            switch(blueGhost.getState())
            {
                case Ghost.SCATTER_STATE:
                case Ghost.CHASE_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getBlueGhost(blueGhost.getCurrentFrame()), blueGhost.getX() + AppConstants.mazeX, blueGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.FRIGHTENED_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(blueGhost.getCurrentFrame()), blueGhost.getX() + AppConstants.mazeX, blueGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.EATEN_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(blueGhost.getCurrentFrame()), blueGhost.getX() + AppConstants.mazeX, blueGhost.getY() + AppConstants.mazeY, null);
                    break;
            }
        }
        else if(gameState == 3)
        {
            if(SystemClock.uptimeMillis() < resetTimer + 1500){
                currentFrame = (currentFrame + 1) % 6 == 0? currentFrame - 5: currentFrame + 1;
                canvas.drawBitmap(AppConstants.getBitmapBank().getBlueGhost(currentFrame), blueGhost.getX() + AppConstants.mazeX, blueGhost.getY() + AppConstants.mazeY, null);
                blueGhost.setCurrentFrame(currentFrame);
            }
        }else if (gameState == 0 || gameState == 2) {
            if(!blueGhost.isBeingEaten())
            {
                if(blueGhost.getState() == Ghost.FRIGHTENED_STATE) {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(currentFrame), blueGhost.getX() + AppConstants.mazeX, blueGhost.getY() + AppConstants.mazeY, null);
                }
                else if(blueGhost.getState() == Ghost.EATEN_STATE) {
                    if(currentFrame > 3)
                        currentFrame = 0;
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(currentFrame), blueGhost.getX() + AppConstants.mazeX, blueGhost.getY() + AppConstants.mazeY, null);
                }
                else {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getBlueGhost(currentFrame), blueGhost.getX() + AppConstants.mazeX, blueGhost.getY() + AppConstants.mazeY, null);
                }
                blueGhost.setCurrentFrame(currentFrame);
            }
        }
    }

    public void updateAndDrawOrangeGhost(Canvas canvas)
    {
        int currentFrame = orangeGhost.getCurrentFrame();

        if(gameState == 1)
        {
            //Movement
            switch(orangeGhost.getState())
            {
                case Ghost.SCATTER_STATE:
                    //Turn 180 when entering state from another
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;
                    orangeGhost.trackTarget(maze, maze.ROWS, 0);
                    break;

                case Ghost.CHASE_STATE:
                    //Turn 180 when entering state (will do in timer)
                    AppConstants.ghostSpeed = AppConstants.NORMAL_SPEED;

                    int rowDifference = Math.abs(pacman.getRow() - orangeGhost.getRow());
                    int colDifference = Math.abs(pacman.getColumn() - orangeGhost.getColumn());

                    //Tracks pacman like Blinky only if 8 or more tiles away
                    if(rowDifference >= 8 || colDifference >= 8 )
                        orangeGhost.trackTarget(maze, pacman.getRow(), pacman.getColumn());
                    else //Otherwise track scatter
                        orangeGhost.trackTarget(maze, maze.ROWS, 0);
                    break;

                case Ghost.FRIGHTENED_STATE:
                    //Turn 180 when entering state
                    AppConstants.ghostSpeed = AppConstants.SLOW_SPEED;
                    orangeGhost.targetRandom(maze);
                    break;

                case Ghost.EATEN_STATE:
                    AppConstants.ghostSpeed = AppConstants.FAST_SPEED;
                    orangeGhost.trackTarget(maze, maze.GHOST_HOME_ROW, maze.GHOST_HOME_COL);
                    if(orangeGhost.getRow() == maze.GHOST_HOME_ROW && orangeGhost.getColumn() == maze.GHOST_HOME_COL) {
                        if(hasEatenPowerUp)
                            AppConstants.getSoundBank().playPowerPellet();
                        else
                            AppConstants.getSoundBank().playSiren();
                        AppConstants.getSoundBank().stopGhostRetreat();
                        orangeGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE : Ghost.CHASE_STATE);
                    }
                    break;
            }
            orangeGhost.updateGhostOnGrid();

            //Side Teleport
            checkAndDoGhostSideTeleporter(orangeGhost);

            //Collision
            checkAndDoCollision(orangeGhost);

            //Animation Logic
            orangeGhost.setCurrentFrame(calculateNextFrame(orangeGhost));
            switch(orangeGhost.getState())
            {
                case Ghost.SCATTER_STATE:
                case Ghost.CHASE_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getOrangeGhost(orangeGhost.getCurrentFrame()), orangeGhost.getX() + AppConstants.mazeX, orangeGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.FRIGHTENED_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(orangeGhost.getCurrentFrame()), orangeGhost.getX() + AppConstants.mazeX, orangeGhost.getY() + AppConstants.mazeY, null);
                    break;

                case Ghost.EATEN_STATE:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(orangeGhost.getCurrentFrame()), orangeGhost.getX() + AppConstants.mazeX, orangeGhost.getY() + AppConstants.mazeY, null);
                    break;
            }


        }else if(gameState == 3)
        {
            if(SystemClock.uptimeMillis() < resetTimer + 1500){
                currentFrame = (currentFrame + 1) % 6 == 0? currentFrame - 5: currentFrame + 1;
                canvas.drawBitmap(AppConstants.getBitmapBank().getOrangeGhost(currentFrame), orangeGhost.getX() + AppConstants.mazeX, orangeGhost.getY() + AppConstants.mazeY, null);
                orangeGhost.setCurrentFrame(currentFrame);
            }
        }else if (gameState == 0 || gameState == 2) {
            if(!orangeGhost.isBeingEaten())
            {
                if(orangeGhost.getState() == Ghost.FRIGHTENED_STATE) {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getFrightGhost(currentFrame), orangeGhost.getX() + AppConstants.mazeX, orangeGhost.getY() + AppConstants.mazeY, null);
                }
                else if(orangeGhost.getState() == Ghost.EATEN_STATE) {
                    if(currentFrame > 3)
                        currentFrame = 0;
                    canvas.drawBitmap(AppConstants.getBitmapBank().getEatenGhost(currentFrame), orangeGhost.getX() + AppConstants.mazeX, orangeGhost.getY() + AppConstants.mazeY, null);
                }
                else {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getOrangeGhost(currentFrame), orangeGhost.getX() + AppConstants.mazeX, orangeGhost.getY() + AppConstants.mazeY, null);
                }
                orangeGhost.setCurrentFrame(currentFrame);
            }
        }
    }

    public void updateAndDrawPellets(Canvas canvas)
    {
        int currentPPFrame = AppConstants.pp_currentFrame;
        for(int i = 0; i < maze.ROWS; i ++)
        {
            for(int j = 0; j < maze.COLS; j++)
            {
                if(maze.getTile(i,j) == Tile.P)
                {
                    int pelletX = AppConstants.mazeX + (AppConstants.getTileSize() * j);
                    int pelletY = AppConstants.mazeY + (AppConstants.getTileSize() * i) - (AppConstants.isNeumont? 0: AppConstants.getTileSize());
                    canvas.drawBitmap(AppConstants.getBitmapBank().getPellet(), pelletX, pelletY, null);
                }
                else if(maze.getTile(i,j) == Tile.R)
                {
                    int powerPelletX = AppConstants.mazeX + (AppConstants.getTileSize() * j);
                    int powerPelletY = AppConstants.mazeY + (AppConstants.getTileSize() * i) - (AppConstants.isNeumont? 0: AppConstants.getTileSize());
                    canvas.drawBitmap(AppConstants.getBitmapBank().getPowerPellet(currentPPFrame), powerPelletX, powerPelletY, null);
                }

            }
        }

        if(gameState > 0) //If not starting up animate power pellets
            currentPPFrame++;

        if(currentPPFrame > AppConstants.PP_MAXFRAME)
            currentPPFrame = 0;

        AppConstants.pp_currentFrame = currentPPFrame;

        //Logic to "eat" pellets and check power pellets
        boolean hasEatenPowerPellet = maze.checkForPellets(pacman.getRow(), pacman.getColumn());

        if(hasEatenPowerPellet) {
            //Sets Ghosts all to frightened
            if(redGhost.getState() != Ghost.EATEN_STATE) //Makes sure ghost isn't in eaten state then turn frightened
                redGhost.setState(Ghost.FRIGHTENED_STATE);

            if(pinkGhost.getState() != Ghost.EATEN_STATE)
                pinkGhost.setState(Ghost.FRIGHTENED_STATE);

            if(blueGhost.getState() != Ghost.EATEN_STATE)
                blueGhost.setState(Ghost.FRIGHTENED_STATE);

            if(orangeGhost.getState() != Ghost.EATEN_STATE)
                orangeGhost.setState(Ghost.FRIGHTENED_STATE);

            redGhost.reverseDirection(maze);
            pinkGhost.reverseDirection(maze);
            blueGhost.reverseDirection(maze);
            orangeGhost.reverseDirection(maze);

            frightTimer = SystemClock.uptimeMillis();
            chaseTimer += 8000;
            scatterTimer += 8000;

            startBlinkingGhost = false;
            hasEatenPowerUp = true;
        }

        if(maze.getPelletCount() == 0 && gameState != 5)
            gameState = 4;
    }

    public void updateGUIAndDrawMaze(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        //Paints whole background Black outside of Maze

        //Which Maze to Draw
        if(AppConstants.isNeumont)
            canvas.drawBitmap(AppConstants.getBitmapBank().getNeumontMaze(), AppConstants.mazeX, AppConstants.mazeY, null);
        else
            canvas.drawBitmap(AppConstants.getBitmapBank().getDefaultMaze(), AppConstants.mazeX, AppConstants.mazeY, null);
        //DefaultMaze Width: 1176px
        //DefaultMaze Height: 1302px

        if(pacman.getLives() > 1){
            canvas.drawBitmap(AppConstants.getBitmapBank().getLive(), AppConstants.mazeX + 50, AppConstants.mazeY + AppConstants.getBitmapBank().getDefaultMazeWidth() + 170, null);
            if(pacman.getLives() > 2) {
                canvas.drawBitmap(AppConstants.getBitmapBank().getLive(), AppConstants.mazeX + 140, AppConstants.mazeY + AppConstants.getBitmapBank().getDefaultMazeWidth() + 170, null);
            }
        }



        if(gameState == 0)
        {
            if(!AppConstants.isNeumont)
                canvas.drawText("Ready!", AppConstants.mazeX + 480, AppConstants.mazeY + 750, readyPaint);
            else
                canvas.drawText("Ready!", AppConstants.mazeX + 810, AppConstants.mazeY + 390, readyPaint);
        }

        //Draws Score on Screen
        canvas.drawText(AppConstants.score + "", 100, 100, scorePaint);
    }

    public void updateTimers(Canvas canvas)
    {
        if(gameState == -1)
        {
            if(SystemClock.uptimeMillis() > firstStartTimer)
            {
                gameState = 0;
                startUpTimer = SystemClock.uptimeMillis();
            }
        }else if(gameState == 0)
        {//WHEN FIRST STARTING GAME
            //Only see ball pacman and ready text
            redGhost.setState(Ghost.SCATTER_STATE);
            pinkGhost.setState(Ghost.SCATTER_STATE);
            blueGhost.setState(Ghost.SCATTER_STATE);
            orangeGhost.setState(Ghost.SCATTER_STATE);
            if(SystemClock.uptimeMillis() > startUpTimer + 2000)
            {
                gameState = 1;
                pacman.setCurrentFrame(0);
                AppConstants.pacmanDirection = AppConstants.LEFT_DIRECTION;
                AppConstants.getSoundBank().playSiren();
            }
        }
        else if(gameState == 1)
        {
            //Scatter and Chase Time Logic
            if(!stopUntilSwap)
            {
                switch(swapModeCount)
                {
                    case 0:
                    case 2:
                        redGhost.setState(Ghost.SCATTER_STATE);
                        pinkGhost.setState(Ghost.SCATTER_STATE);
                        blueGhost.setState(Ghost.SCATTER_STATE);
                        orangeGhost.setState(Ghost.SCATTER_STATE);
                        scatterTimer = SystemClock.uptimeMillis() + 7000;
                        break;
                    case 1:
                    case 3:
                    case 5:
                        redGhost.setState(Ghost.CHASE_STATE);
                        pinkGhost.setState(Ghost.CHASE_STATE);
                        blueGhost.setState(Ghost.CHASE_STATE);
                        orangeGhost.setState(Ghost.CHASE_STATE);
                        chaseTimer = SystemClock.uptimeMillis() + 20000;
                        break;
                    case 4:
                    case 6:
                        redGhost.setState(Ghost.SCATTER_STATE);
                        pinkGhost.setState(Ghost.SCATTER_STATE);
                        blueGhost.setState(Ghost.SCATTER_STATE);
                        orangeGhost.setState(Ghost.SCATTER_STATE);
                        scatterTimer = SystemClock.uptimeMillis() + 5000;
                    case 7:
                        redGhost.setState(Ghost.CHASE_STATE);
                        pinkGhost.setState(Ghost.CHASE_STATE);
                        blueGhost.setState(Ghost.CHASE_STATE);
                        orangeGhost.setState(Ghost.CHASE_STATE);
                }
                stopUntilSwap = true;
            }

            //Swap To Other State Logic
            if(swapModeCount != 7){
                if(SystemClock.uptimeMillis() > scatterTimer && swapModeCount % 2 == 0)
                {
                    swapModeCount++;
                    stopUntilSwap = false;
                }
                else if(SystemClock.uptimeMillis() > chaseTimer && swapModeCount % 2 == 1)
                {
                    swapModeCount++;
                    stopUntilSwap = false;
                }
            }


            //Frighten Mode Time Logic
            if(hasEatenPowerUp)
            {
                if(SystemClock.uptimeMillis() > frightTimer + 8000 || eatCount == 3) //After 8 seconds
                {
                    AppConstants.getSoundBank().pausePowerPellet();
                    AppConstants.getSoundBank().playSiren();
                    if(redGhost.getState() == Ghost.FRIGHTENED_STATE)
                    {
                        redGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE: Ghost.CHASE_STATE);
                        redGhost.reverseDirection(maze);
                    }
                    if(pinkGhost.getState() == Ghost.FRIGHTENED_STATE)
                    {
                        pinkGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE: Ghost.CHASE_STATE);
                        pinkGhost.reverseDirection(maze);
                    }
                    if(blueGhost.getState() == Ghost.FRIGHTENED_STATE)
                    {
                        blueGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE: Ghost.CHASE_STATE);
                        blueGhost.reverseDirection(maze);
                    }
                    if(orangeGhost.getState() == Ghost.FRIGHTENED_STATE)
                    {
                        orangeGhost.setState(swapModeCount % 2 == 0 ? Ghost.SCATTER_STATE: Ghost.CHASE_STATE);
                        orangeGhost.reverseDirection(maze);
                    }

                    startBlinkingGhost = false;
                    hasEatenPowerUp = false;
                    eatCount = -1;
                }else if(SystemClock.uptimeMillis() > frightTimer + 6000 && !startBlinkingGhost)
                {
                    startBlinkingGhost = true;
                }
            }


        }
        else if(gameState == 2)//WHEN EATING GHOST
        {//Freeze for 1 second, ghost is disappeared, pacman is disappeared, shows points gained
            canvas.drawBitmap(AppConstants.getBitmapBank().getEatPTS(eatCount), pacman.getX() + AppConstants.mazeX, pacman.getY() + AppConstants.mazeY, null);
            if(SystemClock.uptimeMillis() > eatTimer + 1000)
            {
                gameState = 1;
                AppConstants.getSoundBank().playGhostRetreat();
                AppConstants.getSoundBank().pausePowerPellet();
                eatTimer = 0;
                frightTimer += 1000; //To not lose time while frozen

                scatterTimer += 1000;
                chaseTimer += 1000;

                redGhost.setBeingEaten(false);
                pinkGhost.setBeingEaten(false);
                blueGhost.setBeingEaten(false);
                orangeGhost.setBeingEaten(false);
            }
        }
        else if(gameState == 3)
        {//WHEN PACMAN DIES
            //Freezes still for 1 second (pacman stops animation, ghost keeps animation)
            //Ghosts disappear and pacman does dying animation
            //Afterwards screen goes black for like a flash
            //Then just like starting screen but you can see the ghosts(not moving)
            AppConstants.getSoundBank().stopGhostRetreat();
            AppConstants.getSoundBank().pausePowerPellet();
            AppConstants.getSoundBank().stopSiren();
            if(pacman.getCurrentFrame() == 43)
            {
                if(pacman.getLives() > 0)
                {
                    AppConstants.getSoundBank().finishDeath();
                    pacman.resetPosition();
                    redGhost.resetPosition();
                    pinkGhost.resetPosition();
                    blueGhost.resetPosition();
                    orangeGhost.resetPosition();

                    startUpTimer = SystemClock.uptimeMillis();

                    gameState = 0;
                    stopUntilSwap = false;
                    swapModeCount = 0;
                }else
                {
                    gameState = 4;
                }

            }
        }
        else if(gameState == 4)
        {
            AppConstants.getSoundBank().finishDeath();
            AppConstants.getGameActivity().sendToMainMenu();
            gameState = 5;
        }
    }

    //Logic Used within Updates

    private void checkAndDoCollision(Ghost ghost)
    {
        int pacmanWidth = AppConstants.getBitmapBank().getPacmanWidth();
        int ghostWidth = AppConstants.getBitmapBank().getGhostWidth();
        int pacmanHeight = AppConstants.getBitmapBank().getPacmanHeight();
        int ghostHeight = AppConstants.getBitmapBank().getGhostHeight();

        if ((ghost.getX() > pacman.getX() && ghost.getX() < pacman.getX() + pacmanWidth ||
            (ghost.getX() + ghostWidth > pacman.getX()) && ghost.getX() + ghostWidth < pacman.getX() + pacmanWidth) &&
            ((ghost.getY() > pacman.getY() && ghost.getY() < pacman.getY() + pacmanHeight) ||
            (ghost.getY() + ghostHeight > pacman.getY() && ghost.getY() + ghostHeight < pacman.getY() + pacmanHeight)))
        {
            if(ghost.getState() == Ghost.SCATTER_STATE || ghost.getState() == Ghost.CHASE_STATE)
            {
                pacman.setLives(pacman.getLives() - 1);
                gameState = 3;
                resetTimer = SystemClock.uptimeMillis();

            }else if(ghost.getState() == Ghost.FRIGHTENED_STATE)
            {
                AppConstants.getSoundBank().playEatGhost();
                ghost.setState(Ghost.EATEN_STATE);
                gameState = 2;
                eatTimer = SystemClock.uptimeMillis();
                eatCount++;
                AppConstants.score += 200 + (eatCount * 200);
                ghost.setBeingEaten(true);
            }
        }
    }

    private int calculateNextFrame(Ghost ghost)
    {
        int currentFrame = ghost.getCurrentFrame();

        if(ghost.getState() == Ghost.SCATTER_STATE || ghost.getState() == Ghost.CHASE_STATE)
        {
            switch(ghost.getDirection()) //Frame Calculations based on Ghost Movement Direction
            {
                case AppConstants.UP_DIRECTION:
                    return (currentFrame + 1 > 17 || currentFrame < 12? 12: currentFrame + 1);
                case AppConstants.RIGHT_DIRECTION:
                    return (currentFrame + 1 > 5 ? 0: currentFrame + 1);
                case AppConstants.DOWN_DIRECTION:
                    return (currentFrame + 1 > 23 || currentFrame < 18? 18: currentFrame + 1);
                case AppConstants.LEFT_DIRECTION:
                    return (currentFrame + 1 > 11 || currentFrame < 6? 6: currentFrame + 1);
            }
        }
        else if(ghost.getState() == Ghost.FRIGHTENED_STATE)
        {
            if(!startBlinkingGhost) //To Not Go to Blinking Frames
                return(currentFrame + 1 > 5? 0: currentFrame + 1);
            else //(For Readability)
                return(currentFrame = currentFrame + 1 > 11? 0: currentFrame + 1);
        }
        else if(ghost.getState() == Ghost.EATEN_STATE)
        {//In this State Ghosts don't animate only eyes change direction
            switch(ghost.getDirection())
            {
                case AppConstants.UP_DIRECTION:
                    return 2;
                case AppConstants.RIGHT_DIRECTION:
                    return 0;
                case AppConstants.DOWN_DIRECTION:
                    return 3;
                case AppConstants.LEFT_DIRECTION:
                    return 1;
            }
        }
        //Should Never Happen
        return currentFrame;
    }

    private void checkAndDoGhostSideTeleporter(Ghost ghost)
    {
        if(ghost.getX() > maze.TELEPORT_RIGHTX)
        {
            ghost.setX(maze.TELEPORT_LEFTX);
            ghost.updateGhostOnGrid();
            ghost.setDirection(ghost.getDirection());
        }else if(ghost.getX() < maze.TELEPORT_LEFTX)
        {
            ghost.setX(maze.TELEPORT_RIGHTX);
            ghost.updateGhostOnGrid();
            ghost.setDirection(ghost.getDirection());
        }

    }

    public Maze getMaze()
    {
        return maze;
    }

    public Pacman getPacman()
    {
        return pacman;
    }
}
