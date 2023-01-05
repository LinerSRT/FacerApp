package ru.liner.facerapp.engine;

import android.text.TextUtils;
import android.util.Log;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.parser.ParseException;

import java.math.BigDecimal;

import ru.liner.facerapp.decoder.DecoderOrdered;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 02.01.2023, понедельник
 **/
public class CachedProperty {
    private final String key;
    private final String value;
    private String computedValue;

    public CachedProperty(String key, String value) {
        this.key = key;
        this.value = value;
        updateValue();
    }

    public void updateValue(){
        this.computedValue = (value.contains("#") || value.contains("$")) ? new DecoderOrdered(value).decode() : value;
    }

    private boolean needCompute(){
        return computedValue.contains("#") || computedValue.contains("$") || computedValue.contains("(");
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    public float getValueAsFloat() {
        return Float.parseFloat(value);
    }

    public int getValueAsInt() {
        return Integer.parseInt(value);
    }
    public boolean getValueAsIBoolean() {
        return Boolean.parseBoolean(value);
    }

    public String getComputedValue() {
        if(needCompute())
            updateValue();
        return computedValue;
    }

    public float getComputedValueAsFloat() {
        if(needCompute())
            updateValue();
        if(TextUtils.isEmpty(computedValue))
            return 0f;
        if(computedValue.contains("\""))
            computedValue = computedValue.replace("\"", "");
        try {
            return Float.parseFloat(computedValue);
        } catch (NumberFormatException e) {
            try {
                return ((BigDecimal) new Expression(computedValue).evaluate().getValue()).floatValue();
            } catch (EvaluationException | ParseException | ClassCastException ex) {
                Log.e("TAGTAG", "Fail to parse: "+computedValue, e.fillInStackTrace());
                return 0f;
            }
        }
    }

    public int getComputedValueAsInt() {
        try {
            return Integer.parseInt(computedValue);
        } catch (NumberFormatException e) {
            try {
                return ((BigDecimal) new Expression(computedValue).evaluate().getValue()).intValue();
            } catch (EvaluationException | ParseException ex) {
                ex.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "CachedProperty{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", computedValue='" + computedValue + '\'' +
                '}';
    }
}
