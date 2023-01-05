package ru.liner.facerapp.engine.drawcache;

import androidx.annotation.NonNull;

import java.util.List;

import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;

public interface RenderCachable {
    void computeRenderInstructions(@NonNull List<CanvasRenderInstruction> list);
}
