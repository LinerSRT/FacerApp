package ru.liner.facerapp.engine.cache;

import android.util.Log;
import android.util.LruCache;

import java.util.Map;

import ru.liner.facerapp.engine.async.Operation;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class OperationCache<K, V> extends LruCache<K, V> {
    private final Operation<K, V> createOperation;

    public OperationCache(int maxSize, Operation<K, V> createOperation) {
        super(maxSize);
        this.createOperation = createOperation;
    }

    public V peekValue(K key) {
        if (key == null) {
            return null;
        }
        Map<K, V> snapshot = snapshot();
        return snapshot.get(key);
    }

    @Override 
    protected V create(K key) {
        V value = peekValue(key);
        if (value == null) {
            try {
                return this.createOperation.execute(key);
            } catch (Exception e) {
                Log.e(OperationCache.class.getSimpleName(), "Unable to construct new cache reference, operation threw an Exception; aborting.", e);
                return null;
            }
        }
        return value;
    }
}