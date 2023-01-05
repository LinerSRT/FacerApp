package ru.liner.facerapp.engine.resource;

import ru.liner.facerapp.engine.resource.reader.ByteArrayReader;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ByteStreamExtractionProtocol extends StreamExtractionProtocol<byte[]> {
    public ByteStreamExtractionProtocol() {
        super(new ByteArrayReader());
    }
}