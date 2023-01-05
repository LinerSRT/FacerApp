package ru.liner.facerapp.engine.resource;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.OutputStream;

import ru.liner.facerapp.engine.resource.writer.StreamWriter;
import ru.liner.facerapp.engine.utils.IOUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class StreamInsertionProtocol<T> implements InsertionProtocol<T, OutputStream> {
    public static final String DEFAULT_ENCODING = "UTF-8";
    private final StreamWriter<T> writer;


    @Override
    public boolean insert(@NonNull T t, @NonNull OutputStream o) {
        return insert2(t, o);
    }

    public StreamInsertionProtocol(@NonNull StreamWriter<T> writer) {
        this.writer = writer;
    }

    /* JADX WARN: Finally extract failed */
    /* renamed from: insert  reason: avoid collision after fix types in other method */
    public boolean insert2(@NonNull T input, @NonNull OutputStream outputStream) {
        StreamWriter<T> writer = getWriter();
        try {
            try {
                boolean writeStream = writer.writeStream(input, outputStream);
                IOUtils.closeQuietly(outputStream);
                return writeStream;
            } catch (Exception e) {
                Log.w(StreamInsertionProtocol.class.getSimpleName(), "Encountered an unexpected Exception while attempting to write to stream; aborting.", e);
                IOUtils.closeQuietly(outputStream);
                return false;
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(outputStream);
            throw th;
        }
    }

    protected StreamWriter<T> getWriter() {
        return this.writer;
    }
}