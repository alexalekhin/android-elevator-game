package edu.amd.spbstu.elevator.AppEntities;

import android.content.res.Resources;
import android.graphics.*;
import edu.amd.spbstu.elevator.AppEntities.Game.Bitmaps;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;

import java.util.ArrayList;
import java.util.HashMap;

public class Elevator extends RectF {
    private Position position;

    private GameView gameView;

    private Bitmap bmp;
    private Matrix matrix;
    //scale factors
    private float sx;
    private float sy;
    /*
     * Movement
     */
    private int direction;
    Position destPosition;
    public float STD_SPEED = 1.0f;
    private double speed;
    private ArrayList<Position> elevatorPositions;
    /*
     * People
     * */
    private static final int maxPersonsInside = 9;
    Person person;
    private ArrayList<Floor> floors;
    private Floor curFloor;
    private int curFloorNum;
    //moving, standing etc

    public static final int WAITING = 0;
    public static final int MOVING = 1;
    private int state;
    int doorsState;

    public Elevator(GameView gameView, int x, int y, float yScale, Bitmap bmp, Floor floor, ArrayList<Floor> floors, ArrayList<Position> elevatorPositions) {
        super();
        this.gameView = gameView;
        sx = ((float) gameView.m_app.getScreenWidth()) / 3 / bmp.getWidth();
        sy = ((float) gameView.m_app.getScreenHeight()) / yScale / bmp.getHeight();

        //Elevator
        this.matrix = new Matrix();
        matrix.setScale(sx, sy);
        this.bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        matrix.reset();

        this.set(0.0f, 0.0f, (float) this.bmp.getWidth(), (float) this.bmp.getHeight());
        this.offset(x, y);
        matrix.setTranslate(x, y);

        //Person
        position = new Position(this.left + this.width() / 3,
                this.bottom - (this.height() * 1.6f / 5),
                this.right - this.width() / 3,
                this.bottom - this.height() / 6);
        person = null;

        //Movement
        this.direction = 0;
        this.speed = 0.0f;

        this.destPosition = new Position(this);
        this.state = WAITING;


        //Floors and positions
        this.elevatorPositions = elevatorPositions;
        this.curFloor = floor;
        this.floors = floors;
        curFloorNum = floor.getNumber();
    }

    void moveY(float path, float dy, int direction) {
        if (direction > 0 && path - direction * dy > 0.0f ||
                direction < 0 && path - direction * dy < 0.0f)
            this.offset(0.0f, direction * dy);
        else
            this.set(this.left, this.destPosition.top, this.right, this.destPosition.bottom);

        this.matrix.setTranslate(this.left, this.top);
    }

