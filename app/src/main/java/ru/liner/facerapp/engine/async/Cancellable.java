package ru.liner.facerapp.engine.async;


public interface Cancellable {
    interface CancelListener {
        void onCancelled();
    }

    void registerCancellationListener(CancelListener cancelListener);

    void unregisterCancellationListener(CancelListener cancelListener);
}
