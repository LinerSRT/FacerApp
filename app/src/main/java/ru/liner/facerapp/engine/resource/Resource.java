package ru.liner.facerapp.engine.resource;

import androidx.annotation.WorkerThread;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public interface Resource<T> {
    @WorkerThread
    T resolve();
}