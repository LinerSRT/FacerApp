package ru.liner.facerapp.engine.resource.resolver;

import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class NullResolver<T, S> implements ResolverStrategy<T, S> {
    @Override 
    public T resolve(S source) {
        return null;
    }
}
