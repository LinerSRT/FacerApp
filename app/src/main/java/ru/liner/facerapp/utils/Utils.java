package ru.liner.facerapp.utils;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 26.12.2021, воскресенье
 **/
@SuppressWarnings("unchecked")
public class Utils {

    public static String readFileToString(@NonNull File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } finally {
            bufferedReader.close();
            fileInputStream.close();
        }
        return stringBuilder.toString();
    }

    public static <V> Object getValue(HashMap<String, String> hashMap, String key, V defaultValue){
        if(hashMap.containsKey(key)){
            if(defaultValue instanceof String){
                return hashMap.get(key);
            } else if(defaultValue instanceof Integer){
                return Integer.parseInt(hashMap.get(key));
            } else if(defaultValue instanceof Float){
                return Float.parseFloat(hashMap.get(key));
            }else if(defaultValue instanceof Double){
                return Double.parseDouble(hashMap.get(key));
            }else if(defaultValue instanceof Boolean){
                return Boolean.parseBoolean(hashMap.get(key));
            }
        }
        return defaultValue;
    }
}
