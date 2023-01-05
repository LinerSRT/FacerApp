package ru.liner.facerapp.engine.decoder.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.liner.facerapp.engine.factory.BitmapBuilder;
import ru.liner.facerapp.engine.factory.BitmapDependencyBuilder;
import ru.liner.facerapp.engine.scenegraph.dependency.ColorFilterDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.ColorFilterNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyColorFilterNode;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.script.dependency.LegacyFloatScriptProperty;
import ru.liner.facerapp.engine.theme.ThemeProperty;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ImageLayerDecoder extends LayerDecoder {
    public static final String DYNAMIC_IMAGE_HASH_AMBIENT_ROUND = "hash_round_ambient";
    public static final String DYNAMIC_IMAGE_HASH_AMBIENT_SQUARE = "hash_square_ambient";
    public static final String DYNAMIC_IMAGE_HASH_ROUND = "hash_round";
    public static final String DYNAMIC_IMAGE_HASH_SQUARE = "hash_square";
    public static final String HEIGHT = "height";
    public static final String IMAGE_HASH = "hash";
    public static final String IS_TINTED = "is_tinted";
    public static final String TINT_COLOR = "tint_color";
    public static final String USES_COMPLICATION = "is_complication";
    public static final String WIDTH = "width";
    private final BitmapDependencyBuilder bitmapBuilder;

    public ImageLayerDecoder(@NonNull Context context, ScriptEngine<String, String> engine, BitmapDependencyBuilder bitmapBuilder) {
        this(context, engine, null, bitmapBuilder);
    }

    public ImageLayerDecoder(@NonNull Context context, ScriptEngine<String, String> engine, List<ThemeProperty<String>> themeProperties, BitmapDependencyBuilder bitmapBuilder) {
        super(engine, themeProperties);
        this.bitmapBuilder = bitmapBuilder;
    }

    public SceneNode decode(JSONObject layerJson) {
        SceneNode sceneNode = null;
        if (layerJson != null) {
            try {
                sceneNode = build(layerJson);
            } catch (JSONException e) {
                Log.e(ImageLayerDecoder.class.getSimpleName(), "Unable to parse layer due to exception; aborting, some layers may not appear.", e);
            }
        }
        return sceneNode;
    }

    protected SceneNode build(@NonNull JSONObject layerJson) throws JSONException {
        BitmapBuilder bitmapBuilder = createBitmapBuilder(layerJson);
        if (bitmapBuilder != null) {
            bitmapBuilder.appendTransform(parseTransform(layerJson));
            bitmapBuilder.setAlignment(parseAlignment(layerJson));
            bitmapBuilder.setAlpha(parseColor(layerJson));
            bitmapBuilder.setColorFilter(parseColorFilter(layerJson));
            return bitmapBuilder.build();
        }
        return null;
    }

    protected BitmapBuilder createBitmapBuilder(JSONObject layerJson) throws JSONException {
        if (layerJson != null) {
            Dependency<Bitmap> bitmapDependency = parseBitmapDependency(layerJson);
            Dependency<Float> widthDependency = parseSizeDependency(layerJson, WIDTH);
            Dependency<Float> heightDependency = parseSizeDependency(layerJson, HEIGHT);
            return new BitmapBuilder(bitmapDependency, widthDependency, heightDependency);
        }
        return null;
    }

    protected Dependency<Float> parseSizeDependency(@NonNull JSONObject layerJson, @NonNull String dimension) throws JSONException {
        if (!layerJson.has(dimension)) {
            return null;
        }
        String sizeString = layerJson.getString(dimension);
        try {
            Dependency<Float> dependency = new ConstantDependency<>(Float.valueOf(Float.parseFloat(sizeString)));
            return dependency;
        } catch (NumberFormatException e) {
            Dependency<Float> dependency2 = new LegacyFloatScriptProperty(getEngine(), sizeString, 1.0f);
            return dependency2;
        }
    }

    protected Dependency<Bitmap> parseBitmapDependency(@NonNull JSONObject layerJson) throws JSONException {
        if (layerJson.has(IMAGE_HASH)) {
            String hash = layerJson.getString(IMAGE_HASH);
            return buildDependency(layerJson, hash);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Dependency<Bitmap> buildDependency(@NonNull JSONObject layerJson, String hash) throws JSONException {
        if (hash == null || "".equals(hash.trim()) || this.bitmapBuilder == null) {
            return null;
        }
        return this.bitmapBuilder.build(layerJson, hash);
    }

    protected ColorFilterNode parseColorFilter(@NonNull JSONObject layerJson) throws JSONException {
        Dependency<Integer> colorDependency = decodeColorDependency(layerJson);
        if (colorDependency != null) {
            return new DependencyColorFilterNode(colorDependency);
        }
        return null;
    }

    protected Dependency<ColorFilter> parseColorFilterDependency(@NonNull JSONObject layerJson) throws JSONException {
        Dependency<Integer> colorDependency = decodeColorDependency(layerJson);
        if (colorDependency != null) {
            return new ColorFilterDependency(colorDependency);
        }
        return null;
    }

    protected Dependency<Integer> decodeColorDependency(@NonNull JSONObject layerJson) throws JSONException {
        if (isThemedLayer(layerJson, getThemeProperties())) {
            return parseThemeColorDependency(layerJson, getThemeProperties());
        }
        if (layerJson.has(IS_TINTED) && layerJson.getBoolean(IS_TINTED)) {
            return parseColorDependency(layerJson, TINT_COLOR, -1);
        }
        return null;
    }


    protected boolean usesComplications(@NonNull JSONObject layerJson) throws JSONException {
        if (layerJson.has(USES_COMPLICATION)) {
            return layerJson.getBoolean(USES_COMPLICATION);
        }
        return false;
    }
}
