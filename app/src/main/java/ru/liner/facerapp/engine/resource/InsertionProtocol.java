package ru.liner.facerapp.engine.resource;

import androidx.annotation.NonNull;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public interface InsertionProtocol<I, O> {
    boolean insert(@NonNull I i, @NonNull O o);
}