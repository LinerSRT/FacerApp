package ru.liner.facerapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

import ru.liner.facerapp.engine.FrameRateListener;
import ru.liner.facerapp.engine.RenderEnvironment;
import ru.liner.facerapp.utils.SnakeView;
import ru.liner.facerapp.wrapper.LegacyRenderer;
import ru.liner.facerapp.wrapper.WatchfaceUtils;

public class EngineActivity extends AppCompatActivity {
    private static final String[] testingWatchfaces = new String[]{
            "JEDI.face",
            "FR_EV_RM01.face",
    };


    private LegacyRenderer legacyRenderer;
    private Button chooseWatchface;
    private Button startRender;
    private Button stopRender;
    private TextView fps;
    private TextView layer;
    private TextView watchfaceName;
    private SnakeView chart;
    private int index = 0;
    private List<File> watchfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_engine);
        legacyRenderer = findViewById(R.id.facerEngine);
        chart = findViewById(R.id.chart);
        fps = findViewById(R.id.fpsCount);
        layer = findViewById(R.id.layerCount);
        watchfaceName = findViewById(R.id.watchfaceName);
        chooseWatchface = findViewById(R.id.selectWatchface);
        startRender = findViewById(R.id.startRender);
        stopRender = findViewById(R.id.stopRender);
        watchfaces = WatchfaceUtils.getWatchfaces();
        legacyRenderer.setFramerateListener(new FrameRateListener() {
            @Override
            public void onFrameRateChanged(float frameRate) {
                chart.post(new Runnable() {
                    @Override
                    public void run() {
                        if (frameRate > chart.getMaxValue())
                            chart.setMaxValue((float) frameRate);
                        chart.addValue((float) frameRate);
                    }
                });
            }
        });
        chooseWatchface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File watchfaceFile = WatchfaceUtils.randomWatchface();
                if (watchfaceFile != null) {
                    watchfaceName.setText(watchfaceFile.getName());
                    legacyRenderer.setWatchface(watchfaceFile);
                }
            }
        });

        startRender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                legacyRenderer.resume();
            }
        });

        stopRender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                legacyRenderer.pause();
            }
        });
        findViewById(R.id.clearWatchface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                watchfaceName.setText(watchfaces.get(index).getName());
                legacyRenderer.setWatchface(watchfaces.get(index));
            }
        });
        findViewById(R.id.nextVatchfaceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index + 1 < watchfaces.size()) {
                    index++;
                    watchfaceName.setText(watchfaces.get(index).getName());
                    legacyRenderer.setWatchface(watchfaces.get(index));
                }
            }
        });
        findViewById(R.id.previousVatchfaceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index - 1 >= 0) {
                    index--;
                    watchfaceName.setText(watchfaces.get(index).getName());
                    legacyRenderer.setWatchface(watchfaces.get(index));
                }
            }
        });
        findViewById(R.id.engineAmbientButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RenderEnvironment renderEnvironment = RenderEnvironment.getInstance();
                renderEnvironment.setRenderMode(renderEnvironment.getRenderMode() == RenderEnvironment.RenderMode.AMBIENT ? RenderEnvironment.RenderMode.ACTIVE : RenderEnvironment.RenderMode.AMBIENT);
                //facerEngine.setLowPower(!facerEngine.isLowPower());
            }
        });
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}