package ru.liner.facerapp.engine.canvas;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

public interface CanvasRenderable {
    void render(@NonNull Canvas canvas, @NonNull Paint paint);
}
