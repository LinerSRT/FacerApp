package ru.liner.facerapp.engine.resource.reader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public interface StreamReader<T> {
    T readStream(InputStream inputStream) throws IOException;
}