package edu.amd.spbstu.elevator.AppEntities.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import edu.amd.spbstu.elevator.AmdIntro.AppIntro;
import edu.amd.spbstu.elevator.MainActivity;

public class HelpView extends ScrollView {
    LinearLayout linearLayout;
    MainActivity m_app;
    public HelpView(Context context) {
        super(context);
        m_app = (MainActivity)context;

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.END);
        linearLayout.setBackgroundColor(Color.LTGRAY);

        TextView textView1 = new TextView(context);


        TextView textView2 = new TextView(context);
        if(m_app.getLanguage() == AppIntro.LANGUAGE_RUS) {
            textView1.setText("Описание игрового процесса:\n");
            textView2.setText("\nК лифту на каждом этаже подходят клиенты, над каждым клиентом выше нарисован круг, в котором \n" +
                    "написана цифра желаемого этажа - куда ему нужно попасть. У каждого клиента есть \"настроение\" - индикатор, отражающий \n" +
                    "степень недовольства ожиданием. Чем больше клиент ждет, тем больше его недовольство. В системе рассчитывается также некий средний \n" +
                    "по всем клиентам индикатор недовольства. Как только этот средний показатель превышает заданный для этого уровня барьер, так игра \n" +
                    "считается проигранной, то есть в здании оказалось слишком много недовольных лифтом клиентов. \n\n\n" +
                    "Управление:\n\n" +
                    "- Лифтом: нужно ткнуть в этаж над или под текущим положением кабины \n" +
                    "- Клиентами: нужно ткнуть в клиента, если он вне кабины для того, чтобы он вошел в кабину. Если клиент в кабине, нужно в него ткнуть, чтобы он вышел. \n\n\n");
        }
        else {
            textView1.setText("Gameplay:\n");
            textView2.setText("\nClients use the elevator on each floor, above each client there is a circle drawn in which\n" +
                    "written the number of the floor where he needs to go. Every client has a \"mood\", a certain indicator that reflects\n" +
                    "degree of dissatisfaction with expectation. The more the client waits, the more his discontent. The system also calculates a certain average\n" +
                    "for all customers an indicator of discontent. As soon as this average exceeds the barrier set for this level, so the game\n" +
                    "It is considered lost, that is, there are too many customers dissatisfied with the elevator in the building.\n\n\n" +
                    "Control:\n\n" +
                    "- Lift: you need to poke into the floor above or below the current position of the cabin\n" +
                    "- Customers: you need to poke into the customer if he is outside the cabin in order for him to enter the cabin. If the client is in the cockpit, you need to poke him to go out.\n" +
                    "If the client goes to the wrong floor, which he originally wanted, then his mood indicator deteriorates.");
        }
        textView1.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        //textView1.setTextSize(getHeight() / 20);
        linearLayout.addView(textView1);
        linearLayout.addView(textView2);

        //textView2.setTextSize(getHeight() / 20);


        //add text view linearLayout.addView();
        //add text view linearLayout.addView();

        //add text view linearLayout.addView();
        //add image view linearLayout.addView();

        //add text view linearLayout.addView();
        //add image view linearLayout.addView();

        //add text view linearLayout.addView();
        //add image view linearLayout.addView();

        setOnTouchListener(m_app);
        addView(linearLayout);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public boolean onTouch(int x, int y, int evtType) {
        m_app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_app.setView(MainActivity.VIEW_MENU);
                setVisibility(View.INVISIBLE);
            }
        });
        return true;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        super.onDraw(canvas);
        invalidate();

    }
}
