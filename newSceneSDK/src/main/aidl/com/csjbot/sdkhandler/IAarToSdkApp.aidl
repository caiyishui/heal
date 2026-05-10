// ISDKAidlInterface.aidl
package com.csjbot.sdkhandler;

// Declare any non-default types here with import statements

interface IAarToSdkApp {
    // 客户端连接并且启动服务
    // 客户端连接并且启动服务
    void connectToSDK(String appName);

    void aarMsgToSDKApp(String message);
}