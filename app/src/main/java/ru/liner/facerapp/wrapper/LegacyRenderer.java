package ru.liner.facerapp.wrapper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import ru.liner.facerapp.engine.FrameRateListener;
import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.decoder.DefaultLegacyParser;
import ru.liner.facerapp.engine.factory.BitmapDependencyBuilder;
import ru.liner.facerapp.engine.factory.DynamicBitmapDependencyBuilder;
import ru.liner.facerapp.engine.factory.LegacyTypefaceDependencyBuilder;
import ru.liner.facerapp.engine.factory.TypefaceDependencyBuilder;
import ru.liner.facerapp.engine.scenegraph.WearSceneGraphBuilder;
import ru.liner.facerapp.engine.script.LegacyScriptEngine;
import ru.liner.facerapp.engine.script.ScriptEngine;
import ru.liner.facerapp.engine.theme.AsyncThemeConfiguration;
import ru.liner.facerapp.engine.theme.SimpleThemeConfiguration;
import ru.liner.facerapp.engine.theme.ThemeProperty;
import ru.liner.facerapp.engine.theme.ThemePropertyValue;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LegacyRenderer extends FacerRenderer {
    private Watchface watchface;
    private DefaultLegacyParser defaultLegacyParser;

    public LegacyRenderer(Context context) {
        this(context, null);
    }

    public LegacyRenderer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LegacyRenderer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void pause() {
        threadHandler.pause();
    }

    public void resume() {
        threadHandler.resume();
    }

    public void terminate() {
        threadHandler.terminate();
    }

    public boolean isStarted() {
        return threadHandler.isStarted();
    }

    public void forceUpdate() {
        if (defaultLegacyParser != null)
            defaultLegacyParser.forceUpdate();
    }

    public ScriptEngine<String, String> getEngine() {
        if (defaultLegacyParser != null)
            return defaultLegacyParser.getEngine();
        return null;
    }

    public TypefaceDependencyBuilder getTypefaceBuilder() {
        if (defaultLegacyParser != null)
            return defaultLegacyParser.getTypefaceBuilder();
        return null;
    }

    public BitmapDependencyBuilder getBitmapBuilder() {
        if (defaultLegacyParser != null)
            return defaultLegacyParser.getBitmapBuilder();
        return null;
    }


    public void setFramerateListener(FrameRateListener framerateListener) {
        this.fpsTracker.attachListener(framerateListener);
    }

    public Watchface getWatchface() {
        return watchface;
    }

    public void setWatchface(File file){
        WatchfaceUtils.clearCurrentWatchface();
        WatchfaceUtils.extractWatchface(file);
        Watchface watchface = new Watchface(file.getName());
        setWatchface(watchface);
    }

    public void setWatchface(Watchface watchface) {
        this.watchface = watchface;
        sceneGraph = null;
        defaultLegacyParser = null;
        TypefaceDependencyBuilder typefaceDependencyBuilder = new LegacyTypefaceDependencyBuilder(getContext(), watchface.getName());
        BitmapDependencyBuilder bitmapDependencyBuilder = new DynamicBitmapDependencyBuilder(getContext(), watchface.getName());
        defaultLegacyParser = new WearSceneGraphBuilder(
                getContext(),
                LegacyScriptEngine.create(getContext(), watchface.getManifest()),
                getWidth(),
                getHeight(),
                typefaceDependencyBuilder,
                bitmapDependencyBuilder
        );
        List<ThemeProperty<String>> themePropertyList = defaultLegacyParser.parseThemeProperties(watchface.getThemes());
        RenderEnvironment renderEnvironment = RenderEnvironment.getInstance();
        AsyncThemeConfiguration asyncThemeConfiguration = new AsyncThemeConfiguration(getContext(), watchface.getName());
        asyncThemeConfiguration.initializeWithProperties(themePropertyList);
        List<ThemePropertyValue<String>> themePropertyValueList = asyncThemeConfiguration.getPropertyValues();
        String id = SimpleThemeConfiguration.getIdForType(themePropertyValueList, ThemeProperty.Type.COLOR);
        renderEnvironment.setThemeConfiguration(id == null ? null : asyncThemeConfiguration);
        sceneGraph = defaultLegacyParser.parse(watchface.getManifest());
    }
}
