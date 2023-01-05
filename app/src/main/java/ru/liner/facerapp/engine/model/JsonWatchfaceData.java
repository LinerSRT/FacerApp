package ru.liner.facerapp.engine.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import ru.liner.facerapp.engine.decoder.decoder.LayerDecoder;
import ru.liner.facerapp.engine.file.UnCompress;
import ru.liner.facerapp.engine.resource.FilesystemManager;
import ru.liner.facerapp.engine.resource.StreamExtractionProtocol;
import ru.liner.facerapp.engine.resource.ZipStreamExtractionProtocol;
import ru.liner.facerapp.engine.resource.reader.StringReader;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class JsonWatchfaceData implements WatchfaceData {
    private static final String LOGTAG = JsonWatchfaceData.class.getSimpleName();
    private JSONArray complicationJson;
    private JSONArray coreData;
    private JSONArray themeJson;
    private final String watchfaceID;
    private HashMap<String, Bitmap> bitmaps = new HashMap<>();
    private boolean isRecycled = false;

    public JsonWatchfaceData(String watchfaceID) {
        this.watchfaceID = watchfaceID;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public String getWatchfaceID() {
        return this.watchfaceID;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public boolean isProtected() {
        return false;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public int getProtectionVersion() {
        return 0;
    }

    public synchronized void addBitmap(String bitmapID, Bitmap bitmap) {
        if (bitmapID != null && bitmap != null) {
            this.bitmaps.put(bitmapID, bitmap);
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized Bitmap getBitmap(String imageID) {
        return this.bitmaps.get(imageID);
    }

    public synchronized int getSizeOfBitmaps() {
        int allocationSize;
        if (this.bitmaps != null && !this.bitmaps.isEmpty()) {
            allocationSize = 0;
            for (Bitmap bitmap : this.bitmaps.values()) {
                allocationSize += bitmap.getByteCount();
            }
        } else {
            allocationSize = 0;
        }
        return allocationSize;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized JSONArray getJsonData() {
        return this.coreData;
    }

    public synchronized void setJsonData(JSONArray newData) {
        this.coreData = newData;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized JSONArray getThemeJson() {
        return this.themeJson;
    }

    public synchronized void setThemeJson(JSONArray themeJson) {
        this.themeJson = themeJson;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized JSONArray getComplicationJson() {
        return this.complicationJson;
    }

    public synchronized void setComplicationJson(JSONArray complicationJson) {
        this.complicationJson = complicationJson;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized JSONObject getSingleLayer(int layerID) {
        JSONObject jSONObject;
        Log.d(LOGTAG, "Getting Layer ID " + layerID);
        if (this.coreData != null) {
            try {
                jSONObject = this.coreData.getJSONObject(layerID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        jSONObject = null;
        return jSONObject;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized void setSingleLayer(int layerID, JSONObject newData) {
        Log.d("LegacyWatchFaceData", "Setting Layer ID " + layerID);
        if (this.coreData != null) {
            try {
                this.coreData.put(layerID, newData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized void recycle() {
        if (this.bitmaps != null && this.bitmaps.size() > 0) {
            for (String bitmapName : this.bitmaps.keySet()) {
                Bitmap bitmap = this.bitmaps.get(bitmapName);
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
            this.bitmaps.clear();
        }
        this.isRecycled = true;
    }

    public synchronized boolean hasBitmaps() {
        boolean z;
        if (this.bitmaps != null) {
            if (this.bitmaps.size() > 0) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WatchfaceData
    public synchronized boolean isRecycled() {
        return this.isRecycled;
    }
}
