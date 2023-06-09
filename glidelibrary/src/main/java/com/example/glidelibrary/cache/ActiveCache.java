package com.example.glidelibrary.cache;

import android.graphics.Bitmap;
import android.util.Log;


import com.example.glidelibrary.Tool;
import com.example.glidelibrary.resource.Value;
import com.example.glidelibrary.resource.ValueCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动缓存 -- 真正被使用的资源
 * @author hanwenrui
 */
public class ActiveCache {

    // 容器
    private Map<String, CustomoWeakReference> mapList = new HashMap<>();
    private Map<String, Bitmap> mapValueList = new HashMap<>();

    private ReferenceQueue<Value> queue; // 目的：为了监听这个弱引用 是否被回收了
    private boolean isCloseThread;
    private Thread thread;
    private boolean isShoudonRemove;
    private ValueCallback valueCallback;

    public ActiveCache(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    /**
     * TODO 添加 活动缓存
     * @param key
     * @param value
     */
    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);


        // 绑定Value的监听 --> Value发起来的（Value没有被使用了，就会发起这个监听，给外界业务需要来使用）
        value.setCallback(valueCallback);

        // 存储 --》 容器
        mapList.put(key, new CustomoWeakReference(value, getQueue(), key));
        mapValueList.put(key, value.getmBitmap());
    }

    /**
     * TODO 给外界获取Value
     * @param key
     * @return
     */
    public Value get(String key) {
        CustomoWeakReference valueWeakReference =  mapList.get(key);
        // Log.d("activeCache", "get: 自定义的弱引用" +  valueWeakReference); // 不一样的
        if (null != valueWeakReference) {
            Value value = valueWeakReference.getValue(); // 返回Value
            value.setmBitmap(mapValueList.get(key));
            value.setKey(key);
            // bug每次 value信息一致
            Log.d("activeCache", "get: Inputkey:" + key + " --- value:" + value.getmBitmap() + "对应 key:" + value.getKey());
            return value;
        }
        return null;
    }

    /**
     * TODO 手动移除
     * @param key
     * @return
     */
    public Value remove(String key) {
        isShoudonRemove = true;
        WeakReference<Value> valueWeakReference = mapList.remove(key);
        isShoudonRemove = false; // 还原 目的是为了 让 GC自动移除 继续工作
        if (null != valueWeakReference) {
            return valueWeakReference.get();
        }
        return null;
    }

    /**
     * TODO 释放 关闭线程
     */
    public void closeThread() {
        isCloseThread = true;


        mapList.clear();

        System.gc();
    }

    /**
     * 监听弱引用 成为弱引用的子类  为什么要成为弱引用的子类（目的：为了监听这个弱引用 是否被回收了）
     */
    public static final class CustomoWeakReference extends WeakReference<Value> {

        private String key;
        private Value value;

        public CustomoWeakReference(Value value, ReferenceQueue<? super Value> queue, String key) {
            super(value, queue);
            this.key = key;
            this.value = value;

            // Log.d("activeCache", "构造 put: Inputkey:" + key + " --- value:" + this.value.getmBitmap() + "对应 key:" + this.value.getKey());
        }

        public Value getValue() {
            return this.value;
        }
    }

    /**
     * 为了监听 弱引用被回收，被动移除的
     * @return
     */
    private ReferenceQueue<Value> getQueue() {
        if (queue == null) {
            queue = new ReferenceQueue<>();

            // 监听这个弱引用 是否被回收了
            thread =  new Thread(){
                @Override
                public void run() {
                    super.run();

                    while (!isCloseThread) {

                        try {
                            if (!isShoudonRemove) {
                                // queue.remove(); 阻塞式的方法

                                Reference<? extends Value> remove = queue.remove(); // 如果已经被回收了，就会执行到这个方法
                                CustomoWeakReference weakReference = (CustomoWeakReference) remove;
                                // 移除容器     !isShoudonRemove：为了区分手动移除 和 被动移除
                                if (mapList != null && !mapList.isEmpty() && !mapValueList.isEmpty()) {
                                    mapList.remove(weakReference.key);
                                    mapValueList.remove(weakReference.key);
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            };
            thread.start();
        }
        return queue;
    }
}
