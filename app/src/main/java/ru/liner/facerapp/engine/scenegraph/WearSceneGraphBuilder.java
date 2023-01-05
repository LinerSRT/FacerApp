package ru.liner.facerapp.engine.scenegraph;

import android.content.Context;

import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.decoder.DefaultLegacyParser;
import ru.liner.facerapp.engine.factory.BitmapDependencyBuilder;
import ru.liner.facerapp.engine.factory.TypefaceDependencyBuilder;
import ru.liner.facerapp.engine.model.WatchfaceData;
import ru.liner.facerapp.engine.script.LegacyScriptEngine;
import ru.liner.facerapp.engine.script.ScriptEngine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class WearSceneGraphBuilder extends DefaultLegacyParser {
    public WearSceneGraphBuilder(@NonNull Context context, ScriptEngine<String, String> engine, float canvasSizeX, float canvasSizeY, TypefaceDependencyBuilder typefaceBuilder, BitmapDependencyBuilder bitmapBuilder) {
        super(context, engine, canvasSizeX, canvasSizeY, typefaceBuilder, bitmapBuilder);
    }

    public static WearSceneGraphBuilder create(Context context, WatchfaceData data, float canvasSizeX, float canvasSizeY, TypefaceDependencyBuilder typefaceBuilder, BitmapDependencyBuilder bitmapBuilder ) {
        if (context == null || data == null)
            return null;
        return new WearSceneGraphBuilder(context,  LegacyScriptEngine.create(context, data.getJsonData()), canvasSizeX, canvasSizeY, typefaceBuilder, bitmapBuilder);
    }
}