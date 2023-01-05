package ru.liner.facerapp.engine.scenegraph.traversal;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.node.GraphNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public abstract class BaseSceneGraphTraverser implements SceneGraphTraverser {
    @Override 
    public void traverse(@NonNull SceneGraph sceneGraph) {
        GraphNode rootNode = sceneGraph.getRootNode();
        if (rootNode != null)
            traverse(sceneGraph, rootNode);
    }

    private void traverse(@NonNull SceneGraph sceneGraph, @NonNull GraphNode graphNode) {
        onPreTraversal(sceneGraph, graphNode);
        onTraversal(sceneGraph, graphNode);
        List<GraphNode> children = graphNode.getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                GraphNode child = children.get(i);
                if (child != null)
                    traverse(sceneGraph, child);
            }
        }
        onPostTraversal(sceneGraph, graphNode);
    }
}