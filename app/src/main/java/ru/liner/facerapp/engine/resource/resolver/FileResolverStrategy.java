package ru.liner.facerapp.engine.resource.resolver;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import ru.liner.facerapp.engine.resource.StreamExtractionProtocol;
import ru.liner.facerapp.engine.resource.reader.StreamReader;

/* loaded from: classes.dex */
public class FileResolverStrategy<T> extends BaseResolverStrategy<T, File> {
    public FileResolverStrategy(@NonNull StreamReader<T> reader) {
        super(reader);
    }

    public InputStream sourceToStream(@NonNull File source) {
        try {
            return new FileInputStream(source);
        } catch (Exception e) {
            return null;
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.model.resource.resolver.BaseResolverStrategy
    protected T loadData(InputStream stream) {
        StreamReader<T> reader = getReader();
        if (reader == null || stream == null) {
            return null;
        }
        return new StreamExtractionProtocol<>(reader).extract(stream);
    }
}
