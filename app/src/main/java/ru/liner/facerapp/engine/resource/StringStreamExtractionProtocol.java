package ru.liner.facerapp.engine.resource;

import ru.liner.facerapp.engine.resource.reader.StringReader;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class StringStreamExtractionProtocol extends StreamExtractionProtocol<String> {
    public StringStreamExtractionProtocol() {
        super(new StringReader());
    }
}
