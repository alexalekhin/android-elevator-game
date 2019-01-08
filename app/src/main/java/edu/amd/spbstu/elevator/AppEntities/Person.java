package edu.amd.spbstu.elevator.AppEntities;

import android.graphics.*;
import edu.amd.spbstu.elevator.AmdIntro.AppIntro;
import edu.amd.spbstu.elevator.AppEntities.Emotions.Anger;
import edu.amd.spbstu.elevator.AppEntities.Emotions.Happiness;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;

public class Person extends RectF {
    private GameView gameView;

    private Bitmap bmpRight;
    private Bitmap bmpLeft;
    private Bitmap bmpMsg;
    private Bitmap bmpDislike;
    Paint bad;
    Paint good;
    Paint neutral;

    int direction;
    //private RectF rect;
    private Matrix matrix;
    private Matrix msgMatrix;
    private Matrix DislikeMatrix;
    //scale factors
    float sx;
    float sy;
    /*
     * Movement
     */
    public static final int ON_SPAWN_POSITION = -3;
    public static final int MOVING_TO_SPAWN = -2;
    public static final int ON_WAIT_POSITION = -1;
    public static final int MOVING_TO_POSITION = 0;
    public static final int MOVING_WITH_ELEVATOR = 2;
    public static final int ON_OUT_POSITION = 3;

    int state;
    boolean impactFlag = false;
    public float STD_SPEED = 0.5f;
    private double speed;
    private Position destPosition;
    private Position inElevatorPosition;
    Elevator elevator;
    Floor curFloor;
    Floor neededFloor;
    /*
     * Superpowers and emotions
     */
    private Happiness happiness;
    private Anger anger;

    //TODO curfloor as parameter
    public Person(GameView gameView, float x, float y, Bitmap bmpRight, Bitmap bmpLeft, Elevator elevator, Floor startFloor, Floor neededFloor) {
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
        DislikeMatrix.setScale(sx, sy);
//        this.bmpDislike = Bitmap.createBitmap(gameView.getBmps().bmpDislike,
//                0, 0,
//                gameView.getBmps().bmpDislike.getWidth(), gameView.getBmps().bmpDislike.getHeight());

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

        this.destPosition = new Position(curFloor.waitPosition);
        this.state = ON_SPAWN_POSITION;


        //Emotions
        this.happiness = new Happiness(0.0f, 0.1f, 0.0f);
        this.anger = new Anger(0.0f, 0.1f, 0.0f);
    }

    public RectF getRect() {
        return this;
    }

