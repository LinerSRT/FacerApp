package ru.liner.facerapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 26.12.2021, воскресенье
 **/
public class Zip {
    private static final String TAG = "ZipUtils";

    public static void extract(@NonNull File archive, @NonNull File destinationDirectory) throws Exception {
        extract(archive, destinationDirectory, false);
    }

    public static void extract(@NonNull File archive, @NonNull File destinationDirectory, boolean clear) throws Exception {
        if (clear && destinationDirectory.exists())
            if(!destinationDirectory.delete()){
                Log.e(TAG, "Cannot clear existing directory!");
            }
        if(!destinationDirectory.exists())
            if(!destinationDirectory.mkdir()){
                Log.e(TAG, "Cannot create directory");
            }
        long startTime = System.currentTimeMillis();
        Log.d(TAG, "Starting extraction: "+archive.getName().replace(".", "")+", to: "+destinationDirectory.getAbsolutePath());
        ZipFile zipFile = new ZipFile(archive);
        Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
        while (zipEntries.hasMoreElements()) {
            unzipEntry(zipFile, zipEntries.nextElement(), destinationDirectory);
        }
        zipFile.close();
        Log.d(TAG, "Extraction finished in: "+(System.currentTimeMillis() - startTime)+" ms");
    }
    private static void unzipEntry(@NonNull ZipFile zipfile, @NonNull ZipEntry zipEntry, @NonNull File destinationDirectory) throws IOException {
        if (zipEntry.isDirectory()) {
            File entryDirectory = new File(destinationDirectory, zipEntry.getName());
            if (!entryDirectory.exists()) {
                if (!entryDirectory.mkdirs())
                    throw new RuntimeException("Cannot create directory: " + entryDirectory.getAbsolutePath() + " for " + zipfile.getName());
            }
        } else {
            File entryFile = new File(destinationDirectory, zipEntry.getName());
            if(!entryFile.exists()) {
                if (!entryFile.createNewFile()) {
                    throw new RuntimeException("Cannot create file: " + entryFile.getAbsolutePath() + " for " + zipfile.getName());
                }
            }
            File entryParentDirectory = entryFile.getParentFile();
            if (entryParentDirectory != null && !entryParentDirectory.exists()) {
                if (!entryParentDirectory.mkdirs())
                    throw new RuntimeException("Cannot create directory: " + entryParentDirectory.getAbsolutePath() + " for " + zipfile.getName());
            }
            InputStream inputStream = zipfile.getInputStream(zipEntry);
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream); BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(entryFile))) {
                int readByte;
                while ((readByte = bufferedInputStream.read()) != -1) {
                    bufferedOutputStream.write(readByte);
                }
            }
        }
    }
}
