package com.posedanto.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.posedanto.gameobjects.Field;
import com.posedanto.gameobjects.Figure;
import com.posedanto.gameobjects.FigureForms;
import com.posedanto.gameobjects.NextFigureField;

/**
 * Created by Agitatore on 25.03.2017.
 */

public class GameWorld {
    private Field myField;
    private Figure myFigure;
    private NextFigureField myNextFigureField;

    private FigureForms.rotation nextRotation;
    private Vector2 nextPosition = new Vector2();
    private Vector2[] fPosition = new Vector2[4];
    private Vector2[] fPos = new Vector2[4];
    private int removingLine;
    private float runTime;

    private GameState currentState;

    public enum GameState {
        FIGURE_FALLING, FIGURE_FELL, FIGURE_ADDITION, LINE_SEARCHING, LINE_REMOVING, LINE_REMOVED,
        GAMEOVER
    }

    public GameWorld() {
        myField = new Field();
        myFigure = new Figure();
        myNextFigureField = new NextFigureField();
        currentState = GameState.FIGURE_FALLING;
    }

    public void update(float delta) {
        /*myFigure.update(delta);
        if (checkBrickUnderFigure()) {
            addFigureToField();

            for (int i = 0; i < Field.COUNT_CELLS_Y; i++) {
                if(isLineFilled(i)) {
                    myField.removeLine(i);
                    i--;
                }
            }

            myFigure.reset();
        }*/
        switch (currentState) {
            case FIGURE_FALLING:
                myFigure.update(delta);
                if (checkBrickUnderFigure())
                    currentState = GameState.FIGURE_FELL;
                break;
            case FIGURE_FELL:
                addFigureToField();
                currentState = GameState.LINE_SEARCHING;
                break;
            case LINE_SEARCHING:
                for (int i = 0; i < Field.COUNT_CELLS_Y; i++) {
                    if(isLineFilled(i)) {
                        removingLine = i;
                        currentState = GameState.LINE_REMOVING;
                        break;
                    }
                }
                if (currentState != GameState.LINE_REMOVING)
                    currentState = GameState.FIGURE_ADDITION;
                break;
            case LINE_REMOVING:
                /*runTime += delta;
                if (runTime >= 2)
                    currentState = GameState.LINE_REMOVED;*/
                break;
            case LINE_REMOVED:
                myField.removeLine(removingLine);
                removingLine = -1;
                runTime = 0;
                currentState = GameState.LINE_SEARCHING;
                break;
            case FIGURE_ADDITION:
                myFigure.setFromNextFigure(myNextFigureField.getNextFigure());
                myNextFigureField.resetNextFigure();
                fPosition = FigureForms.getCoordinates(myFigure.getForm(), myFigure.getRotation(),
                        myFigure.getPosition());
                if(! isHereClean(fPosition))
                    currentState = GameState.GAMEOVER;
                else
                    currentState = GameState.FIGURE_FALLING;
                break;
            case GAMEOVER:
                Gdx.app.log("game", "stopped");
                break;
        }
    }

    private void addFigureToField() {
        fPosition = FigureForms.getCoordinates(myFigure.getForm(), myFigure.getRotation(),
                myFigure.getPosition());
        for (Vector2 pos : fPosition)
            myField.setCell((int) pos.x, (int) pos.y, myFigure.getColorNumber());
    }

    private boolean checkBrickUnderFigure() {
        fPosition = FigureForms.getCoordinates(myFigure.getForm(), myFigure.getRotation(),
                myFigure.getPosition());
        for (Vector2 pos : fPosition)
            if (((pos.y - 1 < 0) || !myField.isEmpty((int) pos.x, (int) pos.y - 1)))
                return true;


        return false;
    }
    public void tryFigureMoveLeft() {
        if(isLeftClean(myFigure.getForm(), myFigure.getRotation(), myFigure.getPosition()))
            myFigure.moveLeft();
    }

    public void tryFigureMoveRight() {
        if(isRightClean(myFigure.getForm(), myFigure.getRotation(), myFigure.getPosition()))
            myFigure.moveRight();
    }

    public void tryFigureRotate() {
        nextRotation = FigureForms.getNextRotation(myFigure.getRotation());
        nextPosition.set(myFigure.getPosition());
        int length = FigureForms.getLength(myFigure.getForm(), nextRotation, nextPosition);

        /*Gdx.app.log("length", length + "");
        Gdx.app.log("x", nextPosition.x + "");
        Gdx.app.log("y", nextPosition.y + "");*/

        //Если фигура выходит за правую границу
        if(nextPosition.x > Field.COUNT_CELLS_X - length) {
            nextPosition.x = Field.COUNT_CELLS_X - length; //на 1-2 клетки левее новой позиции
            fPos = FigureForms.getCoordinates(myFigure.getForm(), nextRotation, nextPosition);
            if(! isHereClean(fPos)) return;
        }
        else {
            fPos = FigureForms.getCoordinates(myFigure.getForm(), nextRotation, nextPosition);
            if(! isHereClean(fPos))
                if (isLeftClean(myFigure.getForm(), nextRotation, nextPosition))
                    nextPosition.x--;
                else if (isRightClean(myFigure.getForm(), nextRotation, nextPosition))
                    nextPosition.x++;
                else
                    return;
        }
        myFigure.rotate(nextPosition, nextRotation);
    }

    private boolean isHereClean(Vector2[] position) {
        for (Vector2 pos : position)
            if (!myField.isEmpty((int) pos.x, (int) pos.y)) return false;
        return true;
    }

    private boolean isLeftClean(FigureForms.forms f, FigureForms.rotation r, Vector2 p) {
        fPosition = FigureForms.getCoordinates(f, r, p);
        System.out.println(r);
        if(myFigure.getPosition().x < 1) return false;
        for(Vector2 pos : fPosition)
            if (!myField.isEmpty((int) pos.x - 1, (int) pos.y)) return false;
        return true;
    }

    private boolean isRightClean(FigureForms.forms f, FigureForms.rotation r, Vector2 p) {
        fPosition = FigureForms.getCoordinates(f, r, p);
        if (myFigure.getPosition().x >= Field.COUNT_CELLS_X - FigureForms.getLength(f, r, p))
            return false;
        for (Vector2 pos : fPosition)
            if (!myField.isEmpty((int) pos.x + 1, (int) pos.y)) return false;
        return true;
    }

    private boolean isLineFilled(int y) {
        for(int i=0; i < Field.COUNT_CELLS_X; i++)
            if (myField.isEmpty(i, y))
                return false;
        return true;
    }

    public void setLineRemoved() {
        currentState = GameState.LINE_REMOVED;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isFigureFalling() {
        return currentState == GameState.FIGURE_FALLING;
    }

    public boolean isLineRemoving() {
        return currentState == GameState.LINE_REMOVING;
    }

    public boolean isLineRemoved() {
        return currentState == GameState.LINE_REMOVED;
    }

    public int getRemovingLine() {
        return removingLine;
    }

    public Field getField() {
        return myField;
    }

    public Figure getFigure() {
        return myFigure;
    }

    public NextFigureField getMyNextFigureField() {
        return myNextFigureField;
    }
}
