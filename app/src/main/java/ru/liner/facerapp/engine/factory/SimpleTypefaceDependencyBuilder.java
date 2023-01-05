package ru.liner.facerapp.engine.factory;

import android.graphics.Typeface;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;

/* loaded from: classes.dex */
public class SimpleTypefaceDependencyBuilder implements TypefaceDependencyBuilder {
    private final ResolverStrategy<Typeface, String> typefaceResolver;

    public SimpleTypefaceDependencyBuilder(ResolverStrategy<Typeface, String> typefaceResolver) {
        this.typefaceResolver = typefaceResolver;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.factory.TypefaceDependencyBuilder
    public Dependency<Typeface> build(@NonNull JSONObject layerJson) throws JSONException {
//        String typefaceName;
//        Typeface typeface;
//        Typeface typeface2;
//        if (this.typefaceResolver != null) {
//            if (layerJson.has(TextLayerDecoder.TEXT_TYPEFACE)) {
//                String typefaceName2 = layerJson.getString(TextLayerDecoder.TEXT_TYPEFACE);
//                if (!(typefaceName2 == null || "".equals(typefaceName2.trim()) || (typeface2 = this.typefaceResolver.resolve(typefaceName2)) == null)) {
//                    return new ConstantDependency(typeface2);
//                }
//            } else if (layerJson.has(TextLayerDecoder.TEXT_TYPEFACE_HASH) && (typefaceName = layerJson.getString(TextLayerDecoder.TEXT_TYPEFACE_HASH)) != null && !"".equals(typefaceName.trim()) && (typeface = this.typefaceResolver.resolve(typefaceName)) != null) {
//                return new ConstantDependency(typeface);
//            }
//        }
        return null;
    }
}
