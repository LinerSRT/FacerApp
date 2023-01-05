package ru.liner.facerapp.engine.scenegraph.dependency;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import ru.liner.facerapp.engine.input.Action;
import ru.liner.facerapp.engine.input.ClickableProperty;

import org.jetbrains.annotations.NotNull;


public final class ActionClickDependency extends Dependency<Action> implements ClickableProperty {
    @NotNull
    private final Action action;
    private boolean enabled = true;

    public ActionClickDependency(@NotNull Action action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        this.action = action;
    }

    @NotNull
    public Action getAction() {
        return this.action;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    @Override 
    public boolean isClickable() {
        return this.enabled;
    }

    @Override 
    @NotNull
    public Action getClickAction() {
        return this.action;
    }

    
    @Override 
    public void updateSelf(long currentTimeMillis) {
    }

    
    @Override 
    @NotNull
    public Action get() {
        return this.action;
    }
}
