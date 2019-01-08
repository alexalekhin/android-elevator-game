package edu.amd.spbstu.elevator.AppEntities;

import android.graphics.*;

import edu.amd.spbstu.elevator.AmdIntro.AppIntro;
import edu.amd.spbstu.elevator.AppEntities.Emotions.Anger;
import edu.amd.spbstu.elevator.AppEntities.Emotions.Happiness;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;
import edu.amd.spbstu.elevator.R;

public class Person extends RectF {
    private GameView gameView;

    private Bitmap bmpRight;
    private Bitmap bmpLeft;
    private Bitmap bmpMsg;
    private Bitmap bmpDislike;
    private Paint bad;
    private Paint good;
    private Paint neutral;

    private int direction;
    //private RectF rect;
    private Matrix matrix;
    private Matrix msgMatrix;
    private float[] vals = new float[9];
    private Matrix DislikeMatrix;
    //scale factors
    private float sx;
    private float sy;
    /*
     * Movement
     */
    static final int ON_SPAWN_POSITION = -3;
    static final int MOVING_TO_SPAWN = -2;
    static final int ON_WAIT_POSITION = -1;
    static final int MOVING_TO_POSITION = 0;
    static final int MOVING_WITH_ELEVATOR = 2;
    private static final int ON_OUT_POSITION = 3;

    int state;
    private float STD_SPEED;
    private double speed;
    private Position destPosition;
    Elevator elevator;
    Floor curFloor;
    Floor neededFloor;
    /*
     * Superpowers and emotions
     */
    private Happiness happiness;
    private Anger anger;

    Person(GameView gameView, float x, float y, Bitmap bmpRight, Bitmap bmpLeft, Elevator elevator, Floor startFloor, Floor neededFloor) {
        super();
        this.gameView = gameView;
        sx = ((float) gameView.m_app.getScreenWidth()) / 10 / bmpRight.getWidth();
        sy = ((float) gameView.m_app.getScreenWidth()) / 10 / bmpRight.getHeight();

        //MsgBox
        this.msgMatrix = new Matrix();
        msgMatrix.setScale(2 * sx, 2 * sy);
        this.bmpMsg = Bitmap.createBitmap(gameView.getBmps().bmpMsg,
                0, 0,
                gameView.getBmps().bmpMsg.getWidth(), gameView.getBmps().bmpMsg.getHeight(),
                msgMatrix, true);
        msgMatrix.setTranslate(this.centerX(), this.top - bmpMsg.getHeight());
        //Dislike
        this.DislikeMatrix = new Matrix();
        DislikeMatrix.setScale(sx / 4, sy / 4);
        this.bmpDislike = Bitmap.createBitmap(gameView.getBmps().bmpDislike,
                0, 0,
                gameView.getBmps().bmpDislike.getWidth(), gameView.getBmps().bmpDislike.getHeight(), DislikeMatrix, true);


        //Text
        this.bad = new Paint();
        bad.setStyle(Paint.Style.FILL);
        bad.setColor(Color.RED);
        bad.setTextSize(bmpMsg.getHeight() / 3);

        this.good = new Paint();
        good.setStyle(Paint.Style.FILL);
        good.setColor(Color.GREEN);
        good.setTextSize(bmpMsg.getHeight() / 3);

        this.neutral = new Paint();
        neutral.setStyle(Paint.Style.FILL);
        neutral.setColor(Color.BLACK);
        neutral.setTextSize(bmpMsg.getHeight() / 3);

        //Floors and elevator
        this.elevator = elevator;
        this.curFloor = startFloor;
        this.neededFloor = neededFloor;

        //Person
        this.matrix = new Matrix();
        matrix.setScale(sx, sy);
        this.bmpRight = Bitmap.createBitmap(bmpRight, 0, 0, bmpRight.getWidth(), bmpRight.getHeight(), matrix, true);
        //this.bmpRight = bmpRight;
        this.bmpLeft = Bitmap.createBitmap(bmpLeft, 0, 0, bmpLeft.getWidth(), bmpLeft.getHeight(), matrix, true);
        matrix.reset();

        this.set(0.0f, 0.0f, (float) this.bmpRight.getWidth(), (float) this.bmpRight.getHeight());
        this.offset(x, y);

        //Movement
        this.direction = 1;
        updateSpeed(0.0);
        STD_SPEED = (float)(gameView.getWidth())/500.0f;

        this.destPosition = new Position(curFloor.waitPosition);
        this.state = ON_SPAWN_POSITION;


        //Emotions
        this.happiness = new Happiness(0.0f, 0.1f, 0.0f);
        this.anger = new Anger(0.0f, 0.1f, 0.0f);
    }

