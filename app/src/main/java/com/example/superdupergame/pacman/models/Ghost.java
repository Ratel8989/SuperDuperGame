package com.example.superdupergame.pacman.models;


public class Ghost {
    private int currentFrame;
    private int ghostState; //What state the ghost is in
    private int ghostX, ghostY;
    private int ghostDirection; //What direction ghost is going
    private int ghostRow, ghostColumn; //Spot in grid array
    private int nextRow, nextColumn; //To force recalculations to occur only once on the tile last calculated
    private boolean isBeingEaten;

    public static final int SCATTER_STATE = 0;
    public static final int CHASE_STATE = 1;
    public static final int FRIGHTENED_STATE = 2;
    public static final int EATEN_STATE = 3;

    private final int STARTING_X;
    private final int STARTING_Y;

    public Ghost() {
        //AppConstants.getGameEngine().getMaze().TILE_SIZE
        if(AppConstants.isNeumont)
        {
            STARTING_X = (26 * 35);
            STARTING_Y = (3 * 35);
        }else
        {
            STARTING_X = (14 * 42);
            STARTING_Y = (11 * 42);
        }

        ghostX = STARTING_X;
        ghostY = STARTING_Y;

        currentFrame = 6; //Left Frame

        ghostState = SCATTER_STATE; //Always starts in scatter state

        updateGhostOnGrid();
        setDirection(AppConstants.LEFT_DIRECTION); //Ghost always starts to the left when leaving the maze
        isBeingEaten = false;
    }

    public boolean isBeingEaten()
    {
        return isBeingEaten;
    }

    public void setBeingEaten(boolean isBeingEaten)
    {
        this.isBeingEaten = isBeingEaten;
    }

    public int getX()
    {
        return ghostX;
    }

    public void setX(int ghostX)
    {
        this.ghostX = ghostX;
    }

    public int getY()
    {
        return ghostY;
    }

    public void setY(int ghostY)
    {
        this.ghostY = ghostY;
    }

    /**
     * <pre>
     *     0: Scatter
     *     1: Chase
     *     2: Frightened
     *     3: Eaten
     * </pre>
     * @return Current Ghost State
     */
    public int getState()
    {
        return ghostState;
    }

    /**
     * <pre>
     *     0: Scatter
     *     1: Chase
     *     2: Frightened
     *     3: Eaten
     * </pre>
     */
    public void setState(int state)
    {
        ghostState = state;
    }

    /**
     * <pre>
     *     1: Up
     *     2: Right
     *     3: Down
     *     4: Left
     * </pre>
     * @return Direction Ghost is Going
     */
    public int getDirection()
    {
        return ghostDirection;
    }

    /**
     * <pre>
     *     1: Up
     *     2: Right
     *     3: Down
     *     4: Left
     * </pre>
     *
     * Additionally Sets Next Row And Column for Tracking Calculations
     */
    public void setDirection(int direction)
    {
        switch(direction)
        {
            case AppConstants.UP_DIRECTION:
                nextRow = ghostRow - 1;
                nextColumn = ghostColumn;
                break;
            case AppConstants.RIGHT_DIRECTION:
                nextRow = ghostRow;
                nextColumn = ghostColumn + 1;
                break;
            case AppConstants.DOWN_DIRECTION:
                nextRow = ghostRow + 1;
                nextColumn = ghostColumn;
                break;
            case AppConstants.LEFT_DIRECTION:
                nextRow = ghostRow;
                nextColumn = ghostColumn - 1;
                break;
        }
        ghostDirection = direction;

    }

    public void reverseDirection(Maze maze)
    {
        switch(ghostDirection)
        {
            case AppConstants.UP_DIRECTION:
                if(!maze.isBottomTileWall(ghostRow, ghostColumn))
                    setDirection(AppConstants.DOWN_DIRECTION);
                break;
            case AppConstants.RIGHT_DIRECTION:
                if(!maze.isLeftTileWall(ghostRow, ghostColumn))
                    setDirection(AppConstants.LEFT_DIRECTION);
                break;
            case AppConstants.DOWN_DIRECTION:
                if(!maze.isTopTileWall(ghostRow, ghostColumn))
                    setDirection(AppConstants.UP_DIRECTION);
                break;
            case AppConstants.LEFT_DIRECTION:
                if(!maze.isLeftTileWall(ghostRow, ghostColumn))
                    setDirection(AppConstants.RIGHT_DIRECTION);
                break;
        }
    }

    public void updateGhostOnGrid()
    {
        int x = ghostX + AppConstants.getBitmapBank().getGhostWidth()/2;
        int y = ghostY + AppConstants.getBitmapBank().getGhostHeight()/2 + AppConstants.getTileSize();

        ghostRow = (int)Math.ceil(y/ AppConstants.getTileSize());
        ghostColumn = (int)Math.ceil(x/ AppConstants.getTileSize());
    }

