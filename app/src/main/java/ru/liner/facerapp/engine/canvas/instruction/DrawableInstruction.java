package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

@Deprecated
public class DrawableInstruction extends RectInstruction {
    private final Drawable drawable;

    public DrawableInstruction(@NonNull Drawable drawable, float width, float height) {
        super(width, height);
        this.drawable = drawable;
    }

    @Override
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        this.drawable.setDither(true);
        this.drawable.setFilterBitmap(true);
        RectF rectF = getRect();
        this.drawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        this.drawable.draw(canvas);
    }
}
