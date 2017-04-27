package com.posedanto.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

/**
 * Created by Agitatore on 25.03.2017.
 */

public class Field {
    public static final int CELL_SIZE = 60;

    public static final int COUNT_CELLS_X = 10;
    public static final int COUNT_CELLS_Y = 20;

    public static final int FIELD_WIDTH = COUNT_CELLS_X *CELL_SIZE;
    public static final int FIELD_HEIGHT = COUNT_CELLS_Y *CELL_SIZE;

    private int positionX, positionY;
    private int[][] myField;

    public Field() {
        positionX = 50;
        positionY = 500;
        myField = new int[COUNT_CELLS_Y][COUNT_CELLS_X];
        for (int i = 0; i < COUNT_CELLS_Y; i++)
            for (int j = 0; j < COUNT_CELLS_X; j++)
                myField[i][j] = -1;
        //myField[0][3] = myField[0][7] = myField[0][5] = myField[1][4] = 0;
        /*for(int i=0; i<10;i++)
            myField[0][i] = 1;
        for(int i=0; i<10;i++)
            myField[1][i] = 3;
        for(int i=0; i<10;i++)
            myField[2][i] = 3;
        for(int i=0; i<10;i++)
            myField[3][i] = 3;*/
        //myField[0][5] = myField[2][5] = myField[1][4] = myField[0][7] = 1;
    }

    public void removeLine(int y) {
        int[] topLine = myField[y];
        for (int i = y; i < COUNT_CELLS_Y - 1; i++)
            myField[i]=myField[i+1];

        myField[COUNT_CELLS_Y-1] = topLine;

        for(int i=0; i < COUNT_CELLS_X; i++)
            myField[COUNT_CELLS_Y-1][i] = -1;
    }

    public void reset() {
        for (int i = 0; i < COUNT_CELLS_Y; i++)
            for (int j = 0; j < COUNT_CELLS_X; j++)
                myField[i][j] = -1;
    }

    public void setCell(int x, int y, int color) {
        myField[y][x] = color;
    }

    public int getCell(int x, int y) {
        return myField[y][x];
    }

    public boolean isEmpty(int x, int y) {
        return myField[y][x] == -1;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
