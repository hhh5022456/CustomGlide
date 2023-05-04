package com.example.glidelibrary.load_data;

import android.content.Context;

import com.example.glidelibrary.resource.Value;


/**
 * 加载外部资源 标准
 * @author hanwenrui
 */
public interface ILoadData {

    /**
     * @param path
     * @param responseListener
     * @param context
     * @return 加载外部资源的行为
     */
    public Value loadResource(String path, ResponseListener responseListener, Context context);

}
