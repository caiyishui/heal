// ISdkToAar.aidl
package com.csjbot.sdkhandler;

// Declare any non-default types here with import statements

interface ISdkAppToAar {
    // SDk app 发送消息到 ASR
    void sdkAppMsgToAar(String msg);

    // 双向绑定成功
    void connectToSDKSucceed();
}