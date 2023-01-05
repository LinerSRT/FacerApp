package ru.liner.facerapp.engine.decoder.decoder;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.liner.facerapp.engine.factory.TextBuilder;
import ru.liner.facerapp.engine.factory.TypefaceDependencyBuilder;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.dependency.StringToLowerDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.StringToUpperDependency;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.GlowNode;
import ru.liner.facerapp.engine.scenegraph.node.render.StyleNode;
import ru.liner.facerapp.engine.scenegraph.node.render.TransformNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyGlowNode;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.script.dependency.LegacyFloatScriptProperty;
import ru.liner.facerapp.engine.script.dependency.LegacyStringScriptProperty;
import ru.liner.facerapp.engine.theme.ThemeProperty;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class TextLayerDecoder extends LayerDecoder {
    public static final String BACKGROUND_COLOR = "bgcolor";
    public static final int FONT_FAMILY_CUSTOM = 9;
    public static final int FONT_FAMILY_ROBOTO = 3;
    public static final int FONT_FAMILY_ROBOTO_BLACK = 4;
    public static final int FONT_FAMILY_ROBOTO_CONDENSED = 5;
    public static final int FONT_FAMILY_ROBOTO_CONDENSED_LIGHT = 2;
    public static final int FONT_FAMILY_ROBOTO_LIGHT = 1;
    public static final int FONT_FAMILY_ROBOTO_SLAB = 8;
    public static final int FONT_FAMILY_ROBOTO_SLAB_LIGHT = 7;
    public static final int FONT_FAMILY_ROBOTO_SLAB_THIN = 6;
    public static final int FONT_FAMILY_ROBOTO_THIN = 0;
    public static final String TEXT = "text";
    public static final String TEXT_BOLD = "bold";
    public static final String TEXT_EFFECT = "text_effect";
    public static final String TEXT_EFFECT_GLOW = "2";
    public static final String TEXT_EFFECT_STROKE = "1";
    public static final String TEXT_FONT_FAMILY = "font_family";
    public static final String TEXT_ITALIC = "italic";
    public static final String TEXT_SIZE = "size";
    public static final String TEXT_TRANSFORM = "transform";
    public static final String TEXT_TRANSFORM_LOWER = "2";
    public static final String TEXT_TRANSFORM_UPPER = "1";
    public static final String TEXT_TYPEFACE = "font_hash";
    public static final String TEXT_TYPEFACE_HASH = "new_font_name";
    private final TypefaceDependencyBuilder typefaceDependencyBuilder;

    public TextLayerDecoder(ScriptEngine<String, String> engine, TypefaceDependencyBuilder typefaceBuilder) {
        this(engine, null, typefaceBuilder);
    }

    public TextLayerDecoder(ScriptEngine<String, String> engine, List<ThemeProperty<String>> themeProperties, TypefaceDependencyBuilder typefaceBuilder) {
        super(engine, themeProperties);
        this.typefaceDependencyBuilder = typefaceBuilder;
    }

    public SceneNode decode(JSONObject layerJson) {
        if (layerJson == null) {
            return null;
        }
        try {
            TextBuilder textBuilder = createTextBuilder(layerJson);
            if (textBuilder != null) {
                textBuilder.appendTransform(parseTransform(layerJson));
                textBuilder.setColorNode(parseColor(layerJson));
                textBuilder.setAlignment(parseTextAlignment(layerJson));
                textBuilder.setStyleNode(parseStrokeStyle(layerJson));
                textBuilder.setGlowNode(parseGlow(layerJson));
                if (hasStrokeEffect(layerJson)) {
                    TransformNode rootNode = new TransformNode();
                    rootNode.attachChild(textBuilder.build());
                    TextBuilder textBuilder2 = createTextBuilder(layerJson);
                    if (textBuilder2 != null) {
                        textBuilder2.appendTransform(parseTransform(layerJson));
                        textBuilder2.setAlignment(parseTextAlignment(layerJson));
                        textBuilder2.setStyleNode(new StyleNode(Paint.Style.FILL));
                        textBuilder2.setColorNode(super.parseColor(layerJson));
                        rootNode.attachChild(textBuilder2.build());
                        return rootNode;
                    }
                    return rootNode;
                }
                return textBuilder.build();
            }
        } catch (JSONException e) {
            Log.e(TextLayerDecoder.class.getSimpleName(), "Unable to parse layer due to exception; aborting.", e);
        }
        return null;
    }

    protected TextBuilder createTextBuilder(JSONObject layerJson) throws JSONException {
        if (layerJson != null && layerJson.has("text")) {
            String text = layerJson.getString("text");
            Dependency<String> textDependency = new LegacyStringScriptProperty(getEngine(), text, "");
            if (layerJson.has(TEXT_TRANSFORM)) {
                String transform = layerJson.getString(TEXT_TRANSFORM);
                if ("1".equals(transform)) {
                    textDependency = new StringToUpperDependency(textDependency);
                } else if ("2".equals(transform)) {
                    textDependency = new StringToLowerDependency(textDependency);
                }
            }
            Dependency<Typeface> typefaceDependency = null;
            if (this.typefaceDependencyBuilder != null) {
                typefaceDependency = this.typefaceDependencyBuilder.build(layerJson);
            }
            Dependency<Float> sizeDependency = null;
            if (layerJson.has(TEXT_SIZE)) {
                String sizeString = layerJson.getString(TEXT_SIZE);
                try {
                    sizeDependency = new ConstantDependency<>(Float.valueOf(Float.parseFloat(sizeString)));
                } catch (NumberFormatException e) {
                    sizeDependency = new LegacyFloatScriptProperty(getEngine(), sizeString, 1.0f);
                }
            }
            return new TextBuilder(textDependency, typefaceDependency, sizeDependency);
        }
        return null;
    }

    protected Paint.Align parseTextAlignment(JSONObject layerJson) throws JSONException {
        if (layerJson != null && layerJson.has(LayerDecoder.ALIGNMENT)) {
            String alignment = layerJson.getString(LayerDecoder.ALIGNMENT);
            if ("0".equals(alignment)) {
                return Paint.Align.LEFT;
            }
            if ("1".equals(alignment)) {
                return Paint.Align.CENTER;
            }
            if ("2".equals(alignment)) {
                return Paint.Align.RIGHT;
            }
        }
        return Paint.Align.LEFT;
    }

    protected StyleNode parseStrokeStyle(JSONObject layerJson) throws JSONException {
        if (layerJson == null || !hasStrokeEffect(layerJson) || !layerJson.has(LayerDecoder.STROKE_SIZE)) {
            return null;
        }
        float size = (float) layerJson.getDouble(LayerDecoder.STROKE_SIZE);
        return new StyleNode(Paint.Style.STROKE, size);
    }

    protected GlowNode parseGlow(@NonNull JSONObject layerJson) throws JSONException {
        if (!hasGlowEffect(layerJson) || !layerJson.has(LayerDecoder.GLOW_SIZE)) {
            return null;
        }
        Dependency<Integer> color = parseColorDependency(layerJson, LayerDecoder.GLOW_COLOR, -1);
        Dependency<Integer> size = new ConstantDependency<>(Integer.valueOf(layerJson.getInt(LayerDecoder.GLOW_SIZE)));
        return new DependencyGlowNode(color, size);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.parse.decoder.LayerDecoder
    public DependencyColorNode parseColor(JSONObject layerJson) throws JSONException {
        Dependency<Integer> colorDependency;
        DependencyColorNode colorNode = super.parseColor(layerJson);
        if (layerJson != null && colorNode != null && hasStrokeEffect(layerJson) && layerJson.has(LayerDecoder.STROKE_COLOR) && (colorDependency = parseColorDependency(layerJson, LayerDecoder.STROKE_COLOR, -1, true)) != null) {
            colorNode.setColorDependency(colorDependency);
        }
        return colorNode;
    }

    protected boolean hasStrokeEffect(JSONObject layerJson) throws JSONException {
        if (layerJson != null && layerJson.has(TEXT_EFFECT)) {
            String effect = layerJson.getString(TEXT_EFFECT);
            if ("1".equals(effect)) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasGlowEffect(JSONObject layerJson) throws JSONException {
        if (layerJson != null && layerJson.has(TEXT_EFFECT)) {
            String effect = layerJson.getString(TEXT_EFFECT);
            if ("2".equals(effect)) {
                return true;
            }
        }
        return false;
    }
}