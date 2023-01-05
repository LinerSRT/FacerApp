package ru.liner.facerapp.engine.resource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.io.File;
import java.net.URL;

import ru.liner.facerapp.engine.resource.reader.BitmapReader;
import ru.liner.facerapp.engine.resource.resolver.FileResolverStrategy;
import ru.liner.facerapp.engine.resource.resolver.NullResolver;
import ru.liner.facerapp.engine.resource.resolver.ResolverStrategy;
import ru.liner.facerapp.engine.resource.resolver.ZipFileResolverStrategy;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class ImageResourceFactory {
    public static Resource<Bitmap> fromURLString(@NonNull String urlString) {
        try {
            URL url = new URL(urlString);
            Uri uri = Uri.parse(url.toURI().toString());
            return new UriResource(uri, new NullResolver());
        } catch (Exception e) {
            String simpleName = ImageResourceFactory.class.getSimpleName();
            StringBuilder append = new StringBuilder().append("Encountered an unexpected exception while attempting to parse URL string [");
            if (urlString == null) {
                urlString = "null";
            }
            Log.w(simpleName, append.append(urlString).append("] to a URI, ImageResource was not built; aborting.").toString(), e);
            return null;
        }
    }

    public static Resource<Bitmap> fromFile(File file) {
        if (file != null) {
            return new FileResource(file, new FileResolverStrategy(new BitmapReader()));
        }
        return null;
    }

    public static Resource<Bitmap> fromZipFile(File file, String filename) {
        if (file == null || filename == null) {
            return null;
        }
        return new FileResource(file, new ZipFileResolverStrategy(new BitmapReader(), filename));
    }

    public static Resource<Bitmap> fromDrawableRes(@NonNull Context context, @DrawableRes int drawableID) {
        final Resources resources = context.getApplicationContext().getResources();
        return new BaseResource(Integer.valueOf(drawableID), new ResolverStrategy<Bitmap, Integer>() { // from class: com.jeremysteckling.facerrel.lib.model.resource.ImageResourceFactory.1
            public Bitmap resolve(Integer source) {
                return BitmapFactory.decodeResource(resources, source.intValue());
            }
        });
    }

}
