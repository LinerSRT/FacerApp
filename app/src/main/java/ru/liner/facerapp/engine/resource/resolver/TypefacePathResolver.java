package ru.liner.facerapp.engine.resource.resolver;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class TypefacePathResolver implements ResolverStrategy<Typeface, String> {
    private final Context context;

    public TypefacePathResolver(@NonNull Context context) {
        this.context = context;
    }

    public Typeface resolve(String path) {
        if (path != null) {
            try {
                Log.e(TypefacePathResolver.class.getSimpleName(), "Attempting to resolve typeface for path [" + path + "]");
                return Typeface.createFromFile(path);
            } catch (Exception e) {
                Log.e(TypefacePathResolver.class.getSimpleName(), "Unable to resolve typeface for path [" + path + "] due to exception; aborting.", e);
            }
        }
        Log.e(TypefacePathResolver.class.getSimpleName(), "Unable to resolve typeface for null path; aborting.");
        return null;
    }
}
