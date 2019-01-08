package edu.amd.spbstu.elevator.AppEntities;

import android.content.res.Resources;
import android.graphics.*;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;
import edu.amd.spbstu.elevator.R;

import java.util.ArrayList;

public class Floor {

    Bitmap bmp;

    Matrix matrix;
    ArrayList<Person> persons;
    public RectF rect;

    private float sx;
    private float sy;

    Integer number;

    BackgroundObject flooring;
    public Position spawnPosition; //behind the screen same as spawn position
    public Position outPosition; //close to the elevator
    public Position waitPosition;

    public Floor(GameView gameView, float dx, float dy, float yScale, Bitmap bmp, int number) {
        this.bmp = bmp;
        matrix = new Matrix();

        sx = ((float) gameView.m_app.getScreenWidth()) * 2 / 3 / bmp.getWidth();
        sy = ((float) gameView.m_app.getScreenHeight()) * 1 / yScale / bmp.getHeight();

        matrix.preTranslate(dx, dy);
        matrix.preScale(sx, sy);

        rect = new RectF(0.0f, 0.0f, (float) bmp.getWidth() * sx, (float) bmp.getHeight() * sy);
        rect.offset(dx, dy);

        this.number = number;

        this.persons = new ArrayList<>();
        //flooring
        this.flooring = new BackgroundObject((int)rect.left, (int)(rect.bottom - rect.height() / 6),
                rect.right, rect.bottom, gameView.getBmps().bmpBgObj);

        outPosition = new Position(rect.right - rect.width() / 3, flooring.top - gameView.m_app.getScreenHeight() / 10,
                rect.right - rect.width() / 6, flooring.top);
        spawnPosition = new Position(0.0f, 0.0f, 0.0f, flooring.top);
        waitPosition = new Position((int)(rect.right - rect.width() / 2), 0.0f, (int)(rect.right - rect.width() / 2), (int)flooring.top);
        }



    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, matrix, null);
        //c.drawRect(spawnPosition, new Paint());
        //c.drawRect(outPosition, new Paint());
        flooring.onDraw(c);
    }

    public Integer getNumber() {
        return number;
    }

    public float getSx() {
        return sx;
    }
    public float getSy() {
        return sy;
    }

    public void update() {
        if (waitPosition.getState() == Position.FREE) {
            //TODO move on wait position for 0 indexed person
            if(persons.size() != 0) {
                Person p;
                p = persons.get(0);
                p.setDestination(waitPosition);
                p.state = Person.MOVING_TO_POSITION;
                waitPosition.setState(Position.BUSY);
            }
        }
    }
}
