package edu.amd.spbstu.elevator.AppEntities.Emotions;

import edu.amd.spbstu.elevator.AppEntities.Time;

public class Happiness implements IEmotion {

    private float level;
    private float increaseStep, reduceStep;


    public Happiness(float level, float increaseStep, float reduceStep) {
        this.level = level;
        this.reduceStep = reduceStep;
        this.increaseStep = increaseStep;
    }

    @Override
    public void reduce(float scale) {
        this.level -= reduceStep * scale;
    }

    @Override
    public void increase(float scale) {
        this.level += increaseStep * scale;
    }

    @Override
    public float getLevel() {
        return level;
    }


}
