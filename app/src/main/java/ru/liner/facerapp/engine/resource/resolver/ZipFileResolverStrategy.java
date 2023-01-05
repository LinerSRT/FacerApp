package ru.liner.facerapp.engine.resource.resolver;

import androidx.annotation.NonNull;

import java.io.InputStream;

import ru.liner.facerapp.engine.resource.reader.StreamReader;
import ru.liner.facerapp.engine.resource.ZipStreamExtractionProtocol;

/* loaded from: classes.dex */
public class ZipFileResolverStrategy<T> extends FileResolverStrategy<T> {
    private final String filename;

    public ZipFileResolverStrategy(@NonNull StreamReader<T> reader, @NonNull String filename) {
        super(reader);
        this.filename = filename;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.resource.resolver.FileResolverStrategy, com.jeremysteckling.facerrel.lib.model.resource.resolver.BaseResolverStrategy
    protected T loadData(InputStream stream) {
        StreamReader<T> reader = getReader();
        if (reader == null || stream == null) {
            return null;
        }
        return new ZipStreamExtractionProtocol<>(reader, this.filename).extract(stream);
    }
}
