package com.posedanto.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.posedanto.TweenAccessors.Transition;
import com.posedanto.helpers.AssetLoader;
import com.posedanto.kubikgame.kGame;
import com.posedanto.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by Agitatore on 29.04.2017.
 */

public class StartScreen implements Screen {

    private List<SimpleButton> menuButtons;
    private boolean someButtonDown = false;
    private float scaleFactorX;
    private float scaleFactorY;
    private Game game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private boolean isStarted = false;

    public StartScreen(kGame kGame) {
        game = kGame;
        menuButtons = new ArrayList<SimpleButton>();
        menuButtons.add(new SimpleButton(190, 1100, 700, 200, AssetLoader.buttonNewGame, AssetLoader.buttonNewGameDown, "NEW_GAME"));
        menuButtons.add(new SimpleButton(190, 710, 700, 200, AssetLoader.buttonExit, AssetLoader.buttonExitDown, "EXIT"));
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        scaleFactorX = screenWidth / 1080f;
        scaleFactorY = screenHeight / 1920f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1080, 1920);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        Transition.prepareTransition(255, 255, 255, 0, null, false);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                for (SimpleButton simpleButton : menuButtons)
                    if(simpleButton.isTouchDown(scaleX(screenX), scaleY(screenY))) {
                        if (someButtonDown)
                            for (SimpleButton b : menuButtons) {
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

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                for (SimpleButton simpleButton : menuButtons)
                    if (pointer == simpleButton.getPointer() && simpleButton.isTouchUp(scaleX(screenX), scaleY(screenY))) {
                        someButtonDown = false;

                        if (simpleButton.getId().equals("NEW_GAME")) doTransition();
                        if (simpleButton.getId().equals("EXIT")) Gdx.app.exit();

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

            private int scaleX(int screenX) {
                return (int) (screenX / scaleFactorX);
            }

            private int scaleY(int screenY) {
                return 1920 - (int) (screenY / scaleFactorY);
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100/255f, 200/255f, 100/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (SimpleButton button : menuButtons)
        button.draw(batch);
        batch.end();
        Transition.drawTransition(shapeRenderer, delta);
    }

    private void doTransition() {
        if (isStarted) return;
        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen());
            }
        };
        Transition.prepareTransition(255, 255, 255, .4f, cb, true);
        isStarted = true;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
