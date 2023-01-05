package ru.liner.facerapp.engine;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;
import ru.liner.facerapp.utils.MathUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class FPSTracker {
    private double deltaSecondsSinceUpdate;
    private double fps;
    private int frameCount;
    private final double updatesPerSecond = 0.5d;
    private long lastFrameNanos = System.nanoTime();
    private final ArrayList<WeakReference<FrameRateListener>> frameRateListeners = new ArrayList<>();

    public final void increment() {
        long currentTimeNanos = System.nanoTime();
        Double d = MathUtils.NANOS_PER_SECOND;
        Intrinsics.checkExpressionValueIsNotNull(d, "MathUtils.NANOS_PER_SECOND");
        double deltaSeconds = (currentTimeNanos - this.lastFrameNanos) / d.doubleValue();
        double updateDelta = 1.0d / this.updatesPerSecond;
        this.frameCount++;
        this.deltaSecondsSinceUpdate += deltaSeconds;
        this.lastFrameNanos = currentTimeNanos;
        if (this.deltaSecondsSinceUpdate > 2.0d * updateDelta) {
            this.deltaSecondsSinceUpdate = 2.0d * updateDelta;
        }
        if (this.deltaSecondsSinceUpdate > updateDelta) {
            setFPS(this.frameCount / this.deltaSecondsSinceUpdate);
            if (this.frameCount > 0) {
                this.frameCount = 0;
                this.deltaSecondsSinceUpdate -= updateDelta;
            }
        }
    }

    private final synchronized void setFPS(double fps) {
        this.fps = fps;
        Iterable $receiver$iv = this.frameRateListeners;
        for (Object element$iv : $receiver$iv) {
            WeakReference listenerRef = (WeakReference) element$iv;
            FrameRateListener frameRateListener = (FrameRateListener) listenerRef.get();
            if (frameRateListener != null) {
                frameRateListener.onFrameRateChanged((float) fps);
            }
        }
    }

    public final synchronized double getFPS() {
        return this.fps;
    }

    public final synchronized void attachListener(@NotNull FrameRateListener frameRateListener) {
        Intrinsics.checkParameterIsNotNull(frameRateListener, "frameRateListener");
        this.frameRateListeners.add(new WeakReference<>(frameRateListener));
    }
}