package com.posedanto.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.posedanto.gameobjects.Field;
import com.posedanto.gameobjects.Figure;
import com.posedanto.gameobjects.FigureForms;
import com.posedanto.gameobjects.NextFigureField;
import com.posedanto.helpers.AssetLoader;
import com.posedanto.helpers.InputHandler;
import com.posedanto.ui.SimpleButton;

import java.util.List;


/**
 * Created by Agitatore on 25.03.2017.
 */

public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Field myField;
    private Figure myFigure;
    private NextFigureField myNextFigureField;

    private Vector2[] fPosition = new Vector2[4];

    private float runTime;

    private Texture field, brick, brick2, scoreboard, next;
    private static Texture[] rainbowBricks;
    private BitmapFont font;

    private List<SimpleButton> gameButtons, pauseButtons, scoreboardButtons;

    public GameRenderer(GameWorld myWorld) {
        myField = myWorld.getField();
        myFigure = myWorld.getFigure();
        myNextFigureField = myWorld.getMyNextFigureField();
        this.myWorld = myWorld;

        runTime = -0.2f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1080, 1920);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        initAssets();

        gameButtons = ((InputHandler)Gdx.input.getInputProcessor()).getGameButtons();
        pauseButtons = ((InputHandler)Gdx.input.getInputProcessor()).getPauseButtons();
        scoreboardButtons = ((InputHandler)Gdx.input.getInputProcessor()).getScoreboardButtons();
    }

    private void initAssets() {
        field = AssetLoader.field;
        brick = AssetLoader.brick;
        brick2 = AssetLoader.brick2;
        scoreboard = AssetLoader.scoreboard;
        next = AssetLoader.next;
        rainbowBricks = AssetLoader.rainbowBricks;
        font = AssetLoader.font;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(19/255f, 29/255f, 48/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (myWorld.isFigureFell())
            runTime = -0.2f;
        batch.begin();
        drawField(delta);
        if (!myWorld.isLineRemoving())
            drawFigure();
        drawInfo();
        drawGameButtons();
        if (myWorld.isPause())
            drawPauseBoard();
        if (myWorld.isGameOver())
            drawScoreboard();
        if (myWorld.isHighscore())
            drawHighscoreBoard();
        batch.end();
    }

    private void drawInfo() {
        font.setColor(Color.RED);
        font.draw(batch, "Next:", 650, 1700, 430, Align.center, true);
        batch.draw(next, myNextFigureField.getPositionX() , myNextFigureField.getPositionY());

        fPosition[0].set(myNextFigureField.getFigurePosition()[0]);
        fPosition[1].set(myNextFigureField.getFigurePosition()[1]);
        fPosition[2].set(myNextFigureField.getFigurePosition()[2]);
        fPosition[3].set(myNextFigureField.getFigurePosition()[3]);

        for(Vector2 pos : fPosition)
            batch.draw(rainbowBricks[myNextFigureField.getFigureColorNumber()],
                    myNextFigureField.getFigurePositionX() + pos.x * Field.CELL_SIZE,
                    myNextFigureField.getFigurePositionY() + pos.y * Field.CELL_SIZE);

        font.draw(batch, "Score:", 650, 1250, 430, Align.center, true);
        font.draw(batch, "" + myWorld.getScore(), 650, 1100, 430, Align.center, true);

        font.setColor(Color.SKY);
        font.draw(batch, "Best:", 650, 950, 430, Align.center, true);
        font.draw(batch, "" + myWorld.getHighScore(), 650, 800, 430, Align.center, true);
    }

    private void drawPauseBoard() {
        batch.draw(scoreboard,  100, 600, 880, 800);
        for (SimpleButton button : pauseButtons)
            button.draw(batch);
    }

    private void drawGameButtons(){
        for (SimpleButton button : gameButtons)
            button.draw(batch);
    }

    private void drawScoreboardButtons(){
        for (SimpleButton button : scoreboardButtons)
            button.draw(batch);
    }

    private void drawField(float delta) {
        batch.draw(field, myField.getPositionX(), myField.getPositionY());
        for (int i = 0; i < Field.COUNT_CELLS_Y; i++) {
            if (myWorld.isLineRemoving() && (myWorld.getRemovingLine() == i)) {
                drawDeletingLine(delta, i);
                continue;
            }
            for (int j = 0; j < Field.COUNT_CELLS_X; j++)
                if (!myField.isEmpty(j, i))
                    batch.draw(rainbowBricks[myField.getCell(j, i)], myField.getPositionX() + j * Field.CELL_SIZE,
                            myField.getPositionY() + i * Field.CELL_SIZE);
        }
    }

    private void drawFigure() {
        fPosition = FigureForms.getCoordinates(myFigure.getForm(), myFigure.getRotation(),
                myFigure.getPosition());
        for(Vector2 pos : fPosition)
            batch.draw(rainbowBricks[myFigure.getColorNumber()],
                    myField.getPositionX() + pos.x * Field.CELL_SIZE,
                    myField.getPositionY() + pos.y * Field.CELL_SIZE);
    }

    private void drawDeletingLine(float delta, int deletingLine) {
        runTime += delta;
        int width;
        int padding;
        for (int i = 0; i < Field.COUNT_CELLS_X; i++) {
            if (runTime >= i*0.5/ Field.COUNT_CELLS_X) {
                if (runTime >= 0.6) {
                    width = (int) (Field.CELL_SIZE * (1.6 - 2.0 * runTime + 0.2 * i));
                    if (width <= 0) {
                        if (i == 9) {
                            runTime = 0;
                            myWorld.setState(GameWorld.GameState.LINE_REMOVED);
                        }
                        else
                            continue;
                    }
                    else if (width > Field.CELL_SIZE)
                        width = Field.CELL_SIZE;
                    padding = (Field.CELL_SIZE - width) / 2;
                    batch.draw(brick2, myField.getPositionX() + padding + i * Field.CELL_SIZE,
                            myField.getPositionY() + padding + deletingLine * Field.CELL_SIZE,
                            width, width);
                }
                else
                    batch.draw(brick2, myField.getPositionX() + i * Field.CELL_SIZE,
                            myField.getPositionY() + deletingLine * Field.CELL_SIZE);
            }
            else
                batch.draw(rainbowBricks[myField.getCell(i, deletingLine)], myField.getPositionX() + i * Field.CELL_SIZE,
                        myField.getPositionY() + deletingLine * Field.CELL_SIZE);
        }
    }

    private void drawScoreboard() {
        batch.draw(scoreboard,  100, 550, 880, 900);
        font.setColor(Color.YELLOW);
        font.draw(batch, "GAME OVER", 100, 1400, 880, Align.center, true);
        font.draw(batch, "SCORE: " + myWorld.getScore(), 100, 1250, 880, Align.center, true);
        font.draw(batch, "HIGHSCORE: " + myWorld.getHighScore(), 100, 1100, 880, Align.center, true);
        drawScoreboardButtons();
    }

    private  void drawHighscoreBoard() {
        batch.draw(scoreboard,  100, 550, 880, 900);
        font.setColor(Color.YELLOW);
        font.draw(batch, "GAME OVER", 100, 1400, 880, Align.center, true);
        font.draw(batch, "NEW HIGHSCORE!!! ", 100, 1250, 880, Align.center, true);
        font.draw(batch, "" + myWorld.getHighScore(), 100, 1100, 880, Align.center, true);
        batch.draw(AssetLoader.buttonNewGame,  277, 800, 525, 150);
        batch.draw(AssetLoader.buttonExit,  277, 600, 525, 150);
    }
}
