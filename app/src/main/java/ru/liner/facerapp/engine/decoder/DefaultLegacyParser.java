package ru.liner.facerapp.engine.decoder;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import ru.liner.facerapp.engine.decoder.decoder.DataDecoder;
import ru.liner.facerapp.engine.decoder.decoder.LayerMetaDecoder;
import ru.liner.facerapp.engine.factory.BitmapDependencyBuilder;
import ru.liner.facerapp.engine.factory.TypefaceDependencyBuilder;
import ru.liner.facerapp.engine.model.WatchfaceData;
import ru.liner.facerapp.engine.scenegraph.LegacySceneGraph;
import ru.liner.facerapp.engine.scenegraph.SceneGraph;
import ru.liner.facerapp.engine.scenegraph.node.SceneNode;
import ru.liner.facerapp.engine.script.LegacyScriptEngine;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.theme.ThemeProperty;
import ru.liner.facerapp.engine.theme.parse.WatchfaceThemeJsonParser;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class DefaultLegacyParser extends WatchfaceJsonParser {
    private final BitmapDependencyBuilder bitmapBuilder;
    private final Context context;
    private final ScriptEngine<String, String> engine;
    private final TypefaceDependencyBuilder typefaceBuilder;
    private List<ThemeProperty<String>> themeProperties = null;

    /* JADX INFO: Access modifiers changed from: protected */
    public DefaultLegacyParser(@NonNull Context context, ScriptEngine<String, String> engine, float canvasSizeX, float canvasSizeY, TypefaceDependencyBuilder typefaceBuilder, BitmapDependencyBuilder bitmapBuilder) {
        super(context, canvasSizeX, canvasSizeY);
        this.context = context;
        this.engine = engine;
        this.typefaceBuilder = typefaceBuilder;
        this.bitmapBuilder = bitmapBuilder;
    }

    public static DefaultLegacyParser create(Context context, WatchfaceData data, float canvasSizeX, float canvasSizeY, TypefaceDependencyBuilder typefaceBuilder, BitmapDependencyBuilder bitmapBuilder) {
        if (context == null || data == null) {
            return null;
        }
        ScriptEngine<String, String> scriptEngine = LegacyScriptEngine.create(context, data.getJsonData());
        return new DefaultLegacyParser(context, scriptEngine, canvasSizeX, canvasSizeY,  typefaceBuilder, bitmapBuilder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Context getContext() {
        return this.context;
    }

    public ScriptEngine<String, String> getEngine() {
        return this.engine;
    }

    public TypefaceDependencyBuilder getTypefaceBuilder() {
        return this.typefaceBuilder;
    }

    public BitmapDependencyBuilder getBitmapBuilder() {
        return this.bitmapBuilder;
    }

    public synchronized List<ThemeProperty<String>> parseThemeProperties(JSONArray themeJson) {
        if (themeJson != null) {
            WatchfaceThemeJsonParser parser = getThemeParser();
            this.themeProperties = parser.parseProperties(themeJson);
        } else {
            Log.e(DefaultLegacyParser.class.getSimpleName(), "Could not parse theme properties for a null JsonArray.");
        }
        return this.themeProperties;
    }


    public void forceUpdate() {
        if (this.engine != null) {
            this.engine.update(true);
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.parse.WatchfaceJsonParser
    protected synchronized SceneGraph getSceneGraph(JSONArray watchfaceJson) {
        return new LegacySceneGraph(engine);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.legacy.parse.WatchfaceJsonParser
    protected synchronized DataDecoder<JSONObject, SceneNode> getLayerDecoder() {
        return new LayerMetaDecoder(this.context, this.engine, this.themeProperties, this.typefaceBuilder, this.bitmapBuilder);
    }

    protected WatchfaceThemeJsonParser getThemeParser() {
        return new WatchfaceThemeJsonParser();
    }
}