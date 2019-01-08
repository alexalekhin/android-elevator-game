package edu.amd.spbstu.elevator.AppEntities;

import android.graphics.*;

import edu.amd.spbstu.elevator.AppEntities.Game.Game;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;
import edu.amd.spbstu.elevator.R;

import java.util.ArrayList;

public class Level3 implements Level {
    private GameView gameView;
    private Game game;

    public ArrayList<Person> persons;
    private Elevator elevator;
    private ArrayList<Position> elevatorPositions;
    private ArrayList<Floor> floors;
    private Time time;

    private Bitmap bmp_background;
    private Matrix matrix;
    public Integer personsCount;
    int floorsNumber;
    private float happinessLevel;
    private RectF happinessLevelRect;
    private float angerLevel;
    private RectF angerLevelRect;
    private float angerLevelThreshold;
    private float happinessLevelThreshold;

    static public final int LANGUAGE_ENG = 0;
    static public final int LANGUAGE_RUS = 1;
    static public final int LANGUAGE_UNKNOWN = 2;

    Paint angerPaint;
    Paint happinessPaint;
    Paint neutralPaint;

    Paint blackRectPaint;

    public Level3(Game game, GameView gameView, int maxPersons, Float maxAnger, int floorsNumber) {
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

            //4 basic floors
            Floor f0 = new Floor(gameView, 0, 0, 5,
                    gameView.getBmps().bmpFloor1,
                    floorsNumber - floors.size());
            floors.add(f0);

            Floor f1 = new Floor(gameView, f0.rect.left, f0.rect.bottom, 5,
                    gameView.getBmps().bmpFloor2,
                    floorsNumber - floors.size());
            floors.add(f1);
            Floor f2 = new Floor(gameView, f1.rect.left, f1.rect.bottom, 5,
                    gameView.getBmps().bmpFloor3,
                    floorsNumber - floors.size());
            floors.add(f2);
            Floor f3 = new Floor(gameView, f2.rect.left, f2.rect.bottom, 5,
                    gameView.getBmps().bmpFloor1,
                    floorsNumber - floors.size());
            floors.add(f3);
            Floor f4 = new Floor(gameView, f3.rect.left, f3.rect.bottom, 5,
                    gameView.getBmps().bmpFloor2,
                    floorsNumber - floors.size());
            floors.add(f4);

            for (Floor f : floors) {
                int idx = floors.indexOf(f);
                elevatorPositions.add(idx, new Position(f.rect.right, f.rect.top, gameView.m_app.getScreenWidth(), f.rect.bottom));
            }

            elevator = new Elevator(gameView, (int) (elevatorPositions.get(2).left), (int) (elevatorPositions.get(2).top), 5,
                    gameView.getBmps().bmpElevator,
                    f2, this.floors, elevatorPositions);

            //f0
            Person p0_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(0), floors.get(1));
            persons.add(p0_0);
            f0.persons.add(p0_0);

            Person p0_1 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(0), floors.get(2));
            persons.add(p0_1);
            f0.persons.add(p0_1);

            Person p0_2 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(0), floors.get(2));
            persons.add(p0_2);
            f0.persons.add(p0_2);

            Person p0_3 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(0), floors.get(2));
            persons.add(p0_3);
            f0.persons.add(p0_3);

            Person p0_4 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(0), floors.get(2));
            persons.add(p0_4);
            f0.persons.add(p0_4);

            Person p0_5 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(0), floors.get(2));
            persons.add(p0_5);
            f0.persons.add(p0_5);
            /*
            Person p0_6 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top, gameView.getResources(),
                    R.drawable.m, elevator, 5, 3);
            persons.add(p0_6);
            f0.persons.add(p0_6);

            Person p0_7 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top, gameView.getResources(),
                    R.drawable.m, elevator, 5, 3);
            persons.add(p0_7);
            f0.persons.add(p0_7);

            Person p0_8 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top, gameView.getResources(),
                    R.drawable.m, elevator, 5, 3);
            persons.add(p0_8);
            f0.persons.add(p0_8);

            Person p0_9 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top, gameView.getResources(),
                    R.drawable.m, elevator, 5, 3);
            persons.add(p0_9);
            f0.persons.add(p0_9);

            Person p0_10 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top, gameView.getResources(),
                    R.drawable.m, elevator, 5, 3);
            persons.add(p0_10);
            f0.persons.add(p0_10);
            */
            //f1
            Person p1_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(1), floors.get(4));
            persons.add(p1_0);
            f1.persons.add(p1_0);

            Person p1_1 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(0));
            persons.add(p1_1);
            f1.persons.add(p1_1);

            Person p1_2 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(0));
            persons.add(p1_2);
            f1.persons.add(p1_2);

            Person p1_3 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_3);
            f1.persons.add(p1_3);

            Person p1_4 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_4);
            f1.persons.add(p1_4);

            Person p1_5 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_5);
            f1.persons.add(p1_5);

            Person p1_6 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_6);
            f1.persons.add(p1_6);

            Person p1_7 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_7);
            f1.persons.add(p1_7);

            Person p1_8 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_8);
            f1.persons.add(p1_8);

            Person p1_9 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_9);
            f1.persons.add(p1_9);

            Person p1_10 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(3));
            persons.add(p1_10);
            f1.persons.add(p1_10);

            //f2
            Person p2_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(2), floors.get(3));
            persons.add(p2_0);
            f2.persons.add(p2_0);

            Person p2_1 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(0));
            persons.add(p2_1);
            f2.persons.add(p2_1);

            Person p2_2 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(1));
            persons.add(p2_2);
            f2.persons.add(p2_2);

            Person p2_3 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(4));
            persons.add(p2_3);
            f2.persons.add(p2_3);

            Person p2_4 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(3));
            persons.add(p2_4);
            f2.persons.add(p2_4);

            Person p2_5 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(4));
            persons.add(p2_5);
            f2.persons.add(p2_5);

            Person p2_6 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(1));
            persons.add(p2_6);
            f2.persons.add(p2_6);

            Person p2_7 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(0));
            persons.add(p2_7);
            f2.persons.add(p2_7);

            Person p2_8 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(4));
            persons.add(p2_8);
            f2.persons.add(p2_8);

            Person p2_9 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(4));
            persons.add(p2_9);
            f2.persons.add(p2_9);

            Person p2_10 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(1));
            persons.add(p2_10);
            f2.persons.add(p2_10);

            //f3
            Person p3_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(3), floors.get(2));
            persons.add(p3_0);
            f3.persons.add(p3_0);
            Person p3_1 = new Person(gameView, p3_0.getRect().right, p3_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(3), floors.get(4));
            persons.add(p3_1);
            f3.persons.add(p3_1);
            //f4
            Person p4_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(4), floors.get(2));
            persons.add(p4_0);
            f4.persons.add(p4_0);
            Person p4_1 = new Person(gameView, p4_0.getRect().right, p4_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(4), floors.get(0));
            persons.add(p4_1);
            f4.persons.add(p4_1);

