package ru.liner.facerapp.engine.async;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class OperationTask<I, O> extends WrappableTask<I, Void, O> {
    private final Operation<I, O> operation;

    public OperationTask(@NonNull Operation<I, O> operation) {
        this.operation = operation;
    }

    @Override // android.os.AsyncTask
    protected O doInBackground(I... params) {
        I parameter = null;
        if (params != null && params.length > 0) {
            parameter = params[0];
        }
        try {
            return this.operation.execute(parameter);
        } catch (IllegalStateException e) {
            return null;
        } catch (Exception e2) {
            Log.w(OperationTask.class.getSimpleName(), "Operation [" + this.operation.getClass().getSimpleName() + "] failed with Exception; aborting.", e2);
            return null;
        }
    }
}