    public Bitmap getBitmap() {
        return this.bmpRight;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    public void updateSpeed(double s) {
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
            //anger.increase(1);
        } else if (state == MOVING_TO_POSITION || state == MOVING_TO_SPAWN) {
            if (state == MOVING_TO_SPAWN)
                msgMatrix.setTranslate(this.centerX(), this.top - bmpMsg.getHeight());

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
                //TODO PLACE ON FINAL POSITION
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

/*
        if (this.state == MOVING_TO_POSITION) {esz
            this.speed = STD_SPEED;
            float path = destPosition.left - this.left;
            int direction = path > 0.0f ? 1 : -1;
            path = Math.abs(path);
            double dx = direction * speed * time.getLevelTimerDelta() * sx;
            if ((int) (path) != 0) {
                if (direction > 0) {
                    if ((this.left + 2 * dx) <= destPosition.left) {
                        this.direction = 1;

                        matrix.reset();
                        matrix.preTranslate( (float)(this.left+ dx),  (float)this.top);
                        //matrix.preScale(sx, sy);

                        this.offset((float) Math.ceil(dx), (float) 0.0f);


                    } else {
                        if ((int) this.destPosition.left == (int) elevator.getElevatorPosition().left) {
                            this.direction = -1;

                            matrix.reset();
                            matrix.preTranslate(elevator.getElevatorPosition().left,
                                    elevator.getElevatorPosition().bottom - this.height());
                            //matrix.preScale(sx, sy);

                            this.set(elevator.getElevatorPosition().left,
                                    elevator.getElevatorPosition().bottom - this.height(),
                                    this.right,
                                    elevator.getElevatorPosition().bottom);

                            this.state = MOVING_WITH_ELEVATOR;
                            this.elevator.person = this;

                        } else {
                            matrix.reset();
                            matrix.preTranslate(destPosition.left, this.top);
                            //matrix.preScale(sx, sy);

                            this.set(destPosition.left, destPosition.bottom - this.height(), this.right, destPosition.bottom);

                            this.state = ON_WAIT_POSITION;
                        }
                    }
                } else {
                    if ((this.left + 2 * dx) >= destPosition.left) {
                        this.direction = -1;

                        matrix.reset();
                        matrix.preTranslate((float) (this.left + dx), (float) this.top);
                        //matrix.preScale(sx, sy);

                        this.offset((int) Math.ceil(dx), (int) 0.0f);


                    } else {
                        if (this.destPosition.left == elevator.getCurFloor().outPosition.left) {

                            matrix.reset();
                            matrix.preTranslate(destPosition.left, destPosition.bottom - this.height());
                            //matrix.preScale(sx, sy);

                            this.set(destPosition.left, destPosition.bottom - this.height(), this.right, destPosition.bottom);

                            this.state = ON_OUT_POSITION;
                            this.elevator.person = null;
                        } else {
                            matrix.reset();
                            matrix.preTranslate(destPosition.left, destPosition.bottom - this.height());
                            //matrix.preScale(sx, sy);

                            this.state = ON_WAIT_POSITION;
                        }
                    }
                }
            } else {
                if (this.left == elevator.getElevatorPosition().left) {
                    this.direction = -1;

                    matrix.reset();
                    matrix.preTranslate(this.left,
                            elevator.getElevatorPosition().bottom - this.height());
                    //matrix.preScale(sx, sy);

                    this.set(this.left,
                            elevator.getElevatorPosition().bottom - this.height(),
                            this.right,
                            elevator.getElevatorPosition().bottom);

                    this.state = MOVING_WITH_ELEVATOR;
                    this.elevator.person = this;

                }
            }

        } else if (this.state == MOVING_WITH_ELEVATOR) {

            matrix.reset();
            matrix.preTranslate(this.left, elevator.getElevatorPosition().bottom - this.height());
            //matrix.preScale(sx, sy);

            this.set(elevator.getElevatorPosition().left,
                    elevator.getElevatorPosition().bottom - this.height(),
                    elevator.getElevatorPosition().right,
                    elevator.getElevatorPosition().bottom);

        } else if (this.state == MOVING_TO_SPAWN) {
            double dx = direction * speed * time.getLevelTimerDelta() * sx;

            if ((this.left + 2*dx) >= destPosition.left) {
                this.direction = -1;
                matrix.reset();
                matrix.preTranslate((float) (this.left + dx), (float) this.top);
                //matrix.preScale(sx, sy);

                this.offset((float) Math.ceil(dx), (float) 0.0f);

                msgMatrix.reset();
                msgMatrix.preTranslate(this.centerX(), this.top - bmpMsg.getHeight() * 2 * sy);
                //msgMatrix.preScale(2 * sx, 2 * sy);
            } else {
                matrix.reset();
                matrix.preTranslate(this.left, destPosition.bottom - this.height());
                //matrix.preScale(sx, sy);
                Integer pCount = gameView.m_app.getAppGame().getCurrentLevel().getPersonsCount();
                gameView.m_app.getAppGame().getCurrentLevel().setPersonsCount(--pCount);
                this.state = ON_SPAWN_POSITION;

                //gameView.m_app.getAppGame().getCurrentLevel().persons.remove(this);
            }
        } else if (this.state == ON_OUT_POSITION) {
            this.state = MOVING_TO_SPAWN;
            this.setDestination(elevator.getCurFloor().spawnPosition);
            if (curFloor != neededFloor && !impactFlag) {
                anger.increase(3000);
                impactFlag = true;
            }
        } else if (this.state == ON_WAIT_POSITION) {
            anger.increase(1);
        }
*/
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
            c.drawBitmap(bmpMsg, msgMatrix, null);
            c.drawText(neededFloor.getNumber().toString(),
                    this.centerX() + bmpMsg.getWidth() * 4 / 10,
                    this.top - bmpMsg.getHeight() / 2,
                    neutral);
            /*
            if (anger.getLevel() >= 30.0f) {
                DislikeMatrix.reset();
                DislikeMatrix.preTranslate(this.centerX() + bmpMsg.getWidth() / 2 * sx, this.top);
                //DislikeMatrix.preScale(sx / 2, sy / 2);
                c.drawBitmap(bmpDislike, DislikeMatrix, null);
                if (anger.getLevel() >= 70.0f) {
                    DislikeMatrix.reset();
                    DislikeMatrix.preTranslate(this.centerX() + bmpMsg.getWidth() / 2 * sx, this.top + bmpDislike.getHeight() * sy / 2);

                    //DislikeMatrix.preScale(sx / 2, sy / 2);
                    c.drawBitmap(bmpDislike, DislikeMatrix, null);
                }
            }
            */
        }
        /*
        if (state == MOVING_TO_SPAWN && curFloor != neededFloor) {
            c.drawBitmap(bmpMsg, msgMatrix, null);
            if (gameView.m_app.getLanguage() == AppIntro.LANGUAGE_RUS)
                c.drawText("НЕТ!",
                        this.centerX() + bmpMsg.getWidth() * (2 * sx) / 4,
                        this.top - bmpMsg.getHeight() * (2 * sy) / 2,
                        bad);
            else
                c.drawText("NO!",
                        this.centerX() + bmpMsg.getWidth() * (2 * sx) / 4,
                        this.top - bmpMsg.getHeight() * (2 * sy) / 2,
                        bad);
        }
        */

        //Exit message
        if (state == MOVING_TO_SPAWN) {
            //c.drawBitmap(bmpMsg, msgMatrix, null);
            if (curFloor == neededFloor) {
//                if (gameView.m_app.getLanguage() == AppIntro.LANGUAGE_RUS)
//                    c.drawText("ДА!", this.centerX() + bmpMsg.getWidth() * (2 * sx) / 4, this.top - bmpMsg.getHeight() * (2 * sy) / 2, good);
//                else
//                    c.drawText("YES!",
//                            this.centerX() + bmpMsg.getWidth() * (2 * sx) / 4,
//                            this.top - bmpMsg.getHeight() * (2 * sy) / 2,
//                            good);
            } else {
//                if (gameView.m_app.getLanguage() == AppIntro.LANGUAGE_RUS)
//                    c.drawText("НЕТ!",
//                            this.centerX() + bmpMsg.getWidth() * (2 * sx) / 4,
//                            this.top - bmpMsg.getHeight() * (2 * sy) / 2,
//                            bad);
//                else
//                    c.drawText("NO!",
//                            this.centerX() + bmpMsg.getWidth() * (2 * sx) / 4,
//                            this.top - bmpMsg.getHeight() * (2 * sy) / 2,
//                            bad);
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

    public Anger getAngry() {
        return anger;
    }
}