    public int getRow()
    {
        return ghostRow;
    }

    public int getColumn()
    {
        return ghostColumn;
    }

    public void trackTarget(Maze maze, int targetRow, int targetColumn)
    {
        boolean canMoveUp = !maze.isTopTileWall(ghostRow, ghostColumn);
        boolean canMoveDown = !maze.isBottomTileWall(ghostRow, ghostColumn);
        boolean canMoveRight = !maze.isRightTileWall(ghostRow, ghostColumn);
        boolean canMoveLeft = !maze.isLeftTileWall(ghostRow, ghostColumn);

        int rowDifference = Math.abs(targetRow - ghostRow);
        int colDifference = Math.abs(targetColumn - ghostColumn);

        if(ghostRow == nextRow && ghostColumn == nextColumn) //Makes sure ghost commits to next tile before changing directions
        {
            switch (ghostDirection)
            {
                case AppConstants.UP_DIRECTION:
                    if (ghostColumn < targetColumn) //If ghost is left of target
                    {
                        if(colDifference < rowDifference)
                        {
                            if(canMoveUp && ghostRow > targetRow)
                                moveUp();
                            else if(canMoveRight)
                                moveRight();
                            else if(canMoveLeft)
                                moveLeft();
                            else if(canMoveUp)
                                moveUp();
                            else
                                moveDown();//Last case scenario means trapped in Neumont Level
                        }else{
                            if (canMoveRight)
                                moveRight();
                            else if (canMoveUp)
                                moveUp();
                            else if (canMoveLeft)
                                moveLeft();
                            else
                                moveDown();//Last case scenario means trapped in Neumont Level
                        }

                    }
                    else if (ghostColumn > targetColumn)
                    {
                        if(colDifference < rowDifference)
                        {
                            if(canMoveUp && ghostRow > targetRow)
                                moveUp();
                            else if(canMoveLeft)
                                moveLeft();
                            else if(canMoveRight)
                                moveRight();
                            else if(canMoveUp)
                                moveUp();
                            else
                                moveDown(); //Last case scenario means trapped in Neumont Level
                        }else{
                            if (canMoveLeft)
                                moveLeft();
                            else if (canMoveUp)
                                moveUp();
                            else if (canMoveRight)
                                moveRight();
                            else
                                moveDown(); //Last case scenario means trapped in Neumont Level
                        }
                    } else {
                        if (canMoveUp && ghostRow > targetRow)
                            moveUp();
                        else if (canMoveLeft)
                            moveLeft();
                        else if (canMoveRight)
                            moveRight();
                        else if(canMoveUp) //In this case only move up if cant go down or left because target is below ghost
                            moveUp();
                        else
                            moveDown();//Last case scenario means trapped in Neumont Level
                    }
                    break;


                case AppConstants.RIGHT_DIRECTION:
                    if (ghostRow < targetRow)//If above target
                    {
                        if(rowDifference < colDifference) //If gap between row
                        {
                            if(canMoveRight && ghostColumn < targetColumn)
                                moveRight();
                            else if(canMoveDown)
                                moveDown();
                            else if(canMoveUp)
                                moveUp();
                            else if(canMoveRight)
                                moveRight();
                            else
                                moveLeft(); //Last case scenario means trapped in Neumont Level
                        }else{
                            if (canMoveDown)
                                moveDown();
                            else if (canMoveRight)
                                moveRight();
                            else if (canMoveUp)
                                moveUp();
                            else
                                moveLeft(); //Last case scenario means trapped in Neumont Level
                        }

                    }
                    else if (ghostRow > targetRow)//If below target
                    {
                        if(rowDifference < colDifference)
                        {
                            if(canMoveRight && ghostColumn < targetColumn)
                                moveRight();
                            else if(canMoveUp)
                                moveUp();
                            else if(canMoveDown)
                                moveDown();
                            else if(canMoveRight)
                                moveRight();
                            else
                                moveLeft(); //Last case scenario means trapped in Neumont Level
                        }else {
                            if (canMoveUp)
                                moveUp();
                            else if (canMoveRight)
                                moveRight();
                            else if (canMoveDown)
                                moveDown();
                            else
                                moveLeft(); //Last case scenario means trapped in Neumont Level
                        }


                    }
                    else
                    {
                        if (canMoveRight && ghostColumn < targetColumn)
                            moveRight();
                        else if (canMoveUp)
                            moveUp();
                        else if (canMoveDown)
                            moveDown();
                        else if (canMoveRight)
                            moveRight();
                        else
                            moveLeft(); //Last case scenario means trapped in Neumont Level

                    }
                    break;


                case AppConstants.DOWN_DIRECTION:
                    if (ghostColumn < targetColumn)// If to the left of pacman
                    {
                        if(colDifference < rowDifference)
                        {
                            if(canMoveDown && ghostRow < targetRow)
                                moveDown();
                            else if(canMoveRight)
                                moveRight();
                            else if(canMoveLeft)
                                moveLeft();
                            else if(canMoveDown)
                                moveDown();
                            else
                                moveUp(); //Last case scenario means trapped in Neumont Level
                        }else{
                            if (canMoveRight)
                                moveRight();
                            else if (canMoveDown)
                                moveDown();
                            else if (canMoveLeft)
                                moveLeft();
                            else
                                moveUp(); //Last case scenario means trapped in Neumont Level
                        }

                    }
                    else if (ghostColumn > targetColumn) //If to the right of pacman
                    {
                        if(colDifference < rowDifference)
                        {
                            if(canMoveDown && ghostRow < targetRow)
                                moveDown();
                            else if(canMoveLeft)
                                moveLeft();
                            else if(canMoveRight)
                                moveRight();
                            else if(canMoveDown)
                                moveDown();
                            else
                                moveUp(); //Last case scenario means trapped in Neumont Level
                        }else{
                            if (canMoveLeft)
                                moveLeft();
                            else if (canMoveDown)
                                moveDown();
                            else if (canMoveRight)
                                moveRight();
                            else
                                moveUp(); //Last case scenario means trapped in Neumont Level
                        }
                    }
                    else
                    {
                        if (canMoveDown && ghostRow < targetRow)
                            moveDown();
                        else if (canMoveLeft)
                            moveLeft();
                        else if (canMoveRight)
                            moveRight();
                        else if (canMoveDown)
                            moveDown();
                        else
                            moveUp(); //Last case scenario means trapped in Neumont Level
                    }
                    break;


                case AppConstants.LEFT_DIRECTION:
                    if (ghostRow < targetRow) //Ghost is above target
                    {
                        if(rowDifference < colDifference)
                        {
                            if(canMoveLeft && ghostColumn > targetColumn)
                                moveLeft();
                            else if(canMoveDown)
                                moveDown();
                            else if(canMoveUp)
                                moveUp();
                            else if(canMoveLeft)
                                moveLeft();
                            else
                                moveRight(); //Last case scenario means trapped in Neumont Level
                        }else{
                            if (canMoveDown)
                                moveDown();
                            else if (canMoveLeft)
                                moveLeft();
                            else if (canMoveUp)
                                moveUp();
                            else
                                moveRight(); //Last case scenario means trapped in Neumont Level
                        }

                    } else if (ghostRow > targetRow) //Ghost is below target
                    {
                        if(rowDifference < colDifference)
                        {
                            if(canMoveLeft && ghostColumn > targetColumn)
                                moveLeft();
                            else if(canMoveUp)
                                moveUp();
                            else if(canMoveDown)
                                moveDown();
                            else if(canMoveLeft)
                                moveLeft();
                            else
                                moveRight(); //Last case scenario means trapped in Neumont Level
                        }else{
                            if (canMoveUp)
                                moveUp();
                            else if (canMoveLeft)
                                moveLeft();
                            else if (canMoveDown)
                                moveDown();
                            else
                                moveRight(); //Last case scenario means trapped in Neumont Level

                        }


                    } else
                    {//If Ghost is on same row in array as target spot
                        if (canMoveLeft && ghostColumn > targetColumn)
                            moveLeft();
                        else if (canMoveUp)
                            moveUp();
                        else if (canMoveDown)
                            moveDown();
                        else if(canMoveLeft)
                            moveLeft();
                        else
                            moveRight(); //Last case scenario means trapped in Neumont Level
                    }
                    break;
            }
        }
        else
        {
            switch(ghostDirection){
                case AppConstants.UP_DIRECTION:
                    setY(getY() - AppConstants.ghostSpeed);
                    break;
                case AppConstants.RIGHT_DIRECTION:
                    setX(getX() + AppConstants.ghostSpeed);
                    break;
                case AppConstants.DOWN_DIRECTION:
                    setY(getY() + AppConstants.ghostSpeed);
                    break;
                case AppConstants.LEFT_DIRECTION:
                    setX(getX() - AppConstants.ghostSpeed);
                    break;
            }
        }
    }

