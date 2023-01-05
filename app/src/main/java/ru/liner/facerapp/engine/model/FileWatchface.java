package ru.liner.facerapp.engine.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;
import java.util.List;

import ru.liner.facerapp.engine.resource.Resource;
import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class FileWatchface  implements MutableWatchface {
    public static final String AUTHOR_USER = "user";
    public static final String BUILD = "build";
    public static final String BUILD_INT = "buildInt";
    public static final String CATEGORY_LIST = "categories";
    public static final String CREATED_DATE = "createdAt";
    public static final String CREATED_TIME = "created";
    public static final String DESCRIPTION = "description";
    public static final String DOWNLOAD_COUNT = "downloadCount";
    public static final String FACE_DATA = "file";
    public static final String FEATURE_LIST = "features";
    public static final String ID = "id";
    public static final String IS_BETA = "isBeta";
    public static final String IS_PROTECTED = "isProtected";
    public static final String LEGACY_BUILD_INT = "build_int";
    public static final String LEGACY_CREATED_DATE = "created_at";
    public static final String LEGACY_IS_BETA = "is_beta";
    public static final String LEGACY_IS_PROTECTED = "is_protected";
    public static final String LEGACY_MODIFIED_DATE = "edited_at";
    public static final String LEGACY_MUTED_COLOR = "secondary_color";
    public static final String LEGACY_MUTED_DARK_COLOR = "secondary_dark_color";
    public static final String LEGACY_MUTED_LIGHT_COLOR = "secondary_light_color";
    public static final String LEGACY_TARGET_WATCH_TYPE = "watch_type";
    public static final String LEGACY_VIBRANT_COLOR = "primary_color";
    public static final String LEGACY_VIBRANT_DARK_COLOR = "primary_dark_color";
    public static final String LEGACY_VIBRANT_LIGHT_COLOR = "primary_light_color";
    public static final String LOCK = "lock";
    public static final String MODIFIED_DATE = "updatedAt";
    public static final String MUTED_COLOR = "secondaryColor";
    public static final String MUTED_DARK_COLOR = "secondaryDarkColor";
    public static final String MUTED_LIGHT_COLOR = "secondaryLightColor";
    public static final String PREVIEW_IMAGE = "preview";
    public static final String PRIORITY = "priority";
    public static final String STATUS = "status";
    public static final String SYNC_COUNT = "syncCount";
    public static final String TAGS_LIST = "userTags";
    public static final String TARGET_WATCH_TYPE = "watchType";
    public static final String TITLE = "title";
    public static final String VIBRANT_COLOR = "primaryColor";
    public static final String VIBRANT_DARK_COLOR = "primaryDarkColor";
    public static final String VIBRANT_LIGHT_COLOR = "primaryLightColor";
    private String authorID;
    private String authorImageUrl;
    private String authorName;
    private String build;
    private int buildInt;
    private List<String> categoryList;
    private Date createdDate;
    private long createdTime;
    private String description;
    private String faceDataUrl;
    private List<String> featureList;
    private boolean hasLock;
    private final String id;
    private boolean isBeta;
    private boolean isProtected;
    private String lockKeyID;
    private int minClientVersion;
    private Date modifiedDate;
    private int mutedColor;
    private int mutedDarkColor;
    private int mutedLightColor;
    private String platform;
    private Resource<Bitmap> previewImageResource;
    private String status;
    private String title;
    private List<String> userTags;
    private int vibrantColor;
    private int vibrantDarkColor;
    private int vibrantLightColor;
    private int watchType;
    private int legacyWatchType = -1;
    private ResolverStrategy<Bitmap, Uri> previewResolverStrategy = null;

    public FileWatchface(String id, String build, int buildInt, long createdTime, Date createdDate, Date modifiedDate, boolean isBeta, boolean isProtected, int watchType, String status, String title, String description, String authorId, String authorName, String authorImageUrl, List<String> featureList, List<String> tagList, List<String> categoryList, String faceDataUrl, Resource<Bitmap> previewImageResource, int vibrantColor, int vibrantDarkColor, int vibrantLightColor, int mutedColor, int mutedDarkColor, int mutedLightColor, boolean hasLock, String lockKeyID, String platform, int minClientVersion) {
        this.id = id;
        this.build = build;
        this.buildInt = buildInt;
        this.createdTime = createdTime;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isBeta = isBeta;
        this.isProtected = isProtected;
        this.watchType = watchType;
        this.status = status;
        this.title = title;
        this.description = description;
        this.authorID = authorId;
        this.authorName = authorName;
        this.authorImageUrl = authorImageUrl;
        this.featureList = featureList;
        this.userTags = tagList;
        this.categoryList = categoryList;
        this.faceDataUrl = faceDataUrl;
        this.previewImageResource = previewImageResource;
        this.vibrantColor = vibrantColor;
        this.vibrantDarkColor = vibrantDarkColor;
        this.vibrantLightColor = vibrantLightColor;
        this.mutedColor = mutedColor;
        this.mutedDarkColor = mutedDarkColor;
        this.mutedLightColor = mutedLightColor;
        this.hasLock = hasLock;
        this.lockKeyID = lockKeyID;
        this.platform = platform;
        this.minClientVersion = minClientVersion;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public String getID() {
        return this.id;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public String getBuild() {
        return this.build;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setBuild(String build) {
        this.build = build;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getBuildInt() {
        return this.buildInt;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setBuildInt(int build) {
        this.buildInt = build;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public long getCreatedTime() {
        return this.createdTime;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public Date getCreatedDate() {
        return this.createdDate;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public Date getLastModifiedDate() {
        return this.modifiedDate;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setLastModifiedDate(Date lastModified) {
        this.modifiedDate = lastModified;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public boolean getIsBeta() {
        return this.isBeta;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setIsBeta(boolean isBeta) {
        this.isBeta = isBeta;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public boolean getIsProtected() {
        return this.isProtected;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setIsProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getTargetWatchType() {
        return this.watchType;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setTargetWatchType(int targetWatchType) {
        this.watchType = targetWatchType;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public String getStatus() {
        return this.status;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setStatus(String status) {
        this.status = status;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public String getTitle() {
        return this.title;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setTitle(String title) {
        this.title = title;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public String getDescription() {
        return this.description;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setDescription(String description) {
        this.description = description;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public String getAuthorUserID() {
        return this.authorID;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setAuthorUserID(String authorUserID) {
        this.authorID = authorUserID;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public String getAuthorName() {
        return this.authorName;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public String getAuthorImageUrl() {
        return this.authorImageUrl;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public List<String> getFeatureList() {
        return this.featureList;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setFeatureList(List<String> featureList) {
        this.featureList = featureList;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public String getFaceDataUrl() {
        return this.faceDataUrl;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setFaceDataUrl(String faceDataUrl) {
        this.faceDataUrl = faceDataUrl;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public Resource<Bitmap> getPreviewImageResource() {
        return this.previewImageResource;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public Resource<Bitmap> getPreviewImageResource(boolean shouldPrioritizeDrafts) {
        return getPreviewImageResource();
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setPreviewImageResource(Resource<Bitmap> resource) {
        this.previewImageResource = resource;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getVibrantColor() {
        return this.vibrantColor;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setVibrantColor(int color) {
        this.vibrantColor = color;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getVibrantDarkColor() {
        return this.vibrantDarkColor;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setVibrantDarkColor(int color) {
        this.vibrantDarkColor = color;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getVibrantLightColor() {
        return this.vibrantLightColor;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setVibrantLightColor(int color) {
        this.vibrantLightColor = color;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getMutedColor() {
        return this.mutedColor;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setMutedColor(int color) {
        this.mutedColor = color;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getMutedDarkColor() {
        return this.mutedDarkColor;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setMutedDarkColor(int color) {
        this.mutedDarkColor = color;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface, com.jeremysteckling.facerrel.lib.model.Watchface
    public int getMutedLightColor() {
        return this.mutedLightColor;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.MutableWatchface
    public void setMutedLightColor(int color) {
        this.mutedLightColor = color;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public String getStoreID() {
        return null;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public boolean hasLock() {
        return this.hasLock;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public String getKeyID() {
        return this.lockKeyID;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public String getPlatform() {
        return this.platform;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public int getMinClientVersion() {
        return this.minClientVersion;
    }

    public void setLegacyWatchType(int watchType) {
        this.legacyWatchType = watchType;
    }

    public int getLegacyWatchType() {
        return this.legacyWatchType;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public List<String> getUserTags() {
        return this.userTags;
    }

    public void setUserTags(List<String> userTags) {
        this.userTags = userTags;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.Watchface
    public List<String> getCategories() {
        return this.categoryList;
    }

    public void setCategories(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "FileWatchface{" +
                "authorID='" + authorID + '\'' +
                ", authorImageUrl='" + authorImageUrl + '\'' +
                ", authorName='" + authorName + '\'' +
                ", build='" + build + '\'' +
                ", buildInt=" + buildInt +
                ", categoryList=" + categoryList +
                ", createdDate=" + createdDate +
                ", createdTime=" + createdTime +
                ", description='" + description + '\'' +
                ", faceDataUrl='" + faceDataUrl + '\'' +
                ", featureList=" + featureList +
                ", hasLock=" + hasLock +
                ", id='" + id + '\'' +
                ", isBeta=" + isBeta +
                ", isProtected=" + isProtected +
                ", lockKeyID='" + lockKeyID + '\'' +
                ", minClientVersion=" + minClientVersion +
                ", modifiedDate=" + modifiedDate +
                ", mutedColor=" + mutedColor +
                ", mutedDarkColor=" + mutedDarkColor +
                ", mutedLightColor=" + mutedLightColor +
                ", platform='" + platform + '\'' +
                ", previewImageResource=" + previewImageResource +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", userTags=" + userTags +
                ", vibrantColor=" + vibrantColor +
                ", vibrantDarkColor=" + vibrantDarkColor +
                ", vibrantLightColor=" + vibrantLightColor +
                ", watchType=" + watchType +
                ", legacyWatchType=" + legacyWatchType +
                ", previewResolverStrategy=" + previewResolverStrategy +
                '}';
    }
}