package ru.liner.facerapp.engine.drawcache;

import java.util.ArrayList;
import java.util.List;

import ru.liner.facerapp.engine.canvas.CachedRenderable;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class ArrayDrawCache implements DrawCache<CachedRenderable> {
    private final List<CachedRenderable> renderQueue = new ArrayList();

    public void append(CachedRenderable cachable) {
        if (cachable != null) {
            this.renderQueue.add(cachable);
        }
    }

    @Override 
    public List<CachedRenderable> getRenderQueue() {
        return this.renderQueue;
    }
}