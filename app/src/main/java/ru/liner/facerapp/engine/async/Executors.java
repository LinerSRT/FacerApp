package ru.liner.facerapp.engine.async;

import java.util.concurrent.Executor;


public class Executors {
    private static final Executor sharedSingleThreadedExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
    private static final Executor sharedThreadPoolExecutor = java.util.concurrent.Executors.newCachedThreadPool();

    public static synchronized Executor getSharedExecutor() {
        Executor executor;
        synchronized (Executors.class) {
            executor = sharedSingleThreadedExecutor;
        }
        return executor;
    }

    public static synchronized Executor getSharedPoolExecutor() {
        Executor executor;
        synchronized (Executors.class) {
            executor = sharedThreadPoolExecutor;
        }
        return executor;
    }
}
