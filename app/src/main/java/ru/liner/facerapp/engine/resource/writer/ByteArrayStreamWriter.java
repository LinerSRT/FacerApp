package ru.liner.facerapp.engine.resource.writer;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.OutputStream;

import ru.liner.facerapp.engine.utils.IOUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ByteArrayStreamWriter implements StreamWriter<byte[]> {
    public boolean writeStream(@NonNull byte[] input, @NonNull OutputStream outputStream) throws IOException {
        IOUtils.write(input, outputStream);
        return true;
    }
}