package ru.liner.facerapp.engine.resource.resolver;

import androidx.annotation.WorkerThread;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public interface ResolverStrategy<T, S> {
    @WorkerThread
    T resolve(S s);
}