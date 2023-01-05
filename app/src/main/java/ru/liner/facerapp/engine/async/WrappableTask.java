package ru.liner.facerapp.engine.async;

import android.os.AsyncTask;


public abstract class WrappableTask<I, U, O> extends AsyncTask<I, U, O> {
    private ExecutionEventHandler<I, U, O> handler;

    
    public interface ExecutionEventHandler<I, U, O> {
        void onCancelled(WrappableTask<I, U, O> wrappableTask, O o);

        void onPostExecute(WrappableTask<I, U, O> wrappableTask, O o);

        void onPreExecute(WrappableTask<I, U, O> wrappableTask);

        void onProgressUpdate(WrappableTask<I, U, O> wrappableTask, U... uArr);
    }

    public synchronized void setHandler(ExecutionEventHandler<I, U, O> handler) {
        this.handler = handler;
    }

    
    @Override 
    public synchronized void onPreExecute() {
        super.onPreExecute();
        if (this.handler != null) {
            this.handler.onPreExecute(this);
        }
    }

    @Override 
    protected void onProgressUpdate(U... values) {
        super.onProgressUpdate(values);
        if (this.handler != null) {
            this.handler.onProgressUpdate(this, values);
        }
    }

    
    @Override 
    public void onPostExecute(O o) {
        super.onPostExecute(o);
        if (this.handler != null) {
            this.handler.onPostExecute(this, o);
        }
    }

    
    @Override 
    public void onCancelled(O o) {
        super.onCancelled(o);
        if (this.handler != null) {
            this.handler.onCancelled(this, o);
        }
    }

    
    @Override 
    public void onCancelled() {
        super.onCancelled();
        if (this.handler != null) {
            this.handler.onCancelled(this, null);
        }
    }
}
