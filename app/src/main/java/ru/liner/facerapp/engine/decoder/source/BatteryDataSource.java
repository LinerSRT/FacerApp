package ru.liner.facerapp.engine.decoder.source;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ru.liner.facerapp.engine.model.BatteryState;
import ru.liner.facerapp.engine.model.BatteryStateFactory;
import ru.liner.facerapp.engine.state.BatteryStatePreferenceState;
import ru.liner.facerapp.engine.utils.PreferenceBoundTimeLatch;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class BatteryDataSource extends DataSource<BatteryState> {
    public static final long DEFAULT_SYNC_INTERVAL = 1;
    public static final long DEFAULT_UPDATE_INTERVAL = 1;
    public static final String DEVICE_ID_LOCAL = "LOCAL";
    public static final String DEVICE_ID_REMOTE = "REMOTE";
    public static final String PREFS_BATTERY_LAST_SYNC_TIME = "BatteryDataSourceLastSyncTime";
    public static final String PREFS_BATTERY_LAST_UPDATE_TIME = "BatteryDataSourceLastUpdateTime";
    public static final String PREFS_BATTERY_STATE = "BatteryDataSourceState";
    public static final String PREFS_SEPARATOR = ".";
    protected static final String TIZEN_DATA = "data";
    protected static final String TIZEN_EVENT = "event";
    protected static final String TIZEN_EVENT_PHONE_BATTERY_UPDATE = "PHONE_BATTERY_UPDATE";
    private final Context context;
    private final String deviceID;
    private final PreferenceBoundTimeLatch syncLatch;
    protected final DataSource.OnUpdateCompleteListener<BatteryState> updateCompleteListener = new DataSource.OnUpdateCompleteListener<BatteryState>() { // from class: com.jeremysteckling.facerrel.lib.sync.local.provenance.battery.BatteryDataSource.1
        public void onUpdateComplete(BatteryState state, boolean wasForced) {
            JSONObject stateJson;
            if ((state != null && BatteryDataSource.this.syncLatch.isReleased()) || wasForced) {
                BatteryDataSource.this.syncLatch.latch(BatteryDataSource.this.context);
            }
        }
    };
    private final PreferenceBoundTimeLatch updateLatch;
    private static BatteryDataSource localInstance = null;
    public static final TimeUnit DEFAULT_UPDATE_TIMEUNIT = TimeUnit.SECONDS;
    public static final TimeUnit DEFAULT_SYNC_TIMEUNIT = TimeUnit.SECONDS;
    protected static final IntentFilter DEFAULT_BATTERY_INTENTFILTER = new IntentFilter("android.intent.action.BATTERY_CHANGED");

    public static synchronized BatteryDataSource getLocalInstance(@NonNull Context context) {
        BatteryDataSource batteryDataSource;
        synchronized (BatteryDataSource.class) {
            if (localInstance == null) {
                localInstance = new BatteryDataSource(context, DEVICE_ID_LOCAL);
                localInstance.addListener(localInstance.updateCompleteListener);
            }
            batteryDataSource = localInstance;
        }
        return batteryDataSource;
    }

    public static synchronized String getDeviceID(@NonNull Context context) {
        String uuid;
        synchronized (BatteryDataSource.class) {
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }

    public BatteryDataSource(@NonNull Context context, @NonNull String deviceID) {
        super(new BatteryStatePreferenceState(context, "BatteryDataSourceState." + deviceID), new DataSource.Options.Builder().setUpdateMode(DataSource.Options.UpdateMode.IGNORE_NULL).build());
        this.context = context;
        this.deviceID = deviceID;
        this.updateLatch = new PreferenceBoundTimeLatch(context, "BatteryDataSourceLastUpdateTime." + deviceID, 1L, DEFAULT_UPDATE_TIMEUNIT);
        this.syncLatch = new PreferenceBoundTimeLatch(context, "BatteryDataSourceLastSyncTime." + deviceID, 1L, DEFAULT_SYNC_TIMEUNIT);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jeremysteckling.facerrel.lib.sync.local.provenance.DataSource
    public BatteryState update(boolean shouldForce) {
        Intent batteryStatus;
        BatteryState previousState = getState();
        if ((previousState == null || this.updateLatch.isReleased() || shouldForce) && (batteryStatus = this.context.registerReceiver(null, DEFAULT_BATTERY_INTENTFILTER)) != null) {
            return BatteryStateFactory.fromIntent(batteryStatus);
        }
        return null;
    }
}