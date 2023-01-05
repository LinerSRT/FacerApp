package ru.liner.facerapp.engine.async;

import android.util.Log;


public class SingleInstanceAsyncTaskWrapper<I, U, O> implements WrappableTask.ExecutionEventHandler<I, U, O> {
    private WrappableTask<I, U, O> task = null;
    private final WrappableTaskBuilder<I, U, O> taskBuilder;

    
    public interface WrappableTaskBuilder<I, U, O> {
        WrappableTask<I, U, O> build();
    }

    public SingleInstanceAsyncTaskWrapper(WrappableTaskBuilder<I, U, O> taskBuilder) {
        this.taskBuilder = taskBuilder;
    }

    public synchronized boolean start(I... params) {
        boolean z = false;
        synchronized (this) {
            if (this.task == null) {
                Log.v(getClass().getSimpleName(), "No Task reference existed; creating a new one.");
                this.task = this.taskBuilder.build();
                if (this.task != null) {
                    this.task.setHandler(this);
                    this.task.executeOnExecutor(Executors.getSharedPoolExecutor(), params);
                    z = true;
                }
            } else {
                Log.v(getClass().getSimpleName(), "Task reference already existed; ignoring creation request.");
            }
        }
        return z;
    }

    public synchronized boolean cancel() {
        boolean wasCancelled;
        if (this.task != null) {
            wasCancelled = this.task.cancel(true);
            if (wasCancelled) {
                this.task = null;
            }
        } else {
            wasCancelled = false;
        }
        return wasCancelled;
    }

    public synchronized boolean clear(WrappableTask<I, U, O> task) {
        boolean z;
        if (this.task == task) {
            this.task = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    @Override 
    public void onPreExecute(WrappableTask<I, U, O> task) {
    }

    @Override 
    public void onProgressUpdate(WrappableTask<I, U, O> task, U... values) {
    }

    @Override 
    public void onPostExecute(WrappableTask<I, U, O> task, O output) {
        clear(task);
    }

    @Override 
    public void onCancelled(WrappableTask<I, U, O> task, O output) {
    }
}
