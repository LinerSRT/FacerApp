package ru.liner.facerapp.engine.scenegraph.dependency;

import android.util.Log;

import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.async.Executors;
import ru.liner.facerapp.engine.async.Operation;
import ru.liner.facerapp.engine.async.OperationTask;


public class AsyncResolutionDependency<T> extends Dependency<T> {
    private final Operation<Long, T> updateOperation;
    private OperationTask<Long, T> updateTask = null;
    private T value;

    public AsyncResolutionDependency(Operation<Long, T> updateOperation) {
        this.updateOperation = updateOperation;
    }

    
    @Override 
    public synchronized void updateSelf(long currentTimeMillis) {
        Log.v(AsyncResolutionDependency.class.getSimpleName(), "AsyncResolutionDependency.updateSelf() has been called.");
        if (this.updateTask == null) {
            this.updateTask = new UpdateTask(this.updateOperation);
            Executors.getSharedPoolExecutor();
            this.updateTask.execute(new Long[]{Long.valueOf(currentTimeMillis)});
        }
    }

    protected synchronized void deliverUpdateResult(T result) {
        this.updateTask = null;
        this.value = result;
    }

    @Override 
    public synchronized T get() {
        return this.value;
    }

    
    protected class UpdateTask extends OperationTask<Long, T> {
        public UpdateTask(@NonNull Operation<Long, T> operation) {
            super(operation);
        }

        @Override 
        public synchronized void onPreExecute() {
            super.onPreExecute();
            Log.v(AsyncResolutionDependency.class.getSimpleName(), "AsyncResolutionDependency UpdateTask has started!");
        }

        @Override 
        public void onPostExecute(T result) {
            super.onPostExecute(result);
            AsyncResolutionDependency.this.deliverUpdateResult(result);
            Log.v(AsyncResolutionDependency.class.getSimpleName(), "AsyncResolutionDependency UpdateTask is complete!");
        }
    }
}
