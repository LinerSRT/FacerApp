package ru.liner.facerapp.engine.resource;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import ru.liner.facerapp.engine.resource.reader.StreamReader;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class StreamExtractionProtocol<T> implements ExtractionProtocol<InputStream, T> {
    public static final String DEFAULT_ENCODING = "UTF-8";
    private final StreamReader<T> reader;

    public StreamExtractionProtocol(@NonNull StreamReader<T> reader) {
        this.reader = reader;
    }

    public T extract(InputStream input) {
        T t = null;
        StreamReader<T> reader = getReader();
        if (input != null && reader != null) {
            try {
                try {
                    t = reader.readStream(input);
                } catch (IOException e) {
                    Log.w(getClass().getSimpleName(), "Encountered an unexpected IOException while attempting to read InputStream; aborting.", e);
                    try {
                        input.close();
                    } catch (IOException e2) {
                    }
                }
            } finally {
                try {
                    input.close();
                } catch (IOException e3) {
                }
            }
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public StreamReader<T> getReader() {
        return this.reader;
    }
}