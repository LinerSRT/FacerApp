package ru.liner.facerapp.engine.factory;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.async.Operation;
import ru.liner.facerapp.engine.resource.FilesystemManager;
import ru.liner.facerapp.engine.scenegraph.dependency.AsyncResolutionDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;

/* loaded from: classes.dex */
public class LegacyTypefaceDependencyBuilder implements TypefaceDependencyBuilder {
    private final Context context;
    private final String watchfaceID;

    public LegacyTypefaceDependencyBuilder(@NonNull Context context, @NonNull String watchfaceID) {
        this.context = context;
        this.watchfaceID = watchfaceID;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.factory.TypefaceDependencyBuilder
    public Dependency<Typeface> build(@NonNull JSONObject layerJson) throws JSONException {
//        final String typefaceID;
//        FilesystemManager filesystemManager = FilesystemManager.create(this.context);
//        boolean isLegacyFontFamily = false;
//        if (layerJson.has(TextLayerDecoder.TEXT_FONT_FAMILY)) {
//            typefaceID = layerJson.getString(TextLayerDecoder.TEXT_FONT_FAMILY);
//            isLegacyFontFamily = true;
//        } else if (layerJson.has(TextLayerDecoder.TEXT_TYPEFACE)) {
//            typefaceID = layerJson.getString(TextLayerDecoder.TEXT_TYPEFACE);
//        } else if (layerJson.has(TextLayerDecoder.TEXT_TYPEFACE_HASH)) {
//            typefaceID = layerJson.getString(TextLayerDecoder.TEXT_TYPEFACE_HASH);
//        } else {
//            typefaceID = null;
//        }
//        if (typefaceID == null) {
//            return null;
//        }
//        if (isLegacyFontFamily) {
//            try {
//                return new ConstantDependency(Typeface.createFromAsset(this.context.getAssets(), getLegacyTypefaceID(Integer.valueOf(typefaceID).intValue(), layerJson.has("italic") && layerJson.getBoolean("italic"), layerJson.has("bold") && layerJson.getBoolean("bold"))));
//            } catch (NumberFormatException ex) {
//                ex.printStackTrace();
//            }
//        }
//        File typefaceDir = new File(filesystemManager.getLegacyWatchfaceFile(this.watchfaceID), FilesystemManager.LEGACY_TYPEFACE_FOLDER);
//        if (typefaceDir.exists() && typefaceDir.isDirectory()) {
//            final File typefaceFile = new File(typefaceDir, typefaceID);
//            if (typefaceFile.exists()) {
//                Typeface cachedValue = TypefaceCache.getInstance(this.context).get(typefaceFile.getAbsolutePath());
//                if (cachedValue != null) {
//                    return new ConstantDependency(cachedValue);
//                }
//                Log.i(LegacyTypefaceDependencyBuilder.class.getSimpleName(), "Returned an AsyncResolutionDependency with target [" + typefaceFile.getAbsolutePath() + "]");
//                return new AsyncResolutionDependency(new Operation<Long, Typeface>() { // from class: com.jeremysteckling.facerrel.lib.engine.clearsky.factory.LegacyTypefaceDependencyBuilder.1
//                    public Typeface execute(Long input) throws Exception {
//                        return TypefaceCache.getInstance(LegacyTypefaceDependencyBuilder.this.context).get(typefaceFile.getAbsolutePath());
//                    }
//                });
//            }
//        }
//        Typeface cachedValue2 = TypefaceCache.getInstance(this.context).get(typefaceID);
//        if (cachedValue2 != null) {
//            return new ConstantDependency(cachedValue2);
//        }
//        Log.i(LegacyTypefaceDependencyBuilder.class.getSimpleName(), "Returned an AsyncResolutionDependency with target [" + typefaceID + "]");
//        return new AsyncResolutionDependency(new Operation<Long, Typeface>() { // from class: com.jeremysteckling.facerrel.lib.engine.clearsky.factory.LegacyTypefaceDependencyBuilder.2
//            public Typeface execute(Long input) throws Exception {
//                return TypefaceCache.getInstance(LegacyTypefaceDependencyBuilder.this.context).get(typefaceID);
//            }
//        });
        return new ConstantDependency<>(Typeface.DEFAULT);
    }

    private String getLegacyTypefaceID(int legacyID, boolean isItalic, boolean isBold) {
        switch (legacyID) {
            case 0:
                if (isItalic) {
                    return "Roboto-ThinItalic.ttf";
                }
                return "Roboto-Thin.ttf";
            case 1:
                if (isItalic) {
                    return "Roboto-LightItalic.ttf";
                }
                return "Roboto-Light.ttf";
            case 2:
                if (isItalic) {
                    return "RobotoCondensed-LightItalic.ttf";
                }
                return "RobotoCondensed-Light.ttf";
            case 3:
                if (isItalic && isBold) {
                    return "Roboto-BoldItalic.ttf";
                }
                if (isItalic) {
                    return "Roboto-Italic.ttf";
                }
                if (isBold) {
                    return "Roboto-Bold.ttf";
                }
                return "Roboto-Regular.ttf";
            case 4:
                return "Roboto-Black.ttf";
            case 5:
                if (isItalic && isBold) {
                    return "RobotoCondensed-BoldItalic.ttf";
                }
                if (isItalic) {
                    return "RobotoCondensed-Italic.ttf";
                }
                if (isBold) {
                    return "RobotoCondensed-Bold.ttf";
                }
                return "RobotoCondensed-Regular.ttf";
            case 6:
                return "RobotoSlab-Thin.ttf";
            case 7:
                return "RobotoSlab-Light.ttf";
            case 8:
                if (isBold) {
                    return "RobotoSlab-Bold.ttf";
                }
                return "RobotoSlab-Regular.ttf";
            case 9:
                return "Roboto-Regular.ttf";
            default:
                return "Roboto-Regular.ttf";
        }
    }
}
