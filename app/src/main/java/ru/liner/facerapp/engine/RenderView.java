package ru.liner.facerapp.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import ru.liner.facerapp.utils.FileUtils;
import ru.liner.facerapp.utils.JsonReader;


public class RenderView extends View implements Runnable {
    public static final int SHAPE_CIRCLE = 0;
    public static final int SHAPE_LINE = 3;
    public static final int SHAPE_POLYGON = 2;
    public static final int SHAPE_SQUARE = 1;
    public static final int SHAPE_TRIANGLE = 4;
    private final Context context;
    private HashMap<String, BitmapDrawable> drawableHashMap;
    private HashMap<String, Typeface> typefaceHashMap;
    private Thread mThread;
    private boolean isRunning = false;
    private int UPDATE_FREQ = 50;
    private boolean isProtected;
    private boolean canvasInited;
    private boolean isRoundWatch = true;
    private boolean isLowPower = false;
    private float renderScale = 2f;
    private final Paint strokePaint = new Paint();
    private final Paint canvasPaint = new Paint();
    private final Path canvasPath = new Path();


    private List<CachedPropertyHolder> cache;

    public RenderView(Context context) {
        super(context);
        this.context = context;
        this.cache = new ArrayList<>();
        this.drawableHashMap = new HashMap<>();
        this.typefaceHashMap = new HashMap<>();
        this.expectedLayers = 0;
    }

