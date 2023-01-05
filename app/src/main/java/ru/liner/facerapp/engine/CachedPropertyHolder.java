package ru.liner.facerapp.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 02.01.2023, понедельник
 **/
public class CachedPropertyHolder {
    private final List<CachedProperty> cachedPropertyList;

    public CachedPropertyHolder() {
        this.cachedPropertyList = new ArrayList<>();
    }

    public List<CachedProperty> getList(){
        return cachedPropertyList;
    }

    public boolean has(String key) {
        for (int i = 0; i < cachedPropertyList.size(); i++)
            if (cachedPropertyList.get(i).getKey().equals(key))
                return true;
        return false;
    }

    public CachedProperty get(String key) {
        for (int i = 0; i < cachedPropertyList.size(); i++) {
            CachedProperty cachedProperty = cachedPropertyList.get(i);
            if (cachedProperty.getKey().equals(key))
                return cachedProperty;
        }
        return null;
    }

    public void addProperty(CachedProperty property) {
        this.cachedPropertyList.add(property);
    }

    public List<CachedProperty> getCachedPropertyList() {
        return cachedPropertyList;
    }

    public void updateValue() {
        for (int i = 0; i < cachedPropertyList.size(); i++)
            cachedPropertyList.get(i).updateValue();
    }

    @Override
    public String toString() {
        return "CachedPropertyHolder{" +
                "cachedPropertyList=" + Arrays.toString(cachedPropertyList.toArray()) +
                '}';
    }
}
