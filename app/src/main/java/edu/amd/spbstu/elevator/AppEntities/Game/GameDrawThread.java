package edu.amd.spbstu.elevator.AppEntities.Game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

import edu.amd.spbstu.elevator.AppEntities.Level;

public class GameDrawThread extends Thread {
    private boolean flagRun = false;
    private final SurfaceHolder surfaceHolder;

    private Game game;
    private Level level;


    public GameDrawThread(GameView gameView, Game game, SurfaceHolder surfaceHolder, Resources res) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
        this.level = game.getCurLevel();
    }

    public void setRunning(boolean run) {
        flagRun = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (flagRun) {
            canvas = null;
            try {
                synchronized (surfaceHolder) {
                    canvas = surfaceHolder.lockCanvas();
                    //surfaceHolder.wait();
                    calculate();
                    if (canvas != null) {
                        onDraw(canvas);
                    }

                }
            } /*catch (/*InterruptedException e) {
                //e.printStackTrace();
            }*/ catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
            if (level.getTime().getLevelTimerDelta() > 16) {
                //Update last checked time
                level.getTime().updatePreviousTime();
            }
            //level.getTime().updatePreviousTime();
        }

    }


    private void calculate() {
        //Check current time
        level.getTime().updateCurrentTime();
        //level.getTime().getLevelTimerDelta();



        game.update();
    }

    private void onDraw(Canvas c) throws InterruptedException {
        c.drawColor(Color.BLACK);
        //if (3 - level.getTime().getLevelTimerDelta() > 0)
        //  Thread.sleep(3 - level.getTime().getLevelTimerDelta());
        level.onDraw(c);
    }

}


