package com.demo.csjbot.csjsdkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;

import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.listener.OnConnectListener;
import com.demo.csjbot.csjsdkdemo.future.BaseActivity;

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //没有权限则申请权限
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        if (CsjRobot.getInstance().getState().isConnect()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            CsjRobot.getInstance().registerConnectListener(new OnConnectListener() {
                @Override
                public void success() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

                @Override
                public void faild() {
                    Log.d("TAG", "registerConnectListener faild!");
                }

                @Override
                public void timeout() {
                    Log.d("TAG", "registerConnectListener timeout!");
                }

                @Override
                public void disconnect() {
                    Log.d("TAG", "registerConnectListener disconnect!");
                }
            });
        }
    }

    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 111);
            } else {

            }
        }
    }
}
