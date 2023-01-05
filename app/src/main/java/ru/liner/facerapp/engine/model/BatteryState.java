package ru.liner.facerapp.engine.model;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public interface BatteryState {
    long getLastModifiedTime();

    int getLevel();

    float getPercentage();

    int getScale();

    int getStatus();

    int getTemperature();

    int getVoltage();

    boolean isCharging();
}
