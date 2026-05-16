package com.csjbot.coshandler.service;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.csjbot.asragent.aiui_soft.AIUIMixedManager;
import com.csjbot.asragent.aiui_soft.AIUIPushSpeechRecgContentListener;
import com.csjbot.asragent.aiui_soft.AiuiMixedService;
import com.csjbot.cosclient.constant.ClientConstant;
import com.csjbot.asragent.aiui_soft.listener.MsgCollentListener;
import com.csjbot.coshandler.aiui.aiui_soft.send_msg.SendMsgThread;
import com.csjbot.coshandler.aiui.aiui_soft.send_msg.http.RetrofitFactory;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.handle_msg.MessageHandleFactory;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.util.ShellUtil;
import com.csjbot.sdkhandler.IAarToSdkApp;
import com.csjbot.sdkhandler.ISdkAppToAar;
import com.iflytek.aiui.jni.AIUI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 处理消息service
 * Created by jingwc on 2017/8/11.
 */

public class HandlerMsgService extends BaseReceiveService {
    private static String[] mInterceptor;
    private static IAarToSdkApp iAarToSdkApp;
    private SendMsgThread sendMsgRunnable;
    private ExecutorService cachedExecutor;
    private static boolean useAiuiTimo = false;

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void setMsgInterceptor(String... interceptor) {
        mInterceptor = interceptor;
    }

