package ru.liner.facerapp.engine.resource;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;

import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class FileResource<T> extends BaseResource<T, File> {
    public FileResource(@NonNull File source, @NonNull ResolverStrategy<T, File> resolver) {
        super(source, resolver);
    }

    public FileResource(@NonNull Uri uri, @NonNull ResolverStrategy<T, File> resolver) {
        this(new File(uri.getPath()), resolver);
    }
}