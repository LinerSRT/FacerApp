package ru.liner.facerapp.engine.scenegraph;

import ru.liner.facerapp.engine.scenegraph.node.SceneNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class BaseSceneGraph implements SceneGraph{
    private SceneNode rootNode = null;
    private SceneNode attachPoint = null;

    @Override
    public synchronized SceneNode getRootNode() {
        return rootNode;
    }

    @Override
    public synchronized void setRootNode(SceneNode rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public synchronized void update(long currentTimeMillis) {
        if (rootNode != null)
            rootNode.update(currentTimeMillis);
    }

    public synchronized void setAttachPoint(SceneNode attachPoint) {
        this.attachPoint = attachPoint;
    }

    public synchronized SceneNode getAttachPoint() {
        return attachPoint;
    }

}
