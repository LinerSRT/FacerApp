package ru.liner.facerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.view.InputDeviceCompat;

import ru.liner.facerapp.engine.Shape;
import ru.liner.facerapp.engine.canvas.RenderPass;
import ru.liner.facerapp.engine.factory.BitmapBuilder;
import ru.liner.facerapp.engine.factory.DrawableBuilder;
import ru.liner.facerapp.engine.factory.ShapeBuilder;
import ru.liner.facerapp.engine.factory.TextBuilder;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.BaseSceneGraph;
import ru.liner.facerapp.engine.scenegraph.ClearMode;
import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ClearScreenNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ConstantRotationNode;
import ru.liner.facerapp.engine.scenegraph.node.render.GlowNode;
import ru.liner.facerapp.engine.scenegraph.node.render.StyleNode;
import ru.liner.facerapp.engine.scenegraph.node.render.TransformNode;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class SceneGraphTestFactory {
    public static SceneGraph getClearScreenTestGraph() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode clearScreenNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, -65281);
        sceneGraph.setRootNode(clearScreenNode);
        return sceneGraph;
    }

    public static SceneGraph getSimpleTriangleGraph() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode clearScreenNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, -65281);
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.TRIANGLE, 1000.0f);
        TransformNode transformNode = new TransformNode(720.0f, 1280.0f);
        transformNode.setRotationDeg(45.0f);
        shapeBuilder.appendTransform(transformNode);
        shapeBuilder.setColorNode(new ColorNode(-16776961));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.STROKE, 10.0f));
        SceneNode triangleNode = shapeBuilder.build();
        clearScreenNode.attachChild(triangleNode);
        sceneGraph.setRootNode(clearScreenNode);
        return sceneGraph;
    }

    public static SceneGraph getRotatingHexagonTest() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode clearScreenNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#202020"));
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.HEXAGON, 500.0f);
        shapeBuilder.appendTransform(new TransformNode(720.0f, 1280.0f));
        shapeBuilder.appendTransform(new ConstantRotationNode(22.0f));
        shapeBuilder.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.STROKE, 15.0f));
        SceneNode shapeNode = shapeBuilder.build();
        clearScreenNode.attachChild(shapeNode);
        sceneGraph.setRootNode(clearScreenNode);
        return sceneGraph;
    }

    public static SceneGraph getAlphaHexagonTest() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode clearScreenNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#202020"));
        TransformNode transformNode = new TransformNode(720.0f, 1280.0f);
        ConstantRotationNode rotationNode = new ConstantRotationNode(22.0f);
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.HEXAGON, 500.0f);
        shapeBuilder.appendTransform(transformNode);
        shapeBuilder.appendTransform(rotationNode);
        shapeBuilder.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.STROKE, 15.0f));
        clearScreenNode.attachChild(shapeBuilder.build());
        ShapeBuilder shapeBuilder2 = new ShapeBuilder(Shape.HEXAGON, 500.0f);
        shapeBuilder2.appendTransform(transformNode);
        shapeBuilder2.appendTransform(rotationNode);
        shapeBuilder2.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY, ColorNode.toAlphaInt(0.05d)));
        shapeBuilder2.setStyleNode(new StyleNode(Paint.Style.FILL));
        clearScreenNode.attachChild(shapeBuilder2.build());
        sceneGraph.setRootNode(clearScreenNode);
        return sceneGraph;
    }

    public static SceneGraph getRotatingTextTest() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode clearScreenNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#103510"));
        TransformNode transformNode = new TransformNode(720.0f, 1280.0f);
        ConstantRotationNode rotationNode = new ConstantRotationNode(22.0f);
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.HEXAGON, 500.0f);
        shapeBuilder.appendTransform(transformNode);
        shapeBuilder.appendTransform(rotationNode);
        shapeBuilder.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.STROKE, 15.0f));
        clearScreenNode.attachChild(shapeBuilder.build());
        TextBuilder textBuilder = new TextBuilder("Hello World", Typeface.DEFAULT, 120.0f);
        textBuilder.setAlignment(Paint.Align.CENTER);
        textBuilder.appendTransform(transformNode);
        textBuilder.appendTransform(rotationNode);
        textBuilder.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY, ColorNode.toAlphaInt(0.05d)));
        textBuilder.setGlowNode(new GlowNode(Color.RED, 300));
        clearScreenNode.attachChild(textBuilder.build());
        sceneGraph.setRootNode(clearScreenNode);
        return sceneGraph;
    }

    public static SceneGraph getAlignedShapeTest() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode rootNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#201020"));
        sceneGraph.setRootNode(rootNode);
        TransformNode transformNode = new TransformNode(720.0f, 1280.0f);
        ConstantRotationNode rotationNode = new ConstantRotationNode(22.0f);
        rootNode.attachChild(transformNode);
        transformNode.attachChild(rotationNode);
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.RECTANGLE, 800.0f, 500.0f);
        shapeBuilder.setAlignment(Alignment.CENTER);
        shapeBuilder.setColorNode(new ColorNode(-16776961));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.FILL));
        rotationNode.attachChild(shapeBuilder.build());
        ShapeBuilder shapeBuilder2 = new ShapeBuilder(Shape.TRIANGLE, 80.0f);
        shapeBuilder2.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY, ColorNode.toAlphaInt(0.5d)));
        shapeBuilder2.setStyleNode(new StyleNode(Paint.Style.STROKE, 10.0f));
        rotationNode.attachChild(shapeBuilder2.build());
        return sceneGraph;
    }

    @Deprecated
    public static SceneGraph getDrawableScenegraphTest(@NonNull Context context) {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode rootNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#201020"));
        sceneGraph.setRootNode(rootNode);
        TransformNode transformNode = new TransformNode(720.0f, 1280.0f);
        ConstantRotationNode rotationNode = new ConstantRotationNode(22.0f);
        rootNode.attachChild(transformNode);
        transformNode.attachChild(rotationNode);
        Drawable drawable = context.getResources().getDrawable(R.drawable.moto360_overlay);
        DrawableBuilder drawableBuilder = new DrawableBuilder(drawable, 800.0f, 800.0f);
        drawableBuilder.setAlignment(Alignment.CENTER);
        rotationNode.attachChild(drawableBuilder.build());
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.TRIANGLE, 80.0f);
        shapeBuilder.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY, ColorNode.toAlphaInt(0.5d)));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.STROKE, 10.0f));
        rotationNode.attachChild(shapeBuilder.build());
        return sceneGraph;
    }

    @Deprecated
    public static SceneGraph getWatchSizedHexagonTest() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode clearScreenNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#202020"));
        TransformNode transformNode = new TransformNode(150.0f, 180.0f);
        ConstantRotationNode rotationNode = new ConstantRotationNode(22.0f);
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.HEXAGON, 100.0f);
        shapeBuilder.appendTransform(transformNode);
        shapeBuilder.appendTransform(rotationNode);
        shapeBuilder.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.STROKE, 5.0f));
        clearScreenNode.attachChild(shapeBuilder.build());
        ShapeBuilder shapeBuilder2 = new ShapeBuilder(Shape.HEXAGON, 100.0f);
        shapeBuilder2.appendTransform(transformNode);
        shapeBuilder2.appendTransform(rotationNode);
        shapeBuilder2.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY, ColorNode.toAlphaInt(0.05d)));
        shapeBuilder2.setStyleNode(new StyleNode(Paint.Style.FILL));
        clearScreenNode.attachChild(shapeBuilder2.build());
        sceneGraph.setRootNode(clearScreenNode);
        return sceneGraph;
    }

    @Deprecated
    public static SceneGraph getWatchAlignedShapeTest() {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode rootNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#201020"));
        sceneGraph.setRootNode(rootNode);
        TransformNode transformNode = new TransformNode(180.0f, 180.0f);
        ConstantRotationNode rotationNode = new ConstantRotationNode(22.0f);
        rootNode.attachChild(transformNode);
        transformNode.attachChild(rotationNode);
        ShapeBuilder shapeBuilder = new ShapeBuilder(Shape.RECTANGLE, 100.0f, 200.0f);
        shapeBuilder.setAlignment(Alignment.CENTER);
        shapeBuilder.setColorNode(new ColorNode(-16776961));
        shapeBuilder.setStyleNode(new StyleNode(Paint.Style.FILL));
        rotationNode.attachChild(shapeBuilder.build());
        ShapeBuilder shapeBuilder2 = new ShapeBuilder(Shape.TRIANGLE, 80.0f);
        shapeBuilder2.setColorNode(new ColorNode(InputDeviceCompat.SOURCE_ANY, ColorNode.toAlphaInt(0.5d)));
        shapeBuilder2.setStyleNode(new StyleNode(Paint.Style.STROKE, 10.0f));
        rotationNode.attachChild(shapeBuilder2.build());
        return sceneGraph;
    }

    public static SceneGraph getTexturedQuadTest(@NonNull Context context, int width, int height) {
        SceneGraph sceneGraph = new BaseSceneGraph();
        ClearScreenNode rootNode = new ClearScreenNode(ClearMode.RGB_BUFFER, RenderPass.INITIALIZE, Color.parseColor("#990000"));
        sceneGraph.setRootNode(rootNode);
        TransformNode transformNode = new TransformNode(width/2f,width/2f);
        rootNode.attachChild(transformNode);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.large_render_test);
        BitmapBuilder builder = new BitmapBuilder(new ConstantDependency<>(bitmap), new ConstantDependency<>(300f), new ConstantDependency<>(300f));
        builder.setAlignment(Alignment.CENTER);
        builder.setDrawPass(RenderPass.DEFAULT);
        builder.appendTransform(new ConstantRotationNode(100));
        transformNode.attachChild(builder.build());
        return sceneGraph;
    }

}
