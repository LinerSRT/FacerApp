package ru.liner.facerapp.engine.decoder.decoder;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.factory.BitmapDependencyBuilder;
import ru.liner.facerapp.engine.scenegraph.dependency.ClipModeDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.dependency.RenderModeDependency;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.theme.ThemeProperty;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class DynamicImageLayerDecoder extends ImageLayerDecoder {
    public DynamicImageLayerDecoder(@NonNull Context context, ScriptEngine<String, String> engine, BitmapDependencyBuilder bitmapBuilder) {
        super(context, engine, bitmapBuilder);
    }

    public DynamicImageLayerDecoder(@NonNull Context context, ScriptEngine<String, String> engine, List<ThemeProperty<String>> themeProperties, BitmapDependencyBuilder bitmapBuilder) {
        super(context, engine, themeProperties, bitmapBuilder);
    }

    @Override
    protected Dependency<Bitmap> parseBitmapDependency(@NonNull JSONObject layerJson) throws JSONException {
        Dependency<Bitmap> ambientSquareDependency = parseBitmap(layerJson, ImageLayerDecoder.DYNAMIC_IMAGE_HASH_AMBIENT_SQUARE);
        Dependency<Bitmap> ambientRoundDependency = parseBitmap(layerJson, ImageLayerDecoder.DYNAMIC_IMAGE_HASH_AMBIENT_ROUND);
        Dependency<Bitmap> squareDependency = parseBitmap(layerJson, ImageLayerDecoder.DYNAMIC_IMAGE_HASH_SQUARE);
        Dependency<Bitmap> roundDependency = parseBitmap(layerJson, ImageLayerDecoder.DYNAMIC_IMAGE_HASH_ROUND);
        if (ambientRoundDependency == null && ambientSquareDependency == null && roundDependency == null && squareDependency == null)
            return null;
        ClipModeDependency<Bitmap> activeClipDependency = new ClipModeDependency<>(squareDependency, roundDependency);
        activeClipDependency.addVisibleMode(RenderEnvironment.ClipMode.SQUARE);
        ClipModeDependency<Bitmap> ambientClipDependency = new ClipModeDependency<>(ambientSquareDependency, ambientRoundDependency);
        ambientClipDependency.addVisibleMode(RenderEnvironment.ClipMode.SQUARE);
        RenderModeDependency<Bitmap> compositeDependency = new RenderModeDependency<>(activeClipDependency, ambientClipDependency);
        compositeDependency.addVisibleMode(RenderEnvironment.RenderMode.ACTIVE);
        compositeDependency.addVisibleMode(RenderEnvironment.RenderMode.DETAILED);
        return compositeDependency;
    }

    protected Dependency<Bitmap> parseBitmap(@NonNull JSONObject layerJson, @NonNull String bitmapField) throws JSONException {
        if (layerJson.has(bitmapField)) {
            return buildDependency(layerJson, layerJson.getString(bitmapField));
        }
        return null;
    }
}