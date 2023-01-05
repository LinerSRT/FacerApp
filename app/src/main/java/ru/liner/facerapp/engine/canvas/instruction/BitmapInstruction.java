package ru.liner.facerapp.engine.canvas.instruction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.NonNull;

public class BitmapInstruction extends RectInstruction {
    private final Bitmap bitmap;
    private final Rect bitmapBounds;

    public BitmapInstruction(@NonNull Bitmap bitmap, @NonNull Rect bitmapBounds, float width, float height) {
        super(width, height);
        this.bitmap = bitmap;
        this.bitmapBounds = bitmapBounds;
    }

    @Override 
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        if (!this.bitmap.isRecycled()) {
            RectF targetRect = getRect();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setFilterBitmap(true);
            canvas.drawBitmap(this.bitmap, this.bitmapBounds, targetRect, paint);
        }
    }
}
