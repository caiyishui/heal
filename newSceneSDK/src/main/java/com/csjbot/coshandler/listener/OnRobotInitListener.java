package com.csjbot.coshandler.listener;

public interface OnRobotInitListener {
    void onBasicInfoState(int state, String code);

    void onServerConnectState(int state, String code);

    void onHardWareHealthState(int state, String code);

    void onSlamState(int state, String code);

    void onSoftWareState(int state, String code);
}