    public RectF getRect() {
        return this;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    private void updateSpeed(double s) {
        this.speed = s;
    }

    private void moveX(float path, float dx, int direction) {
        if (direction > 0 && path - direction * dx > 0.0f ||
                direction < 0 && path - direction * dx < 0.0f)
            this.offset(direction * dx, 0.0f);
        else
            this.set(this.destPosition.left, this.top, this.destPosition.right, this.bottom);

        this.matrix.setTranslate(this.left, this.top);
    }

    public void update(Time time) {

        if (state == ON_WAIT_POSITION) {
            msgMatrix.setTranslate(this.centerX(), this.top - bmpMsg.getHeight());
            anger.increase(1);
        } else if (state == ON_SPAWN_POSITION) {
            //destPosition.set(curFloor.spawnPosition);
            //this.state = MOVING_TO_SPAWN;
        } else if (state == ON_OUT_POSITION) {
            msgMatrix.setTranslate(this.centerX(), this.top - bmpMsg.getHeight());
            this.setDestination(curFloor.spawnPosition);
            this.state = MOVING_TO_SPAWN;
            if (!this.curFloor.getNumber().equals(neededFloor.getNumber()))
                anger.increase(500);
        } else if (state == MOVING_TO_POSITION || state == MOVING_TO_SPAWN) {
            if (state == MOVING_TO_SPAWN)
                msgMatrix.setTranslate(this.centerX(), this.top - bmpMsg.getHeight());
            STD_SPEED = (float)(gameView.getWidth())/3000.0f;
            updateSpeed(STD_SPEED);
            float path = destPosition.left - this.left;
            float dx = (float) speed * time.getLevelTimerDelta();

            if (Math.abs(path) == 0.0f) {
                if (destPosition.left == elevator.getElevatorPosition().left) {
                    this.elevator.getElevatorPosition().set(this);
                    this.state = MOVING_WITH_ELEVATOR;
                    direction = -1;
                    this.elevator.person = this;
                } else if (destPosition.left == curFloor.waitPosition.left) {
                    this.state = ON_WAIT_POSITION;
                    direction = 1;


                } else if (destPosition.left == curFloor.outPosition.left) {
                    this.state = ON_OUT_POSITION;
                    direction = -1;
                    this.elevator.person = null;
                } else if (destPosition.left == curFloor.spawnPosition.left) {
                    this.state = ON_SPAWN_POSITION;
                    Integer pCount = gameView.m_app.getAppGame().getCurrentLevel().getPersonsCount();
                    gameView.m_app.getAppGame().getCurrentLevel().setPersonsCount(--pCount);
                }
                this.set(this.destPosition.left, this.top, this.destPosition.right, this.bottom);
            } else {
                if (path > 0.0f)
                    //Left-to-Right
                    direction = 1;
                else if (path < 0.0f)
                    //Right-to-Left
                    direction = -1;
                moveX(path, dx, direction);
            }
        } else if (state == MOVING_WITH_ELEVATOR) {
            this.set(elevator.getElevatorPosition());
            this.matrix.setTranslate(this.left, this.top);
        }


    }

    public void onDraw(Canvas c) {
        //Person
        //c.drawRect(this, new Paint()); //DEBUG

        if (direction == 1)
            c.drawBitmap(bmpRight, matrix, null);
        else
            c.drawBitmap(bmpLeft, matrix, null);

        //Needed floor message
        if (state == ON_WAIT_POSITION) {
            msgMatrix.setTranslate(this.centerX(), this.top - bmpMsg.getHeight());
            msgMatrix.getValues(vals);
            float x = vals[Matrix.MTRANS_X];
            float y = vals[Matrix.MTRANS_Y];
            c.drawBitmap(bmpMsg, msgMatrix, null);
            c.drawText(neededFloor.getNumber().toString(),
                    x + bmpMsg.getWidth() * 2 / 5,
                    y + bmpMsg.getHeight() / 2,
                    neutral);

            if (anger.getLevel() >= 35.0f) {
                DislikeMatrix.setTranslate(this.right, this.top);
                c.drawBitmap(bmpDislike, DislikeMatrix, null);
                if (anger.getLevel() >= 70.0f) {
                    DislikeMatrix.setTranslate(this.right + bmpDislike.getWidth() / 3,
                            this.top + bmpDislike.getHeight());

                    c.drawBitmap(bmpDislike, DislikeMatrix, null);
                    if (anger.getLevel() >= 105.0f) {
                        DislikeMatrix.setTranslate(this.right + bmpDislike.getWidth() / 2,
                                this.top + bmpDislike.getHeight() * 2);

                        c.drawBitmap(bmpDislike, DislikeMatrix, null);
                    }
                }
            }

        }

        //Exit message
        if (state == MOVING_TO_SPAWN) {
            c.drawBitmap(bmpMsg, msgMatrix, null);
            msgMatrix.getValues(vals);
            float x = vals[Matrix.MTRANS_X];
            float y = vals[Matrix.MTRANS_Y];
            if (curFloor == neededFloor) {
                //debug
                if (gameView.m_app.getLanguage() == AppIntro.LANGUAGE_RUS)

                    c.drawText(gameView.getResources().getString(R.string.yes), x + bmpMsg.getWidth() / 4, y + bmpMsg.getHeight() / 2, good);
                else
                    c.drawText(gameView.getResources().getString(R.string.yes),
                            x + bmpMsg.getWidth() / 5, y + bmpMsg.getHeight() / 2,
                            good);
            } else {
                if (gameView.m_app.getLanguage() == AppIntro.LANGUAGE_RUS)
                    c.drawText(gameView.getResources().getString(R.string.no),
                            x + bmpMsg.getWidth() / 5, y + bmpMsg.getHeight() / 2,
                            bad);
                else
                    c.drawText(gameView.getResources().getString(R.string.no),
                            x + bmpMsg.getWidth() / 4, y + bmpMsg.getHeight() / 2,
                            bad);
            }
        }
        //c.drawRect(this, new Paint()); //DEBUG

    }


    void setPosition(RectF r) {
        super.set(r);
        matrix.reset();
        matrix.preTranslate(r.left, r.top);
        //matrix.preScale(sx, sy);
    }

    void setDestination(RectF r) {
        destPosition.set(r);
    }

    public boolean onTouch(int x, int y, int evtType) {
        if (contains(x, y)) {
            //Check positions in elevator
            /*check that elevator is stopped and person's not in elevator*/
            if ((this.state == ON_WAIT_POSITION)
                    && elevator.getState() == Elevator.WAITING) {
                if (elevator.getElevatorPosition().getState()
                        && (curFloor.getNumber() == elevator.getCurFloorNumber())) {
                    //update destination point
                    this.destPosition.set(elevator.getElevatorPosition());
                    this.state = MOVING_TO_POSITION;
                    elevator.getElevatorPosition().setState(Position.BUSY);
                    this.elevator.getCurFloor().persons.remove(this);
                    elevator.getCurFloor().waitPosition.setState(Position.FREE);
                }

            } else if (this.state == MOVING_WITH_ELEVATOR
                    && elevator.getState() == Elevator.WAITING) {
                this.curFloor = elevator.getCurFloor();
                Position p = curFloor.outPosition;
                if (p.getState() == Position.FREE) {
                    //set previous position free
                    this.destPosition.set(p);
                    this.state = MOVING_TO_POSITION;
                    elevator.getElevatorPosition().setState(Position.FREE);
                    //elevator.getCurFloor().outPosition.setState(Position.BUSY);
                    //this.elevator.getCurFloor().persons.add(this);
                }
            }
            return true;
        }
        return false;
    }

    public Happiness getHappy() {
        return happiness;
    }


    Anger getAngry() {
        return anger;
    }
}
