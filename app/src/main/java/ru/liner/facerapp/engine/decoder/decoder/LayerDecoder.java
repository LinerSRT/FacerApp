package ru.liner.facerapp.engine.decoder.decoder;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.scenegraph.Alignment;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.scenegraph.dependency.RenderModeDependency;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.scenegraph.node.render.TransformNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyColorNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyTransformNode;
import ru.liner.facerapp.engine.scenegraph.node.render.dependency.DependencyVisibilityNode;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.script.dependency.AlphaFloatProperty;
import ru.liner.facerapp.engine.script.dependency.LegacyFloatScriptProperty;
import ru.liner.facerapp.engine.script.dependency.LegacyIntegerScriptProperty;
import ru.liner.facerapp.engine.theme.ThemeOption;
import ru.liner.facerapp.engine.theme.ThemeProperty;
import ru.liner.facerapp.engine.theme.ThemeTarget;
import ru.liner.facerapp.engine.theme.dependency.ThemeColorDependency;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public abstract class LayerDecoder implements DataDecoder<JSONObject, SceneNode> {
    public static final String ALIGNMENT = "alignment";
    public static final String ALIGNMENT_CENTER = "1";
    public static final String ALIGNMENT_LEFT = "0";
    public static final String ALIGNMENT_RIGHT = "2";
    public static final String ALPHA = "opacity";
    public static final String AMBIENT_MODE_COLOR = "low_power_color";
    public static final String AMBIENT_MODE_ENABLED = "low_power";
    public static final String COLOR = "color";
    public static final String DEFAULT_THEME_OPTION = "Default";
    public static final String DYNAMIC_MODE_ONE_ENABLED = "state_active_1";
    public static final String DYNAMIC_MODE_TWO_ENABLED = "state_active_2";
    public static final String GLOW_COLOR = "glow_color";
    public static final String GLOW_SIZE = "glow_size";
    public static final String POSITION_X = "x";
    public static final String POSITION_Y = "y";
    public static final String ROTATION_DEGREES = "r";
    public static final String STROKE_COLOR = "stroke_color";
    public static final String STROKE_SIZE = "stroke_size";
    public static final String TYPE = "type";
    public static final String TYPE_DYNAMIC_IMAGE = "dynamic_image";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_SHAPE = "shape";
    public static final String TYPE_TEXT = "text";
    public static final String UID = "uid";
    private final ScriptEngine<String, String> engine;
    private final List<ThemeProperty<String>> themeProperties;

    public LayerDecoder(ScriptEngine<String, String> engine) {
        this(engine, null);
    }

    public LayerDecoder(ScriptEngine<String, String> engine, List<ThemeProperty<String>> themeProperties) {
        this.engine = engine;
        this.themeProperties = themeProperties;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ScriptEngine<String, String> getEngine() {
        return this.engine;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<ThemeProperty<String>> getThemeProperties() {
        return this.themeProperties;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TransformNode parseTransform(JSONObject layerJson) throws JSONException {
        DependencyTransformNode transformNode = new DependencyTransformNode();
        if (layerJson != null) {
            if (layerJson.has(POSITION_X)) {
                String value = layerJson.getString(POSITION_X);
                try {
                    transformNode.setPosX(Float.parseFloat(value));
                } catch (NumberFormatException e) {
                    if (this.engine != null) {
                        transformNode.setXDependency(new LegacyFloatScriptProperty(this.engine, value, 0.0f));
                    }
                }
            }
            if (layerJson.has(POSITION_Y)) {
                String value2 = layerJson.getString(POSITION_Y);
                try {
                    transformNode.setPosY(Float.parseFloat(value2));
                } catch (NumberFormatException e2) {
                    if (this.engine != null) {
                        transformNode.setYDependency(new LegacyFloatScriptProperty(this.engine, value2, 0.0f));
                    }
                }
            }
            if (layerJson.has(ROTATION_DEGREES)) {
                String value3 = layerJson.getString(ROTATION_DEGREES);
                try {
                    transformNode.setRotationDeg(Float.parseFloat(value3));
                } catch (NumberFormatException e3) {
                    if (this.engine != null) {
                        transformNode.setRotDependency(new LegacyFloatScriptProperty(this.engine, value3, 0.0f));
                    }
                }
            }
        }
        return transformNode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DependencyVisibilityNode parseVisibility(JSONObject layerJson) throws JSONException {
        DependencyVisibilityNode visibilityNode = new DependencyVisibilityNode();
        if (layerJson != null) {
            RenderModeDependency<Boolean> visibilityDependency = new RenderModeDependency<>(new ConstantDependency(true), new ConstantDependency(false));
            boolean hasHandledVisibiltiy = false;
            if (layerJson.has(AMBIENT_MODE_ENABLED)) {
                boolean isAmbientEnabled = layerJson.getBoolean(AMBIENT_MODE_ENABLED);
                if (isAmbientEnabled) {
                    visibilityDependency.addVisibleMode(RenderEnvironment.RenderMode.AMBIENT);
                }
                hasHandledVisibiltiy = true;
            }
            if (layerJson.has(DYNAMIC_MODE_ONE_ENABLED)) {
                boolean isEnabled = layerJson.getBoolean(DYNAMIC_MODE_ONE_ENABLED);
                if (isEnabled) {
                    visibilityDependency.addVisibleMode(RenderEnvironment.RenderMode.ACTIVE);
                }
                hasHandledVisibiltiy = true;
            } else {
                visibilityDependency.addVisibleMode(RenderEnvironment.RenderMode.ACTIVE);
            }
            if (layerJson.has(DYNAMIC_MODE_TWO_ENABLED)) {
                boolean isEnabled2 = layerJson.getBoolean(DYNAMIC_MODE_TWO_ENABLED);
                if (isEnabled2) {
                    visibilityDependency.addVisibleMode(RenderEnvironment.RenderMode.DETAILED);
                }
                hasHandledVisibiltiy = true;
            } else {
                visibilityDependency.addVisibleMode(RenderEnvironment.RenderMode.DETAILED);
            }
            if (hasHandledVisibiltiy) {
                visibilityNode.setVisibilityDependency(visibilityDependency);
                return visibilityNode;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Alignment parseAlignment(@NonNull JSONObject layerJson) throws JSONException {
        if (layerJson.has(ALIGNMENT)) {
            int alignmentIndex = layerJson.getInt(ALIGNMENT);
            switch (alignmentIndex) {
                case 0:
                    return Alignment.TOP_LEFT;
                case 1:
                    return Alignment.TOP;
                case 2:
                    return Alignment.TOP_RIGHT;
                case 3:
                    return Alignment.LEFT;
                case 4:
                    return Alignment.CENTER;
                case 5:
                    return Alignment.RIGHT;
                case 6:
                    return Alignment.BOTTOM_LEFT;
                case 7:
                    return Alignment.BOTTOM;
                case 8:
                    return Alignment.BOTTOM_RIGHT;
            }
        }
        return Alignment.CENTER;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DependencyColorNode parseColor(JSONObject layerJson) throws JSONException {
        DependencyColorNode colorNode = new DependencyColorNode(null);
        if (layerJson != null) {
            Dependency<Integer> colorDependency = null;
            List<ThemeProperty<String>> themeProperties = getThemeProperties();
            if (themeProperties != null) {
                colorDependency = parseThemeColorDependency(layerJson, themeProperties);
            }
            if (colorDependency != null) {
                colorNode.setColorDependency(colorDependency);
            } else {
                Dependency<Integer> colorDependency2 = parseColorDependency(layerJson, "color", -1);
                if (colorDependency2 != null) {
                    colorNode.setColorDependency(colorDependency2);
                }
            }
            colorNode.setAlphaDependency(parseAlphaDependency(layerJson));
        }
        return colorNode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Dependency<Float> parseAlphaDependency(@NonNull JSONObject layerJson) throws JSONException {
        if (layerJson.has(ALPHA)) {
            String value = layerJson.getString(ALPHA);
            try {
                return new ConstantDependency(Float.valueOf(Float.parseFloat(value) * 0.01f));
            } catch (NumberFormatException e) {
                if (this.engine != null) {
                    return new AlphaFloatProperty(this.engine, value, 100.0f);
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Dependency<Integer> parseColorDependency(@NonNull JSONObject layerJson, @NonNull String tag, @ColorInt int defaultColor) throws JSONException {
        return parseColorDependency(layerJson, tag, defaultColor, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Dependency<Integer> parseColorDependency(@NonNull JSONObject layerJson, @NonNull String tag, @ColorInt int defaultColor, boolean shouldIgnoreAmbient) throws JSONException {
        if (layerJson.has(tag)) {
            String valueString = layerJson.getString(tag);
            Dependency<Integer> activeDependency = null;
            Dependency<Integer> disabledDependency = null;
            try {
                int value = Integer.parseInt(valueString);
                if (value == 0) {
                    value = -16777216;
                }
                Dependency<Integer> activeDependency2 = new ConstantDependency<>(Integer.valueOf(value));
                activeDependency = activeDependency2;
            } catch (NumberFormatException e) {
                if (this.engine != null) {
                    activeDependency = new LegacyIntegerScriptProperty(this.engine, valueString, Integer.valueOf(defaultColor));
                }
            }
            if (!shouldIgnoreAmbient) {
                if (layerJson.has(AMBIENT_MODE_COLOR)) {
                    String ambientString = layerJson.getString(AMBIENT_MODE_COLOR);
                    try {
                        int value2 = Integer.parseInt(ambientString);
                        if (value2 == 0) {
                            value2 = -16777216;
                        }
                        disabledDependency = new ConstantDependency<>(Integer.valueOf(value2));
                    } catch (NumberFormatException e2) {
                        if (this.engine != null) {
                            disabledDependency = new LegacyIntegerScriptProperty(this.engine, ambientString, Integer.valueOf((int) ViewCompat.MEASURED_STATE_MASK));
                        }
                    }
                }
                if (activeDependency != null) {
                    if (disabledDependency != null) {
                        return getRenderColorDependency(activeDependency, disabledDependency);
                    }
                    return activeDependency;
                } else if (disabledDependency != null) {
                    Dependency<Integer> activeDependency3 = disabledDependency;
                    return activeDependency3;
                }
            } else {
                return activeDependency;
            }
        }
        return null;
    }

    private RenderModeDependency<Integer> getRenderColorDependency(Dependency<Integer> active, Dependency<Integer> ambient) {
        RenderModeDependency<Integer> renderModeDependency = new RenderModeDependency<>(active, ambient);
        renderModeDependency.addVisibleMode(RenderEnvironment.RenderMode.ACTIVE);
        renderModeDependency.addVisibleMode(RenderEnvironment.RenderMode.DETAILED);
        return renderModeDependency;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Dependency<Integer> parseThemeColorDependency(@NonNull JSONObject layerJson, List<ThemeProperty<String>> themeProperties) throws JSONException {
        Log.v(LayerDecoder.class.getSimpleName(), " -> parseThemeColorDependency()");
        if (!isThemedLayer(layerJson, themeProperties) || themeProperties == null || themeProperties.isEmpty())
            return null;
        for(ThemeProperty<String> themeProperty : themeProperties){
            if(ThemeProperty.Type.COLOR.equals(themeProperty.getType())){
                ThemeTarget<String> themeTarget = themeProperty.getTarget();
                if(themeTarget != null) {
                    if(themeTarget.matches(ThemeTarget.Type.LAYER, parseLayerID(layerJson))){
                        List<ThemeOption<String>> themeOptions = themeProperty.getOptions();
                        if(themeOptions == null || themeOptions.isEmpty())
                            return new ThemeColorDependency(parseLayerID(layerJson), Color.TRANSPARENT);
                        for(ThemeOption<String> option : themeOptions){
                            if(option.getName().equals(DEFAULT_THEME_OPTION)){
                                String id = themeProperty.getID();
                                int value = Integer.parseInt(option.value());
                                if(value != 0){
                                    return new ThemeColorDependency(id, value);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isThemedLayer(@NonNull JSONObject layerJson, List<ThemeProperty<String>> themeProperties) throws JSONException {
        // Declare variables outside of the if block to avoid re-declaring them
        String layerID = parseLayerID(layerJson);
        String simpleName = LayerDecoder.class.getSimpleName();

// Check if themeProperties and layerID are not null before looping through the list
        if (themeProperties != null && layerID != null) {
            for (ThemeProperty<String> themeProperty : themeProperties) {
                ThemeTarget target = themeProperty.getTarget();
                if (target != null && target.matches(ThemeTarget.Type.LAYER, layerID)) {
                    Log.v(simpleName, "Layer [" + layerID + "] is themed!");
                    return true;
                }
            }
        }

// Log a message if the loop completes without returning true
        Log.v(simpleName, "Layer [" + layerID + "] is NOT themed!");
        return false;
    }

    protected String parseLayerID(@NonNull JSONObject layerJson) throws JSONException {
        if (layerJson.has("uid")) {
            return layerJson.getString("uid");
        }
        return null;
    }
}
