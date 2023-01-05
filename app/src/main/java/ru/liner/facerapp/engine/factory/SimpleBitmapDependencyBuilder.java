package ru.liner.facerapp.engine.factory;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;

/* loaded from: classes.dex */
public class SimpleBitmapDependencyBuilder implements BitmapDependencyBuilder {
    private final ResolverStrategy<Bitmap, String> bitmapResolver;

    public SimpleBitmapDependencyBuilder(ResolverStrategy<Bitmap, String> bitmapResolver) {
        this.bitmapResolver = bitmapResolver;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.factory.BitmapDependencyBuilder
    public Dependency<Bitmap> build(@NonNull JSONObject layerJson, String bitmapID) throws JSONException {
        Bitmap bitmap;
        if (this.bitmapResolver == null || bitmapID == null || "".equals(bitmapID.trim()) || (bitmap = this.bitmapResolver.resolve(bitmapID)) == null) {
            return null;
        }
        return new ConstantDependency(bitmap);
    }
}
