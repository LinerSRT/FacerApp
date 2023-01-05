package ru.liner.facerapp.engine.resource.reader;

import java.io.IOException;
import java.io.InputStream;

import ru.liner.facerapp.engine.utils.IOUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class StringReader implements StreamReader<String> {
    @Override
    public String readStream(InputStream stream) throws IOException {
        if (stream == null)
            return null;
        return new String(IOUtils.toCharArray(stream));
    }
}