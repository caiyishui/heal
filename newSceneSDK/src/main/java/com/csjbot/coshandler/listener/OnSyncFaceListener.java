package com.csjbot.coshandler.listener;

/**
 * Created by xiasuhuei321 on 2017/11/20.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public interface OnSyncFaceListener {
    void response(int errorCode);
    void complete(int errorCode);
}
