package ru.liner.facerapp.render;

import androidx.annotation.NonNull;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public interface ThreadHandler<R> {
    void pause();
    void resume();
    void shutdown();
    void terminate();
    void start(@NonNull RenderRunnable<R> runnable);
}
