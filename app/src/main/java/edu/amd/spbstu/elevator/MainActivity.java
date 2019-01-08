package edu.amd.spbstu.elevator;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

import edu.amd.spbstu.elevator.AmdIntro.AppIntro;
import edu.amd.spbstu.elevator.AmdIntro.ViewIntro;
import edu.amd.spbstu.elevator.AppEntities.Game.*;
import edu.amd.spbstu.elevator.AppEntities.Menu;

public class MainActivity extends Activity implements View.OnTouchListener, MediaPlayer.OnCompletionListener {

    // ********************************************
    // CONST
    // ********************************************

    public static final int VIEW_INTRO = 0;
    public static final int VIEW_MENU = 1;
    public static final int VIEW_GAME = 2;
    public static final int VIEW_FAILURE = 3;
    public static final int VIEW_WIN = 4;
    public static final int VIEW_HELP = 5;


    // *************************************************
    // DATA
    // *************************************************
    int m_viewCur = -1;
    int m_modeCur = -1;
    int language;

    private AppIntro m_appIntro;

    private Game m_appGame;
    private Menu m_appMenu;

    private ViewIntro m_viewIntro;
    public MenuView m_viewMenu;
    private GameView m_viewGame;
    private FailureView m_viewFailure;
    private WinView m_viewWin;
    private HelpView m_viewHelp;

    // screen dim
    private int m_screenW;
    private int m_screenH;

    private String m_log = "ELEV";

    // *************************************************
    // METHODS
    // *************************************************
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(0, 0);
        // No Status bar
        final Window win = getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Application is never sleeps
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        m_screenW = point.x;
        m_screenH = point.y;

        Log.d(m_log, "Screen size is " + String.valueOf(m_screenW) + " * " + String.valueOf(m_screenH));

        // Detect language
        String strLang = Locale.getDefault().getDisplayLanguage();
        if (strLang.equalsIgnoreCase("english")) {
            Log.d(m_log, "LOCALE: English");
            language = AppIntro.LANGUAGE_ENG;
        } else if (strLang.equalsIgnoreCase("русский")) {
            Log.d(m_log, "LOCALE: Russian");
            language = AppIntro.LANGUAGE_RUS;
        } else {
            Log.d(m_log, "LOCALE unknown: " + strLang);
            language = AppIntro.LANGUAGE_UNKNOWN;
        }

        // Create application intro
        m_appIntro = new AppIntro(this, language);

