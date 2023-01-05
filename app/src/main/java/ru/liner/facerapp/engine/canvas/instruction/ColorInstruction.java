package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class ColorInstruction implements CanvasRenderInstruction {
    private final int alpha;
    @ColorInt
    private final int color;
    public static final ColorInstruction MAGENTA = new ColorInstruction(Color.MAGENTA);
    public static final ColorInstruction WHITE = new ColorInstruction(Color.WHITE);
    public static final ColorInstruction BLACK = new ColorInstruction(Color.BLACK);
    public static final ColorInstruction BLUE = new ColorInstruction(Color.BLUE);
    public static final ColorInstruction RED = new ColorInstruction(Color.RED);
    public static final ColorInstruction GREEN = new ColorInstruction(Color.GREEN);

    public ColorInstruction(@ColorInt int color) {
        this(color, 255);
    }

    public ColorInstruction(@ColorInt int color, int alpha) {
        this.color = color;
        this.alpha = Math.max(Math.min(alpha, 255), 0);
    }

    @Override
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        paint.setColor(this.color);
        paint.setAlpha(this.alpha);
    }
}
