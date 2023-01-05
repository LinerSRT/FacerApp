package ru.liner.facerapp;

import android.app.Application;
import android.os.StrictMode;

import ru.liner.facerapp.wrapper.PM;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class Core extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PM.init(this);
    }
}
