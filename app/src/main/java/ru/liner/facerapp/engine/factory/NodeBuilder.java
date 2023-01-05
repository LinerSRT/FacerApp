package ru.liner.facerapp.engine.factory;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.dependency.DependencySceneNode;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.MultiPassRenderableNode;
import ru.liner.facerapp.engine.scenegraph.node.render.TransformNode;

/* loaded from: classes.dex */
public abstract class NodeBuilder {
    private final List<TransformNode> transforms = new ArrayList();
    private final SceneNode rootNode = new TransformNode();
    private SceneNode previousNode = null;
    private RenderPass renderPass = null;
    private boolean hasBeenBuilt = false;
    private final Map<Integer, Dependency> dependencies = new HashMap(0);

    protected abstract boolean buildNodes();

    public NodeBuilder appendTransform(TransformNode transformNode) {
        this.transforms.add(transformNode);
        return this;
    }

    public NodeBuilder setDrawPass(RenderPass renderPass) {
        this.renderPass = renderPass;
        return this;
    }

    public NodeBuilder appendDependency(int dependencyID, Dependency dependency) {
        this.dependencies.put(Integer.valueOf(dependencyID), dependency);
        return this;
    }

    public SceneNode build() {
        if (this.hasBeenBuilt) {
            throw new IllegalStateException("NodeBuilder.build() cannot be called twice! This will cause a StackOverflowError if permitted.");
        }
        this.hasBeenBuilt = true;
        if (!this.transforms.isEmpty()) {
            for (TransformNode transform : this.transforms) {
                attach(transform);
            }
        }
        if (buildNodes()) {
            return this.rootNode;
        }
        return null;
    }

    protected void attach(SceneNode childNode) {
        if (childNode != null) {
            if ((childNode instanceof MultiPassRenderableNode) && this.renderPass != null) {
                ((MultiPassRenderableNode) childNode).setDrawPass(this.renderPass);
            }
            if (this.previousNode != null && this.previousNode != childNode) {
                this.previousNode.attachChild(childNode);
            } else if (this.rootNode != childNode) {
                this.rootNode.attachChild(childNode);
            }
            this.previousNode = childNode;
        }
    }

    protected void applyDependencies(@NonNull DependencySceneNode primaryNode) {
        if (!this.dependencies.isEmpty()) {
            for (Integer dependencyID : this.dependencies.keySet()) {
                primaryNode.setDependency(dependencyID, this.dependencies.get(dependencyID));
            }
        }
    }
}
