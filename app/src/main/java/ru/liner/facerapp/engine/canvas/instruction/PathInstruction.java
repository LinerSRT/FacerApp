package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.NonNull;

public class PathInstruction implements CanvasRenderInstruction {
    private final Path path;

    protected final Path getPath() {
        return this.path;
    }

    public PathInstruction(@NonNull Path path) {
        this.path = path;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawPath(this.path, paint);
    }
}
