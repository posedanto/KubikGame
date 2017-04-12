package com.posedanto.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Agitatore on 12.04.2017.
 */

public class NextFigureField {
    private Figure nextFigure;
    private int positionX, positionY, width;
    private int length, height;
    Vector2 pos;

    public NextFigureField() {
        positionX = 735;
        positionY = 1300;
        width = 260;
        nextFigure = new Figure();
        length = FigureForms.getLength(nextFigure);
        height = FigureForms.getHeight(nextFigure);
    }

    public void resetNextFigure() {
        nextFigure.reset();
        length = FigureForms.getLength(nextFigure.getForm(), nextFigure.getRotation(), pos);
        height = FigureForms.getHeight(nextFigure.getForm(), nextFigure.getRotation(), pos);
    }

    public Vector2[] getFigurePosition() {
        pos = new Vector2(0, height - 1);
        return FigureForms.getCoordinates(nextFigure.getForm(), nextFigure.getRotation(), pos);
    }

    public int getFigurePositionX() {
        return positionX + (width - Field.CELL_SIZE * length) / 2;
    }

    public int getFigurePositionY() {
        return positionY + (width - Field.CELL_SIZE * height) / 2;
    }

    public int getFigureColorNumber() {
        return nextFigure.getColorNumber();
    }

    public Figure getNextFigure() {
        return nextFigure;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
