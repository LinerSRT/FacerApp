package ru.liner.facerapp.engine.resource;

public interface ExtractionProtocol<I, O> {
    O extract(I i);
}