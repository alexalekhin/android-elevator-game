package edu.amd.spbstu.elevator.AppEntities.Game;

import android.view.View;
import edu.amd.spbstu.elevator.AppEntities.Level;
import edu.amd.spbstu.elevator.AppEntities.SimpleLevel;
import edu.amd.spbstu.elevator.MainActivity;



public class Game {
    private MainActivity m_app;
    private GameView gameView;
    private Bitmaps bmps;

    /*
     * Game states
     */
    public static final int GAME_LOST = 0;
    public static final int GAME_WIN = 1;
    public static final int GAME_PAUSE = 2;
    public static final int GAME_PLAYED = 3;

    public int language;

    private int state;

    /*
     * Game variables
     * */

    private Level curLevel;
    /*
     * Add some stats
     * */


    public Game(MainActivity app, int language, int level) {
        m_app = app;
        this.gameView = m_app.getGameView();
        this.language = language;

        //state = GAME_PAUSE;
        state = GAME_PLAYED;
        if (level == 1)
            curLevel = new SimpleLevel(this, this.gameView, 6, 100.0f, 3);
        if (level == 2)
            curLevel = new SimpleLevel(this, this.gameView, 20, 100.0f, 4);
        if (level == 3)
            curLevel = new SimpleLevel(this, this.gameView, 50, 100.0f, 5);
    }

    public void update() {
        if (state == GAME_PLAYED) {
            curLevel.update();
        } else if (state == GAME_PAUSE) {

        } else {
            gameView.stop();
            m_app.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (state == GAME_LOST)
                        m_app.setView(MainActivity.VIEW_FAILURE);
                    if (state == GAME_WIN)
                        m_app.setView(MainActivity.VIEW_WIN);
                    //show lose screen
                    /*try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    //m_app.setView(MainActivity.VIEW_MENU);
                    gameView.setVisibility(View.INVISIBLE);

                    if (gameView.getDrawThread() != null) {
                        gameView.getDrawThread().interrupt();
                        gameView.setDrawThread(null);
                    }
                }
            });

        }
    }

    public void updateState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public Level getCurrentLevel() {
        return this.curLevel;
    }

    Level getCurLevel() {
        return curLevel;
    }


    public boolean onTouch(int x, int y, int eventType) {
        this.curLevel.onTouch(x, y, eventType);
        return true;
    }
}
