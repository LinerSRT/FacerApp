package ru.liner.facerapp.engine.scenegraph.node.render;

import android.graphics.Matrix;

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
import ru.liner.facerapp.engine.canvas.instruction.RestoreCanvasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.RotateCanvasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.SaveCanvasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.ScaleCanvasInstruction;
import ru.liner.facerapp.engine.canvas.instruction.TranslateCanvasInstruction;
import ru.liner.facerapp.utils.MathUtils;


public class TransformNode extends MultiPassRenderableNode {
    public static final int TRANSFORM_MASK_DESIGN_TO_SCREEN = 1;
    public static final int TRANSFORM_MASK_OBJECT_TO_ALIGNMENT = 4;
    public static final int TRANSFORM_MASK_SCREEN_TO_OBJECT = 2;
    private float posX;
    private float posY;
    private float rotationDeg;
    private RotateCanvasInstruction rotationInstruction;
    private ScaleCanvasInstruction scaleInstruction;
    private float scaleX;
    private float scaleY;
    private final int transformMask;
    private TranslateCanvasInstruction translateInstruction;

    public TransformNode() {
        this(TRANSFORM_MASK_SCREEN_TO_OBJECT);
    }

    public TransformNode(int mask) {
        super(RenderPass.ALL);
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.rotationDeg = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.translateInstruction = null;
        this.rotationInstruction = null;
        this.scaleInstruction = null;
        this.transformMask = mask;
    }

    public TransformNode(float posX, float posY) {
        this(posX, posY, TRANSFORM_MASK_SCREEN_TO_OBJECT);
    }

    public TransformNode(float posX, float posY, int mask) {
        super(RenderPass.ALL);
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.rotationDeg = 0.0f;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.translateInstruction = null;
        this.rotationInstruction = null;
        this.scaleInstruction = null;
        setPosX(posX);
        setPosY(posY);
        this.transformMask = mask;
    }

    public synchronized void setPosX(float posX) {
        this.posX = posX;
        float posY = getPosY();
        if (!MathUtils.isZero(posX) || !MathUtils.isZero(posY)) {
            this.translateInstruction = new TranslateCanvasInstruction(posX, posY);
        } else {
            this.translateInstruction = null;
        }
    }

    protected synchronized float getPosX() {
        return this.posX;
    }

    public synchronized void setPosY(float posY) {
        this.posY = posY;
        float posX = getPosX();
        if (!MathUtils.isZero(posX) || !MathUtils.isZero(posY)) {
            this.translateInstruction = new TranslateCanvasInstruction(posX, posY);
        } else {
            this.translateInstruction = null;
        }
    }

    protected synchronized float getPosY() {
        return this.posY;
    }

    public synchronized void setRotationDeg(float rotationDeg) {
        this.rotationDeg = rotationDeg;
        if (MathUtils.isZero(rotationDeg)) {
            this.rotationInstruction = null;
        }
        this.rotationInstruction = new RotateCanvasInstruction(rotationDeg, 0.0f, 0.0f);
    }

    protected synchronized float getRotationDeg() {
        return this.rotationDeg;
    }

    public synchronized void setScaleX(float scale) {
        this.scaleX = scale;
        float scaleY = getScaleY();
        if (!MathUtils.isZero(this.scaleX) || !MathUtils.isZero(scaleY)) {
            this.scaleInstruction = new ScaleCanvasInstruction(this.scaleX, scaleY);
        } else {
            this.scaleInstruction = null;
        }
    }

    protected synchronized float getScaleX() {
        return this.scaleX;
    }

    public synchronized void setScaleY(float scale) {
        this.scaleY = scale;
        float scaleX = getScaleX();
        if (!MathUtils.isZero(scaleX) || !MathUtils.isZero(this.scaleY)) {
            this.scaleInstruction = new ScaleCanvasInstruction(scaleX, this.scaleY);
        } else {
            this.scaleInstruction = null;
        }
    }

    protected synchronized float getScaleY() {
        return this.scaleY;
    }

    public int getTransformMask() {
        return this.transformMask;
    }

    @Override 
    protected void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        instructions.add(SaveCanvasInstruction.INSTRUCTION);
        if (this.translateInstruction != null) {
            instructions.add(this.translateInstruction);
        }
        if (this.rotationInstruction != null) {
            instructions.add(this.rotationInstruction);
        }
        if (this.scaleInstruction != null) {
            instructions.add(this.scaleInstruction);
        }
    }

    @Override 
    protected void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions, @NonNull RenderPass renderPass) {
        instructions.add(RestoreCanvasInstruction.INSTRUCTION);
    }

    public synchronized Matrix getTransformMatrix() {
        Matrix matrix;
        matrix = new Matrix();
        matrix.postTranslate(this.posX, this.posY);
        matrix.postRotate(this.rotationDeg);
        matrix.postScale(this.scaleX, this.scaleY);
        return matrix;
    }

    public synchronized Matrix getInverseTransformMatrix() {
        Matrix matrix;
        matrix = new Matrix();
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.postScale(this.scaleX, this.scaleY);
        scaleMatrix.invert(scaleMatrix);
        matrix.postConcat(scaleMatrix);
        Matrix rotMatrix = new Matrix();
        rotMatrix.postRotate(this.rotationDeg);
        rotMatrix.invert(rotMatrix);
        matrix.postConcat(rotMatrix);
        Matrix translateMatrix = new Matrix();
        translateMatrix.postTranslate(this.posX, this.posY);
        translateMatrix.invert(translateMatrix);
        matrix.postConcat(translateMatrix);
        return matrix;
    }

    public String toString() {
        return "T [" + this.posX + ", " + this.posY + "], R [" + this.rotationDeg + "], S [" + this.scaleX + ", " + this.scaleY + "]";
    }

    @Override 
    public Bound2D getClickBound() {
        return null;
    }
}
