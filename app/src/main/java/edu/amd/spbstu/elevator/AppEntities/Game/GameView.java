package edu.amd.spbstu.elevator.AppEntities.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.view.View;
import edu.amd.spbstu.elevator.AmdIntro.AppIntro;
import edu.amd.spbstu.elevator.MainActivity;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public MainActivity m_app;
    private GameDrawThread g_drawThread;
    //private GameUpdateThread g_updateThread;

    private Bitmaps bmps;

    public GameView(Context cntx) {
        super(cntx);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GameView(MainActivity app) {
        super(app);
        m_app = app;
        getHolder().addCallback(this);
        getHolder().setFormat(0x00000004); //RGB_565

        this.bmps = new Bitmaps(getResources());


        setOnTouchListener(app);
    }

    public boolean onTouch(int x, int y, int evtType) {
        if (evtType != AppIntro.TOUCH_DOWN)
            return false;

        if (g_drawThread != null && m_app.getAppGame().getState() == m_app.getAppGame().GAME_PLAYED) {
            //g_drawThread.handleTouch(m_app.getAppGame().GAME_PAUSE);
            m_app.getAppGame().onTouch(x, y, evtType);
            return true;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        g_drawThread = new GameDrawThread(this, this.m_app.getAppGame(), getHolder(), getResources());
        //g_updateThread = new GameUpdateThread(this, this.m_app.getAppGame(), getHolder(), getResources());

        g_drawThread.setRunning(true);
        //g_updateThread.setRunning(true);

        //g_updateThread.start();
        g_drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        g_drawThread.setRunning(false);
        while (retry) {
            try {
                g_drawThread.join();
                //g_updateThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //keep trying and crying T_T
            }
        }

    }

    public void start() {
        if (g_drawThread != null)
            g_drawThread.setRunning(true);
//        if (g_updateThread != null)
//            g_updateThread.setRunning(true);
    }

    public void stop() {
        if (g_drawThread != null)
            g_drawThread.setRunning(false);
//        if (g_updateThread != null)
//            g_updateThread.setRunning(false);
    }

    public GameDrawThread getDrawThread() {
        return g_drawThread;
    }

    public void setDrawThread(GameDrawThread gdt) {
        this.g_drawThread = gdt;
    }
    public Bitmaps getBmps() {
        return bmps;
    }


}
