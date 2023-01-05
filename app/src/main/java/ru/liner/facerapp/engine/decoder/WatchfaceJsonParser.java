package ru.liner.facerapp.engine.decoder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.Shape;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.decoder.decoder.DataDecoder;
import ru.liner.facerapp.engine.decoder.decoder.LayerDecoder;
import ru.liner.facerapp.engine.factory.ShapeBuilder;
import ru.liner.facerapp.engine.factory.TextBuilder;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.BaseSceneGraph;
import ru.liner.facerapp.engine.scenegraph.ClearMode;
import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.dependency.FPSVisibilityDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.FramerateTextDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.ScaleDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.TapPointDebugDependency;
import ru.liner.facerapp.engine.scenegraph.node.GraphNode;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ClearScreenNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ClipToCircleNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.TransformNode;
import ru.liner.facerapp.engine.scenegraph.node.render.VisibilityNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyTransformNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyVisibilityNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public abstract class WatchfaceJsonParser {
    public static final String TAG = WatchfaceJsonParser.class.getSimpleName();
    private final float canvasHeight;
    private final float canvasWidth;
    protected abstract DataDecoder<JSONObject, SceneNode> getLayerDecoder();

    protected abstract SceneGraph getSceneGraph(JSONArray jSONArray);

    public WatchfaceJsonParser(Context context, float canvasSizeX, float canvasSizeY) {
        this.canvasWidth = canvasSizeX;
        this.canvasHeight = canvasSizeY;
    }

    protected float getCanvasWidth() {
        return this.canvasWidth;
    }

    protected float getCanvasHeight() {
        return this.canvasHeight;
    }

    public SceneGraph parse(JSONArray watchfaceJson) {
        if (watchfaceJson == null || watchfaceJson.length() <= 0) {
            return new BaseSceneGraph();
        }
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null) {
            environment.setHasDetailMode(false);
        }
        SceneGraph sceneGraph = getSceneGraph(watchfaceJson);
        ClearScreenNode alphaClearNode = new ClearScreenNode(ClearMode.ALPHA, RenderPass.INITIALIZE);
        sceneGraph.setRootNode(alphaClearNode);
        ClipToCircleNode clipNode = new ClipToCircleNode(this.canvasWidth);
        alphaClearNode.attachChild(clipNode);
        ClearScreenNode clearNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.BLACK);
        clipNode.attachChild(clearNode);
        DependencyTransformNode rootNode = new DependencyTransformNode(1);
        rootNode.setScaleXDependency(new ScaleDependency(getCanvasWidth(), ScaleDependency.Mode.x));
        rootNode.setScaleYDependency(new ScaleDependency(getCanvasHeight(), ScaleDependency.Mode.y));
        clearNode.attachChild(rootNode);
        if (sceneGraph instanceof BaseSceneGraph) {
            ((BaseSceneGraph) sceneGraph).setAttachPoint(rootNode);
        }
        SceneNode fpsNode = buildFPSCounter();
        if (fpsNode != null) {
            rootNode.attachChild(fpsNode);
        }
        SceneNode tapPointDebugNode = buildTapPointDebugNode();
        if (tapPointDebugNode != null) {
            clearNode.attachChild(tapPointDebugNode);
        }
        DataDecoder<JSONObject, SceneNode> layerDecoder = getLayerDecoder();
        int builtLayers = 0;
        for (int i = 0; i < watchfaceJson.length(); i++) {
            try {
                JSONObject layerData = watchfaceJson.getJSONObject(i);
                SceneNode layerNode = layerDecoder.decode(layerData);
                if (layerNode != null) {
                    rootNode.attachChild(layerNode);
                    builtLayers++;
                } else {
                    Log.e(WatchfaceJsonParser.class.getSimpleName(), "Built null layer for json: \n\t" + layerData.toString());
                }
                if (layerData != null) {
                    handleLayerConfiguration(layerData);
                }
            } catch (JSONException e) {
                Log.e(WatchfaceJsonParser.class.getSimpleName(), "Unable to build SceneGraph due to error, watchface layer will not be displayed; skipping layer.");
            }
        }
        Log.e(WatchfaceJsonParser.class.getSimpleName(), "Successfully built [" + builtLayers + "] watchface layers from JSON.");
        return sceneGraph;
    }

    protected SceneNode buildFPSCounter() {
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.RECTANGLE, 100.0f, 35.0f);
        shapeBuilder.setColorNode(new ColorNode(ViewCompat.MEASURED_STATE_MASK, ColorNode.toAlphaInt(0.8d)));
        shapeBuilder.appendTransform(new TransformNode(220.0f, 57.0f));
        shapeBuilder.setAlignment(Alignment.BOTTOM);
        shapeBuilder.setDrawPass(RenderPass.FINAL);
        GraphNode shapeNode = shapeBuilder.build();
        VisibilityNode visibilityNode = new DependencyVisibilityNode(new FPSVisibilityDependency());
        visibilityNode.attachChild(shapeNode);
        TextBuilder builder = new TextBuilder("", Typeface.DEFAULT, 30.0f);
        builder.setTextDependency(new FramerateTextDependency());
        builder.setColorNode(new ColorNode(-1, ColorNode.toAlphaInt(0.800000011920929d)));
        builder.appendTransform(new TransformNode(220.0f, 50.0f));
        builder.setAlignment(Paint.Align.CENTER);
        builder.setDrawPass(RenderPass.FINAL);
        visibilityNode.attachChild(builder.build());
        return visibilityNode;
    }

    protected SceneNode buildTapPointDebugNode() {
        DependencyTransformNode transformNode = new DependencyTransformNode();
        transformNode.setXDependency(new TapPointDebugDependency(TapPointDebugDependency.Mode.X));
        transformNode.setYDependency(new TapPointDebugDependency(TapPointDebugDependency.Mode.Y));
        ShapeBuilder builder = new ShapeBuilder(Shape.HEXAGON, 10.0f);
        builder.appendTransform(transformNode);
        builder.setColorNode(new ColorNode(-65281, ColorNode.toAlphaInt(0.6d)));
        builder.setAlignment(Alignment.CENTER);
        builder.setDrawPass(RenderPass.DEBUG);
        return builder.build();
    }

    protected void handleLayerConfiguration(@NonNull JSONObject layerJson) throws JSONException {
        RenderEnvironment environment;
        if (layerJson.has(LayerDecoder.DYNAMIC_MODE_TWO_ENABLED) && layerJson.getBoolean(LayerDecoder.DYNAMIC_MODE_TWO_ENABLED)) {
            if ((!layerJson.has(LayerDecoder.DYNAMIC_MODE_ONE_ENABLED) || !layerJson.getBoolean(LayerDecoder.DYNAMIC_MODE_ONE_ENABLED)) && (environment = RenderEnvironment.getInstance()) != null) {
                environment.setHasDetailMode(true);
            }
        }
    }

}
