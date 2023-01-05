package ru.liner.facerapp.engine.bounds;


import android.graphics.Rect;

import androidx.annotation.NonNull;

public class RectBound implements Bound2D {
    private final Rect bounds;

    public RectBound(@NonNull Rect bounds) {
        this.bounds = bounds;
    }

    public Rect getBounds() {
        return this.bounds;
    }

    @Override 
    public boolean test(float pointX, float pointY) {
        return this.bounds.contains((int) pointX, (int) pointY);
    }

    @Override 
    public boolean test(@NonNull Bound2D other) {
        if (other instanceof RectBound) {
            return this.bounds.contains(((RectBound) other).getBounds());
        }
        return false;
    }

    public String toString() {
        return this.bounds.toString();
    }
}