/*
            for (Person person : persons) {
                for (Floor f : floors) {
                    if (person.curFloor == f.number) {
                        f.persons.add(person);
                    }
                }
            }
*/
            for (Floor f : floors) {
                //TODO set people on spawn\start position
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

        //elevator = new Elevator(gameView, floors.get(2).rect.right, floors.get(2).rect.top, gameView.getResources(), R.drawable.elevator);
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
        /*
        for (Floor f : floors) {
            f.update();
        }
        */
        elevator.update(time);
        for (int i = 0; i < persons.size(); ++i) {
            Person p = persons.get(i);
            if (p != null) {
                p.update(time);
            }
        }

        /*for (Person p : persons) {
            if (p != null && p.state != Person.ON_SPAWN_POSITION) {
                p.update(time);
            }
        }*/

        angerLevel = 0.0f;
        happinessLevel = 0.0f;
        int personCount = 0;

        for (int i = 0; i < persons.size(); ++i) {
            if (persons.size() != 0) {
                Person p = persons.get(i);
                if (p.state == Person.ON_WAIT_POSITION) {
                    personCount++;
                }
            }
        }

        for (int i = 0; i < persons.size(); ++i) {
            if (persons.size() != 0) {
                Person p = persons.get(i);
                if (p.state == Person.ON_WAIT_POSITION)
                    angerLevel += p.getAngry().getLevel() / personCount;
            }
        }
        /*
        for (Person p : persons) {
            if (persons.size() != 0) {
                happinessLevel += p.getHappy().getLevel() / persons.size();
                angerLevel += p.getAngry().getLevel() / persons.size();
            }
        }
        */
        if (checkForWin())
            game.updateState(Game.GAME_WIN);
        updateEmotions();
    }

    public void updateEmotions() {
        float step = 0.0f;
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

        //c.drawRect(0.0f, 0.0f, gameView.m_app.getScreenWidth() / 3, gameView.m_app.getScreenHeight() / 20, new Paint());

        //angerPaint.getTextBounds(happinessLevel.toString(), 0, happinessLevel.toString().length(), foundBounds);
        //angerPaint.getTextBounds("Anger:", 0, "Anger:".length(), angerBounds);
        //angerBounds.offset(0, this.gameView.m_app.getScreenHeight() / 40);
        neutralPaint.setTextSize(gameView.m_app.getScreenHeight() / 40);
        neutralPaint.setColor(Color.WHITE);

        angerPaint.setColor(Color.RED);

        //happinessPaint.getTextBounds("Happiness:", 0, "Happiness:".length(), angerBounds);
        //happinessBounds.offset(0, this.gameView.m_app.getScreenHeight() / 40);
        //happinessPaint.setTextSize(gameView.m_app.getScreenHeight() / 40);
        happinessPaint.setColor(Color.GREEN);

        //c.drawRect(happinessLevelRect, happinessPaint);
        c.drawRect(angerLevelRect, angerPaint);

        if (game.language == LANGUAGE_RUS) {
            //c.drawText("Комфорт", happinessLevelRect.left, gameView.m_app.getScreenHeight() / 40, neutralPaint);
            c.drawText("Злость", angerLevelRect.left, gameView.m_app.getScreenHeight() / 40, neutralPaint);

        } else {
            //c.drawText("Happiness", happinessLevelRect.left, gameView.m_app.getScreenHeight() / 40, neutralPaint);
            c.drawText("Anger", angerLevelRect.left, gameView.m_app.getScreenHeight() / 40, neutralPaint);
        }

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

    public boolean checkForLose() {
        return angerLevel >= angerLevelThreshold;
    }

    public boolean checkForWin() {
        return (personsCount == 0);
    }

    public boolean onTouch(int x, int y, int eventType) {
        for (int i = 0; i < persons.size(); ++i) {
            Person p = persons.get(i);
            if (p.onTouch(x, y, eventType))
                return true;
        }
        if (elevator.onTouch(x, y, eventType))
            return true;
        /*
        for (Position p : elevatorPositions) {
            if (p.onTouch(x, y, eventType))
                return true;
        }
        */
        return false;
    }
}

