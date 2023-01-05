package ru.liner.facerapp.engine.scenegraph.traversal;


import android.graphics.Matrix;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Stack;

import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.node.GraphNode;
import ru.liner.facerapp.engine.scenegraph.node.render.TransformNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class TransformStackTraverser extends BaseSceneGraphTraverser {
    public static final int TRANFORM_MASK_DEFAULT = 65535;
    private final Stack<Matrix> transformStack = new Stack<>();
    private int transformMask = 65535;

    public void setTransformMask(int mask) {
        this.transformMask = mask;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.traversal.SceneGraphTraverser
    public void onPreTraversal(@NonNull SceneGraph sceneGraph, @NonNull GraphNode graphNode) {
        if (graphNode instanceof TransformNode) {
            TransformNode transformNode = (TransformNode) graphNode;
            if ((transformNode.getTransformMask() & this.transformMask) != 0) {
                Matrix matrix = transformNode.getTransformMatrix();
                this.transformStack.push(matrix);
            }
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.traversal.SceneGraphTraverser
    public void onPostTraversal(@NonNull SceneGraph sceneGraph, @NonNull GraphNode graphNode) {
        if (graphNode instanceof TransformNode) {
            TransformNode transformNode = (TransformNode) graphNode;
            if ((transformNode.getTransformMask() & this.transformMask) != 0) {
                this.transformStack.pop();
            }
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.traversal.SceneGraphTraverser
    public void onTraversal(@NonNull SceneGraph sceneGraph, @NonNull GraphNode graphNode) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Matrix calculateInverseTranformMatrix() {
        Matrix result = new Matrix();
        if (!this.transformStack.isEmpty()) {
            Iterator<Matrix> it = this.transformStack.iterator();
            while (it.hasNext()) {
                Matrix transform = it.next();
                if (!transform.isIdentity()) {
                    Matrix inverse = new Matrix();
                    transform.invert(inverse);
                    result.postConcat(inverse);
                }
            }
        }
        return result;
    }

    protected Matrix calculateTransformMatrix() {
        Matrix result = new Matrix();
        if (!this.transformStack.isEmpty()) {
            Iterator<Matrix> it = this.transformStack.iterator();
            while (it.hasNext()) {
                Matrix transform = it.next();
                if (!transform.isIdentity()) {
                    result.postConcat(transform);
                }
            }
        }
        return result;
    }
}