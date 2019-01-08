package edu.amd.spbstu.elevator.AppEntities;

import android.content.res.Resources;
import android.graphics.*;
import edu.amd.spbstu.elevator.AppEntities.Game.Game;
import edu.amd.spbstu.elevator.AppEntities.Game.GameView;
import edu.amd.spbstu.elevator.R;

import java.util.ArrayList;

public class Level1 implements Level {
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
    private Float happinessLevel;
    private RectF happinessLevelRect;
    private Float angerLevel;
    private RectF angerLevelRect;
    private Float angerLevelThreshold;
    private Float happinessLevelThreshold;

    static public final int LANGUAGE_ENG = 0;
    static public final int LANGUAGE_RUS = 1;
    static public final int LANGUAGE_UNKNOWN = 2;

    Paint angerPaint;
    Paint happinessPaint;
    Paint neutralPaint;

    Paint blackRectPaint;

    public Level1(Game game, GameView gameView, int maxPersons, Float maxAnger, int floorsNumber) {
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

            //3 basic floors
            Floor f0 = new Floor(gameView, 0, 0, 3,
                    gameView.getBmps().bmpFloor1,
                    floorsNumber - floors.size());
            floors.add(f0);

            Floor f1 = new Floor(gameView, f0.rect.left, f0.rect.bottom, 3,
                    gameView.getBmps().bmpFloor2,
                    floorsNumber - floors.size());
            floors.add(f1);
            Floor f2 = new Floor(gameView, f1.rect.left, f1.rect.bottom, 3,
                    gameView.getBmps().bmpFloor3,
                    floorsNumber - floors.size());
            floors.add(f2);

            for (Floor f : floors) {
                int idx = floors.indexOf(f);
                elevatorPositions.add(idx, new Position(f.rect.right, f.rect.top, gameView.m_app.getScreenWidth(), f.rect.bottom));
            }

            elevator = new Elevator(gameView,
                    (int) (elevatorPositions.get(2).left), (int) (elevatorPositions.get(2).top), 3,
                    gameView.getBmps().bmpElevator,
                    f2, this.floors, elevatorPositions);

            //f0
            Person p0_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(0), floors.get(1));
            persons.add(p0_0);
            Person p0_1 = new Person(gameView, p0_0.getRect().right, p0_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(0), floors.get(1));
            persons.add(p0_1);

            //f1
            Person p1_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(1), floors.get(0));
            persons.add(p1_0);
            Person p1_1 = new Person(gameView, p1_0.getRect().right, p1_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(1), floors.get(2));
            persons.add(p1_1);

            //f2
            Person p2_0 = new Person(gameView, 0.0f, 0.0f,
                    gameView.getBmps().bmpFemRight, gameView.getBmps().bmpFemLeft,
                    elevator, floors.get(2), floors.get(1));
            persons.add(p2_0);
            Person p2_1 = new Person(gameView, p2_0.getRect().right, p2_0.getRect().top,
                    gameView.getBmps().bmpMRight, gameView.getBmps().bmpMLeft,
                    elevator, floors.get(2), floors.get(0));
            persons.add(p2_1);

            for (Person person : persons) {
                for (Floor f : floors) {
                    if (person.curFloor.getNumber() == f.number) {
                        f.persons.add(person);
                    }
                }
            }

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

        for (int i = 0; i < persons.size(); ++i) {
            Person p = persons.get(i);
            if (persons.size() != 0) {
                happinessLevel += p.getHappy().getLevel() / persons.size();
                angerLevel += p.getAngry().getLevel() / persons.size();
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


    private Bitmap loadBitmap(Resources res, int ind) {
        return BitmapFactory.decodeResource(res, ind);
    }
}
