package com.csjbot.coshandler.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.listener.OnCameraListener;
import com.csjbot.coshandler.log.CsjlogProxy;
@Deprecated
public class CameraService extends Service implements OnCameraListener {
    public CameraService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CsjlogProxy.getInstance().debug("class:CameraService method:onCreate");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }, 10000);
    }

    private void connect() {
        CsjRobot.getInstance().registerCameraListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void response(Bitmap bitmap) {
        if (bitmap != null) {
            CsjRobot.getInstance().pushCamera(bitmap);
        }
    }
}
