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
public interface MutableWatchface extends Watchface{
    void setAuthorUserID(String str);

    void setBuild(String str);

    void setBuildInt(int i);

    void setDescription(String str);

    void setFaceDataUrl(String str);

    void setFeatureList(List<String> list);

    void setIsBeta(boolean z);

    void setIsProtected(boolean z);

    void setLastModifiedDate(Date date);

    void setMutedColor(int i);

    void setMutedDarkColor(int i);

    void setMutedLightColor(int i);

    void setPreviewImageResource(Resource<Bitmap> resource);

    void setStatus(String str);

    void setTargetWatchType(int i);

    void setTitle(String str);

    void setVibrantColor(int i);

    void setVibrantDarkColor(int i);

    void setVibrantLightColor(int i);

}
