package edu.amd.spbstu.elevator.AppEntities.Game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import edu.amd.spbstu.elevator.AppEntities.Level;

class GameUpdateThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private boolean flagRun = false;

    private Game game;
    private Level level;

    public GameUpdateThread(GameView gameView, Game game, SurfaceHolder surfaceHolder, Resources res) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
        this.level = game.getCurLevel();
    }

    public void setRunning(boolean run) {
        flagRun = run;
    }

    @Override
    public void run() {
        while (flagRun) {
            synchronized (surfaceHolder) {
                //if (level.getTime().getLevelTimerDelta() >= 16) {
                    calculate();
                    level.getTime().updatePreviousTime();
                //}
                surfaceHolder.notify();
            }

        }

    }


    private void calculate() {
        level.getTime().updateCurrentTime();

        game.update();
    }
}
