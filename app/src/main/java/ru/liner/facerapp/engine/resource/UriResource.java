package ru.liner.facerapp.engine.resource;

import android.net.Uri;

import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class UriResource<T> extends BaseResource<T, Uri> {
    public UriResource(@NonNull Uri source, @NonNull ResolverStrategy<T, Uri> resolver) {
        super(source, resolver);
    }
}
