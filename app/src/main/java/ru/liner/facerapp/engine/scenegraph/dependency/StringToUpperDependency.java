package ru.liner.facerapp.engine.scenegraph.dependency;

import androidx.annotation.NonNull;

import java.util.Locale;


public class StringToUpperDependency extends StringTransformDependency {
    public StringToUpperDependency(Dependency<String> dependency) {
        super(dependency);
    }

    @Override 
    protected String transform(@NonNull String string) {
        return string.toUpperCase(Locale.getDefault());
    }
}
