package ru.liner.facerapp.engine.scenegraph.node.render;

import android.util.SparseArray;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.drawcache.RenderCachable;
import ru.liner.facerapp.engine.input.Action;
import ru.liner.facerapp.engine.input.ClickBoundable;
import ru.liner.facerapp.engine.input.ClickableProperty;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.dependency.DependencySceneNode;
import ru.liner.facerapp.engine.scenegraph.node.GraphNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public abstract class RenderableNode extends DependencySceneNode implements RenderCachable, ClickBoundable {
    private boolean isClickable = false;

    protected abstract void computePostTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> list);

    protected abstract void computePreTraversalRenderInstructions(@NonNull List<CanvasRenderInstruction> list);

    public void computeRenderInstructions(@NonNull List<CanvasRenderInstruction> instructions) {
        computePreTraversalRenderInstructions(instructions);
        List<GraphNode> children = getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                GraphNode child = children.get(i);
                if (child instanceof RenderableNode) {
                    ((RenderableNode) child).computeRenderInstructions(instructions);
                }
            }
        }
        computePostTraversalRenderInstructions(instructions);
    }

    @Override 
    public boolean isClickable() {
        if (this.isClickable) {
            return true;
        }
        SparseArray<Dependency<?>> dependencies = getDependencies();
        if (dependencies != null && dependencies.size() > 0) {
            for (int i = 0; i < dependencies.size(); i++) {
                Dependency<?> dependency = dependencies.valueAt(i);
                if ((dependency instanceof ClickableProperty)) {
                    ClickableProperty clickableProperty = (ClickableProperty) dependency;
                    if (clickableProperty.isClickable()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    @Override 
    public boolean onClicked() {
        SparseArray<Dependency<?>> dependencies = getDependencies();
        if (dependencies != null && dependencies.size() > 0) {
            for (int i = 0; i < dependencies.size(); i++) {
                Dependency<?> dependency = dependencies.valueAt(i);
                if ((dependency instanceof ClickableProperty)) {
                    ClickableProperty clickableProperty = (ClickableProperty) dependency;
                    Action action = clickableProperty.getClickAction();
                    if (clickableProperty.isClickable() && action != null) {
                        action.activate();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}