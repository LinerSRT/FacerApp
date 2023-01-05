package ru.liner.facerapp.engine.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

import ru.liner.facerapp.engine.resource.Resource;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public interface Watchface {
    public static final String FEATURE_BATTERY = "battery";
    public static final String FEATURE_COMPLICATIONS = "complciations";
    public static final String FEATURE_DATE = "date";
    public static final String FEATURE_DIGITAL_TIME = "digital";
    public static final String FEATURE_STEPS = "steps";
    public static final String FEATURE_WEATHER = "weather";
    public static final String PLATFORM_ANDROID = "android";
    public static final String PLATFORM_IOS = "ios";
    public static final String SECRET_KEY = "secretKey";
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_FEATURED = "featured";
    public static final String STATUS_LIVE = "live";
    public static final String STATUS_PENDING = null;
    public static final String STATUS_PRE_LIVE = "pre-live";
    public static final String STATUS_PRIVATE = "private";
    public static final String STATUS_UNLOCKABLE = "unlockable";
    public static final String WATCHFACE_EXTRA = "Watchface";
    public static final String WATCHFACE_ID_EXTRA = "WatchfaceID";

    String getAuthorImageUrl();

    String getAuthorName();

    String getAuthorUserID();

    String getBuild();

    int getBuildInt();

    List<String> getCategories();

    Date getCreatedDate();

    long getCreatedTime();

    String getDescription();

    String getFaceDataUrl();

    List<String> getFeatureList();

    String getID();

    boolean getIsBeta();

    boolean getIsProtected();

    String getKeyID();

    Date getLastModifiedDate();

    int getMinClientVersion();

    int getMutedColor();

    int getMutedDarkColor();

    int getMutedLightColor();

    String getPlatform();

    Resource<Bitmap> getPreviewImageResource();

    Resource<Bitmap> getPreviewImageResource(boolean z);

    String getStatus();

    String getStoreID();

    int getTargetWatchType();

    String getTitle();

    List<String> getUserTags();

    int getVibrantColor();

    int getVibrantDarkColor();

    int getVibrantLightColor();

    boolean hasLock();
}