    public void targetRandom(Maze maze)
    {
        boolean canMoveUp = !maze.isTopTileWall(ghostRow, ghostColumn);
        boolean canMoveDown = !maze.isBottomTileWall(ghostRow, ghostColumn);
        boolean canMoveRight = !maze.isRightTileWall(ghostRow, ghostColumn);
        boolean canMoveLeft = !maze.isLeftTileWall(ghostRow, ghostColumn);

        if(ghostRow == nextRow && ghostColumn == nextColumn)
        {
            boolean cantMove = true;

            //Flags to prevent ghosts from being stuck in neumont level /(freeze game)
            boolean checkTop = false;
            boolean checkRight = false;
            boolean checkBottom = false;
            boolean checkLeft = false;
            switch(ghostDirection)
            {
                case AppConstants.UP_DIRECTION:
                    while(cantMove)
                    {
                        switch((int)(Math.random() * 3))
                        {
                            case 0:
                                if(canMoveRight) {
                                    moveRight();
                                    cantMove = false;
                                }else checkRight = true;
                                break;
                            case 1:
                                if(canMoveUp){
                                    moveUp();
                                    cantMove = false;
                                }else checkTop = true;
                                break;
                            case 2:
                                if(canMoveLeft) {
                                    moveLeft();
                                    cantMove = false;
                                }else checkLeft = true;
                                break;
                        }
                        if(checkRight && checkLeft && checkTop) //To make sure ghost doesn't get stuck and freeze the game
                        {
                            moveDown();
                            cantMove = false;
                        }
                    }
                    break;
                case AppConstants.RIGHT_DIRECTION:
                    while(cantMove)
                    {
                        switch((int)(Math.random() * 3))
                        {
                            case 0:
                                if(canMoveUp) {
                                    moveUp();
                                    cantMove = false;
                                }else checkTop = true;
                                break;
                            case 1:
                                if(canMoveDown){
                                    moveDown();
                                    cantMove = false;
                                } else checkBottom = true;

                                break;
                            case 2:
                                if(canMoveRight) {
                                    moveRight();
                                    cantMove = false;
                                }else checkRight = true;
                                break;
                        }
                        if(checkTop && checkBottom && checkRight) //To make sure ghost doesn't get stuck and freeze the game
                        {
                            moveLeft();
                            cantMove = false;
                        }
                    }
                    break;
                case AppConstants.DOWN_DIRECTION:
                    while(cantMove)
                    {
                        switch((int)(Math.random() * 3))
                        {
                            case 0:
                                if(canMoveDown) {
                                    moveDown();
                                    cantMove = false;
                                }else checkBottom = true;
                                break;
                            case 1:
                                if(canMoveRight){
                                    moveRight();
                                    cantMove = false;
                                }else checkRight = true;

                                break;
                            case 2:
                                if(canMoveLeft) {
                                    moveLeft();
                                    cantMove = false;
                                }else checkLeft = true;
                                break;
                        }
                        if(checkBottom && checkRight && checkLeft) //To make sure ghost doesn't get stuck and freeze the game
                        {
                            moveUp();
                            cantMove = false;
                        }
                    }
                    break;
                case AppConstants.LEFT_DIRECTION:
                    while(cantMove)
                    {
                        switch((int)(Math.random() * 3))
                        {
                            case 0:
                                if(canMoveUp) {
                                    moveUp();
                                    cantMove = false;
                                }else checkTop = true;
                                break;
                            case 1:
                                if(canMoveLeft){
                                    moveLeft();
                                    cantMove = false;
                                }else checkLeft = true;

                                break;
                            case 2:
                                if(canMoveDown) {
                                    moveDown();
                                    cantMove = false;
                                }else checkBottom = true;
                                break;
                        }
                        if(checkTop && checkLeft && checkBottom)
                        {
                            moveRight();
                            cantMove = false;
                        }
                    }
                    break;
            }
        }else {
            switch(ghostDirection){
                case AppConstants.UP_DIRECTION:
                    setY(getY() - AppConstants.ghostSpeed);
                    break;
                case AppConstants.RIGHT_DIRECTION:
                    setX(getX() + AppConstants.ghostSpeed);
                    break;
                case AppConstants.DOWN_DIRECTION:
                    setY(getY() + AppConstants.ghostSpeed);
                    break;
                case AppConstants.LEFT_DIRECTION:
                    setX(getX() - AppConstants.ghostSpeed);
                    break;
            }
        }

    }

    private void moveUp()
    {
        setY(getY() - AppConstants.ghostSpeed);
        setDirection(AppConstants.UP_DIRECTION);
    }

    private void moveRight()
    {
        setX(getX() + AppConstants.ghostSpeed);
        setDirection(AppConstants.RIGHT_DIRECTION);
    }

    private void moveDown()
    {
        setY(getY() + AppConstants.ghostSpeed);
        setDirection(AppConstants.DOWN_DIRECTION);
    }

    private void moveLeft()
    {
        setX(getX() - AppConstants.ghostSpeed);
        setDirection(AppConstants.LEFT_DIRECTION);
    }

    public void setCurrentFrame(int frame)
    {
        currentFrame = frame;
    }

    public int getCurrentFrame()
    {
        return currentFrame;
    }

    public void resetPosition()
    {
        ghostX = STARTING_X - 30;
        ghostY = STARTING_Y - 10;
        updateGhostOnGrid();
        setDirection(AppConstants.LEFT_DIRECTION);
        currentFrame = 6;
    }

}
