package com.posedanto.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Agitatore on 25.03.2017.
 */

public class Figure {
    private Vector2 position;
    private float runTime, fallDelay;
    private FigureForms.rotation rotation;
    private FigureForms.forms form;
    private boolean isStopped = false;

    public Figure() {
        reset();
    }

    public void reset() {
        position = new Vector2(4, 19);
        runTime = 0;
        fallDelay = 1;
        isStopped = false;
        rotation = FigureForms.rotation.FLIP_0;
        form = FigureForms.getRandomForm();
        //form = FigureForms.forms.S_FORM;
    }

    public void update(float delta) {
        runTime += delta;
        if (runTime >= fallDelay) {
            position.y--;
            runTime = 0;
        }
    }

    public void setFromNextFigure(Figure fig) {
        position = new Vector2(4, 19);
        runTime = 0;
        isStopped = false;
        rotation = fig.getRotation();
        form = fig.getForm();
    }

    public void moveLeft() {
        position.x--;
    }

    public void moveRight() {
        position.x++;
    }

    public void rotate(Vector2 nextPosition, FigureForms.rotation nextRotation) {
        position.set(nextPosition.x, nextPosition.y);
        rotation = nextRotation;
    }

    public int getColorNumber() {
        return FigureForms.getFormNumber(form);
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void decFallDelay() {
        fallDelay = 0.1f;
    }

    public void setFallDelayDefault() {
        fallDelay = 1f;
    }

    public Vector2 getPosition() {
        return position;
    }

    public FigureForms.rotation getRotation() {
        return rotation;
    }

    public FigureForms.forms getForm() {
        return form;
    }
}
