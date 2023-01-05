package ru.liner.facerapp.engine.scenegraph.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class BaseGraphNode implements GraphNode {
    private final List<GraphNode> children = new ArrayList<>();

    @Override
    public synchronized boolean attachChild(GraphNode graphNode) {
        if (graphNode == null)
            return false;
        children.add(graphNode);
        return true;
    }

    @Override
    public synchronized void clear() {
        children.clear();;
    }

    @Override
    public synchronized List<GraphNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public synchronized boolean unattachChild(GraphNode graphNode) {
        return graphNode != null && children.remove(graphNode);
    }
}