    public static void sendMessageToSDKApp(String json) {
        if (iAarToSdkApp != null) {
            try {
                iAarToSdkApp.aarMsgToSDKApp(json);
                if (!json.contains("face") || !json.contains("FACE")) {
                    if (enableDebug) {
                        CsjlogProxy.getInstance().warn("SDK_aidl SDKAar 发送消息到 SDKApp == " + json);
                    }
                }
            } catch (RemoteException e) {
                CsjRobot.getInstance().getState().setConnect(false);
                e.printStackTrace();
            }

            if (json.contains(REQConstants.SPEECH_ISR_STOP_REQ)) {
                AIUIMixedManager.getInstance().cancelAudioRecognize();
                return;
            }

            if (json.contains(REQConstants.SPEECH_ISR_START_REQ)) {
                AIUIMixedManager.getInstance().startAudioRecognize();
                return;
            }

            if (json.contains(REQConstants.SPEECH_SERVICE_STOP_REQ)) {
                AIUIMixedManager.getInstance().closeSpeechService();
            }

            if (json.contains(REQConstants.SPEECH_SERVICE_START_REQ)) {
                AIUIMixedManager.getInstance().reInit();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void connect() {
        bindSDKService();
    }

    // 事件处理与上抛
    @Override
    protected void handleMessage(String json) {
        if (enableDebug) {
            CsjlogProxy.getInstance().info("SDK_aidl SDKAar handleMessage = " + json);
        }
        if (mInterceptor != null && mInterceptor.length > 0) {
            for (String s : mInterceptor) {
                if (json.contains(s)) {
                    CsjlogProxy.getInstance().info("interceptor " + s);
                    return;
                }
            }
        }

        MessageHandleFactory.getInstance().startWork(json);
        if (directListener != null) {
            directListener.onRecMessage(json);
        }
    }

    private AIUIPushSpeechRecgContentListener aiuiPushSpeechRecgContentListener = new AIUIPushSpeechRecgContentListener() {
        @Override
        public void pushSpeechRecgData(String text, boolean isLast) {
            sendMsgRunnable.setTextContent(text, 1000, "com.csjbot.zhanguan", isLast);
            cachedExecutor.execute(sendMsgRunnable);
        }
    };

    private final MsgCollentListener aiuiMsgCollectionListener = new MsgCollentListener() {
        @Override
        public void collentMessage(String msg) {
            handleMessage(msg);
        }
    };

    private void bindSDKService() {
        clientConfig.setEnableAsr(false);
        ShellUtil.execCmd("am force-stop com.csjbot.asragent", true, false);
        if (TextUtils.equals(clientConfig.getRobotType(), CsjRobot.RobotType.TIMO.getName())) {
            useAiuiTimo = true;
        }

        AiuiMixedService.setPushDataListener(aiuiPushSpeechRecgContentListener);
        AiuiMixedService.setMsgCollentListener(aiuiMsgCollectionListener);
        mHandler.postDelayed(() -> startService(new Intent(HandlerMsgService.this, AiuiMixedService.class)), 10000);


        if (sendMsgRunnable == null) {
            sendMsgRunnable = new SendMsgThread(this, aiuiMsgCollectionListener);
        }

        if (cachedExecutor == null) {
            cachedExecutor = Executors.newCachedThreadPool(new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread ret = new Thread(r, "AsrHandlePool");
                    ret.setDaemon(true);
                    return ret;
                }
            });
        }

        RetrofitFactory.initClient();
        CsjlogProxy.getInstance().warn("AIUI Robot is {}, AiuiMixedService", clientConfig.getRobotType());

        CsjlogProxy.getInstance().info("SDK_aidl SDKAar 开始绑定 SDKApp 服务 RobotSdkService");
        Intent intent = new Intent();
        intent.setClassName("com.csjbot.robotsdk.ten", "com.csjbot.robotsdk.service.RobotSdkService");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iAarToSdkApp = IAarToSdkApp.Stub.asInterface(service);
            CsjlogProxy.getInstance().info("SDK_aidl SDKApp 绑定成功 ComponentName = [" + name + "]");

            String remoteAppName = getRemoteAppName();
//            CsjlogProxy.getInstance().error("CSJ_SDK_REMOTE_APP_NAME = " + remoteAppName);

            if (TextUtils.isEmpty(remoteAppName)) {
                CsjlogProxy.getInstance().error("请在 manifest 中设置 metaData 字段 :CSJ_SDK_REMOTE_APP_NAME");
            }

            try {
                if (iAarToSdkApp != null) {
                    iAarToSdkApp.connectToSDK(remoteAppName);
                    CsjlogProxy.getInstance().info("SDK_aidl SDKAar 发送命令 让SDKApp连接到aar = [" + remoteAppName + "]");

                    // 注册死亡代理
                    service.linkToDeath(mDeathRecipient, 0);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            serviceConnection = null;
        }
    };

    private String getRemoteAppName() {
        try {
            ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("CSJ_SDK_REMOTE_APP_NAME");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }


    @Override
    protected void connectStatus(int type) {
        CsjRobot.getInstance().pushConnect(type);
        Log.d("CSJBOT", "class:HandlerMsgService method:connectStatus type:" + type);
    }

    @Override
    protected void sendFailed() {
        Log.d("CSJBOT", "class:HandlerMsgService method:sendFailed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends ISdkAppToAar.Stub {
        @Override
        public void sdkAppMsgToAar(String msg) throws RemoteException {
            if (!msg.contains("face") && !msg.contains("FACE")) {
                if (enableDebug) {
                    CsjlogProxy.getInstance().debug("SDK_aidl SDKAar 收到消息 == " + msg);
                }
            }
            handleMessage(msg);
        }

        @Override
        public void connectToSDKSucceed() throws RemoteException {
            connectStatus(ClientConstant.EVENT_CONNECT_SUCCESS);
            CsjlogProxy.getInstance().warn("SDK_aidl SDKAar 双向绑定成功 !!");

//            sendMessage(JsonFormatUtil.SplicingSimpleJson(CmdConstants.SET_ROBOT_TYPE_CMD, "type", clientConfig.getRobotType()));
//            sendMessage(JsonFormatUtil.SplicingSimpleJson(CmdConstants.ROBOT_SDK_AGENT_ENABLE,
//                    "asrEnable", clientConfig.isEnableAsr(),
//                    "slamEnable", clientConfig.isEnableSlam(),
//                    "faceEnable", clientConfig.isEnableFace()));
        }
    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            iAarToSdkApp.asBinder().unlinkToDeath(mDeathRecipient, 0);
//            bindSDKService();
        }
    };
}
