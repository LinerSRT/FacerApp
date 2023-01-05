package ru.liner.facerapp.engine.decoder.decoder;

public interface DataDecoder<I, O> {
    O decode(I input);
}
