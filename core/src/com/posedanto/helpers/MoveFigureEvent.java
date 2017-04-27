package com.posedanto.helpers;

import com.badlogic.gdx.Gdx;
import com.posedanto.gameworld.GameWorld;

/**
 * Created by Agitatore on 27.04.2017.
 */

public class MoveFigureEvent implements Runnable{
    Thread thread;
    boolean isRunning;
    GameWorld myWorld;

    public MoveFigureEvent(GameWorld world, String name) {
        myWorld = world;
        isRunning = true;
        Gdx.app.log("Thread ", name + " started");
        thread = new Thread(this, name);
        thread.start();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(100);
                if (!isRunning) break;
                if(thread.getName().equals("LEFT"))
                    myWorld.tryFigureMoveLeft();
                if(thread.getName().equals("RIGHT"))
                    myWorld.tryFigureMoveRight();
            }
            catch (InterruptedException exc) {
                System.out.println("Thread.sleep error");
            }
        }
        Gdx.app.log("Thread ", thread.getName() + " stopped");
    }
}
