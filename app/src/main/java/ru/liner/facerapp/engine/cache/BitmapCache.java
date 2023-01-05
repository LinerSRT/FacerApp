package ru.liner.facerapp.engine.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.io.File;

import ru.liner.facerapp.engine.async.Operation;
import ru.liner.facerapp.engine.resource.FilesystemManager;
import ru.liner.facerapp.engine.resource.ImageResourceFactory;
import ru.liner.facerapp.engine.resource.Resource;
import ru.liner.facerapp.wrapper.WatchfaceUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class BitmapCache {
    private static final int MAX_CACHE_SIZE = 90000000;
    private static BitmapCache instance;
    private final OperationCache<Pair<String, String>, Bitmap> cache;

    public static BitmapCache getInstance(Context context, CreateBitmapOperation operation) {
        if (instance == null && context != null) {
            if (operation == null) {
                operation = new CreateBitmapOperation(context.getApplicationContext());
            }
            instance = new BitmapCache(operation);
        }
        return instance;
    }

    public static BitmapCache getInstance(Context context) {
        return getInstance(context, null);
    }

    public BitmapCache(@NonNull CreateBitmapOperation operation) {
        this.cache = new BitmapOperationCache(MAX_CACHE_SIZE, operation);
    }

    public Bitmap get(String watchfaceID, String bitmapID) {
        return this.cache.get(new Pair<>(watchfaceID, bitmapID));
    }

    public Bitmap peek(String watchfaceID, String bitmapID) {
        return this.cache.peekValue(new Pair<>(watchfaceID, bitmapID));
    }

    /* loaded from: classes.dex */
    public static class CreateBitmapOperation implements Operation<Pair<String, String>, Bitmap> {
        private final Context context;

        public CreateBitmapOperation(@NonNull Context context) {
            this.context = context;
        }

        public Bitmap execute(Pair<String, String> key) throws Exception {
            Bitmap bitmap;
            String watchfaceID = (String) key.first;
            if (watchfaceID == null || "".equals(watchfaceID.trim())) {
                return null;
            }
            String bitmapID = (String) key.second;
            if (bitmapID == null || "".equals(bitmapID.trim())) {
                return null;
            }
            Resource<Bitmap> resource = getResource(this.context, watchfaceID, bitmapID);
            if (resource == null) {
                resource = getResource(this.context, watchfaceID + ":draft", bitmapID);
            }
            if (resource != null && (bitmap = resource.resolve()) != null) {
                Log.e(BitmapCache.class.getSimpleName(), "Loaded bitmap from disk for id [" + bitmapID + "]");
                return bitmap;
            }
            Log.e(BitmapCache.class.getSimpleName(), "Failed to acquire a resource for id [" + bitmapID + "]");
            return null;
        }

        protected Resource<Bitmap> getResource(@NonNull Context context, @NonNull String watchfaceID, @NonNull String bitmapID) {
            return ImageResourceFactory.fromFile(new File(WatchfaceUtils.currentWatchfaceDirectory, "images" + File.separator + bitmapID));
        }
    }

    /* loaded from: classes.dex */
    private static class BitmapOperationCache extends OperationCache<Pair<String, String>, Bitmap> {
        public BitmapOperationCache(int maxSize, Operation<Pair<String, String>, Bitmap> createOperation) {
            super(maxSize, createOperation);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public int sizeOf(Pair<String, String> key, Bitmap bitmap) {
            if (bitmap != null) {
                return bitmap.getByteCount();
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void entryRemoved(boolean evicted, Pair<String, String> key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            if (evicted && oldValue != null) {
                Log.e(BitmapCache.class.getSimpleName(), " -> Evicting bitmap for key [" + ((String) key.first) + ", " + ((String) key.second) + "], and size [" + oldValue.getByteCount() + "]");
                oldValue.recycle();
            }
        }
    }
}