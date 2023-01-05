package ru.liner.facerapp.engine.state;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public final class QuadraticUpdateController implements IUpdateController {
    private final long baseDelayInterval;
    @NotNull
    private final TimeUnit baseDelayTimeUnit;
    private int currentRetry;
    private long lastAttemptTime;
    private final long maxRetries;

    public QuadraticUpdateController(@NotNull TimeUnit baseDelayTimeUnit, long baseDelayInterval, long maxRetries) {
        this.baseDelayTimeUnit = baseDelayTimeUnit;
        this.baseDelayInterval = baseDelayInterval;
        this.maxRetries = maxRetries;
    }

    public final long getBaseDelayInterval() {
        return this.baseDelayInterval;
    }

    @NotNull
    public final TimeUnit getBaseDelayTimeUnit() {
        return this.baseDelayTimeUnit;
    }

    public final long getMaxRetries() {
        return this.maxRetries;
    }

    @Override // com.jeremysteckling.facerrel.lib.sync.local.util.IUpdateController
    public boolean canUpdate() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis > this.lastAttemptTime + calculateTimeBetweenRetry();
    }

    @Override // com.jeremysteckling.facerrel.lib.sync.local.util.IUpdateController
    public void onUpdateSuccess() {
        this.currentRetry = 0;
        this.lastAttemptTime = System.currentTimeMillis();
        Log.d(getClass().getSimpleName(), "Update retries reset, next update will be permitted in [" + TimeUnit.MILLISECONDS.toSeconds(calculateTimeBetweenRetry()) + "] seconds.");
    }

    @Override // com.jeremysteckling.facerrel.lib.sync.local.util.IUpdateController
    public void onUpdateFailed() {
        this.currentRetry++;
        this.lastAttemptTime = System.currentTimeMillis();
        Log.d(getClass().getSimpleName(), "Incremented retry count to [" + this.currentRetry + "], next update will be permitted in [" + TimeUnit.MILLISECONDS.toSeconds(calculateTimeBetweenRetry()) + "] seconds.");
    }

    private final long calculateTimeBetweenRetry() {
        long baseDelay = this.baseDelayTimeUnit.toMillis(this.baseDelayInterval);
        long delay = 1;
        long j = 0;
        if (j <= baseDelay) {
            while (true) {
                delay *= baseDelay;
                if (j == baseDelay) {
                    break;
                }
                j++;
            }
        }
        return delay;
    }
}