package ru.liner.facerapp.engine.factory;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.async.Operation;
import ru.liner.facerapp.engine.cache.BitmapCache;
import ru.liner.facerapp.engine.scenegraph.dependency.AsyncResolutionDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.ConstantDependency;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;

/* loaded from: classes.dex */
public class DynamicBitmapDependencyBuilder implements BitmapDependencyBuilder {
    private final Context context;
    private final String watchfaceID;

    public DynamicBitmapDependencyBuilder(@NonNull Context context, @NonNull String watchfaceID) {
        this.context = context;
        this.watchfaceID = watchfaceID;
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.factory.BitmapDependencyBuilder
    public Dependency<Bitmap> build(@NonNull JSONObject layerJson, final String bitmapID) throws JSONException {
        final Bitmap cachedValue = getBitmap(this.context, this.watchfaceID, bitmapID);

        if (cachedValue != null && !cachedValue.isRecycled()) {
            Log.i(DynamicBitmapDependencyBuilder.class.getSimpleName(), "Returned a ConstantDependency with target [" + this.watchfaceID + ", " + bitmapID + "]");
            return new ConstantDependency<>(cachedValue);
        }

        Log.i(DynamicBitmapDependencyBuilder.class.getSimpleName(), "Returned an AsyncResolutionDependency with target [" + this.watchfaceID + ", " + bitmapID + "]");
        return new AsyncResolutionDependency<Bitmap>(new Operation<Long, Bitmap>() {
            @Override
            public Bitmap execute(Long input) throws Exception {
                return DynamicBitmapDependencyBuilder.this.getBitmap(DynamicBitmapDependencyBuilder.this.context, DynamicBitmapDependencyBuilder.this.watchfaceID, bitmapID);
            }
        }) {
            @Override
            public synchronized boolean isInvalidated() {
                return super.isInvalidated() || (cachedValue != null && cachedValue.isRecycled());
            }
        };
    }

    protected Bitmap getBitmap(@NonNull Context context, @NonNull String watchfaceID, @NonNull String bitmapID) {
        return BitmapCache.getInstance(context, new BitmapCache.CreateBitmapOperation(context)).get(watchfaceID, bitmapID);
    }
}
