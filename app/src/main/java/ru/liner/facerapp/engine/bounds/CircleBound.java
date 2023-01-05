package ru.liner.facerapp.engine.bounds;

import android.graphics.Rect;

import androidx.annotation.NonNull;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class CircleBound implements Bound2D {
    final float radius;
    final float x;
    final float y;

    public CircleBound(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }

    @Override 
    public boolean test(float pointX, float pointY) {
        return test(pointX, pointY, this.radius);
    }

    public boolean test(float pointX, float pointY, float radius) {
        float distX = Math.abs(pointX - this.x);
        float distY = Math.abs(pointY - this.y);
        if (distX > radius || distY > radius) {
            return false;
        }
        return distX + distY <= radius || (distX * distX) + (distY * distY) <= radius * radius;
    }

    @Override 
    public boolean test(@NonNull Bound2D other) {
        if (other instanceof RectBound) {
            RectBound rectBound = (RectBound) other;
            Rect rect = rectBound.getBounds();
            return test(rect.left, rect.top) || test(rect.right, rect.top) || test(rect.left, rect.bottom) || test(rect.right, rect.bottom);
        } else if (other instanceof CircleBound) {
            CircleBound circleBound = (CircleBound) other;
            return circleBound.test(circleBound.getX(), circleBound.getY(), this.radius + circleBound.getRadius());
        }
        return false;
    }
}