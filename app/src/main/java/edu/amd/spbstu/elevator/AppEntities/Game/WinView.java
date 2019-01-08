
package edu.amd.spbstu.elevator.AppEntities.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import edu.amd.spbstu.elevator.AppEntities.Person;
import edu.amd.spbstu.elevator.MainActivity;

public class WinView extends View {
    static public final int LANGUAGE_ENG = 0;
    static public final int LANGUAGE_RUS = 1;
    static public final int LANGUAGE_UNKNOWN = 2;

    int language;

    Paint p;
    MainActivity m_app;

    public WinView(Context context) {
        super(context);
    }

    public WinView(MainActivity context, int language) {
        super(context);
        m_app = context;
        this.language = language;

        p = new Paint();
        p.setColor(Color.GREEN);
        p.setTextSize(m_app.getScreenHeight() / 20);
        p.setTextAlign(Paint.Align.CENTER);
        setOnTouchListener(m_app);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawColor(Color.LTGRAY);
        int xPos = (getWidth() / 2);
        int yPos = (int) ((getHeight() / 2) - ((p.descent() + p.ascent()) / 2));

        if (language == LANGUAGE_RUS) {
            c.drawText("Победа", xPos, yPos, p);

        } else {
            c.drawText("You win", xPos, yPos, p);
        }
    }


    public boolean onTouch(int x, int y, int eventType) {
        m_app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_app.setView(MainActivity.VIEW_MENU);
                setVisibility(View.INVISIBLE);
            }
        });

        return true;
    }
}


