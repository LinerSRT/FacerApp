package ru.liner.facerapp.engine.async;

import android.app.Dialog;

import androidx.annotation.NonNull;


public class DialogTaskWrapper<I, U, O> extends SingleInstanceAsyncTaskWrapper<I, U, O> implements Cancellable.CancelListener {
    private final Dialog dialog;

    public DialogTaskWrapper(@NonNull SingleInstanceAsyncTaskWrapper.WrappableTaskBuilder<I, U, O> taskBuilder, @NonNull Dialog dialog) {
        super(taskBuilder);
        this.dialog = dialog;
        if (dialog instanceof Cancellable) {
            ((Cancellable) dialog).registerCancellationListener(this);
        }
    }

    @Override 
    public void onPreExecute(WrappableTask<I, U, O> task) {
        super.onPreExecute(task);
        this.dialog.show();
    }

    @Override 
    public void onPostExecute(WrappableTask<I, U, O> task, O output) {
        super.onPostExecute(task, output);
        this.dialog.dismiss();
    }

    @Override 
    public synchronized boolean clear(WrappableTask<I, U, O> task) {
        return super.clear(task);
    }

    @Override 
    public void onCancelled() {
        cancel();
    }
}
