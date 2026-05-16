package com.csjbot.coshandler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;



import com.csjbot.cosclient.CosClientAgent;
import com.csjbot.cosclient.constant.ClientConstant;
import com.csjbot.cosclient.entity.CommonPacket;
import com.csjbot.cosclient.entity.MessagePacket;
import com.csjbot.cosclient.listener.ClientEvent;
import com.csjbot.cosclient.listener.EventListener;
import com.csjbot.cosclient.utils.ClientConfig;
import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.BuildConfig;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.core.interfaces.DirectListener;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.global.ConnectConstants;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.util.JsonFormatUtil;

/**
 * 负责和底层通信的service
 * Created by jingwc on 2017/8/11.
 */

public abstract class BaseReceiveService extends Service {
    protected static ClientConfig clientConfig = new ClientConfig();
    protected static DirectListener directListener = null;

    public static void setEnableDebug(boolean enableDebug) {
        BaseReceiveService.enableDebug = enableDebug;
    }

    protected static boolean enableDebug = BuildConfig.DEBUG;

    public static void setDirectListener(DirectListener listener) {
        directListener = listener;
    }

    public static void setConfig(ClientConfig config) {
        clientConfig = config;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connect();
    }

    /**
     * 与底层建立连接
     */
    protected abstract void connect();


    /**
     * 处理消息
     *
     * @param json
     */
    protected abstract void handleMessage(String json);

    /**
     * 连接状态
     *
     * @param type
     */
    protected abstract void connectStatus(int type);

    /**
     * 发送失败
     */
    protected abstract void sendFailed();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