    public void update(Time time) {
        //TODO

        if (this.state == WAITING) {
            this.speed = 0.0f;
        } else if (this.state == MOVING) {
            this.speed = STD_SPEED;

            float path = destPosition.top - this.top;
            float dy = (float) speed * time.getLevelTimerDelta();

            if (Math.abs(path) == 0.0f) {
                this.state = WAITING;
                this.set(this.left, this.destPosition.top, this.right, this.destPosition.bottom);
            } else {
                if (path > 0.0f) {
                    direction = 1;
                } else if (path < 0.0f) {
                    direction = -1;
                }
                moveY(path, dy, direction);
            }
            position.set(position.left, this.bottom - this.height() / 6 - position.height(),
                    position.right, this.bottom - this.height() / 6);
        }

        /*
        if (this.state == MOVING) {
            this.speed = STD_SPEED;
            float path = destPosition.top - this.top;
            int direction = path > 0.0f ? 1 : -1;
            path = Math.abs(path);
            double dy = direction * speed * time.getLevelTimerDelta() / sy;
            if ((int) (path) != 0) {
                if (direction > 0) {
                    if ((int) (this.top + dy/2) <= (int) destPosition.top) {

                        matrix.reset();
                        matrix.preTranslate((int) (this.left), (int) (this.top + dy));
                        matrix.preScale(sx, sy);

                        this.offset((int) 0.0f, (int) Math.ceil(dy * sy));
                        position.set(this.left + this.width() / 3, this.bottom - (this.height() * 1.6f / 5),
                                this.right - this.width() / 3, this.bottom - this.height() / 6);

                    } else {
                        matrix.reset();
                        matrix.preTranslate(this.left, destPosition.top);
                        matrix.preScale(sx, sy);
                        this.state = WAITING;

                        this.set(this.left, destPosition.top, this.right, destPosition.top + this.height());
                        position.set(this.left + this.width() / 3, this.bottom - (this.height() * 1.6f / 5),
                                this.right - this.width() / 3, this.bottom - this.height() / 6);


                    }
                } else {
                    if ((int) (this.top + dy/2) >= (int) destPosition.top) {
                        matrix.reset();
                        matrix.preTranslate((int) (this.left), (int) (this.top + dy));
                        matrix.preScale(sx, sy);

                        this.offset((int) 0.0f, (int) Math.ceil(dy * sy));
                        position.set(this.left + this.width() / 3, this.bottom - (this.height() * 1.6f / 5),
                                this.right - this.width() / 3, this.bottom - this.height() / 6);

                    } else {
                        matrix.reset();
                        matrix.preTranslate(this.left, destPosition.top);
                        matrix.preScale(sx, sy);
                        this.state = WAITING;


                        this.set(this.left, destPosition.top, this.right, destPosition.top + this.height());
                        position.set(this.left + this.width() / 3, this.bottom - (this.height() * 1.6f / 5),
                                this.right - this.width() / 3, this.bottom - this.height() / 6);
                    }
                }
            } else {
                matrix.reset();
                matrix.preTranslate(this.left, destPosition.top);
                matrix.preScale(sx, sy);
                this.state = WAITING;

                this.set(this.left, destPosition.top, this.right, destPosition.top + this.height());
                position.set(this.left + this.width() / 3, this.bottom - (this.height() * 1.6f / 5),
                        this.right - this.width() / 3, this.bottom - this.height() / 6);
            }
        }
        */
    }

    private RectF getRect() {
        return this;
    }

    void openDoors() {

    }

    void isTeleportActivated(boolean isActive) {

    }

    public boolean checkPosition(Position p) {
        return p.getState();
    }

    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, matrix, null);
        //c.drawBitmap(bmp, left, top, null);
        //c.drawRect(this.position, new Paint(Color.GREEN)); //DEBUG
        //c.drawRect(this, new Paint(Color.GREEN)); //DEBUG
    }

    //touch reaction
    public boolean onTouch(int x, int y, int evtType) {
        if (this.state == WAITING) {
            for (Position p : elevatorPositions) {
                if ((p.onTouch(x, y, evtType) &&
                        (person == null && position.getState() == Position.FREE
                                || person != null && person.state == Person.MOVING_WITH_ELEVATOR && position.getState() == Position.BUSY))) {
                    float dy = (p.top - top) / sy;
                    //this.destPosition.setState(p.FREE);
                    this.destPosition.set(p);
                    int floorStep = Math.round(Math.abs(dy) * sy / (height()));
                    if (dy > 0.0f) {
                        state = MOVING;
                        curFloorNum -= floorStep;
                        if (curFloorNum > floors.size()) curFloorNum = floors.size() - 1;
                        curFloor = floors.get(floors.size() - curFloorNum);
                    } else if (dy < 0.0f) {
                        state = MOVING;
                        curFloorNum += floorStep;
                        if (curFloorNum > floors.size()) curFloorNum = floors.size() - 1;
                        curFloor = floors.get(floors.size() - curFloorNum);
                    }
                    return true;
                }
            }
        } else if (this.state == MOVING) {

        }
        return false;
    }

    private Bitmap loadBitmap(Resources res, int ind) {
        return BitmapFactory.decodeResource(res, ind);
    }

    public Position getElevatorPosition() {
        return position;
    }

    public int getCurFloorNumber() {
        return curFloorNum;
    }

    public int getState() {
        return state;
    }

    public double getSpeed() {
        return this.speed * sy;
    }

    public float getSx() {
        return sx;
    }

    public float getSy() {
        return sy;
    }

    public Floor getCurFloor() {
        return this.curFloor;
    }
}
