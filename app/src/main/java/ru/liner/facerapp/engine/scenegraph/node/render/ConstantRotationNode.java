package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.PorterDuff;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.bounds.Bound2D;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ColorInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PathInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PorterDuffColorFilterInstruction;
import ru.liner.facerapp.engine.canvas.instruction.PorterDuffXferInstruction;

public class ConstantRotationNode extends TransformNode {
    private long lastUpdateTimeMillis;
    private float velocity;

    public ConstantRotationNode() {
        this.velocity = 0.0f;
        this.lastUpdateTimeMillis = 0;
    }

    public ConstantRotationNode(float velocity) {
        this.lastUpdateTimeMillis = 0;
        this.velocity = velocity;
    }

    @Override 
    public void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.lastUpdateTimeMillis == 0) {
            this.lastUpdateTimeMillis = currentTimeMillis;
        }
        setRotationDeg(getRotationDeg() + (this.velocity * (((float) (currentTimeMillis - this.lastUpdateTimeMillis)) / 1000.0f)));
        this.lastUpdateTimeMillis = currentTimeMillis;
    }
}
