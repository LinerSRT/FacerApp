package ru.liner.facerapp.engine;

import android.content.Context;
import android.content.pm.PackageManager;

import org.json.JSONObject;

import ru.liner.facerapp.engine.file.WriteFile;
import ru.liner.facerapp.engine.sensor.MotionSensor;
import ru.liner.facerapp.engine.utils.PreferenceBoundTimeLatch;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class Status {
    private static JSONObject mPhoneData;
    private static PreferenceBoundTimeLatch phoneDataUpdateLatch;
    private static PreferenceBoundTimeLatch weatherDataUpdateLatch;
    public static String a = "Rn";
    private static Boolean isZenMode = false;
    private static Boolean isLowPower = false;
    private static Integer mWatchDevice = 1;
    private static Integer mNotificationCount = 0;
    private static Integer mStepCount = 0;
    public static Integer mLastSeenInList = 0;
    private static WriteFile mWriteFile = new WriteFile();
    private static double mLat = MotionSensor.INTEGRATION_MIN_INTERVAL;
    private static double mLon = MotionSensor.INTEGRATION_MIN_INTERVAL;
    private static int versionCode = 0;
    private static String versionName = "0.0";

    public static void savePhoneData(JSONObject phoneData) {
        mPhoneData = phoneData;
    }

    public static JSONObject getPhoneData() {
        return mPhoneData;
    }


    public static double getLat() {
        return mLat;
    }

    public static void setLat(double mLat2) {
        mLat = mLat2;
    }

    public static double getLon() {
        return mLon;
    }

    public static void setLon(double mLon2) {
        mLon = mLon2;
    }

    public static Integer getmWatchDevice() {
        return mWatchDevice;
    }

    public static void setmWatchDevice(Integer mWatchDevice2) {
        mWatchDevice = mWatchDevice2;
    }

    public static Boolean getIsLowPower() {
        return isLowPower;
    }

    public static void setIsLowPower(Boolean isLowPower2) {
        isLowPower = isLowPower2;
    }

    public static Boolean getIsZenMode() {
        return isZenMode;
    }

    public static void setIsZenMode(Boolean isZenMode2) {
        isZenMode = isZenMode2;
    }

    public static Integer getmNotificationCount() {
        return mNotificationCount;
    }

    public static void setmNotificationCount(Integer mNotificationCount2) {
        mNotificationCount = mNotificationCount2;
    }

    public static Integer getmStepCount() {
        return mStepCount;
    }

    public static void setmStepCount(Integer mStepCount2) {
        mStepCount = mStepCount2;
    }

}
