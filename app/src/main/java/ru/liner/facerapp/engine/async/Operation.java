package ru.liner.facerapp.engine.async;

import androidx.annotation.WorkerThread;

public interface Operation<I, O> {
    @WorkerThread
    O execute(I i) throws Exception;
}