        // Create view
        setView(VIEW_INTRO);
    }

    public int getLanguage() {
        return language;
    }

    public AppIntro getAppIntro() {
        return m_appIntro;
    }

    public Game getAppGame() {
        return m_appGame;
    }

    public ViewIntro getViewIntro() {
        return m_viewIntro;
    }

    public GameView getGameView() {
        return m_viewGame;
    }

    public MenuView getMenuView() {
        return m_viewMenu;
    }

    public FailureView getFailureView() {
        return m_viewFailure;
    }

    public int getScreenWidth() {
        return m_screenW;
    }

    public int getScreenHeight() {
        return m_screenH;
    }

    public int getView() {
        return m_viewCur;
    }

    public void setView(int viewID) {
        if (m_viewCur == viewID) {
            Log.d(m_log, "setView: already set");
            return;
        }

        m_viewCur = viewID;
        if (m_viewCur == VIEW_INTRO) {
            m_viewIntro = new ViewIntro(this);
            setContentView(m_viewIntro);
        }
        if (m_viewCur == VIEW_MENU) {
            m_viewMenu = new MenuView(this);
            setContentView(m_viewMenu);
        }
        if (m_viewCur == VIEW_GAME) {
            m_viewGame = new GameView(this);
            int level = m_viewMenu.getLevel();
            m_appGame = new Game(this, this.getLanguage(), level);
            setContentView(m_viewGame);
        }
        if (m_viewCur == VIEW_HELP) {
            m_viewHelp = new HelpView(this);
            setContentView(m_viewHelp);
        }

        if (m_viewCur == VIEW_FAILURE) {
            m_viewFailure = new FailureView(this, language);
            setContentView(m_viewFailure);
        }
        if (m_viewCur == VIEW_WIN) {
            m_viewWin = new WinView(this, language);
            setContentView(m_viewWin);
        }
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.

        // delayedHide(100);
    }

    public void onCompletion(MediaPlayer mp) {
        Log.d(m_log, "onCompletion: Video play is completed");
        //switchToGame();
    }

    public boolean onTouch(View vsetView, MotionEvent evt) {
        int x = (int) evt.getX();
        int y = (int) evt.getY();
        int touchType = AppIntro.TOUCH_DOWN;

        //if (evt.getAction() == MotionEvent.ACTION_DOWN)
        //  Log.d("DCT", "Touch pressed (ACTION_DOWN) at (" + String.valueOf(x) + "," + String.valueOf(y) +  ")"  );

        if (evt.getAction() == MotionEvent.ACTION_MOVE)
            touchType = AppIntro.TOUCH_MOVE;
        if (evt.getAction() == MotionEvent.ACTION_UP)
            touchType = AppIntro.TOUCH_UP;

        if (m_viewCur == VIEW_INTRO)
            return m_viewIntro.onTouch(x, y, touchType);
        //if (m_viewCur == VIEW_MENU)
        //  return m_viewMenu.onTouch(x, y, touchType);
        if (m_viewCur == VIEW_GAME)
            return m_viewGame.onTouch(x, y, touchType);
        if (m_viewCur == VIEW_FAILURE)
            return m_viewFailure.onTouch(x, y, touchType);
        if (m_viewCur == VIEW_WIN)
            return m_viewWin.onTouch(x, y, touchType);
        if (m_viewCur == VIEW_HELP)
            return m_viewHelp.onTouch(x, y, touchType);
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent evt) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (m_viewCur == VIEW_MENU) {
                boolean ret = super.onKeyDown(keyCode, evt);
                return ret;
            }
            if (m_viewCur == VIEW_GAME) {
                m_viewGame.stop();
                m_viewGame.setVisibility(View.INVISIBLE);
                setView(VIEW_MENU);
                return true;
            }
            if (m_viewCur == VIEW_HELP) {
                m_viewHelp.setVisibility(View.INVISIBLE);
                setView(VIEW_MENU);
                return true;
            }
            if (m_viewCur == VIEW_FAILURE) {
                m_viewFailure.setVisibility(View.INVISIBLE);
                setView(VIEW_MENU);
                return true;
            }
            if (m_viewCur == VIEW_WIN) {
                m_viewWin.setVisibility(View.INVISIBLE);
                setView(VIEW_MENU);
                return true;
            }
            //Log.d("DCT", "Back key pressed");
        }


        if (keyCode == KeyEvent.KEYCODE_HOME) {
            if (m_appGame != null && m_viewGame != null) {
                m_viewGame.stop();
                m_appGame.updateState(Game.GAME_PAUSE);
            }

            return true;
        }

        boolean ret = super.onKeyDown(keyCode, evt);
        return ret;
    }

    protected void onResume() {
        super.onResume();
        if (m_viewCur == VIEW_INTRO)
            m_viewIntro.start();
        if (m_viewCur == VIEW_GAME) {
            m_viewGame.start();
        }
        //Log.d(m_log, "App onResume");
    }

    protected void onPause() {
        // stop anims
        if (m_viewCur == VIEW_INTRO)
            m_viewIntro.stop();
        if (m_viewCur == VIEW_GAME) {
            m_viewGame.stop();
        }
        // complete system
        super.onPause();
        //Log.d(m_log, "App onPause");
    }

    protected void onDestroy() {
        if (m_viewCur == VIEW_MENU) {
            //m_viewMenu.onDestroy();
        }
        super.onDestroy();
        //Log.d("DCT", "App onDestroy");
    }

    public void onConfigurationChanged(Configuration confNew) {
        super.onConfigurationChanged(confNew);
        m_viewIntro.onConfigurationChanged(confNew);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
