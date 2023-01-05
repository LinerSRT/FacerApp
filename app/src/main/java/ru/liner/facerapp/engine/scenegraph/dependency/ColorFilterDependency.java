package ru.liner.facerapp.engine.scenegraph.dependency;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.ColorInt;


public class ColorFilterDependency extends Dependency<ColorFilter> {
    public static final PorterDuff.Mode DEFAULT_FILTER_MODE = PorterDuff.Mode.MULTIPLY;
    @ColorInt
    private int color;
    private Dependency<Integer> colorDependency;
    private ColorFilter colorFilter;
    private PorterDuff.Mode filterMode;

    public ColorFilterDependency(@ColorInt int color) {
        this(color, DEFAULT_FILTER_MODE);
    }

    public ColorFilterDependency(@ColorInt int color, PorterDuff.Mode filterMode) {
        this.color = color;
        setFilterMode(filterMode);
    }

    public ColorFilterDependency(Dependency<Integer> colorDependency) {
        this(colorDependency, DEFAULT_FILTER_MODE);
    }

    public ColorFilterDependency(Dependency<Integer> colorDependency, PorterDuff.Mode filterMode) {
        this.colorDependency = colorDependency;
        this.filterMode = filterMode;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    public void setFilterMode(PorterDuff.Mode filterMode) {
        this.filterMode = filterMode;
    }

    @Override 
    public synchronized void invalidate() {
        super.invalidate();
        if (this.colorDependency.isInvalidated()) {
            this.colorDependency.invalidate();
        }
    }

    @Override 
    public synchronized boolean isInvalidated() {
        boolean isInvalidated;
        if (this.colorDependency != null) {
            isInvalidated = super.isInvalidated() || this.colorDependency.isInvalidated();
        } else {
            isInvalidated = super.isInvalidated();
        }
        return isInvalidated;
    }

    
    @Override 
    public void updateSelf(long currentTimeMillis) {
        if (this.colorDependency != null) {
            this.colorDependency.update(currentTimeMillis);
            this.color = this.colorDependency.get().intValue();
        }
        this.colorFilter = new PorterDuffColorFilter(this.color, this.filterMode);
    }

    
    @Override 
    public ColorFilter get() {
        return this.colorFilter;
    }
}
