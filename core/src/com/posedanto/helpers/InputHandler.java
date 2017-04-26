package com.posedanto.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.posedanto.gameworld.GameWorld;
import com.posedanto.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agitatore on 26.03.2017.
 */

public class InputHandler implements InputProcessor {

    private GameWorld myWorld;
    private List<SimpleButton> gameButtons;

    private float scaleFactorX;
    private float scaleFactorY;

    private boolean someButtonDown = false;

    public InputHandler(GameWorld myWorld) {
        this.myWorld = myWorld;
        gameButtons = new ArrayList<SimpleButton>();
        gameButtons.add(new SimpleButton(10, 40, 250, 250, AssetLoader.buttonLeft, AssetLoader.buttonLeftDown, "LEFT"));
        gameButtons.add(new SimpleButton(280, 40, 250, 250, AssetLoader.buttonRight, AssetLoader.buttonRightDown, "RIGHT"));
        gameButtons.add(new SimpleButton(550, 40, 250, 250, AssetLoader.buttonDown, AssetLoader.buttonDownDown, "DOWN"));
        gameButtons.add(new SimpleButton(820, 40, 250, 250, AssetLoader.buttonRotate, AssetLoader.buttonRotateDown, "ROTATE"));

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        scaleFactorX = screenWidth / 1080f;
        scaleFactorY = screenHeight / 1920f;
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
        for (SimpleButton simpleButton : gameButtons)
            if(simpleButton.isTouchDown(scaleX(screenX), scaleY(screenY))) {
                if (someButtonDown) {
                    for (SimpleButton b : gameButtons) {
                        if (b == simpleButton) continue;
                        if (b.isTouchUp(scaleX(screenX), scaleY(screenY)))
                            break;
                    }
                }
                else
                    someButtonDown = true;

                if (simpleButton.getId().equals("LEFT")) myWorld.tryFigureMoveLeft();
                if (simpleButton.getId().equals("RIGHT")) myWorld.tryFigureMoveRight();
                if (simpleButton.getId().equals("DOWN")) myWorld.getFigure().decFallDelay();
                if (simpleButton.getId().equals("ROTATE")) myWorld.tryFigureRotate();

                return true;
            }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        someButtonDown = false;
        for (SimpleButton simpleButton : gameButtons)
            if(simpleButton.isTouchUp(scaleX(screenX), scaleY(screenY)))
                return true;
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

    public List<SimpleButton> getGameButtons() {
        return gameButtons;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return 1920 - (int) (screenY / scaleFactorY);
    }
}
