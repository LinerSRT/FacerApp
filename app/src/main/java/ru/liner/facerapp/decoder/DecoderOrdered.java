package ru.liner.facerapp.decoder;

import java.util.regex.Pattern;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 05.01.2023, четверг
 **/
public class DecoderOrdered {
    private String value;

    public DecoderOrdered(String value) {
        this.value = value;
    }

    public String decode() {
        if(!shouldDecode(value))
            return value;
        String result = value;
        if (containVariable(result))
            return "0";
        if (containTag(result)) {
            result = new UserSettingsDecoder(result)
                    .next(AstronomyDecoder.class)
                    .next(DateTimeDecoder.class)
                    .next(DeviceDecoder.class)
                    .next(HealthDecoder.class)
                    .next(WeatherDecoder.class).getComputedValue();
        }
        if (containMathCondition(result)) {
            result = new MathEvaluator(result).evaluate();
        }
        if (containCondition(result)) {
            result = new ConditionEvaluator(result).evaluate();
        }
        return result.replace("&", "").replace("#", "");
    }

    private boolean shouldDecode(String value){
        return containTag(value) || containCondition(value) || containMathCondition(value) || containVariable(value);
    }

    private boolean containTag(String value) {
        return value.contains("#");
    }

    private boolean containCondition(String value) {
        return value.contains("&") || value.contains("?") || value.contains(":");
    }

    private boolean containMathCondition(String value) {
        return value.contains("(") || value.contains(")");
    }

    private boolean containVariable(String value) {
        return Pattern.compile("(\\#VAR_[0-9]*\\#)").matcher(value).find();
    }
}
