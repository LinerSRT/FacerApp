package ru.liner.facerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Objects;

import ru.liner.facerapp.decoder.DecoderOrdered;
import ru.liner.facerapp.decoder.MathEvaluator;

public class MainActivity extends AppCompatActivity {
    private TextView tagsDecodeResult;
    private AppCompatEditText tagsDecodeEditText;

    private TextView mathDecodeResult;
    private AppCompatEditText mathDecodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_main);
        tagsDecodeResult = findViewById(R.id.tagsDecodeResult);
        tagsDecodeEditText = findViewById(R.id.tagsDecodeEditText);
        mathDecodeResult = findViewById(R.id.mathResult);
        mathDecodeEditText = findViewById(R.id.mathEditText);
        findViewById(R.id.tagsDecodeButton).setOnClickListener(view -> {
            String encodedText = Objects.requireNonNull(tagsDecodeEditText.getText()).toString();
            long startTime = System.nanoTime();
            String computedValue =  new DecoderOrdered(encodedText).decode();
            long endTime = System.nanoTime() - startTime;
            tagsDecodeResult.setText(computedValue);
            Log.d("TAGTAG", "Tags evaluation took: " + (endTime / 1000000f) + " ms");

        });
        findViewById(R.id.mathDecodeButton).setOnClickListener(view -> {
            String encodedText = Objects.requireNonNull(mathDecodeEditText.getText()).toString();
            MathEvaluator mathEvaluator = new MathEvaluator(encodedText);
            long startTime = System.nanoTime();
            String computedValue = mathEvaluator.evaluate();
            long endTime = System.nanoTime() - startTime;
            mathDecodeResult.setText(computedValue);
            Log.d("TAGTAG", "Math evaluation took: " + (endTime / 1000000f) + " ms");
        });
        findViewById(R.id.launchEngine).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, EngineActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
    }
}