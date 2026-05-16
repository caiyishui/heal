package com.demo.csjbot.csjsdkdemo;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import android.util.Log;

import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.listener.OnAuthenticationListener;
import com.demo.csjbot.csjsdkdemo.utils.SharedPreferencesSDCard;

/**
 * Created by Administrator on 2019/7/13.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "Authorization start!");
//        CsjRobot.authentication(this, "e19811c5-ad7f-4544-bca1-9f4c75e2539c", "39DF1D9393DA62389DDD34E0F49BF29A", new OnAuthenticationListener() {
        CsjRobot.authentication(this, "39DF1D9393DA62389DDD34E0F49BF29A", "e19811c5-ad7f-4544-bca1-9f4c75e2539c", new OnAuthenticationListener() {
            @Override
            public void success() {
                Log.d("TAG", "Authorization succeeded!");
            }

            @Override
            public void error() {
                Log.d("TAG", "privilege grant failed!");
            }
        });

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 是否启用语音模块(默认开启)  #####在init之前调用
        // Enable voice module (enabled by default) ##### Call before init
        CsjRobot.enableAsr(true);
        // 是否启用人脸识别模块
        // Whether to enable face recognition module
        CsjRobot.enableFace(true);
        // 是否启用导航模块
        // Is the navigation module enabled
        CsjRobot.enableSlam(true);
        // 设置通信的地址，默认是192.168.99.101， 60002
        // Set the address of communication. The default is 192.168.99.10160002
//        CsjRobot.setIpAndrPort("127.0.0.1", 60002);

        /*
         * 设置机器人类型(在init之前调用)
         * 如果设置了ALICE，则为第八代机器人的协议
         *
         * Set robot type (called before init)
         * If ALICE is set, it is the protocol of the eighth generation robot
         */
        CsjRobot.setRobotType(CsjRobot.RobotType.TIMO);

        /*
         * 初始化SDK
         */
        CsjRobot.getInstance().init(this);

        SharedPreferencesSDCard.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
