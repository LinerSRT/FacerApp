package ru.liner.facerapp.engine.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import ru.liner.facerapp.utils.MathUtils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class MotionSensor implements SensorEventListener {
    public static final int DEFAULT_SAMPLING_PERIOD = 3;
    public static final float DRAG_COEFFICIENT = 1.0f;
    private static final String INTEGRATE_MODE = "IntegrateMode.Request";
    private static final String INTEGRATE_MODE_ALL = "IntegrateMode.All";
    private static final String INTEGRATE_MODE_REQUEST = "IntegrateMode.Request";
    private static final String INTEGRATE_MODE_UPDATE = "IntegrateMode.Update";
    public static final double INTEGRATION_MAX_ACCELERATION = 100.0d;
    public static final double INTEGRATION_MAX_INTERVAL = 1.0d;
    public static final double INTEGRATION_MAX_VELOCITY = 100.0d;
    public static final double INTEGRATION_MIN_ACCELERATION = -100.0d;
    public static final double INTEGRATION_MIN_INTERVAL = 0.0d;
    public static final double INTEGRATION_MIN_VELOCITY = -100.0d;
    private static final boolean SHOULD_LOG_FRAMERATE = false;
    private static MotionSensor instance;
    private static final int[] validSensorTypes = {4, 1};
    private final SensorManager sensorManager;
    private final SparseArray<float[]> accelerationValues = new SparseArray<>();
    private final SparseArray<float[]> positionValues = new SparseArray<>();
    private final SparseArray<float[]> previousPositionValues = new SparseArray<>();
    private final SparseArray<Long> lastUpdateTimes = new SparseArray<>();
    private final SparseArray<Long> frames = new SparseArray<>();
    private final SparseArray<Long> lastFrameTickTimes = new SparseArray<>();

    public static MotionSensor getInstance(Context context) {
        SensorManager sensorManager;
        if (instance == null && context != null && (sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE)) != null) {
            instance = new MotionSensor(sensorManager);
        }
        return instance;
    }

    private MotionSensor(@NonNull SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public boolean registerForEvents() {
        int[] iArr;
        if (validSensorTypes != null && validSensorTypes.length > 0) {
            for (int sensorType : validSensorTypes) {
                Sensor sensor = this.sensorManager.getDefaultSensor(sensorType);
                this.sensorManager.registerListener(this, sensor, 3);
            }
            return true;
        }
        return true;
    }

    public boolean unregisterForEvents() {
        this.sensorManager.unregisterListener(this);
        return true;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor;
        int[] iArr;
        if (event != null && (sensor = event.sensor) != null && validSensorTypes != null && validSensorTypes.length > 0) {
            for (int sensorType : validSensorTypes) {
                if (sensor.getType() == sensorType) {
                    this.accelerationValues.put(sensor.getType(), event.values);
                    if ("IntegrateMode.Request".equals(INTEGRATE_MODE_ALL) || "IntegrateMode.Request".equals("IntegrateMode.Request")) {
                        integrate(sensorType);
                        return;
                    }
                    return;
                }
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected void integrate(int sensorType) {
        float[] accelerations = this.accelerationValues.get(sensorType);
        if (accelerations != null && accelerations.length > 0) {
            float[] positions = this.positionValues.get(sensorType);
            if (positions == null || positions.length < accelerations.length) {
                positions = new float[accelerations.length];
            }
            float[] previousPositions = this.previousPositionValues.get(sensorType);
            if (previousPositions == null || positions.length < accelerations.length) {
                previousPositions = new float[accelerations.length];
            }
            if (positions.length >= accelerations.length && previousPositions.length >= accelerations.length) {
                long lastUpdateTimeMillis = this.lastUpdateTimes.get(sensorType, Long.valueOf(System.currentTimeMillis())).longValue();
                long currentTimeMillis = System.currentTimeMillis();
                for (int index = 0; index < positions.length; index++) {
                    float nextPos = verletPosition(positions[index], previousPositions[index], accelerations[index], currentTimeMillis - lastUpdateTimeMillis);
                    previousPositions[index] = positions[index];
                    positions[index] = nextPos;
                }
                this.previousPositionValues.put(sensorType, previousPositions);
                this.positionValues.put(sensorType, positions);
                this.lastUpdateTimes.put(sensorType, Long.valueOf(currentTimeMillis));
            }
        }
    }

    protected float verletPosition(float position, float previousPosition, float acceleration, long updateIntervalMillis) {
        double seconds = MathUtils.clamp(updateIntervalMillis / 1000.0d, (double) INTEGRATION_MIN_INTERVAL, 1.0d);
        double deltaVel = ((1.0f * position) - (1.0f * previousPosition)) * seconds;
        double deltaAccel = acceleration * seconds;
        double nextPos = position + MathUtils.clamp(deltaVel, -100.0d, 100.0d) + MathUtils.clamp(deltaAccel, -100.0d, 100.0d);
        return (float) nextPos;
    }

    protected float verletVelocity(float position, long lastUpdateTimeMillis) {
        return 0.0f;
    }

    protected void accumulateFrames(int sensorType) {
        long currentTimeMillis = System.currentTimeMillis();
        long lastGlobalTickTime = this.lastFrameTickTimes.get(0, 0L).longValue();
        if (lastGlobalTickTime == 0) {
            lastGlobalTickTime = currentTimeMillis;
            this.lastFrameTickTimes.put(0, Long.valueOf(lastGlobalTickTime));
        }
        long globalFrameCount = this.frames.get(0, 0L).longValue() + 1;
        float elapsedGlobalSeconds = ((float) (currentTimeMillis - lastGlobalTickTime)) / 1000.0f;
        if (elapsedGlobalSeconds > 1.0f) {
            Log.e(MotionSensor.class.getSimpleName(), "Global FPS: [" + (elapsedGlobalSeconds > 0.0f ? ((float) globalFrameCount) / elapsedGlobalSeconds : 0.0f) + "]");
            globalFrameCount = 0;
            this.lastFrameTickTimes.put(0, Long.valueOf(currentTimeMillis));
        }
        this.frames.put(0, Long.valueOf(globalFrameCount));
        long lastSensorTickTime = this.lastFrameTickTimes.get(sensorType, 0L).longValue();
        if (lastSensorTickTime == 0) {
            lastSensorTickTime = currentTimeMillis;
            this.lastFrameTickTimes.put(sensorType, Long.valueOf(lastSensorTickTime));
        }
        long sensorFrameCount = this.frames.get(sensorType, 0L).longValue() + 1;
        float elapsedSensorSeconds = ((float) (currentTimeMillis - lastSensorTickTime)) / 1000.0f;
        if (elapsedSensorSeconds > 1.0f) {
            Log.e(MotionSensor.class.getSimpleName(), "Sensor FPS for SensorType [" + sensorType + "]: [" + (elapsedSensorSeconds > 0.0f ? ((float) sensorFrameCount) / elapsedSensorSeconds : 0.0f) + "]");
            sensorFrameCount = 0;
            this.lastFrameTickTimes.put(sensorType, Long.valueOf(currentTimeMillis));
        }
        this.frames.put(sensorType, Long.valueOf(sensorFrameCount));
    }

    public float getXAccel(int sensorType) {
        float[] values = this.accelerationValues.get(sensorType);
        if (values == null || values.length <= 0) {
            return 0.0f;
        }
        return values[0];
    }

    public float getYAccel(int sensorType) {
        float[] values = this.accelerationValues.get(sensorType);
        if (values == null || values.length <= 1) {
            return 0.0f;
        }
        return values[1];
    }

    public float getXPos(int sensorType) {
        if ("IntegrateMode.Request".equals(INTEGRATE_MODE_ALL) || "IntegrateMode.Request".equals("IntegrateMode.Request")) {
            integrate(sensorType);
        }
        float[] positions = this.positionValues.get(sensorType);
        if (positions == null || positions.length <= 0) {
            return 0.0f;
        }
        return positions[0];
    }

    public float getYPos(int sensorType) {
        if ("IntegrateMode.Request".equals(INTEGRATE_MODE_ALL) || "IntegrateMode.Request".equals("IntegrateMode.Request")) {
            integrate(sensorType);
        }
        float[] positions = this.positionValues.get(sensorType);
        if (positions == null || positions.length <= 1) {
            return 0.0f;
        }
        return positions[1];
    }

    public float getGyroscopeX() {
        return getXPos(4);
    }

    public float getGyroscopeY() {
        return getYPos(4);
    }

    public float getAccelerometerX() {
        return getXPos(1);
    }

    public float getAccelerometerY() {
        return getYPos(1);
    }

    public void reset() {
        this.previousPositionValues.clear();
        this.positionValues.clear();
        this.lastUpdateTimes.clear();
    }

    public void update() {
        int[] iArr;
        if (("IntegrateMode.Request".equals(INTEGRATE_MODE_ALL) || "IntegrateMode.Request".equals(INTEGRATE_MODE_UPDATE)) && validSensorTypes != null && validSensorTypes.length > 0) {
            for (int sensorType : validSensorTypes) {
                integrate(sensorType);
            }
        }
    }
}