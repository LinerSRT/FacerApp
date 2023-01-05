package ru.liner.facerapp.engine.scenegraph.node;

import java.util.List;

public interface GraphNode {
    boolean attachChild(GraphNode graphNode);
    void clear();
    List<GraphNode> getChildren();
    boolean unattachChild(GraphNode graphNode);
}
