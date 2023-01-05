package ru.liner.facerapp.engine.async;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.util.Log;

import androidx.annotation.NonNull;


public class DialogFragmentTaskWrapper<I, U, O> extends SingleInstanceAsyncTaskWrapper<I, U, O> implements Cancellable.CancelListener {
    private Activity activity;
    private DialogFragment dialog;
    private long dialogLastShownTime = 0;
    private long dialogLastHiddenTime = 0;

    public DialogFragmentTaskWrapper(@NonNull SingleInstanceAsyncTaskWrapper.WrappableTaskBuilder<I, U, O> taskBuilder, DialogFragment dialog) {
        super(taskBuilder);
        this.dialog = dialog;
        if (dialog instanceof Cancellable) {
            ((Cancellable) dialog).registerCancellationListener(this);
        }
    }

    public synchronized void setActivity(Activity activity) {
        this.activity = activity;
    }

    protected synchronized Activity getActivity() {
        return this.activity;
    }

    @Override 
    public void onPreExecute(WrappableTask<I, U, O> task) {
        super.onPreExecute(task);
        Activity activity = getActivity();
        if (activity != null) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (fragmentManager != null) {
                this.dialog.show(fragmentManager, "DialogFragmentTaskWrapper:Tag");
                this.dialogLastShownTime = System.currentTimeMillis();
                Log.v(DialogFragmentTaskWrapper.class.getSimpleName(), "Successfully showed task dialog; total runtime will be displayed on dismissal.");
                return;
            }
            Log.w(DialogFragmentTaskWrapper.class.getSimpleName(), "Couldn't show task dialog; fragmentManager was null.");
            return;
        }
        Log.w(DialogFragmentTaskWrapper.class.getSimpleName(), "Couldn't show task dialog; activity was null.");
    }

    @Override 
    public void onPostExecute(WrappableTask<I, U, O> task, O output) {
        super.onPostExecute(task, output);
        if (this.dialog.getFragmentManager() != null) {
            this.dialog.dismissAllowingStateLoss();
            this.dialogLastHiddenTime = System.currentTimeMillis();
            Log.v(DialogFragmentTaskWrapper.class.getSimpleName(), "Successfully dismissed task dialog; total runtime was [" + (this.dialogLastHiddenTime - this.dialogLastShownTime) + " ms]");
        }
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
