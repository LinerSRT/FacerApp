package ru.liner.facerapp.render;

public interface Engine<R>{
    void render(R render);
    void handleClickEvent(float x, float y);
    void sizeChanged(float width, float height);
    void update(long currentTimeMillis);
}
