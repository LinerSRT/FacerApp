package ru.liner.facerapp.engine.file;

import androidx.annotation.NonNull;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class WatchfaceRawBytes {
    private final byte[] bytes;
    private final String watchfaceID;

    public WatchfaceRawBytes(@NonNull String watchfaceID, @NonNull byte[] bytes) {
        this.watchfaceID = watchfaceID;
        this.bytes = bytes;
    }

    public String getWatchfaceID() {
        return this.watchfaceID;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

}
