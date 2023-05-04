package com.example.glidelibrary.load_data;


import com.example.glidelibrary.resource.Value;

/**
 * 加载外部资源 成功与失败的 回调
 *
 * @author hanwenrui
 */
public interface ResponseListener {

    public void responseSuccess(Value value);

    public void responseException(Exception e);

}
