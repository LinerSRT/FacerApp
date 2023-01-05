package ru.liner.facerapp.engine.utils;

import android.graphics.Color;

import androidx.core.view.ViewCompat;

import java.util.Random;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ColorUtils {
    public static final int COLOR_INT_BLACK = fromHexString("#000");

    public static String toHexString(int color) {
        String hexString = String.format("#%06X", Integer.valueOf(16777215 & color));
        return "#ff" + hexString.substring(1, hexString.length());
    }

    public static int fromHexString(String colorCode) {
        if (colorCode == null)
            return Color.TRANSPARENT;

        String colorCode2 = colorCode.trim();
        String colorHex = colorCode2;
        try {
            if (!colorHex.startsWith("#")) {
                colorHex ="#" + colorHex;
            }
            return Color.parseColor(colorHex);
        } catch (IllegalArgumentException e) {
            try {
                return Integer.parseInt(colorCode2);
            } catch (NumberFormatException e2) {
                return ViewCompat.MEASURED_STATE_MASK;
            }
        }
    }

    public static int alterColorShade(int color, float shade) {
        float[] hsl = getHSLComponents(color);
        hsl[2] += shade;
        return androidx.core.graphics.ColorUtils.HSLToColor(hsl);
    }

    public static int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(255), random.nextInt(255),random.nextInt(255));
    }

    public static int[] getRGBComponents(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return new int[]{red, green, blue};
    }
    public static float[] getHSLComponents(int color) {
        float[] hsl = new float[3];
        int[] rgb = getRGBComponents(color);
        androidx.core.graphics.ColorUtils.RGBToHSL(rgb[0], rgb[1], rgb[2], hsl);
        return hsl;
    }
}
