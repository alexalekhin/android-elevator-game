package edu.amd.spbstu.elevator.AppEntities.Emotions;

import edu.amd.spbstu.elevator.AppEntities.Time;

public class Anger implements IEmotion {

    private float level;
    private float increaseStep, reduceStep;


    public Anger(float level, float increaseStep, float reduceStep) {
        this.level = level;
        this.reduceStep = reduceStep;
        this.increaseStep = increaseStep;
    }

    @Override
    public void increase(float scale) {
        this.level += increaseStep * scale;
    }

    @Override
    public void reduce(float scale) {
        this.level += reduceStep * scale;
    }

    @Override
    public float getLevel() {
        return level;
    }

    public void setLevel(float lvl) {
        level = lvl;
    }

}
