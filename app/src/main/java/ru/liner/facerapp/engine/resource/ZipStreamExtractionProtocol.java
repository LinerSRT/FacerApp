package ru.liner.facerapp.engine.resource;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ru.liner.facerapp.engine.resource.reader.StreamReader;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class ZipStreamExtractionProtocol <T> extends StreamExtractionProtocol<T>{
    public static final String COMPLICATION_FILENAME = "complications.json";
    public static final String DATA_FILENAME = "watchface.json";
    public static final String METADATA_FILENAME = "description.json";
    public static final String THEME_FILENAME = "options.json";
    private final String filename;

    public ZipStreamExtractionProtocol(@NonNull StreamReader<T> reader, String filename) {
        super(reader);
        this.filename = filename;
    }

    @Override
    public T extract(InputStream input) {
        T result = null;
        StreamReader<T> reader = getReader();
        if (input == null || reader == null)
            return null;
        try (ZipInputStream zipStream = new ZipInputStream(input)) {
            ZipEntry zipEntry;
            while ((zipEntry = zipStream.getNextEntry()) != null) {
                if (zipEntry.getName().endsWith(this.filename)) {
                    result = reader.readStream(zipStream);
                    break;
                }
            }
        } catch (IOException e) {
            Log.w(getClass().getSimpleName(), "Encountered an IOException while attempting to unzip an InputStream; aborting.", e);
        }
        return result;
    }
}
