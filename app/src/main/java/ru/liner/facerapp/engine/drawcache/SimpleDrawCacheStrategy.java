package ru.liner.facerapp.engine.drawcache;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.canvas.CachedRenderable;
import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.node.BaseGraphNode;
import ru.liner.facerapp.engine.scenegraph.node.render.RenderableNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class SimpleDrawCacheStrategy implements DrawCacheStrategy<CachedRenderable> {
    private final List<CanvasRenderInstruction> instructions = new ArrayList();

    @Override 
    public DrawCache<CachedRenderable> generate(SceneGraph sceneGraph) {
        if (sceneGraph == null) {
            return null;
        }
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null) {
            environment.clearDefaultPaintInstructions();
        }
        BaseGraphNode rootNode = sceneGraph.getRootNode();
        if (rootNode != null && (rootNode instanceof RenderableNode)) {
            return computeDrawCache((RenderableNode) rootNode);
        }
        return null;
    }

    protected DrawCache<CachedRenderable> computeDrawCache(@NonNull RenderableNode rootNode) {
        flushCache();
        rootNode.computeRenderInstructions(this.instructions);
        if (!this.instructions.isEmpty()) {
            CachedRenderable renderable = new CachedRenderable();
            renderable.addAll(this.instructions);
            ArrayDrawCache cache = new ArrayDrawCache();
            cache.append(renderable);
            return cache;
        }
        return null;
    }

    @Override 
    public void flushCache() {
        this.instructions.clear();
    }

    protected void printRenderInstructions(List<CanvasRenderInstruction> instructions) {
        int instructionCount = instructions != null ? instructions.size() : 0;
        StringBuilder builder = new StringBuilder("DrawCache Instruction List: ([" + instructionCount + "] Total)");
        if (instructions != null) {
            for (int i = 0; i < instructions.size(); i++) {
                builder.append("\n\t").append(i).append(" -> ").append(instructions.get(i).getClass().getSimpleName());
            }
        }
        Log.e(SimpleDrawCacheStrategy.class.getSimpleName(), builder.toString());
    }
}