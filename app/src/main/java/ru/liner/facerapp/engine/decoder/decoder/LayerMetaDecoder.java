package ru.liner.facerapp.engine.decoder.decoder;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.liner.facerapp.engine.factory.BitmapDependencyBuilder;
import ru.liner.facerapp.engine.factory.TypefaceDependencyBuilder;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.theme.ThemeProperty;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LayerMetaDecoder extends LayerDecoder {
    private BitmapDependencyBuilder bitmapBuilder;
    private final Context context;
    private TypefaceDependencyBuilder typefaceBuilder;

    public LayerMetaDecoder(@NonNull Context context, ScriptEngine<String, String> engine, TypefaceDependencyBuilder typefaceBuilder, BitmapDependencyBuilder bitmapBuilder) {
        this(context, engine, null, typefaceBuilder, bitmapBuilder);
    }

    public LayerMetaDecoder(@NonNull Context context, ScriptEngine<String, String> engine, List<ThemeProperty<String>> themeProperties, TypefaceDependencyBuilder typefaceBuilder, BitmapDependencyBuilder bitmapBuilder) {
        super(engine, themeProperties);
        this.context = context;
        this.typefaceBuilder = typefaceBuilder;
        this.bitmapBuilder = bitmapBuilder;
    }

    public SceneNode decode(JSONObject layerJson) {
        if (layerJson == null) {
            return null;
        }
        try {
            if (layerJson.has("type")) {
                SceneNode visibilityNode = parseVisibility(layerJson);
                String type = layerJson.getString("type");
                SceneNode contentNode = null;
                switch (type) {
                    case LayerDecoder.TYPE_TEXT:
                        contentNode = parseTextLayer(layerJson);
                        break;
                    case LayerDecoder.TYPE_SHAPE:
                        contentNode = parseShapeLayer(layerJson);
                        break;
                    case LayerDecoder.TYPE_IMAGE:
                        contentNode = parseImageLayer(context, layerJson);
                        break;
                    case LayerDecoder.TYPE_DYNAMIC_IMAGE:
                        contentNode = parseDynamicImageLayer(context, layerJson);
                        break;
                }
                if (visibilityNode == null) {
                    return contentNode;
                }
                if (contentNode != null) {
                    visibilityNode.attachChild(contentNode);
                    return visibilityNode;
                }
                return visibilityNode;
            }
        } catch (JSONException e) {
            Log.e(LayerMetaDecoder.class.getSimpleName(), "Could not parse watchface due to exception; aborting, layers may be missing.", e);
        }
        return null;
    }

    protected SceneNode parseTextLayer(JSONObject layerJson) {
        return new TextLayerDecoder(getEngine(), getThemeProperties(), this.typefaceBuilder).decode(layerJson);
    }

    protected SceneNode parseShapeLayer(JSONObject layerJson) {
        return new ShapeLayerDecoder(getEngine(), getThemeProperties()).decode(layerJson);
    }

    protected SceneNode parseImageLayer(@NonNull Context context, JSONObject layerJson) {
        return new ImageLayerDecoder(context, getEngine(), getThemeProperties(), this.bitmapBuilder).decode(layerJson);
    }

    protected SceneNode parseDynamicImageLayer(@NonNull Context context, JSONObject layerJson) {
        return new DynamicImageLayerDecoder(context, getEngine(), getThemeProperties(), this.bitmapBuilder).decode(layerJson);
    }
}