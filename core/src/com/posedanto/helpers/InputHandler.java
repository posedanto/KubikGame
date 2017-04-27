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
    private List<SimpleButton> gameButtons, pauseButtons, scoreboardButtons;

    private float scaleFactorX;
    private float scaleFactorY;

    private boolean someButtonDown = false;
    private int pointerButtonDown = -1;
    private MoveFigureEvent moveFigureEvent;

    public InputHandler(GameWorld myWorld) {
        this.myWorld = myWorld;
        gameButtons = new ArrayList<SimpleButton>();
        gameButtons.add(new SimpleButton(10, 80, 250, 250, AssetLoader.buttonLeft, AssetLoader.buttonLeftDown, "LEFT"));
        gameButtons.add(new SimpleButton(280, 80, 250, 250, AssetLoader.buttonRight, AssetLoader.buttonRightDown, "RIGHT"));
        gameButtons.add(new SimpleButton(550, 80, 250, 250, AssetLoader.buttonDown, AssetLoader.buttonDownDown, "DOWN"));
        gameButtons.add(new SimpleButton(820, 80, 250, 250, AssetLoader.buttonRotate, AssetLoader.buttonRotateDown, "ROTATE"));
        gameButtons.add(new SimpleButton(900, 1770, 100, 100, AssetLoader.buttonPause, AssetLoader.buttonPauseDown, "PAUSE"));

        pauseButtons = new ArrayList<SimpleButton>();
        pauseButtons.add(new SimpleButton(190, 1150, 700, 200, AssetLoader.buttonContinue, AssetLoader.buttonContinueDown, "CONTINUE"));
        pauseButtons.add(new SimpleButton(190, 900, 700, 200, AssetLoader.buttonNewGame, AssetLoader.buttonNewGameDown, "NEW_GAME"));
        pauseButtons.add(new SimpleButton(190, 650, 700, 200, AssetLoader.buttonExit, AssetLoader.buttonExitDown, "EXIT"));

        scoreboardButtons = new ArrayList<SimpleButton>();
        scoreboardButtons.add(new SimpleButton(277, 800, 525, 150, AssetLoader.buttonNewGame, AssetLoader.buttonNewGameDown, "NEW_GAME"));
        scoreboardButtons.add(new SimpleButton(277, 600, 525, 150, AssetLoader.buttonExit, AssetLoader.buttonExitDown, "EXIT"));

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
        if (myWorld.isPause())
            return touchDownPauseButtons(screenX, screenY, pointer);
        if (myWorld.isGameOver() || myWorld.isHighscore())
            touchDownScoreboardButtons(screenX, screenY, pointer);
        if (myWorld.isRunning())
            return touchDownGameButtons(screenX, screenY, pointer);
        return false;
    }

    private boolean touchDownPauseButtons(int screenX, int screenY, int pointer) {
        for (SimpleButton simpleButton : pauseButtons)
            if(simpleButton.isTouchDown(scaleX(screenX), scaleY(screenY))) {
                if (someButtonDown)
                    for (SimpleButton b : pauseButtons) {
                        if (b == simpleButton) continue;
                        if (b.isTouchUp(screenX, screenY))
                            break;
                    }
                simpleButton.setPointer(pointer);
                someButtonDown = true;

                return true;
            }
        return false;
    }

    private boolean touchDownScoreboardButtons(int screenX, int screenY, int pointer) {
        for (SimpleButton simpleButton : scoreboardButtons)
            if(simpleButton.isTouchDown(scaleX(screenX), scaleY(screenY))) {
                if (someButtonDown)
                    for (SimpleButton b : scoreboardButtons) {
                        if (b == simpleButton) continue;
                        if (b.isTouchUp(screenX, screenY))
                            break;
                    }
                simpleButton.setPointer(pointer);
                someButtonDown = true;

                return true;
            }
        return false;
    }

    private boolean touchDownGameButtons(int screenX, int screenY, int pointer) {
        for (SimpleButton simpleButton : gameButtons)
            if(simpleButton.isTouchDown(scaleX(screenX), scaleY(screenY))) {
                if (pointerButtonDown != -1)
                    for (SimpleButton b : gameButtons) {
                        if (b.getPointer() == pointerButtonDown) {
                            if (b == simpleButton) continue;
                            if (b.getId().equals("LEFT"))
                                moveFigureEvent.stop();
                            if (b.getId().equals("RIGHT")) moveFigureEvent.stop();
                            if (b.getId().equals("DOWN")) myWorld.getFigure().setFallDelayDefault();
                            b.isTouchUp(screenX, screenY);
                            break;
                        }
                    }

                if ((simpleButton.getPointer() == -1) && (simpleButton.getId().equals("LEFT"))) {
                    myWorld.tryFigureMoveLeft();
                    moveFigureEvent = new MoveFigureEvent(myWorld, "LEFT");
                }

                if ((simpleButton.getPointer() == -1) && (simpleButton.getId().equals("RIGHT"))) {
                    myWorld.tryFigureMoveRight();
                    moveFigureEvent = new MoveFigureEvent(myWorld, "RIGHT");
                }
                if (simpleButton.getId().equals("DOWN")) myWorld.getFigure().decFallDelay();
                if (simpleButton.getId().equals("ROTATE")) myWorld.tryFigureRotate();

                simpleButton.setPointer(pointer);
                pointerButtonDown = pointer;
                return true;
            }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if ( myWorld.isPause())
            return touchUpPauseButtons(screenX, screenY, pointer);
        if (myWorld.isGameOver() || myWorld.isHighscore())
            touchUpScoreboardButtons(screenX, screenY, pointer);
        if (myWorld.isRunning())
            return touchUpGameButtons(screenX, screenY, pointer);
        return false;
    }

    private boolean touchUpPauseButtons(int screenX, int screenY, int pointer) {
        for (SimpleButton simpleButton : pauseButtons)
            if (pointer == simpleButton.getPointer() && simpleButton.isTouchUp(scaleX(screenX), scaleY(screenY))) {
                someButtonDown = false;

                if (simpleButton.getId().equals("CONTINUE")) myWorld.continueGame();
                if (simpleButton.getId().equals("NEW_GAME")) myWorld.resetGame();
                if (simpleButton.getId().equals("EXIT")) Gdx.app.exit();

                return true;
            }
        return false;
    }

    private boolean touchUpScoreboardButtons(int screenX, int screenY, int pointer) {
        for (SimpleButton simpleButton : scoreboardButtons)
            if (pointer == simpleButton.getPointer() && simpleButton.isTouchUp(scaleX(screenX), scaleY(screenY))) {
                someButtonDown = false;

                if (simpleButton.getId().equals("NEW_GAME")) myWorld.resetGame();
                if (simpleButton.getId().equals("EXIT")) Gdx.app.exit();

                return true;
            }
        return false;
    }

    private boolean touchUpGameButtons(int screenX, int screenY, int pointer) {
        for (SimpleButton simpleButton : gameButtons)
            if (pointer == simpleButton.getPointer()) {
                if (simpleButton.isTouchUp(scaleX(screenX), scaleY(screenY)))
                    if (simpleButton.getId().equals("PAUSE")) myWorld.setState(GameWorld.GameState.PAUSE);

                if (simpleButton.getId().equals("LEFT")) moveFigureEvent.stop();
                if (simpleButton.getId().equals("RIGHT")) moveFigureEvent.stop();
                if (simpleButton.getId().equals("DOWN")) myWorld.getFigure().setFallDelayDefault();
                pointerButtonDown = -1;
                return true;
            }
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

    public List<SimpleButton> getPauseButtons() {
        return pauseButtons;
    }

    public List<SimpleButton> getScoreboardButtons() {
        return scoreboardButtons;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return 1920 - (int) (screenY / scaleFactorY);
    }
}
