package ru.liner.facerapp.engine.bounds;

import androidx.annotation.NonNull;

public interface Bound2D {
    boolean test(float f, float f2);

    boolean test(@NonNull Bound2D bound2D);

}
