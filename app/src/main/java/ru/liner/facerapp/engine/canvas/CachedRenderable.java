package ru.liner.facerapp.engine.canvas;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class CachedRenderable implements CanvasRenderable{
    private final List<CanvasRenderInstruction> instructions = new ArrayList();

    public CachedRenderable() {
    }

    public CachedRenderable(List<CanvasRenderInstruction> instructions) {
        addAll(instructions);
    }

    public void add(CanvasRenderInstruction instruction){
        if(instruction != null)
            instructions.add(instruction);
    }

    public void addAll(List<CanvasRenderInstruction> renderInstructionList){
        if(renderInstructionList != null && !renderInstructionList.isEmpty()){
            for(CanvasRenderInstruction instruction:renderInstructionList)
                add(instruction);
        }
    }

    public void remove(CanvasRenderInstruction instruction){
        if(instruction != null)
            instructions.remove(instruction);
    }

    public void removeAll(List<CanvasRenderInstruction> renderInstructionList){
        if(renderInstructionList != null && !renderInstructionList.isEmpty()){
            for(CanvasRenderInstruction instruction:renderInstructionList)
                remove(instruction);
        }
    }

    public void clear(){
        instructions.clear();
    }

    @Override
    public void render(@NonNull Canvas canvas, @NonNull Paint paint) {
        for(CanvasRenderInstruction instruction : instructions)
            instruction.render(canvas, paint);
    }
}
