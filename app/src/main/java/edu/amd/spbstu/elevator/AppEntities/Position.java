package edu.amd.spbstu.elevator.AppEntities;

import android.graphics.RectF;

public class Position extends RectF {
    public static final boolean FREE = true;
    public static final boolean BUSY = false;

    private boolean state;

    Position(RectF r) {
        super(r);
        this.state = true;
    }
    Position(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        this.state = true;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }


    public boolean onTouch(int x, int y, int evtType) {
        return this.contains(x, y);
    }
}
