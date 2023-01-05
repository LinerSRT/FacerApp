package ru.liner.facerapp.engine.resource.writer;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public interface StreamWriter<T> {
    boolean writeStream(@NonNull T t, @NonNull OutputStream outputStream) throws IOException, IOException;
}