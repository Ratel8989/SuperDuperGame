package com.example.superdupergame.pacman.models;


public class DefaultMaze extends Maze
{
    //DefaultMaze[31][28] //8x8 pixels each tile
    //Grid[33][28]
    // 42 x 42
//    public static final int ROWS = 31;
//    public static final int COLS = 28;
    public static final int HOME_ROW = 12;
    public static final int HOME_COL = 12;

    public static final int TILE_SIZE = 42;
    //Calculate Tile Size by rows by columns to width and height

    public DefaultMaze()
    {
        super(31, 28, 244);
        initializeMaze();
    }

    @Override
    protected void initializeMaze()
    {
        grid = new Tile[][]{
          /*0*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
          /*1*/ {Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W},
          /*2*/ {Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W},
          /*3*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
          /*4*/ {Tile.W, Tile.R, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.R, Tile.W},
          /*5*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
          /*6*/ {Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W},
          /*7*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
          /*8*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
          /*9*/ {Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W},
         /*10*/ {Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W},
         /*11*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
         /*12*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
         /*13*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
         /*14*/ {Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W},
       /*(15)*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.P, Tile.E, Tile.E, Tile.E, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.E, Tile.E, Tile.E, Tile.P, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
         /*16*/ {Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W},
         /*17*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
         /*18*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
         /*19*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E},
         /*20*/ {Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.E, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W},
         /*21*/ {Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W},
         /*22*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
         /*23*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
       /*<24>*/ {Tile.W, Tile.R, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.E, Tile.E, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.R, Tile.W},
         /*25*/ {Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W},
         /*26*/ {Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W},
         /*27*/ {Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W, Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W},
         /*28*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
         /*29*/ {Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.P, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.P, Tile.W},
         /*30*/ {Tile.W, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.P, Tile.W},
         /*31*/ {Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W, Tile.W},
         /*32*/ {Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E, Tile.E}
        };
    }














}
