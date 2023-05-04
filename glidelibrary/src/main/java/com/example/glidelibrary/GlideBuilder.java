package com.example.glidelibrary;

/**
 * @author hanwenrui
 */
public class GlideBuilder {

    /**
     * 创建Glide
     * @return
     */
    public Glide build() {
        RequestManagerRetriver requestManagerRetriver = new RequestManagerRetriver();
        Glide glide = new Glide(requestManagerRetriver);
        return glide;
    }
}
