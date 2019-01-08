package edu.amd.spbstu.elevator.AppEntities.Emotions;

public interface IEmotion {
    float getLevel();
    void reduce(float scale);
    void increase(float scale);
}
