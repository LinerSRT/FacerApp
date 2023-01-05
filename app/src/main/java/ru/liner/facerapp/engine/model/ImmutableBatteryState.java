package ru.liner.facerapp.engine.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ImmutableBatteryState implements BatteryState, Parcelable {
    public static final String EXTRA_BATTERY_STATE = "BatteryState";
    private final long lastModifiedTime;
    private final int level;
    private final int scale;
    private final int status;
    private final int temperature;
    private final int voltage;

    public ImmutableBatteryState(int status, int level, int scale, int temperature, int voltage, long lastModifiedTime) {
        this.status = status;
        this.level = level;
        this.scale = scale;
        this.temperature = temperature;
        this.voltage = voltage;
        this.lastModifiedTime = lastModifiedTime;
    }

    public ImmutableBatteryState(BatteryState batteryState) {
        if (batteryState == null) {
            this.status = -1;
            this.level = -1;
            this.scale = -1;
            this.temperature = -1;
            this.voltage = -1;
            this.lastModifiedTime = 0L;
            Log.w(getClass().getSimpleName(), "Attempted to create a copy of null BatteryState; ignoring, default values used.");
            return;
        }
        this.status = batteryState.getStatus();
        this.level = batteryState.getLevel();
        this.scale = batteryState.getScale();
        this.temperature = batteryState.getTemperature();
        this.voltage = batteryState.getVoltage();
        this.lastModifiedTime = batteryState.getLastModifiedTime();
    }

    private ImmutableBatteryState(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readLong());
    }

    public static final Creator<ImmutableBatteryState> CREATOR = new Creator<ImmutableBatteryState>() {
        @Override
        public ImmutableBatteryState createFromParcel(Parcel in) {
            return new ImmutableBatteryState(in);
        }

        @Override
        public ImmutableBatteryState[] newArray(int size) {
            return new ImmutableBatteryState[size];
        }
    };

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public boolean isCharging() {
        return status == 2 || status == 5;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getScale() {
        return scale;
    }

    @Override
    public float getPercentage() {
        if (level <= 0 || scale <= 0)
            return 0.0f;
        return (float) level / scale;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public int getVoltage() {
        return voltage;
    }

    @Override
    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(status);
        parcel.writeInt(level);
        parcel.writeInt(scale);
        parcel.writeInt(temperature);
        parcel.writeInt(voltage);
        parcel.writeLong(lastModifiedTime);
    }

}
