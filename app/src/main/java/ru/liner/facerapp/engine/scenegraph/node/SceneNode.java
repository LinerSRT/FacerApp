package ru.liner.facerapp.engine.scenegraph.node;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class SceneNode extends BaseGraphNode{
    public void update(long currentTimeMillis){
        updateSelf(currentTimeMillis);
        updateChildren(currentTimeMillis);
    }

    public void updateSelf(long currentTimeMillis){

    }

    protected void updateChildren(long currentTimeMillis){
        for(GraphNode graphNode : getChildren())
            if(graphNode instanceof SceneNode)
                ((SceneNode) graphNode).update(currentTimeMillis);
    }
}
