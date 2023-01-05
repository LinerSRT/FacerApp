package ru.liner.facerapp.wrapper;

import android.graphics.Bitmap;
import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import ru.liner.facerapp.engine.resource.FileResource;
import ru.liner.facerapp.engine.resource.Resource;
import ru.liner.facerapp.engine.resource.reader.BitmapReader;
import ru.liner.facerapp.engine.resource.reader.ByteArrayReader;
import ru.liner.facerapp.engine.resource.reader.StringReader;
import ru.liner.facerapp.engine.resource.resolver.FileResolverStrategy;
import ru.liner.facerapp.utils.Zip;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class WatchfaceUtils {
    private static final String TAG = WatchfaceUtils.class.getSimpleName();
    public static final File watchfaceDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "Facer");
    public static final File currentWatchfaceDirectory = new File(watchfaceDirectory, "current" + File.separator);


    static {
        if (!watchfaceDirectory.exists())
            watchfaceDirectory.mkdirs();
        if (!currentWatchfaceDirectory.exists())
            currentWatchfaceDirectory.mkdirs();

    }

    public static boolean extractWatchface(File watchface) {
        try {
            Zip.extract(watchface, currentWatchfaceDirectory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    public static File randomWatchface() {
        List<File> fileList = new ArrayList<>();
        for (File file : Objects.requireNonNull(watchfaceDirectory.listFiles())) {
            if (file.isFile())
                fileList.add(file);
        }
        return fileList.get(new Random().nextInt(fileList.size()));
    }

    public static List<File> getWatchfaces() {
        List<File> fileList = new ArrayList<>();
        for (File file : Objects.requireNonNull(watchfaceDirectory.listFiles())) {
            if (file.isFile())
                fileList.add(file);
        }
        return fileList;
    }

    @Nullable
    public static File getWatchface(String name) {
        List<File> fileList = getWatchfaces();
        for (File file : fileList)
            if (file.getName().equals(name))
                return file;
        return null;
    }

    public static void clearCurrentWatchface() {
        clearCurrentWatchface(currentWatchfaceDirectory);
    }

    private static void clearCurrentWatchface(File file) {
        if (file.isDirectory())
            for (File child : file.listFiles())
                clearCurrentWatchface(child);
        file.delete();
    }

    public static File getWatchfaceFile(String filename) {
        File file = new File(currentWatchfaceDirectory, filename);
        if (file.exists() && file.canRead())
            return file;
        throw new RuntimeException("Cannot allocate " + filename + " in " + currentWatchfaceDirectory.getAbsolutePath());
    }

    public Resource<Bitmap> getBitmapResource(String name) {
        return new FileResource<>(getWatchfaceFile(name), new FileResolverStrategy<>(new BitmapReader()));
    }

    public Resource<String> getStringResource(String name) {
        return new FileResource<>(getWatchfaceFile(name), new FileResolverStrategy<>(new StringReader()));
    }

    public Resource<byte[]> getByteResource(String name) {
        return new FileResource<>(getWatchfaceFile(name), new FileResolverStrategy<>(new ByteArrayReader()));
    }
}
