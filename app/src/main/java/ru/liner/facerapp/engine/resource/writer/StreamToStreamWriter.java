package ru.liner.facerapp.engine.resource.writer;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ru.liner.facerapp.engine.utils.IOUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class StreamToStreamWriter implements StreamWriter<InputStream> {
    public boolean writeStream(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) throws IOException {
        IOUtils.copy(inputStream, outputStream);
        return true;
    }
}