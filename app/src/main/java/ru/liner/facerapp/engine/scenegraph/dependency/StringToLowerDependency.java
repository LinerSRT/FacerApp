package ru.liner.facerapp.engine.scenegraph.dependency;


import androidx.annotation.NonNull;

import java.util.Locale;


public class StringToLowerDependency extends StringTransformDependency {
    public StringToLowerDependency(Dependency<String> dependency) {
        super(dependency);
    }

    @Override 
    protected String transform(@NonNull String string) {
        return string.toLowerCase(Locale.getDefault());
    }
}
