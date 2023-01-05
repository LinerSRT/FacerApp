package ru.liner.facerapp.engine.scenegraph.traversal;

import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.node.GraphNode;

public interface SceneGraphTraverser {
    void onPostTraversal(@NonNull SceneGraph sceneGraph, @NonNull GraphNode graphNode);

    void onPreTraversal(@NonNull SceneGraph sceneGraph, @NonNull GraphNode graphNode);

    void onTraversal(@NonNull SceneGraph sceneGraph, @NonNull GraphNode graphNode);

    void traverse(@NonNull SceneGraph sceneGraph);

}
