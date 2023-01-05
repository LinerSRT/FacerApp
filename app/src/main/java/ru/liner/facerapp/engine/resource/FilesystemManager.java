package ru.liner.facerapp.engine.resource;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.liner.facerapp.R;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class FilesystemManager {
    public static final String CACHE_FOLDER = "cache";
    public static final String LEGACY_COMPLICATIONS_FILE = "complications.json";
    public static final String LEGACY_DATA_FILE = "watchface.json";
    public static final String LEGACY_IMAGES_FOLDER = "images";
    public static final String LEGACY_METADATA_FILE = "description.json";
    public static final String LEGACY_PREVIEW_FILE = "preview.png";
    public static final String LEGACY_THEME_FILE = "options.json";
    public static final String LEGACY_TYPEFACE_FOLDER = "fonts";
    public static final String PREVIEW_GIF_EXT = ".gif";
    public static final String PREVIEW_IMAGE_EXT = ".png";
    public static final String SHARE_FOLDER = "share";
    public static final String TEMPORARY_FILE_EXT = "_tmp";
    public static final String TEMP_FOLDER = "temp";
    public static final String TEMP_IMAGES = "images";
    public static final String TEMP_WATCHFACES = "watchfaces";
    public static final String TYPEFACE_EXT = ".ttf";
    public static final String TYPEFACE_EXT_UNDERSCORE = "_ttf";
    public static final String WATCHFACE_DATA_EXT = ".face";
    public static final String WATCHFACE_META_EXT = ".face";
    private final File legacyWatchfaceDirectory;
    private List<String> legacyWatchfaceList;
    private List<File> oldFileList;
    private List<String> oldWatchfaceList;
    private List<String> privateTypefaceList;
    private List<String> publicTypefaceList;
    private final File temporayWatchfaceDirectory;
    private final File watchfaceDataCacheDirectory;
    private List<File> watchfaceFileList;
    private List<File> watchfaceLegacyFileList;
    private List<String> watchfaceList;
    private final File watchfaceMetaCacheDirectory;

    public static FilesystemManager create(Context context) {
        if (context == null) {
            return null;
        }
        return new FilesystemManager(context);
    }

    private FilesystemManager(Context context) {
        File externalStorage = Environment.getExternalStorageDirectory();
        this.legacyWatchfaceDirectory = new File(externalStorage, context.getString(R.string.watchface_legacydir));
        if(legacyWatchfaceDirectory.exists())
            legacyWatchfaceDirectory.mkdirs();
        this.watchfaceMetaCacheDirectory = new File(legacyWatchfaceDirectory + File.separator + CACHE_FOLDER, context.getString(R.string.watchface_metadir));
        if(watchfaceMetaCacheDirectory.exists())
            watchfaceMetaCacheDirectory.mkdirs();
        this.watchfaceDataCacheDirectory = new File(legacyWatchfaceDirectory + File.separator + CACHE_FOLDER, context.getString(R.string.watchface_datadir));
        if(watchfaceDataCacheDirectory.exists())
            watchfaceDataCacheDirectory.mkdirs();
        this.temporayWatchfaceDirectory = new File(legacyWatchfaceDirectory + File.separator + "temp" + File.separator + "watchfaces");
        if(temporayWatchfaceDirectory.exists())
            temporayWatchfaceDirectory.mkdirs();
    }

    public File getLegacyWatchfaceFile(String watchfaceID) {
        return new File(this.legacyWatchfaceDirectory, watchfaceID);
    }

    public synchronized List<String> fetchLegacyWatchfaceList(Context context, boolean shouldForceRefresh) {
        List<String> list;
        if (context == null) {
            list = null;
        } else {
            if (!shouldForceRefresh) {
                if (this.legacyWatchfaceList != null) {
                }
            }
            this.legacyWatchfaceList = new ArrayList();
            String[] filenames = this.legacyWatchfaceDirectory.list();
            if (filenames != null) {
                for (String filename : filenames) {
                    if (!this.legacyWatchfaceDirectory.getName().equals(filename) && !this.legacyWatchfaceDirectory.getName().equals(filename) && !filename.contains(TEMPORARY_FILE_EXT)) {
                        this.legacyWatchfaceList.add(filename);
                    }
                }
            }
            list = this.legacyWatchfaceList;
        }
        return list;
    }
}