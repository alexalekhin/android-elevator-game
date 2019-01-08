package edu.amd.spbstu.elevator.AppEntities;

import android.graphics.*;

import edu.amd.spbstu.elevator.AppEntities.Game.Bitmaps;
import edu.amd.spbstu.elevator.AppEntities.Game.Game;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;
import edu.amd.spbstu.elevator.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SimpleLevel implements Level {
    private GameView gameView;
    private Game game;


    private ArrayList<Person> persons;
    private Elevator elevator;
    private ArrayList<Position> elevatorPositions;
    private ArrayList<Floor> floors;
    private Time time;

    private Bitmap bmp_background;
    private Matrix matrix;
    private Integer personsCount;
    private int floorsNumber;
    private float happinessLevel;
    private RectF happinessLevelRect;
    private float angerLevel;
    private RectF angerLevelRect;
    private float angerLevelThreshold;
    private float happinessLevelThreshold;

    static public final int LANGUAGE_ENG = 0;
    static public final int LANGUAGE_RUS = 1;
    static public final int LANGUAGE_UNKNOWN = 2;

    private Paint angerPaint;
    private Paint happinessPaint;
    private Paint neutralPaint;

    private Paint blackRectPaint;

    public SimpleLevel(Game game, GameView gameView, int maxPersons, Float maxAnger, int floorsNumber) {
        this.game = game;
        this.gameView = gameView;
        this.personsCount = maxPersons;
        this.angerLevelThreshold = maxAnger;
        this.happinessLevelThreshold = maxAnger / 2;
        this.floorsNumber = floorsNumber;

        neutralPaint = new Paint();
        angerPaint = new Paint();
        happinessPaint = new Paint();

        blackRectPaint = new Paint();

        persons = new ArrayList<>();
        floors = new ArrayList<>();
        elevatorPositions = new ArrayList<>();

        if (gameView != null) {

            int floorBmpMin = 0;
            int floorBmpMax = 3;
            int randomFloorBmpIdx;

            for (int i = 0; i < floorsNumber; ++i) {
                Floor f;
                randomFloorBmpIdx = new Random().nextInt((floorBmpMax - floorBmpMin)) + floorBmpMin;
                Bitmap floorBmp;
                switch (randomFloorBmpIdx) {
                    case 0:
                        floorBmp = gameView.getBmps().bmpFloor1;
                        break;
                    case  1:
                        floorBmp = gameView.getBmps().bmpFloor2;
                        break;
                    case  2:
                        floorBmp = gameView.getBmps().bmpFloor3;
                        break;
                        default:
                            floorBmp = gameView.getBmps().bmpFloor1;
                    break;
                }
                if (i == 0) {
                    f = new Floor(gameView, 0, 0, floorsNumber,
                            floorBmp,
                            floorsNumber - floors.size());
                } else {
                    f = new Floor(gameView, floors.get(i - 1).rect.left, floors.get(i - 1).rect.bottom, floorsNumber,
                            floorBmp,
                            floorsNumber - floors.size());
                }
                floors.add(f);

            }

            for (Floor f : floors) {
                int idx = floors.indexOf(f);
                elevatorPositions.add(idx,
                        new Position(f.rect.right, f.rect.top,
                                gameView.m_app.getScreenWidth(), f.rect.bottom));
            }

            elevator = new Elevator(gameView, (int) (elevatorPositions.get(2).left), (int) (elevatorPositions.get(2).top), floorsNumber,
                    gameView.getBmps().bmpElevator,
                    floors.get(2), this.floors, elevatorPositions);


            int floorMin = 0;
            int randomStartFloor;
            int randomNeededFloor;

            int personBmpMin = 0;
            int personBmpMax = 4;
            int randomPersonBmpIdx;

            //Basic set of persons
            for (int i = 0; i < floorsNumber; ++i) {
                randomStartFloor = i;
                randomNeededFloor = new Random().nextInt((floorsNumber - floorMin)) + floorMin;
                if (randomStartFloor == randomNeededFloor) {
                    randomNeededFloor++;
                    randomNeededFloor %= floorsNumber;
                }

                randomPersonBmpIdx = new Random().nextInt((personBmpMax - personBmpMin)) + personBmpMin;
                Bitmap personBmpLeft;
                Bitmap personBmpRight;
                switch (randomPersonBmpIdx) {
                    case 0:
                        personBmpLeft = gameView.getBmps().bmpFemLeft;
                        personBmpRight = gameView.getBmps().bmpFemRight;
                        break;
                    case  1:
                        personBmpLeft = gameView.getBmps().bmpMLeft;
                        personBmpRight = gameView.getBmps().bmpMRight;
                        break;
                    case  2:
                        personBmpLeft = gameView.getBmps().bmpOldFemLeft;
                        personBmpRight = gameView.getBmps().bmpOldFemRight;
                        break;
                    case  3:
                        personBmpLeft = gameView.getBmps().bmpOldMLeft;
                        personBmpRight = gameView.getBmps().bmpOldMRight;
                        break;
                    default:
                        personBmpLeft = gameView.getBmps().bmpOldMLeft;
                        personBmpRight = gameView.getBmps().bmpOldMRight;
                        break;
                }

                Person p = new Person(gameView, 0.0f, 0.0f,
                        personBmpRight, personBmpLeft,
                        elevator, floors.get(randomStartFloor), floors.get(randomNeededFloor));

                persons.add(p);
                floors.get(i).persons.add(p);
            }


            for (int i = 0; i < (maxPersons - floorsNumber); ++i) {
                randomStartFloor = new Random().nextInt((floorsNumber - floorMin)) + floorMin;
                randomNeededFloor = new Random().nextInt((floorsNumber - floorMin)) + floorMin;
                if (randomStartFloor == randomNeededFloor) {
                    randomNeededFloor++;
                    randomNeededFloor %= floorsNumber;
                }

                randomPersonBmpIdx = new Random().nextInt((personBmpMax - personBmpMin)) + personBmpMin;
                Bitmap personBmpLeft;
                Bitmap personBmpRight;
                switch (randomPersonBmpIdx) {
                    case 0:
                        personBmpLeft = gameView.getBmps().bmpFemLeft;
                        personBmpRight = gameView.getBmps().bmpFemRight;
                        break;
                    case  1:
                        personBmpLeft = gameView.getBmps().bmpMLeft;
                        personBmpRight = gameView.getBmps().bmpMRight;
                        break;
                    case  2:
                        personBmpLeft = gameView.getBmps().bmpOldFemLeft;
                        personBmpRight = gameView.getBmps().bmpOldFemRight;
                        break;
                    case  3:
                        personBmpLeft = gameView.getBmps().bmpOldMLeft;
                        personBmpRight = gameView.getBmps().bmpOldMRight;
                        break;
                    default:
                        personBmpLeft = gameView.getBmps().bmpOldMLeft;
                        personBmpRight = gameView.getBmps().bmpOldMRight;
                        break;
                }

                Person p = new Person(gameView, 0.0f, 0.0f,
                        personBmpRight, personBmpLeft,
                        elevator, floors.get(randomStartFloor), floors.get(randomNeededFloor));

                persons.add(p);
                floors.get(randomStartFloor).persons.add(p);
            }


            for (Floor f : floors) {
                //set spawn for every floor
                f.spawnPosition.set(f.spawnPosition.left - f.persons.get(0).height(), f.spawnPosition.bottom - f.persons.get(0).height(),
                        f.spawnPosition.left, f.spawnPosition.bottom);
                //set waitPosition for every floor
                f.waitPosition.set(f.waitPosition.left, f.waitPosition.bottom - f.persons.get(0).height(),
                        f.waitPosition.left + f.persons.get(0).width(), f.waitPosition.bottom);

                //move every person on spawn
                for (Person p : f.persons) {
                    p.setPosition(f.spawnPosition);
                }
            }

        }

        time = new Time(System.currentTimeMillis());
        happinessLevel = 0.0f;
        happinessLevelRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        angerLevel = 0.0f;
        angerLevelRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);

    }

    public void update() {
        if (checkForLose())
            game.updateState(Game.GAME_LOST);
        //update persons on every floor
        for (int i = 0; i < floors.size(); ++i) {
            Floor f = floors.get(i);
            f.update();
        }

        elevator.update(time);
        for (int i = 0; i < persons.size(); ++i) {
            Person p = persons.get(i);
            if (p != null) {
                p.update(time);
            }
        }


        angerLevel = 0.0f;
        happinessLevel = 0.0f;
        int personCount = 0;

        for (int i = 0; i < persons.size(); ++i) {
            if (persons.size() != 0) {
                Person p = persons.get(i);
                if (p.state == Person.ON_WAIT_POSITION ||
                        (p.state == Person.MOVING_TO_SPAWN
                                && !p.curFloor.getNumber().equals(p.neededFloor.getNumber()))) {
                    personCount++;
                }
            }
        }

        for (int i = 0; i < persons.size(); ++i) {
            if (persons.size() != 0) {
                Person p = persons.get(i);
                if (p.state == Person.ON_WAIT_POSITION ||
                        (p.state == Person.MOVING_TO_SPAWN
                                && !p.curFloor.getNumber().equals(p.neededFloor.getNumber())))
                    angerLevel += p.getAngry().getLevel() / personCount;
            }
        }

        if (checkForWin())
            game.updateState(Game.GAME_WIN);
        updateEmotions();
    }

    private void updateEmotions() {
        float step;
        if (gameView.m_app.getScreenWidth() / 3 * happinessLevel / happinessLevelThreshold < gameView.m_app.getScreenWidth() / 3)
            step = gameView.m_app.getScreenWidth() / 3 * happinessLevel / happinessLevelThreshold;
        else
            step = gameView.m_app.getScreenWidth() / 3;

        happinessLevelRect.set(
                happinessLevelRect.left,
                happinessLevelRect.top,
                happinessLevelRect.left + step,
                happinessLevelRect.top + gameView.m_app.getScreenHeight() / 20);


        if (gameView.m_app.getScreenWidth() / 3 * angerLevel / angerLevelThreshold < gameView.m_app.getScreenWidth() / 3)
            step = gameView.m_app.getScreenWidth() / 3 * angerLevel / angerLevelThreshold;
        else
            step = gameView.m_app.getScreenWidth() / 3;
        angerLevelRect.set(
                //gameView.m_app.getScreenWidth() / 3,
                0.0f,
                angerLevelRect.top,
                angerLevelRect.left + step,
                angerLevelRect.top + gameView.m_app.getScreenHeight() / 20);
    }

    public Time getTime() {
        return time;
    }

    void start() {
    }

    void stop() {
    }

    public void onDraw(Canvas c) {
        //c.drawBitmap(bmp_background, matrix, null);
        for (int i = 0; i < floors.size(); ++i) {
            Floor f = floors.get(i);
            f.onDraw(c);
        }
        elevator.onDraw(c);
        for (int i = 0; i < persons.size(); ++i) {
            Person p = persons.get(i);
            if (p.state != Person.ON_SPAWN_POSITION)
                p.onDraw(c);
        }

        neutralPaint.setTextSize(gameView.m_app.getScreenHeight() / 40);
        neutralPaint.setColor(Color.WHITE);

        angerPaint.setColor(Color.RED);


        happinessPaint.setColor(Color.GREEN);

        c.drawRect(angerLevelRect, angerPaint);


        c.drawText(gameView.getResources().getString(R.string.anger), angerLevelRect.left, gameView.m_app.getScreenHeight() / 40, neutralPaint);


        blackRectPaint.setStyle(Paint.Style.STROKE);
        blackRectPaint.setColor(Color.BLACK);
        blackRectPaint.setStrokeWidth(2);

        c.drawRect(0.0f,
                0.0f,
                gameView.m_app.getScreenWidth() / 3,
                gameView.m_app.getScreenHeight() / 20, blackRectPaint);

    }

    @Override
    public Integer getPersonsCount() {
        return personsCount;
    }

    @Override
    public void setPersonsCount(Integer pCount) {
        personsCount = pCount;
    }

    private boolean checkForLose() {
        return angerLevel >= angerLevelThreshold;
    }

    private boolean checkForWin() {
        return (personsCount == 0);
    }

    public boolean onTouch(int x, int y, int eventType) {
        for (int i = 0; i < persons.size(); ++i) {
            Person p = persons.get(i);
            if (p.onTouch(x, y, eventType))
                return true;
        }
        return elevator.onTouch(x, y, eventType);

    }
}

