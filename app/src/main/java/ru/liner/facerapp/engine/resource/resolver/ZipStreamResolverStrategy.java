package ru.liner.facerapp.engine.resource.resolver;

import androidx.annotation.NonNull;

import java.io.InputStream;

import ru.liner.facerapp.engine.resource.reader.StreamReader;
import ru.liner.facerapp.engine.resource.ZipStreamExtractionProtocol;

/* loaded from: classes.dex */
public class ZipStreamResolverStrategy<T> extends BaseResolverStrategy<T, InputStream> {
    private final String filename;

    public ZipStreamResolverStrategy(@NonNull StreamReader<T> reader, @NonNull String filename) {
        super(reader);
        this.filename = filename;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public InputStream sourceToStream(@NonNull InputStream source) {
        return source;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.resource.resolver.BaseResolverStrategy
    protected T loadData(InputStream stream) {
        StreamReader<T> reader = getReader();
        if (reader == null || stream == null) {
            return null;
        }
        return new ZipStreamExtractionProtocol<>(reader, this.filename).extract(stream);
    }
}