    public RenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.cache = new ArrayList<>();
        this.drawableHashMap = new HashMap<>();
        this.typefaceHashMap = new HashMap<>();
        this.expectedLayers = 0;
    }

    public void setWatchface(File file) {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoading = true;
                cache.clear();
                drawableHashMap.clear();
                typefaceHashMap.clear();
                try {
                    if (file.exists() && file.canRead()) {
                        String manifest = FileUtils.read(FileUtils.getFileFromZip(file, "watchface.json"));
                        String description = FileUtils.read(FileUtils.getFileFromZip(file, "description.json"));
                        isProtected = JsonReader.getBoolean(new JSONObject(description), "is_protected", false);
                        JSONArray watchfaceLayersJson = new JSONArray(isProtected ? new String(Base64.decode(manifest, 0), StandardCharsets.UTF_8) : manifest);
                        expectedLayers = watchfaceLayersJson.length();
                        for (int i = 0; i < watchfaceLayersJson.length(); i++) {
                            JSONObject layer = watchfaceLayersJson.getJSONObject(i);
                            Iterator<String> keyIterator = layer.keys();
                            CachedPropertyHolder holder = new CachedPropertyHolder();
                            while (keyIterator.hasNext()) {
                                String key = keyIterator.next();
                                String value = String.valueOf(layer.get(key));
                                if (!value.equals("null")) {
                                    holder.addProperty(new CachedProperty(key, value));
                                }
                            }
                            insertImageHash(layer, file, "hash");
                            insertImageHash(layer, file, "hash_round");
                            insertImageHash(layer, file, "hash_round_ambient");
                            insertImageHash(layer, file, "hash_square");
                            insertImageHash(layer, file, "hash_square_ambient");
                            if (layer.has("new_font_name")) {
                                try {
                                    typefaceHashMap.put(layer.getString("new_font_name"), Typeface.createFromFile(FileUtils.getFileFromZip(file, "fonts/" + layer.get("new_font_name"))));
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                            cache.add(holder);
                         }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private boolean isLoading = false;
    private int expectedLayers = 0;

    public boolean isLoading() {
        if(cache.isEmpty())
            return false;
        return cache.size() == expectedLayers;
    }

    public int getExpectedLayers() {
        return expectedLayers;
    }

    private double drawStart;
    private double drawEnd;
    private double tickStart;
    private double tickEnd;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStart = System.nanoTime();
        renderScale = (float) getWidth() / 320f;
        if (cache != null) {
            if (isRoundWatch) {
                canvasPath.reset();
                canvasPath.addCircle((float) (getWidth() / 2), (float) (getWidth() / 2), (float) (getWidth() / 2), Path.Direction.CCW);
                canvasPath.close();
                canvas.clipPath(canvasPath);
            }
            strokePaint.setStrokeWidth(6.0f);
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(Color.parseColor("#009957"));
            if (!canvasInited) {
                canvasInited = true;
            }
            canvas.drawColor(Color.parseColor("#ff000000"));
            canvasPaint.reset();
            if (cache.size() != 0) {
                for (int i = 0; i < cache.size(); i++) {
                    CachedPropertyHolder holder = cache.get(i);
                    if(holder == null)
                        continue;
                    if (holder.has("type") && shouldRender(holder)) {
                        try {
                            switch (holder.get("type").getValue()) {
                                case "text":
                                    drawText(canvas, holder);
                                    break;
                                case "image":
                                    drawImage(canvas, holder);
                                    break;
                                case "dynamic_image":
                                    drawDynamicImage(canvas, holder);
                                    break;
                                case "shape":
                                    drawShape(canvas, holder);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        drawEnd = System.nanoTime() - drawStart;
        if(onTick != null)
            onTick.tick(this);
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                tickStart = System.nanoTime();
                tick();
                postInvalidate();
                Thread.sleep(UPDATE_FREQ);
                tickEnd = System.nanoTime() - tickStart;
            } catch (InterruptedException localInterruptedException) {
                localInterruptedException.printStackTrace();
            }
        }
    }
    private onTick onTick;

    public void setOnTick(RenderView.onTick onTick) {
        this.onTick = onTick;
    }

    public interface onTick{
        void tick(RenderView renderView);
    }

    public double getDrawDelay() {
        return Math.round(drawEnd);
    }

    public double getTickDelay() {
        return Math.round(drawEnd);
    }

    public int getLayerCount(){
        return cache.size();
    }

    public void makeDrawCall() {
        for (int i = 0; i < cache.size(); i++) {
            cache.get(i).updateValue();
        }
        postInvalidateOnAnimation();
    }

    public void startDraw() {
        isRunning = true;
        if (mThread != null && mThread.isAlive()) {
            return;
        }
        mThread = new Thread(this);
        mThread.start();
    }

    public void startDraw(int updateFreq) {
        this.UPDATE_FREQ = updateFreq;
        this.
                isRunning = true;
        if (mThread != null && mThread.isAlive()) {
            return;
        }
        mThread = new Thread(this);
        mThread.start();
    }

    public void stopDraw() {
        isRunning = false;
    }

    //Control type
    public boolean isLowPower() {
        return isLowPower;
    }

    public boolean isRoundWatch() {
        return isRoundWatch;
    }

    public void setLowPower(boolean lowPower) {
        this.isLowPower = lowPower;
    }

    public void setRoundWatch(boolean roundWatch) {
        this.isRoundWatch = roundWatch;
    }

    private Mode currentMode = Mode.STANDARD;

    private boolean shouldRender(CachedPropertyHolder propertyHolder) {
        if (this.currentMode != Mode.AMBIENT || !propertyHolder.has("low_power") || !propertyHolder.get("low_power").getValueAsIBoolean()) {
            if (this.currentMode == Mode.DETAILED && propertyHolder.has("state_active_2") && propertyHolder.get("state_active_2").getValueAsIBoolean()) {
                return true;
            }
            if (this.currentMode == Mode.STANDARD && propertyHolder.has("state_active_1") && propertyHolder.get("state_active_1").getValueAsIBoolean()) {
                return true;
            }
            if (this.currentMode == Mode.STANDARD && !propertyHolder.has("state_active_1")) {
                return true;
            }
            return this.currentMode == Mode.DETAILED && !propertyHolder.has("state_active_2");
        }
        return true;
    }



    //Draw methods
    private void drawText(Canvas canvas, CachedPropertyHolder layerData) throws JSONException {
        if(!shouldRender(layerData))
            return;
        float x = layerData.get("x").getComputedValueAsFloat();
        float y = layerData.get("y").getComputedValueAsFloat();
        float rotation = layerData.get("r").getComputedValueAsFloat();
        float alpha;
        String text;
        canvasPaint.reset();
        try {
            alpha = Math.round(2.55d * layerData.get("opacity").getComputedValueAsFloat());
        } catch (NumberFormatException e) {
            alpha = 255f;
        }
        if (alpha != 0f) {
            if (layerData.has("text")) {
                text = layerData.get("text").getComputedValue();
            } else {
                text = "_ERR_";
            }
            if (layerData.has("transform")) {
                if (Objects.requireNonNull(layerData.get("transform")).getValue().matches("1")) {
                    text = text.replace(text, text.toUpperCase());
                } else if (Objects.requireNonNull(layerData.get("transform")).getValue().matches("2")) {
                    text = text.replace(text, text.toLowerCase());
                }
            }

            if (isLowPower) {
                if (layerData.has("low_power_color")) {
                    canvasPaint.setColor(Integer.parseInt(Objects.requireNonNull(layerData.get("low_power_color").getValue())));
                } else {
                    canvasPaint.setColor(-1);
                }
            } else {
                if (layerData.has("color")) {
                    canvasPaint.setColor(Integer.parseInt(Objects.requireNonNull(layerData.get("color")).getValue()));
                } else {
                    canvasPaint.setColor(-1);
                }
            }
            int strokeColor;
            boolean shouldStroke;
            if (layerData.has("text_effect")) {
                shouldStroke = false;
                strokeColor = Color.WHITE;
                canvasPaint.clearShadowLayer();
                switch (Integer.parseInt(Objects.requireNonNull(layerData.get("text_effect")).getValue())) {
                    case 1:
                        if (layerData.has("stroke_size")) {
                            canvasPaint.setStrokeWidth(Float.parseFloat(Objects.requireNonNull(layerData.get("stroke_size").getValue())));
                        }
                        if (layerData.has("stroke_color")) {
                            strokeColor = Integer.parseInt(Objects.requireNonNull(layerData.get("stroke_color").getValue()));
                        }
                        shouldStroke = true;
                        break;
                    case 2:
                        if (!layerData.has("glow_size") || !layerData.has("glow_color")) {
                            break;
                        }
                        canvasPaint.setShadowLayer((float) Integer.parseInt(Objects.requireNonNull(layerData.get("glow_size").getValue())), 0.0f, 0.0f, Integer.parseInt(Objects.requireNonNull(layerData.get("glow_color").getValue())));
                        shouldStroke = true;
                        break;
                    default:
                        break;
                }
            } else {
                shouldStroke = false;
                strokeColor = Color.WHITE;
                canvasPaint.clearShadowLayer();
            }
            if (layerData.has("size")) {
                try {
                    canvasPaint.setTextSize(renderScale * 1);
                } catch (NumberFormatException e) {
                    canvasPaint.setTextSize(1);
                }
            }
            if (alpha > 255f || alpha < 0f) {
                canvasPaint.setAlpha(255);
            } else {
                canvasPaint.setAlpha(Math.round(alpha));
            }
            if (layerData.has("alignment")) {
                if (Objects.requireNonNull(layerData.get("alignment").getValue()).matches("0")) {
                    canvasPaint.setTextAlign(Paint.Align.LEFT);
                } else {
                    if (Objects.requireNonNull(layerData.get("alignment").getValue()).matches("1")) {
                        canvasPaint.setTextAlign(Paint.Align.CENTER);
                    } else {
                        if (Objects.requireNonNull(layerData.get("alignment").getValue()).matches("2")) {
                            canvasPaint.setTextAlign(Paint.Align.RIGHT);
                        } else {
                            canvasPaint.setTextAlign(Paint.Align.LEFT);
                        }
                    }
                }
            }
            if (layerData.has("new_font_name")) {
                if (typefaceHashMap.containsKey(layerData.get("new_font_name").getValue())) {
                    canvasPaint.setTypeface(typefaceHashMap.get(layerData.get("new_font_name").getValue()));
                }
            }
            if (layerData.has("r")) {
                canvas.save();
                canvas.rotate(rotation, x * renderScale, y * renderScale);
            }
            canvasPaint.setAntiAlias(true);
            if (shouldStroke) {
                canvasPaint.setStyle(Paint.Style.STROKE);
                canvasPaint.setColor(strokeColor);
                if (isLowPower) {
                    if (layerData.has("low_power")) {
                        if (Boolean.parseBoolean(layerData.get("low_power").getValue())) {
                            canvas.drawText(text, x * renderScale, y * renderScale, canvasPaint);
                            canvasPaint.setStyle(Paint.Style.FILL);
                            canvasPaint.setColor(Color.BLACK);
                        }
                    }
                } else {
                    canvas.drawText(text, x * renderScale, y * renderScale, canvasPaint);
                }
            } else {
                if (isLowPower) {
                    if (layerData.has("low_power")) {
                        if (Boolean.parseBoolean(layerData.get("low_power").getValue())) {
                            canvas.drawText(text, x * renderScale, y * renderScale, canvasPaint);
                        }
                    }
                }
                if (!isLowPower) {
                    canvas.drawText(text, x * renderScale, y * renderScale, canvasPaint);
                }
            }
            canvas.restore();
            canvasPaint.setTextAlign(Paint.Align.LEFT);
        }
    }

    private void drawImage(Canvas canvas, CachedPropertyHolder layerData) throws JSONException {
        if(!shouldRender(layerData))
            return;
        BitmapDrawable mBitmap;
        float alpha = (float) Math.round(((double) layerData.get("opacity").getComputedValueAsFloat()) * 2.55d);
        canvasPaint.reset();
        canvasPaint.setAntiAlias(true);
        if (alpha != 0.0f) {
            if (layerData.has("hash")) {
                mBitmap = drawableHashMap.get(layerData.get("hash").getValue());
                float tempX = layerData.get("x").getComputedValueAsFloat() * renderScale;
                float tempY = layerData.get("y").getComputedValueAsFloat() * renderScale;
                float tempWidth = layerData.get("width").getComputedValueAsFloat() * renderScale;
                float tempHeight = layerData.get("height").getComputedValueAsFloat() * renderScale;
                float tempR = layerData.get("r").getComputedValueAsFloat();
                int tmpOffset = layerData.get("alignment").getValueAsInt();
                if (layerData.has("is_tinted")) {
                    if (layerData.has("tint_color") && mBitmap != null) {
                        if (layerData.get("is_tinted").getValueAsIBoolean()) {
                            mBitmap.setColorFilter(new PorterDuffColorFilter(layerData.get("tint_color").getValueAsInt(), PorterDuff.Mode.MULTIPLY));
                        } else {
                            mBitmap.setColorFilter(null);
                        }
                    }
                }
                if (layerData.get("low_power").getValueAsIBoolean() || !isLowPower) {
                    canvas.save();
                    canvas.rotate(tempR, tempX, tempY);
                    if (mBitmap != null) {
                        mBitmap.setAlpha(Math.round(alpha));
                        calculateAlignmentOffset(tempWidth, tempHeight, tempX, tempY, tmpOffset, mBitmap);
                        mBitmap.draw(canvas);
                    }
                    canvas.restore();
                }
            }
        }
    }

    private void drawDynamicImage(Canvas canvas, CachedPropertyHolder layerData) throws JSONException {
        if(!shouldRender(layerData))
            return;
        BitmapDrawable mBitmap;
        float alpha = (float) Math.round(((double) layerData.get("opacity").getComputedValueAsFloat()) * 2.55d);
        canvasPaint.reset();
        canvasPaint.setAntiAlias(true);
        if (alpha != 0.0f) {
            if (layerData.has("hash_round")) {
                String hash = null;
                if (isRoundWatch) {
                    if (isLowPower) {
                        if (layerData.has("hash_round_ambient")) {
                            if (Objects.requireNonNull(layerData.get("hash_round_ambient").getValue()).length() != 0) {
                                hash = layerData.get("hash_round_ambient").getValue();
                            }
                        }
                    } else {
                        if (layerData.has("hash_round")) {
                            if (Objects.requireNonNull(layerData.get("hash_round").getValue()).length() != 0) {
                                hash = layerData.get("hash_round").getValue();
                            }
                        }
                    }
                } else {
                    if (isLowPower) {
                        if (layerData.has("hash_square_ambient")) {
                            if (Objects.requireNonNull(layerData.get("hash_square_ambient").getValue()).length() != 0) {
                                hash = layerData.get("hash_square_ambient").getValue();
                            }
                        }
                    } else {
                        if (layerData.has("hash_square")) {
                            if (Objects.requireNonNull(layerData.get("hash_square").getValue()).length() != 0) {
                                hash = layerData.get("hash_square").getValue();
                            }
                        }
                    }
                }
                mBitmap = drawableHashMap.get(hash);
                float tempX = layerData.get("x").getComputedValueAsFloat() * renderScale;
                float tempY = layerData.get("y").getComputedValueAsFloat() * renderScale;
                float tempR = layerData.get("r").getComputedValueAsFloat();
                float tempWidth = layerData.get("width").getComputedValueAsFloat() * renderScale;
                float tempHeight = layerData.get("height").getComputedValueAsFloat() * renderScale;
                int tmpOffset = layerData.get("alignment").getValueAsInt();
                if (layerData.has("is_tinted")) {
                    if (layerData.has("tint_color") && mBitmap != null) {
                        if (layerData.get("is_tinted").getValueAsIBoolean()) {
                            mBitmap.setColorFilter(new PorterDuffColorFilter(layerData.get("tint_color").getValueAsInt(), PorterDuff.Mode.MULTIPLY));
                        } else {
                            mBitmap.setColorFilter(null);
                        }
                    }
                }
                if (layerData.get("low_power").getValueAsIBoolean() || !isLowPower) {
                    canvas.save();
                    canvas.rotate(tempR, tempX, tempY);
                    if (mBitmap != null) {
                        mBitmap.setAlpha(Math.round(alpha));
                        calculateAlignmentOffset(tempWidth, tempHeight, tempX, tempY, tmpOffset, mBitmap);
                        mBitmap.draw(canvas);
                    }
                    canvas.restore();
                }
            }
        }
    }

    private void drawShape(Canvas canvas, CachedPropertyHolder layerData) throws JSONException {
        if(!shouldRender(layerData))
            return;
        float alpha;
        if (layerData.has("shape_type")) {
            if (layerData.get("low_power").getValueAsIBoolean() || !isLowPower) {
                canvasPaint.reset();
                canvasPaint.setAntiAlias(true);
                int width = 90;
                int height = 90;
                int radius = 0;
                boolean shouldClip = false;
                float tempX = layerData.get("x").getComputedValueAsFloat() * renderScale;
                float tempY = layerData.get("y").getComputedValueAsFloat() * renderScale;
                float tempR = layerData.get("r").getComputedValueAsFloat();
                int sides = layerData.get("sides").getValueAsInt();
                if (layerData.has("color")) {
                    canvasPaint.setColor(layerData.get("color").getValueAsInt());
                } else {
                    canvasPaint.setColor(-1);
                }
                switch (layerData.get("shape_opt").getValueAsInt()) {
                    case 0:
                        canvasPaint.setStyle(Paint.Style.FILL);
                        break;
                    case 1:
                        canvasPaint.setStyle(Paint.Style.STROKE);
                        if (layerData.has("stroke_size")) {
                            canvasPaint.setStrokeWidth(layerData.get("stroke_size").getComputedValueAsFloat() * renderScale);
                        }
                        break;
                    case 2:
                        shouldClip = true;
                        break;

                }
                if (layerData.has("opacity")) {
                    alpha = (float) Math.round(((double) layerData.get("opacity").getComputedValueAsFloat()) * 2.55d);
                    if (alpha > 255f || alpha < 0f) {
                        canvasPaint.setAlpha(255);
                    } else {
                        canvasPaint.setAlpha(Math.round(alpha));
                    }
                }
                if (layerData.has("radius")) {
                    radius = (int) (layerData.get("radius").getComputedValueAsFloat() * renderScale);
                }
                if (layerData.has("width") && layerData.has("height")) {
                    width = (int) (layerData.get("width").getComputedValueAsFloat() * renderScale);
                    height = (int) (layerData.get("height").getComputedValueAsFloat() * renderScale);
                }
                canvas.save();
                canvas.rotate(tempR, tempX, tempY);
                if (!shouldClip) {
                    switch (layerData.get("shape_type").getValueAsInt()) {
                        case SHAPE_CIRCLE:
                            canvas.drawCircle(tempX, tempY, (float) radius, canvasPaint);
                            break;
                        case SHAPE_SQUARE:
                        case SHAPE_LINE:
                            canvas.drawRect(tempX, tempY, ((float) width) + tempX, ((float) height) + tempY, canvasPaint);
                            break;
                        case SHAPE_POLYGON:

                            canvas.drawPath(calculatePolygonPoints(renderScale, sides, radius, (int) tempX, (int) tempY, renderScale), canvasPaint);
                            break;
                        case SHAPE_TRIANGLE:
                            canvas.drawPath(calculatePolygonPoints(renderScale, 3, radius, (int) tempX, (int) tempY, renderScale), canvasPaint);
                            break;
                    }
                }
                Path tempPath = new Path();
                switch (layerData.get("shape_type").getValueAsInt()) {
                    case SHAPE_CIRCLE:
                        tempPath.addCircle(tempX, tempY, tempR, Path.Direction.CW);
                        tempPath.close();
                        canvas.clipPath(tempPath);
                        break;
                    case SHAPE_SQUARE:
                    case SHAPE_LINE:
                        canvas.save();
                        canvas.rotate(tempR, tempX, tempY);
                        tempPath.addRect(tempX, tempY, tempX + ((float) width), tempY + ((float) height), Path.Direction.CW);
                        tempPath.close();
                        canvas.clipPath(tempPath);
                        canvas.restore();
                        break;
                    case SHAPE_POLYGON:
                        canvas.clipPath(calculatePolygonPoints(renderScale, sides, radius, (int) tempX, (int) tempY, renderScale));
                        break;
                    case SHAPE_TRIANGLE:
                        canvas.clipPath(calculatePolygonPoints(renderScale, 3, radius, (int) tempX, (int) tempY, renderScale));
                        break;
                }
                canvas.restore();
            }
        }
    }


    //Util
    private Path calculatePolygonPoints(float scale, int sides, int radius, int mX, int mY, float mMultiplyFactor) {
        Path mPath = new Path();
        double segment = 6.283185307179586d / ((double) sides);
        double x1 = 0.0d;
        double y1 = 0.0d;
        for (int i = 1; i <= sides; i++) {
            double x = (Math.sin(((double) i) * segment) * ((double) (((float) radius) / mMultiplyFactor))) + ((double) (((float) mX) / mMultiplyFactor));
            double y = (Math.cos(((double) i) * segment) * ((double) (((float) radius) / mMultiplyFactor))) + ((double) (((float) mY) / mMultiplyFactor));
            if (i == 1) {
                mPath.moveTo(((float) x) * scale, ((float) y) * scale);
                x1 = x;
                y1 = y;
            } else {
                mPath.lineTo(((float) x) * scale, ((float) y) * scale);
            }
        }
        mPath.lineTo(((float) x1) * scale, ((float) y1) * scale);
        mPath.close();
        return mPath;
    }

    private void calculateAlignmentOffset(float width, float height, float x, float y, int align, BitmapDrawable drawable) {
        switch (align) {
            case 1:
                drawable.setBounds((int) (x - (width / 2)), (int) y, (int) (x + (width / 2)), (int) (height + y));
                break;
            case 2:
                drawable.setBounds((int) (x - width), (int) y, (int) x, (int) (height + y));
                break;
            case 3:
                drawable.setBounds((int) x, (int) (y - (height / 2)), (int) (width + x), (int) (y + (height / 2)));
                break;
            case 4:
                drawable.setBounds((int) (x - (width / 2)), (int) (y - (height / 2)), (int) (x + (width / 2)), (int) (y + (height / 2)));
                break;
            case 5:
                drawable.setBounds((int) (x - width), (int) (y - (height / 2)), (int) x, (int) (y + (height / 2)));
                break;
            case 6:
                drawable.setBounds((int) x, (int) (y - height), (int) (x + width), (int) y);
                break;
            case 7:
                drawable.setBounds((int) (x - (width / 2)), (int) (y - height), (int) (x + (width / 2)), (int) y);
                break;
            case 8:
                drawable.setBounds((int) (x - width), (int) (y - height), (int) x, (int) y);
                break;
            default:
                drawable.setBounds((int) x, (int) y, (int) (width + x), (int) (height + y));
                break;
        }
    }

    private void insertImageHash(JSONObject layer, File watchfaceFile, String key) {
        File imageFile;
        byte[] converted_data;
        BitmapDrawable bitmapDrawable;
        if (layer.has(key)) {
            try {
                imageFile = FileUtils.getFileFromZip(watchfaceFile, "images/" + layer.getString(key));
                if (imageFile != null) {
                    if (isProtected) {
                        try {
                            converted_data = Base64.decode(FileUtils.read(imageFile), 0);
                            bitmapDrawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(converted_data, 0, converted_data.length));
                            drawableHashMap.put(layer.getString(key), bitmapDrawable);
                        } catch (IllegalArgumentException e7) {
                            bitmapDrawable = new BitmapDrawable(getResources(), decodeSampledBitmap(imageFile.getAbsolutePath(), getWidth()));
                            drawableHashMap.put(layer.getString(key), bitmapDrawable);
                        }
                    } else {
                        bitmapDrawable = new BitmapDrawable(getResources(), decodeSampledBitmap(imageFile.getAbsolutePath(), getWidth()));
                        drawableHashMap.put(layer.getString(key), bitmapDrawable);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            while ((width / 2) / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap decodeSampledBitmap(String file, int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);
        if (options.outHeight > reqWidth * 2 && options.outWidth > reqWidth * 2) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth);
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file, options);
    }

    private boolean isProtected(JSONObject object) {
        if (object != null) {
            try {
                if (object.has("is_protected")) {
                    return object.getBoolean("is_protected");
                }
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    private Drawable scaleDrawable(Drawable drawable, float scale) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldBmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newBmp = Bitmap.createBitmap(oldBmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(newBmp);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    private int getResolution(int paramInt) {
        return (int) (paramInt * renderScale);
    }


    private void tick() {
        for (int i = 0; i < cache.size(); i++) {
            cache.get(i).updateValue();
        }
    }

    public void clearWatchface() {
        cache.clear();
        drawableHashMap.clear();
        typefaceHashMap.clear();
    }
}
