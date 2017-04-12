package com.posedanto.helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.posedanto.gameworld.GameWorld;

/**
 * Created by Agitatore on 26.03.2017.
 */

public class InputHandler implements InputProcessor {

    private GameWorld myWorld;

    public InputHandler(GameWorld myWorld) {
        this.myWorld = myWorld;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                myWorld.tryFigureMoveLeft();
                break;
            case Input.Keys.RIGHT:
                myWorld.tryFigureMoveRight();
                break;
            case Input.Keys.DOWN:
                myWorld.getFigure().decFallDelay();
                break;
            case Input.Keys.SPACE:
                myWorld.tryFigureRotate();
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
