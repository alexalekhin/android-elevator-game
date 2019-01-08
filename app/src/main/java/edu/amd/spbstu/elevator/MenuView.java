package edu.amd.spbstu.elevator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;


public class MenuView extends LinearLayout {

    MainActivity m_app;
    int level;
    TextView gameStart;
    Button buttonLvl1;
    Button buttonLvl2;
    Button buttonLvl3;
    Button buttonHelp;

    public MenuView(Context context) {
        super(context);

        init(context);

        this.m_app = (MainActivity) context;

    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuView(MainActivity mainActivity, Context context) {
        super(context);

    }

    public MenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(Context context) {
        this.setOrientation(VERTICAL);
        gameStart = new TextView(context);
        buttonLvl1 = new Button(context);
        buttonLvl2 = new Button(context);
        buttonLvl3 = new Button(context);
        buttonHelp = new Button(context);

        gameStart.setText(R.string.game_start);
        buttonLvl1.setText(R.string.level1);
        buttonLvl2.setText(R.string.level2);
        buttonLvl3.setText(R.string.level3);
        buttonHelp.setText(R.string.help);

        gameStart.setAllCaps(true);
        gameStart.setTypeface(null, Typeface.BOLD_ITALIC);

        LayoutParams txtParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        txtParams.gravity = Gravity.BOTTOM;

        gameStart.setLayoutParams(txtParams);

        buttonLvl1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        buttonLvl2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        buttonLvl3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        buttonHelp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        this.addView(gameStart);
        this.addView(buttonLvl1);
        this.addView(buttonLvl2);
        this.addView(buttonLvl3);
        this.addView(buttonHelp);


        View.OnClickListener handler = new View.OnClickListener() {

            public void onClick(View v) {

                if (v == buttonLvl1) {
                    level = 1;
                    m_app.setView(MainActivity.VIEW_GAME);
                }
                if (v == buttonLvl2) {
                    level = 2;
                    m_app.setView(MainActivity.VIEW_GAME);
                }
                if (v == buttonLvl3) {
                    level = 3;
                    m_app.setView(MainActivity.VIEW_GAME);
                }
                if (v == buttonHelp) {
                    level = 3;
                    m_app.setView(MainActivity.VIEW_HELP);
                }

            }

        };
        buttonLvl1.setOnClickListener(handler);
        buttonLvl2.setOnClickListener(handler);
        buttonLvl3.setOnClickListener(handler);
        buttonHelp.setOnClickListener(handler);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        invalidate();
    }

    public int getLevel() {
        return level;
    }
}
