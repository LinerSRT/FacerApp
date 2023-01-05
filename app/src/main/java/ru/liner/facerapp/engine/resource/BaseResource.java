package ru.liner.facerapp.engine.resource;

import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;

public class BaseResource<T, S> implements Resource<T> {
    private final ResolverStrategy<T, S> resolver;
    private final S source;

    public BaseResource(@NonNull S source, @NonNull ResolverStrategy<T, S> resolver) {
        this.source = source;
        this.resolver = resolver;
    }

    public S getSource() {
        return this.source;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.resource.Resource
    public T resolve() {
        return this.resolver.resolve(getSource());
    }
}