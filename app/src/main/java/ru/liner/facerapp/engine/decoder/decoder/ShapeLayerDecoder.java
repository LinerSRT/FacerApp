package ru.liner.facerapp.engine.decoder.decoder;

import android.graphics.Paint;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.liner.facerapp.engine.Shape;
import ru.liner.facerapp.engine.factory.ShapeBuilder;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.StyleNode;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.script.dependency.LegacyFloatScriptProperty;
import ru.liner.facerapp.engine.theme.ThemeProperty;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ShapeLayerDecoder extends LayerDecoder {
    public static final String HEIGHT = "height";
    public static final String RADIUS = "radius";
    public static final String SHAPE = "shape_type";
    public static final String SHAPE_CIRCLE = "0";
    public static final String SHAPE_LINE = "3";
    public static final String SHAPE_POLYGON = "2";
    public static final String SHAPE_SQUARE = "1";
    public static final String SHAPE_TRIANGLE = "4";
    public static final String SIDES = "sides";
    public static final String STYLE = "shape_opt";
    public static final String STYLE_FILL = "0";
    public static final String STYLE_STROKE = "1";
    public static final String WIDTH = "width";

    public ShapeLayerDecoder(ScriptEngine<String, String> engine) {
        super(engine);
    }

    public ShapeLayerDecoder(ScriptEngine<String, String> engine, List<ThemeProperty<String>> themeProperties) {
        super(engine, themeProperties);
    }

    public SceneNode decode(JSONObject layerJson) {
        if (layerJson == null) {
            return null;
        }
        try {
            ShapeBuilder shapeBuilder = createShapeBuilder(layerJson);
            if (shapeBuilder != null) {
                shapeBuilder.appendTransform(parseTransform(layerJson));
                shapeBuilder.setColorNode(parseColor(layerJson));
                shapeBuilder.setStyleNode(parseStyle(layerJson));
                return shapeBuilder.build();
            }
        } catch (JSONException e) {
            Log.e(ShapeLayerDecoder.class.getSimpleName(), "Unable to parse layer due to exception; aborting, some layers my not appear.", e);
        }
        return null;
    }

    protected ShapeBuilder createShapeBuilder(JSONObject layerJson) throws JSONException {
        String shapeText;
        Shape shape;
        if (layerJson == null || !layerJson.has(SHAPE) || (shapeText = layerJson.getString(SHAPE)) == null || "".equals(shapeText.trim())) {
            return null;
        }
        char c = 65535;
        switch (shapeText.hashCode()) {
            case 48:
                if (shapeText.equals("0")) {
                    c = 0;
                    break;
                }
                break;
            case 49:
                if (shapeText.equals("1")) {
                    c = 2;
                    break;
                }
                break;
            case 50:
                if (shapeText.equals("2")) {
                    c = 3;
                    break;
                }
                break;
            case 51:
                if (shapeText.equals(SHAPE_LINE)) {
                    c = 1;
                    break;
                }
                break;
            case 52:
                if (shapeText.equals(SHAPE_TRIANGLE)) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                shape = Shape.CIRCLE;
                break;
            case 1:
            case 2:
                shape = Shape.RECTANGLE;
                break;
            case 3:
                shape = parseShape(layerJson);
                break;
            case 4:
                shape = Shape.TRIANGLE;
                break;
            default:
                shape = Shape.TRIANGLE;
                break;
        }
        Dependency<Float> radiusDependency = null;
        if (layerJson.has(RADIUS)) {
            String radiusString = layerJson.getString(RADIUS);
            try {
                radiusDependency = new ConstantDependency<>(Float.valueOf(Float.parseFloat(radiusString)));
            } catch (NumberFormatException e) {
                radiusDependency = new LegacyFloatScriptProperty(getEngine(), radiusString, 1.0f);
            }
        }
        Dependency<Float> widthDependency = null;
        if (layerJson.has("width")) {
            String widthString = layerJson.getString("width");
            try {
                widthDependency = new ConstantDependency<>(Float.valueOf(Float.parseFloat(widthString)));
            } catch (NumberFormatException e2) {
                widthDependency = new LegacyFloatScriptProperty(getEngine(), widthString, 1.0f);
            }
        }
        Dependency<Float> heightDependency = null;
        if (layerJson.has("height")) {
            String heightString = layerJson.getString("height");
            try {
                heightDependency = new ConstantDependency<>(Float.valueOf(Float.parseFloat(heightString)));
            } catch (NumberFormatException e3) {
                heightDependency = new LegacyFloatScriptProperty(getEngine(), heightString, 1.0f);
            }
        }
        if (Shape.RECTANGLE.equals(shape)) {
            return new ShapeBuilder(shape, widthDependency, heightDependency);
        }
        return new ShapeBuilder(shape, radiusDependency);
    }

    protected Shape parseShape(@NonNull JSONObject layerJson) throws JSONException {
        if (layerJson.has(SIDES)) {
            int sides = layerJson.getInt(SIDES);
            switch (sides) {
                case 2:
                    return Shape.LINE;
                case 3:
                    return Shape.TRIANGLE;
                case 4:
                    return Shape.RECTANGLE;
                case 5:
                    return Shape.PENTAGON;
                case 6:
                    return Shape.HEXAGON;
                case 7:
                    return Shape.SEPTAGON;
                case 8:
                    return Shape.OCTAGON;
            }
        }
        return Shape.TRIANGLE;
    }

    protected StyleNode parseStyle(JSONObject layerJson) throws JSONException {
        if (layerJson != null && layerJson.has(STYLE)) {
            String effect = layerJson.getString(STYLE);
            if ("1".equals(effect) && layerJson.has(LayerDecoder.STROKE_SIZE)) {
                float size = (float) layerJson.getDouble(LayerDecoder.STROKE_SIZE);
                return new StyleNode(Paint.Style.STROKE, size);
            }
        }
        return new StyleNode(Paint.Style.FILL);
    }
}