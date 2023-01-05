package ru.liner.facerapp.engine.resource.resolver;

import android.content.Context;
import android.graphics.Typeface;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class TypefaceResolver implements ResolverStrategy<Typeface, String> {
    private final Context context;

    public TypefaceResolver(@NonNull Context context) {
        this.context = context;
    }

    public Typeface resolve(String typefaceID) {
        return Typeface.DEFAULT;
        //return TypefaceManager.getInstance().fetchTypeface(this.context, typefaceID);
    }
}
