package ru.liner.facerapp.engine.resource.reader;

import java.io.IOException;
import java.io.InputStream;

import ru.liner.facerapp.engine.utils.IOUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class ByteArrayReader implements StreamReader<byte[]>{
    @Override
    public byte[] readStream(InputStream inputStream) throws IOException {
        return inputStream == null ? null : IOUtils.toByteArray(inputStream);
    }
}
