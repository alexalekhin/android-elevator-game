package edu.amd.spbstu.elevator.AppEntities;

public class Time {
    /*
     * Time constants
     */
    public long minLevelTime = 0; // to be defined
    public long superPowerCoolDown = 0; // to be defined

    /*
     * Time variables
     */
    private long levelStartTime;

    private long prevTimeStep;
    private long curTimeStep;
    private long levelTimerDelta;

    private long timeStopTimerDelta;

    public Time(long startTime) {
        this.levelStartTime = startTime;
        this.prevTimeStep = startTime;
        this.curTimeStep = startTime;
        this.levelTimerDelta = 0;
        this.timeStopTimerDelta = 0;
    }

    public long getLevelStartTime() {
        return this.levelStartTime;
    }

    public long getLevelTimerDelta() {
        return curTimeStep - prevTimeStep;
    }

    public long getTimeStopTimerDelta() {
        return timeStopTimerDelta;
    }

    public long getMinLevelTime() {
        return minLevelTime;
    }

    public long getSuperPowerCoolDown() {
        return superPowerCoolDown;
    }

    public void updateCurrentTime() {
        this.curTimeStep = System.currentTimeMillis();
    }

    public void updatePreviousTime() {
        this.prevTimeStep = this.curTimeStep;
    }

}
