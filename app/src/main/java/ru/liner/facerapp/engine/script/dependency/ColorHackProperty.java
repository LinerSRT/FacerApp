package ru.liner.facerapp.engine.script.dependency;

import androidx.annotation.ColorInt;

import ru.liner.facerapp.engine.scenegraph.dependency.Dependency;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ColorHackProperty extends Dependency<Integer> {
    @ColorInt
    private static int color;

    public ColorHackProperty(@ColorInt int color2) {
        setColor(color2);
    }

    public static void setColor(@ColorInt int nextColor) {
        synchronized (ColorHackProperty.class) {
            color = nextColor;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.Dependency
    public void updateSelf(long currentTimeMillis) {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.engine.clearsky.scenegraph.dependency.Dependency
    public Integer get() {
        Integer valueOf;
        synchronized (ColorHackProperty.class) {
            valueOf = Integer.valueOf(color);
        }
        return valueOf;
    }
}