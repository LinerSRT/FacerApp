package ru.liner.facerapp.engine.resource.resolver;

import androidx.annotation.NonNull;

import java.io.InputStream;

import ru.liner.facerapp.engine.resource.reader.StreamReader;


public abstract class BaseResolverStrategy<T, S> implements ResolverStrategy<T, S> {
    private final StreamReader<T> reader;

    protected abstract T loadData(InputStream inputStream);

    protected abstract InputStream sourceToStream(@NonNull S s);

    public BaseResolverStrategy(@NonNull StreamReader<T> reader) {
        this.reader = reader;
    }

    protected StreamReader<T> getReader() {
        return this.reader;
    }

    @Override 
    public T resolve(S source) {
        if (source != null) {
            return loadData(sourceToStream(source));
        }
        return null;
    }
}
