package ru.liner.facerapp.engine.factory;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;


/* loaded from: classes.dex */
public interface BitmapDependencyBuilder {
    Dependency<Bitmap> build(@NonNull JSONObject jSONObject, String str) throws JSONException;
}
