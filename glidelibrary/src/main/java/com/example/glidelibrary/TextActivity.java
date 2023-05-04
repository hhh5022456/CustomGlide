package com.example.glidelibrary;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
*
* $author Handy
* created at 2023/5/4 14:20
 * * 示例
*/
public class TextActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSIONS = 123;
    private void requestPermissionsIfNeeded() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, continue with your operation
            } else {
                // Permissions denied, show a message or disable features that require these permissions
            }
        }
    }
    private ImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        requestPermissionsIfNeeded();
//        imageView1 = findViewById(R.id.image1);
//        imageView2 = findViewById(R.id.image2);
//        imageView3 = findViewById(R.id.image3);
        requestPermissionsIfNeeded();
        Glide.with(this).load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg").into(imageView1);

    }

    // 加载此图片：https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg
    public void t1(View view) {
    }

    public void t2(View view) {
        Glide.with(this).load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg").into(imageView2);
    }

    public void t3(View view) {
        Glide.with(this).load("https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg").into(imageView3);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
