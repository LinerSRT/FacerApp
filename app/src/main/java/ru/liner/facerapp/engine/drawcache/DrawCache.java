package ru.liner.facerapp.engine.drawcache;

import java.util.List;

import ru.liner.facerapp.engine.canvas.CachedRenderable;

public interface DrawCache<T extends CachedRenderable> {
    List<T> getRenderQueue();
}
