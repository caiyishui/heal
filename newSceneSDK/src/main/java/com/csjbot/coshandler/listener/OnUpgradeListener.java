package com.csjbot.coshandler.listener;

/**
 * Created by jingwc on 2018/1/17.
 */

public interface OnUpgradeListener {
    void checkRsp(int errorCode);

    void upgradeRsp(int errorCode);

    void upgradeProgress(int downloadProgress);
}
