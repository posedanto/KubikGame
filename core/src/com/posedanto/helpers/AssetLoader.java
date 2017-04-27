package com.posedanto.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Agitatore on 25.03.2017.
 */

public class AssetLoader {
    public static Texture field, brick, brick2, scoreboard, next;
    public static Texture[] rainbowBricks = new Texture[7];
    public static Texture buttonLeft, buttonLeftDown,buttonRight, buttonRightDown,
            buttonDown, buttonDownDown, buttonRotate, buttonRotateDown,
            buttonPause, buttonPauseDown;
    public static Texture buttonContinue, buttonContinueDown, buttonNewGame, buttonNewGameDown,
            buttonExit, buttonExitDown;
    public static BitmapFont font;

    public static Preferences preferences;

    public static void load() {
        preferences = Gdx.app.getPreferences("KubikGame");
        if (!preferences.contains("highScore"))
            preferences.putInteger("highScore", 0);
        field = new Texture(Gdx.files.internal("field.png"));
        brick = new Texture(Gdx.files.internal("brick.png"));
        brick2 = new Texture(Gdx.files.internal("brick2.png"));
        scoreboard = new Texture(Gdx.files.internal("scoreboard.png"));
        next = new Texture(Gdx.files.internal("next.png"));

        rainbowBricks[0] = new Texture(Gdx.files.internal("brickRED.png"));
        rainbowBricks[1] = new Texture(Gdx.files.internal("brickORANGE.png"));
        rainbowBricks[2] = new Texture(Gdx.files.internal("brickYELLOW.png"));
        rainbowBricks[3] = new Texture(Gdx.files.internal("brickGREEN.png"));
        rainbowBricks[4] = new Texture(Gdx.files.internal("brickBLUE.png"));
        rainbowBricks[5] = new Texture(Gdx.files.internal("brickINDIGO.png"));
        rainbowBricks[6] = new Texture(Gdx.files.internal("brickVIOLET.png"));

        buttonLeft = new Texture(Gdx.files.internal("buttonLEFT.png"));
        buttonLeftDown = new Texture(Gdx.files.internal("buttonLEFTpressed.png"));
        buttonRight = new Texture(Gdx.files.internal("buttonRIGHT.png"));
        buttonRightDown = new Texture(Gdx.files.internal("buttonRIGHTpressed.png"));
        buttonDown = new Texture(Gdx.files.internal("buttonDOWN.png"));
        buttonDownDown = new Texture(Gdx.files.internal("buttonDOWNpressed.png"));
        buttonRotate = new Texture(Gdx.files.internal("buttonROTATE.png"));
        buttonRotateDown = new Texture(Gdx.files.internal("buttonROTATEpressed.png"));
        buttonPause = new Texture(Gdx.files.internal("buttonPAUSE.png"));
        buttonPauseDown = new Texture(Gdx.files.internal("buttonPAUSEpressed.png"));

        buttonContinue = new Texture(Gdx.files.internal("continue.png"));
        buttonContinueDown = new Texture(Gdx.files.internal("continuePRESSED.png"));
        buttonNewGame = new Texture(Gdx.files.internal("newgame.png"));
        buttonNewGameDown = new Texture(Gdx.files.internal("newgamePRESSED.png"));
        buttonExit = new Texture(Gdx.files.internal("exit.png"));
        buttonExitDown = new Texture(Gdx.files.internal("exitPRESSED.png"));

        font = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    public static void setHighScore(int val)
    {
        preferences.putInteger("highScore", val);
        preferences.flush();
    }

    public static int getHighScore() {
        return preferences.getInteger("highScore");
    }

    public static void dispose() {
        field.dispose();
        brick.dispose();
        brick2.dispose();
        scoreboard.dispose();
        next.dispose();
        for(Texture b : rainbowBricks)
            b.dispose();
        font.dispose();
    }
}
