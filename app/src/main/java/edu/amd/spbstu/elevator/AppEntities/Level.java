package edu.amd.spbstu.elevator.AppEntities;

import android.graphics.Canvas;

public interface Level {

    void update();

    boolean onTouch(int x, int y, int eventType);

    Time getTime();

    void onDraw(Canvas c);

    Integer getPersonsCount();
    void setPersonsCount(Integer pCount);
}
