package com.example.glidelibrary.cache;


import com.example.glidelibrary.resource.Value;

/**
 * 内存缓存中，元素被移除的接口回调
 * @author hanwenrui
 */
public interface MemoryCacheCallback {

    /**
     * 内存缓存中移除的 key--value
     * @param key
     * @param oldValue
     */
    public void entryRemovedMemoryCache(String key, Value oldValue);

}
