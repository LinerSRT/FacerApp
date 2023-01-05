package ru.liner.facerapp.engine.theme.dependency;

import android.util.Log;

import androidx.annotation.ColorInt;

import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;
import ru.liner.facerapp.engine.theme.ThemeConfiguration;

/* loaded from: classes.dex */
public class ThemeColorDependency extends Dependency<Integer> {
    @ColorInt
    private int color;
    private String propertyID;

    public ThemeColorDependency(String propertyID, @ColorInt int color) {
        this.propertyID = propertyID;
        this.color = color;
        Log.v(ThemeColorDependency.class.getSimpleName(), "Created a new ThemeColorDependency with propertyID [" + propertyID + "], and color [" + color + "]");
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.Dependency
    public void updateSelf(long currentTimeMillis) {
        RenderEnvironment environment = RenderEnvironment.getInstance();
        if (environment != null) {
            ThemeConfiguration<String> configuration = environment.getThemeConfiguration();
            if (configuration != null) {
                String value = configuration.getPropertyValue(this.propertyID);
                if (value == null) {
                    value = configuration.getDefaultPropertyValue(this.propertyID);
                }
                try {
                    this.color = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    Log.w(ThemeColorDependency.class.getSimpleName(), "Error updating theme, color property did not evaluate to color int; aborting, color will be unchanged.", e);
                }
            } else {
                Log.v(ThemeColorDependency.class.getSimpleName(), "RenderEnvironment didn't have a ThemeConfiguration instance; no color will be generated!");
            }
        } else {
            Log.v(ThemeColorDependency.class.getSimpleName(), "Unabled to get RenderEnvironment instance; no color will be generated!");
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.Dependency
    public Integer get() {
        return Integer.valueOf(this.color);
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.Dependency
    public boolean isInvalidated() {
        return true;
    }
}
