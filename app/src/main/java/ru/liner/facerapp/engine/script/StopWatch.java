package ru.liner.facerapp.engine.script;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class StopWatch {
    private long lastStartedTimeMillis;
    private long lastStoppedTimeMillis;
    private long totalLapTimeMillis;

    public final long getLastStartedTimeMillis() {
        return this.lastStartedTimeMillis;
    }

    public final void setLastStartedTimeMillis(long j) {
        this.lastStartedTimeMillis = j;
    }

    public final long getLastStoppedTimeMillis() {
        return this.lastStoppedTimeMillis;
    }

    public final void setLastStoppedTimeMillis(long j) {
        this.lastStoppedTimeMillis = j;
    }

    public final long getTotalLapTimeMillis() {
        return this.totalLapTimeMillis;
    }

    public final void setTotalLapTimeMillis(long j) {
        this.totalLapTimeMillis = j;
    }

    public void toggle() {
        if (isRunning()) {
            pause();
        } else if (isPaused()) {
            resume();
        } else {
            start();
        }
    }

    public final void start() {
        this.lastStoppedTimeMillis = 0L;
        this.lastStartedTimeMillis = System.currentTimeMillis();
    }

    public final void pause() {
        this.lastStoppedTimeMillis = System.currentTimeMillis();
    }

    public final void resume() {
        this.totalLapTimeMillis += this.lastStoppedTimeMillis - this.lastStartedTimeMillis;
        start();
    }

    public void reset() {
        this.lastStartedTimeMillis = 0L;
        this.lastStoppedTimeMillis = 0L;
        this.totalLapTimeMillis = 0L;
    }

    public void toggleAndReset() {
        if (isRunning()) {
            pause();
        } else if (isPaused()) {
            reset();
        } else {
            start();
        }
    }

    public final long getCurrentLapTimeMillis() {
        return isRunning() ? System.currentTimeMillis() - this.lastStartedTimeMillis : this.lastStoppedTimeMillis - this.lastStartedTimeMillis;
    }

    public long getElapsedTimeMillis() {
        return this.totalLapTimeMillis + getCurrentLapTimeMillis();
    }

    public boolean isRunning() {
        return this.lastStartedTimeMillis != 0 && this.lastStoppedTimeMillis == 0;
    }

    public final boolean isPaused() {
        return (this.lastStartedTimeMillis == 0 || this.lastStoppedTimeMillis == 0) ? false : true;
    }

    public final boolean isReset() {
        return this.lastStartedTimeMillis == 0;
    }

}
