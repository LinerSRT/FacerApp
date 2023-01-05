package ru.liner.facerapp.engine.scenegraph.dependency;

import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import ru.liner.facerapp.engine.scenegraph.node.SceneNode;


public class DependencySceneNode extends SceneNode {
    public static final int DEPENDENCY_INTERACTION = 10000;
    private final SparseArray<Dependency<?>> dependencies = new SparseArray<>();

    protected void attachDependency(@NonNull Integer key, @NonNull Dependency<?> dependency) {
        this.dependencies.put(key, dependency);
        dependency.invalidate();
    }

    protected void removeDependency(@NonNull Integer key) {
        this.dependencies.remove(key);
    }

    public void setDependency(@NonNull Integer key, Dependency<?> dependency) {
        if (dependency != null) {
            attachDependency(key, dependency);
        } else {
            removeDependency(key);
        }
    }

    protected SparseArray<Dependency<?>> getDependencies() {
        return this.dependencies;
    }

    protected <T> Dependency<T> getDependency(@NonNull Integer key) {
        try {
            if (this.dependencies.get(key) != null) {
                return (Dependency<T>) this.dependencies.get(key);
            }
        } catch (ClassCastException e) {
            Log.w(DependencySceneNode.class.getSimpleName(), "Failed to retrieve Typed Dependency with key [" + key + "]", e);
        }
        return null;
    }

    @Override
    public void updateSelf(long currentTimeMillis) {
        super.updateSelf(currentTimeMillis);
        if (this.dependencies.size() > 0) {
            for (int index = 0; index < this.dependencies.size(); index++) {
                this.dependencies.valueAt(index).updateSelf(currentTimeMillis);
            }
        }
    }
}
