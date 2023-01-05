package ru.liner.facerapp.engine.scenegraph;

import ru.liner.facerapp.engine.scenegraph.node.SceneNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public interface SceneGraph {
    SceneNode getRootNode();

    void setRootNode(SceneNode sceneNode);

    void update(long currentTimeMillis);

}
