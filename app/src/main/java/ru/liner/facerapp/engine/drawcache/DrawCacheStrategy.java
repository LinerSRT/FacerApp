package ru.liner.facerapp.engine.drawcache;

import ru.liner.facerapp.engine.canvas.CachedRenderable;
import ru.liner.facerapp.engine.scenegraph.SceneGraph;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public interface DrawCacheStrategy<T extends CachedRenderable> {
    void flushCache();

    DrawCache<T> generate(SceneGraph sceneGraph);
}
