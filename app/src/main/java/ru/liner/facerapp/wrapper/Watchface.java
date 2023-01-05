package ru.liner.facerapp.wrapper;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.nio.charset.StandardCharsets;

import ru.liner.facerapp.engine.model.FileWatchface;
import ru.liner.facerapp.engine.resource.FileResource;
import ru.liner.facerapp.engine.resource.FilesystemManager;
import ru.liner.facerapp.engine.resource.reader.StringReader;
import ru.liner.facerapp.engine.resource.resolver.FileResolverStrategy;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class Watchface {
    private final String name;
    private final JSONArray manifest;
    private final JSONArray themes;
    private final JSONArray complications;
    private boolean isProtected;

    public Watchface(String name) {
        this.name = name;
        this.isProtected = false;
        this.manifest = load(FilesystemManager.LEGACY_DATA_FILE);
        this.themes = load(FilesystemManager.LEGACY_THEME_FILE);
        this.complications = load(FilesystemManager.LEGACY_COMPLICATIONS_FILE);

    }

    public String getName() {
        return name;
    }

    public JSONArray getComplications() {
        return complications;
    }

    public JSONArray getManifest() {
        return manifest;
    }

    public JSONArray getThemes() {
        return themes;
    }

    public boolean isProtected() {
        return isProtected;
    }

    @Nullable
    private JSONArray load(String name) {
        FileResource<String> fileResource = new FileResource<>(new File(WatchfaceUtils.currentWatchfaceDirectory, name), new FileResolverStrategy<>(new StringReader()));
        if(fileResource.getSource().exists()) {
            try {
                return new JSONArray(fileResource.resolve());
            } catch (JSONException e) {
                try {
                    isProtected = true;
                    return new JSONArray(new String(Base64.decode(fileResource.resolve(), 0), StandardCharsets.UTF_8));
                } catch (JSONException ex) {
                    Log.w(FileWatchface.class.getSimpleName(), "Encountered a JSONException while attempting to parse data from file [" + fileResource.getSource().getAbsolutePath() + "]; aborting.", ex);
                }
            }
        }
        return null;
    }
}
