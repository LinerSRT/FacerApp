package ru.liner.facerapp.engine.resource.reader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class BitmapReader implements StreamReader<Bitmap> {
    private int targetWidth = 0;
    private int targetHeight = 0;
    private boolean shouldUseSampling = false;
    protected BitmapFactory.Options options = new BitmapFactory.Options();

    public BitmapReader() {
        this.options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        setTargetHeight(320);
        setTargetWidth(320);
    }

    public synchronized void setOptions(BitmapFactory.Options options) {
        this.options = options;
    }

    public synchronized BitmapFactory.Options getOptions() {
        return this.options;
    }

    public synchronized void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public synchronized void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    protected synchronized int getTargetWidth() {
        return this.targetWidth;
    }

    protected synchronized int getTargetHeight() {
        return this.targetHeight;
    }

    public synchronized void setShouldUseSampling(boolean shouldUseSampling) {
        this.shouldUseSampling = shouldUseSampling;
    }

    protected synchronized boolean shouldUseSampling() {
        return this.shouldUseSampling;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.file.extraction.reader.StreamReader
    public synchronized Bitmap readStream(InputStream input) throws IOException {
        byte[] bytes;
        BitmapFactory.Options options;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[16384];
        int nRead;
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        bytes = buffer.toByteArray();
        options = getOptions();
        if (getTargetHeight() >= 1 && getTargetWidth() >= 1) {
            int sampleSize = calculateSampleSize(options, bytes, getTargetWidth(), getTargetHeight());
            if (shouldUseSampling() && sampleSize > 1) {
                options.inSampleSize = sampleSize;
            }
        }
        return extractBitmapFromByteArray(options, bytes);
    }

    protected Bitmap extractBitmapFromByteArray(BitmapFactory.Options options, byte[] imageData) {
        if (imageData != null) {
            Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
            if (image == null) {
                try {
                    byte[] converted_data = Base64.decode(imageData, 0);
                    return BitmapFactory.decodeByteArray(converted_data, 0, converted_data.length, options);
                } catch (IllegalArgumentException e) {
                    Log.w(getClass().getSimpleName(), "Could not decode image bitmap, an unexpected encoding type was present; aborting.");
                    return image;
                }
            }
            return image;
        }
        return null;
    }

    protected int calculateSampleSize(BitmapFactory.Options options, byte[] imageData, int targetWidth, int targetHeight) {
        int sampleSize = 1;
        if (targetHeight > 0 && targetWidth > 0) {
            options.inJustDecodeBounds = true;
            extractBitmapFromByteArray(options, imageData);
            int height = options.outHeight;
            int width = options.outWidth;
            sampleSize = 1;
            if (height > targetHeight || width > targetWidth) {
                int halfHeight = height / 2;
                int halfWidth = width / 2;
                while (halfWidth / sampleSize >= targetWidth && halfHeight / sampleSize >= targetHeight) {
                    sampleSize *= 2;
                }
            }
            Log.e(BitmapReader.class.getSimpleName(), "Calculated sampleSize of [" + sampleSize + "] to fit image size [" + width + ", " + height + "] to target [" + targetWidth + ", " + targetHeight + "]");
            options.inJustDecodeBounds = false;
        }
        return sampleSize;
    }
}