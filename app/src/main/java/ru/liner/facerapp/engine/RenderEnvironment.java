package ru.liner.facerapp.engine;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.liner.facerapp.engine.canvas.instruction.CanvasRenderInstruction;
import ru.liner.facerapp.engine.theme.SimpleThemeConfiguration;
import ru.liner.facerapp.engine.theme.ThemeConfiguration;
import ru.liner.facerapp.engine.theme.ThemeProperty;
import ru.liner.facerapp.engine.theme.ThemePropertyValue;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class RenderEnvironment {
    private static final RenderEnvironment instance = new RenderEnvironment();
    public static final RenderMode DEFAULT_RENDER_MODE = RenderMode.ACTIVE;
    public static final ClipMode DEFAULT_CLIP_MODE = ClipMode.SQUARE;
    private final List<CanvasRenderInstruction> defaultPaintInstructions = new ArrayList();
    private RenderMode renderMode = RenderMode.ACTIVE;
    private boolean hasDetailMode = false;
    private ClipMode clipMode = ClipMode.SQUARE;
    private boolean hasWeatherTags = false;
    private boolean hasUserHourMode = false;
    private int canvasSizeX = 320;
    private int canvasSizeY = 320;
    private ThemeConfiguration<String> themeConfiguration = null;


    /* loaded from: classes.dex */
    public enum ClipMode {
        ROUND,
        SQUARE
    }

    /* loaded from: classes.dex */
    public enum RenderMode {
        ACTIVE,
        DETAILED,
        AMBIENT
    }

    public static RenderEnvironment getInstance() {
        return instance;
    }

    private RenderEnvironment() {
    }

    public synchronized void setRenderMode(RenderMode renderMode) {
        if (renderMode != null) {
            this.renderMode = renderMode;
        } else {
            this.renderMode = DEFAULT_RENDER_MODE;
        }
    }
    public synchronized void setThemeConfiguration(ThemeConfiguration<String> configuration) {
        this.themeConfiguration = configuration;
    }

    public synchronized ThemeConfiguration<String> getThemeConfiguration() {
        return this.themeConfiguration;
    }

    public synchronized void reset() {
        setHasWeatherTags(false);
        setHasUserHourMode(false);
        this.themeConfiguration = null;

    }
    public synchronized boolean hasColorTheme() {
        boolean z = false;
        synchronized (this) {
            if (this.themeConfiguration != null) {
                List<ThemePropertyValue<String>> propertyValues = this.themeConfiguration.getPropertyValues();
                String propertyID = SimpleThemeConfiguration.getIdForType(propertyValues, ThemeProperty.Type.COLOR);
                if (this.themeConfiguration.getPropertyValue(propertyID) != null) {
                    z = true;
                }
            }
        }
        return z;
    }
    public boolean isThemeAvailable() {
        return getThemeConfiguration() != null;
    }

    public synchronized RenderMode getRenderMode() {
        return this.renderMode != null ? this.renderMode : DEFAULT_RENDER_MODE;
    }

    public synchronized void setHasDetailMode(boolean hasDetailMode) {
        this.hasDetailMode = hasDetailMode;
    }

    public synchronized boolean hasDetailMode() {
        return this.hasDetailMode;
    }

    public synchronized void setClipMode(ClipMode clipMode) {
        if (clipMode != null) {
            this.clipMode = clipMode;
            Log.w(RenderEnvironment.class.getSimpleName(), "Set ClipMode to [" + clipMode.name() + "]");
        } else {
            this.clipMode = DEFAULT_CLIP_MODE;
        }
    }

    public synchronized ClipMode getClipMode() {
        return this.clipMode != null ? this.clipMode : DEFAULT_CLIP_MODE;
    }

    public synchronized void addDefaultPaintInstruction(CanvasRenderInstruction instruction) {
        if (!this.defaultPaintInstructions.contains(instruction)) {
            this.defaultPaintInstructions.add(instruction);
        }
    }

    public synchronized void removeDefaultPaintInstruction(CanvasRenderInstruction instruction) {
        while (this.defaultPaintInstructions.contains(instruction)) {
            this.defaultPaintInstructions.remove(instruction);
        }
    }

    public synchronized void clearDefaultPaintInstructions() {
        this.defaultPaintInstructions.clear();
    }

    public synchronized List<CanvasRenderInstruction> getDefaultPaintInstructions() {
        return Collections.unmodifiableList(this.defaultPaintInstructions);
    }

    public synchronized void setHasWeatherTags(boolean hasWeatherTags) {
        this.hasWeatherTags = hasWeatherTags;
    }

    public synchronized boolean hasWeatherTags() {
        return this.hasWeatherTags;
    }

    public synchronized void setHasUserHourMode(boolean hasUserHourMode) {
        this.hasUserHourMode = hasUserHourMode;
    }

    public synchronized boolean hasUserHourModeTags() {
        return this.hasUserHourMode;
    }

    public synchronized void setCanvasSize(int sizeX, int sizeY) {
        this.canvasSizeX = sizeX;
        this.canvasSizeY = sizeY;
    }

    public synchronized int getCanvasSizeX() {
        return this.canvasSizeX;
    }

    public synchronized int getCanvasSizeY() {
        return this.canvasSizeY;
    }
}
