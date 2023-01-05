package ru.liner.facerapp.engine.factory;

import android.graphics.Typeface;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;

/* loaded from: classes.dex */
public interface TypefaceDependencyBuilder {
    Dependency<Typeface> build(@NonNull JSONObject jSONObject) throws JSONException;
}
