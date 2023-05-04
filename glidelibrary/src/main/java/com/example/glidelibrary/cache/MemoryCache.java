package com.example.glidelibrary.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.glidelibrary.resource.Value;


/**
 * 内存缓存--LRU算法
 * @author hanwenrui
 */
public class MemoryCache extends LruCache<String, Value> {

    private boolean shoudonRemove;

    // TODO 手动移除
    public Value shoudonRemove(String key) {
        shoudonRemove = true;
        Value value = remove(key);
        shoudonRemove = false;  // !shoudonRemove == 被动的
        return value;
    }

    // put get

    private MemoryCacheCallback memoryCacheCallback;

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        this.memoryCacheCallback = memoryCacheCallback;
    }

    /**
     * 传入元素最大值，给LruCache
     * @param maxSize
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(@NonNull String key, @NonNull Value value) {
        // return super.sizeOf(key, value);
        Bitmap bitmap = value.getmBitmap(); // 8


        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }

        return bitmap.getByteCount();
    }

    /**
     * 1.重复的key
     * 2.最少使用的元素会被移除
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, @NonNull String key, @NonNull Value oldValue, @Nullable Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);

        if (memoryCacheCallback != null && !shoudonRemove) { // !shoudonRemove == 被动的
            memoryCacheCallback.entryRemovedMemoryCache(key, oldValue);
        }

    }
}
