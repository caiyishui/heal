package com.csjbot.coshandler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;



import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.widget.ShowLogView;
import com.csjbot.coshandler.widget.ShowPingView;

/**
 * Created by jingwc on 2017/11/17.
 */

public class CsjlogService extends Service {

    WindowManager mWindowManager;

    WindowManager.LayoutParams wmLogParams;

    WindowManager.LayoutParams wmPingParams;

    ShowLogView mShowLogView;

    ShowPingView mShowPingView;

    LogReceiver mLogReceiver;

    PingReceiver mPingReceiver;

    boolean isShowLog;


    @Override
    public void onCreate() {
        super.onCreate();
        isShowLog = true;
        CsjlogProxy.isShowPingWindow = true;
        createFloatPingView();
        registerLogReceiver();
        registerPingReceiver();

        mShowPingView.tvPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowLog) {
                    createFloatView();
                    isShowLog = false;
                } else {
                    removeFloatView();
                    isShowLog = true;
                }
            }
        });
    }

    private void createFloatPingView() {
        mShowPingView = new ShowPingView(getApplicationContext());
        wmPingParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        //设置window type
        wmPingParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmPingParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmPingParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmPingParams.gravity = Gravity.END | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmPingParams.x = 85;
        wmPingParams.y = 0;

        // 设置悬浮窗口长宽数据
        wmPingParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmPingParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowManager.addView(mShowPingView, wmPingParams);
    }

    private void createFloatView() {
        mShowLogView = new ShowLogView(getApplicationContext());
        wmLogParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        //设置window type
        wmLogParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmLogParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmLogParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmLogParams.gravity = Gravity.START | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmLogParams.x = 0;
        wmLogParams.y = 0;


        // 设置悬浮窗口长宽数据
        wmLogParams.width = 600;
        wmLogParams.height = 1080;
        mShowLogView.setParams(wmLogParams);
        mWindowManager.addView(mShowLogView, wmLogParams);

        CsjlogProxy.isShowLogWindow = true;

    }

    private void removeFloatView() {
        if (mShowLogView != null) {
            //移除悬浮窗口
            mWindowManager.removeView(mShowLogView);
        }
    }

    private void removeFloatPingView() {
        if (mShowPingView != null) {
            //移除悬浮窗口
            mWindowManager.removeView(mShowPingView);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        CsjlogProxy.isShowPingWindow = false;
        CsjlogProxy.isShowLogWindow = false;
        removeFloatView();
        removeFloatPingView();
        unregisterLogReceiver();
        unregisterPingReceiver();
    }

    public void registerLogReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CsjlogProxy.LOG_ACTION);
        registerReceiver(mLogReceiver = new LogReceiver(), filter);
    }

    private void unregisterLogReceiver() {
        unregisterReceiver(mLogReceiver);
    }

    public void registerPingReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CsjlogProxy.PING_ACTION);
        registerReceiver(mPingReceiver = new PingReceiver(), filter);
    }

    private void unregisterPingReceiver() {
        unregisterReceiver(mPingReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class LogReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String log = intent.getStringExtra(CsjlogProxy.LOG_CONTENT);
                int color = intent.getIntExtra(CsjlogProxy.LOG_COLOR, 0);
                if (mShowLogView != null) {
                    mShowLogView.addLogMsg(log, color);
                }
            }
        }
    }

    class PingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String ping = intent.getStringExtra(CsjlogProxy.PING_NAME);
                if (mShowPingView != null) {
                    mShowPingView.setPing(ping);
                }
            }
        }
    }
}
