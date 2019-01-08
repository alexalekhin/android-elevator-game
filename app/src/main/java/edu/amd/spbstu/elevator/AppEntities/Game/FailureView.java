package edu.amd.spbstu.elevator.AppEntities.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import edu.amd.spbstu.elevator.AppEntities.Person;
import edu.amd.spbstu.elevator.MainActivity;

public class FailureView extends View {
    Paint p;
    MainActivity m_app;

    static public final int LANGUAGE_ENG = 0;
    static public final int LANGUAGE_RUS = 1;
    static public final int LANGUAGE_UNKNOWN = 2;

    int language;

    public FailureView(Context context) {
        super(context);
    }

    public FailureView(MainActivity context, int language) {
        super(context);
        m_app = context;
        this.language = language;

        p = new Paint();
        p.setColor(Color.RED);
        p.setTextSize(m_app.getScreenHeight() / 20);
        p.setTextAlign(Paint.Align.CENTER);
        setOnTouchListener(m_app);

    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawColor(Color.LTGRAY);
        int xPos = (getWidth() / 2);
        int yPos = (int) ((getHeight() / 2) - ((p.descent() + p.ascent()) / 2));

        if (language == LANGUAGE_RUS) {
            c.drawText("Проигрыш", xPos, yPos, p);

        } else {
            c.drawText("You lost", xPos, yPos, p);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
