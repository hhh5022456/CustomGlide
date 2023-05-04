package com.example.glidelibrary.pool;

import android.graphics.Bitmap;


/**
* Bitmap 标准
* $author Handy
* created at 2023/5/4 14:26
*/
public interface BitmapPool {

    /**
     * 加入到Bitmap内存复用池
     * @param bitmap
     */
    void put(Bitmap bitmap);

    /**
     * 从Bitmap内存复用池里面取出来
     * @param width
     * @param height
     * @param config
     * @return
     */
    Bitmap get(int width, int height, Bitmap.Config config);

}
