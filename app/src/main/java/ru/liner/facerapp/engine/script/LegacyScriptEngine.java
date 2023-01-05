package ru.liner.facerapp.engine.script;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.liner.facerapp.decoder.DecoderOrdered;
import ru.liner.facerapp.decoder.MathMethod;
import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.engine.sensor.MotionSensor;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class LegacyScriptEngine implements ScriptEngine<String, String> {
    public static final String ACTION_RELOAD_WATCHFACE = "com.jeremysteckling.facerrel.RELOAD_WATCHFACE";
    public static final int FEATURE_COMPLICATION = 3;
    public static final int FEATURE_HOUR_MODE = 2;
    public static final int FEATURE_MOTION_SENSOR = 0;
    public static final int FEATURE_NO_FEATURE = -1;
    public static final int FEATURE_WEATHER = 1;
    public static final String TAG = LegacyScriptEngine.class.getSimpleName();
    private static final long TICK_RATE = TimeUnit.SECONDS.toMillis(1) / 60;
    private final Context context;
    private long lastUpdateMillis = System.currentTimeMillis();
    private boolean shouldWake = false;
    private BroadcastReceiver timeUpdateReceiver = new BroadcastReceiver() { // from class: com.jeremysteckling.facerrel.lib.engine.legacy.script.LegacyScriptEngine.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (context != null && intent != null && "android.intent.action.TIME_SET".equals(intent.getAction())) {
                long currentTimeMillis = System.currentTimeMillis();
                long lastUpdateMillis = LegacyScriptEngine.this.getLastUpdateMillis();
                if (currentTimeMillis < lastUpdateMillis) {
                    Intent reloadIntent = new Intent("com.jeremysteckling.facerrel.RELOAD_WATCHFACE");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(reloadIntent);
                    Log.e(LegacyScriptEngine.class.getSimpleName(), ":: SENT RELOAD BROADCAST :: ");
                }
            }
        }
    };

    private LegacyScriptEngine(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    public static LegacyScriptEngine create(Context context, JSONArray watchfaceJson) {
        if (context == null || watchfaceJson == null) {
            return null;
        }
        LegacyScriptEngine engine = new LegacyScriptEngine(context);
        engine.init();
        return engine;
    }

    public void init() {
        try {
            IntentFilter filter = new IntentFilter("android.intent.action.DATE_CHANGED");
            filter.addAction("android.intent.action.TIME_SET");
            this.context.registerReceiver(this.timeUpdateReceiver, filter);
        } catch (Exception e) {
            Log.w(LegacyScriptEngine.class.getSimpleName(), "Encountered an exception while attempting to register receivers; ignoring.", e);
        }
        onDeviceWake();
    }

    public synchronized void onDeviceWake() {
        this.shouldWake = true;
    }

    protected synchronized boolean shouldWake() {
        boolean z;
        if (this.shouldWake) {
            if (!RenderEnvironment.RenderMode.AMBIENT.equals(RenderEnvironment.getInstance().getRenderMode())) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    protected synchronized void doWake() {
        updateWifiLevel();
        Log.d(LegacyScriptEngine.class.getSimpleName(), "Script Engine is in Standalone Mode; onWake() requesting asynchronous Weather Update...");
        this.shouldWake = false;
        Log.e(LegacyScriptEngine.class.getSimpleName(), " ::: LEGACY SCRIPT ENGINE WAKE EVENT :::");
    }

    private void updateWifiLevel() {
        try {
            JSONObject phoneData = new JSONObject();
            phoneData.put("wifi_level", 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override // com.jeremysteckling.facerrel.lib.engine.script.ScriptEngine
    public void update(long currentTimeMillis) {
        if (shouldWake()) {
            doWake();
        }
        long lastUpdateMillis = getLastUpdateMillis();
        if (currentTimeMillis - lastUpdateMillis > TICK_RATE || currentTimeMillis < lastUpdateMillis) {
            setLastUpdateMillis(currentTimeMillis);
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.script.ScriptEngine
    public void update(boolean shouldForce) {
        if (shouldForce) {
            if (shouldWake()) {
                doWake();
            }
        }
    }

    @Override // com.jeremysteckling.facerrel.lib.engine.script.ScriptEngine
    public void shutdown() {
        MotionSensor motionSensor = MotionSensor.getInstance(null);
        if (motionSensor != null) {
            motionSensor.unregisterForEvents();
        }
        try {
            this.context.unregisterReceiver(this.timeUpdateReceiver);
        } catch (Exception e) {
            Log.w(LegacyScriptEngine.class.getSimpleName(), "Encountered an exception while attempting to unregister receivers; ignoring.", e);
        }
    }

    public String parse(String script) {
        return new DecoderOrdered(script).decode();
    }

    public boolean isDynamic(String script) {
        return true;
    }

    public int[] checkFeatures(String script) {
        int[] features = null;
        if (script != null && !script.trim().isEmpty()) {
            List<Integer> featureList = new ArrayList<>(3);
            if (
                    script.contains("#M") ||
                            script.contains(MathMethod.ACCEL_X.getName()) ||
                            script.contains(MathMethod.ACCEL_Y.getName()) ||
                            script.contains(MathMethod.GYRO_X.getName()) ||
                            script.contains(MathMethod.GYRO_Y.getName())
            ) {
                Log.w(LegacyScriptEngine.class.getSimpleName(), "Watchface uses sensor data, registering for motion events!");
                MotionSensor motionSensor = MotionSensor.getInstance(context);
                if (motionSensor != null) {
                    motionSensor.registerForEvents();
                }
                featureList.add(0);
            }
            if (!featureList.isEmpty()) {
                features = featureList.stream().mapToInt(i -> i).toArray();
            }
        }
        return features;
    }


    protected synchronized void setLastUpdateMillis(long lastUpdateMillis) {
        this.lastUpdateMillis = lastUpdateMillis;
    }

    protected synchronized long getLastUpdateMillis() {
        return this.lastUpdateMillis;
    }



}
