package ru.liner.facerapp.engine.scenegraph.dependency;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class FPSVisibilityDependency extends Dependency<Boolean> {
    public static final Companion Companion = new Companion(null);
    private static boolean isVisible;

    @JvmStatic
    public static final void toggle() {
        Companion.toggle();
    }

    
    @Override 
    public void updateSelf(long currentTimeMillis) {
    }

    
    @Override 
    @NotNull
    public Boolean get() {
        return Boolean.valueOf(Companion.isVisible());
    }

    @Override 
    public boolean isInvalidated() {
        return true;
    }


    public static final class Companion {
        private Companion() {
        }

        public  Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        
        public final synchronized boolean isVisible() {
            return FPSVisibilityDependency.isVisible;
        }

        
        public final synchronized void setVisible(boolean z) {
            FPSVisibilityDependency.isVisible = z;
        }

        @JvmStatic
        public final void toggle() {
            setVisible(!isVisible());
        }
    }
}
