package com.csjbot.coshandler.listener;

/**
 * Created by jingwc on 2017/10/25.
 */

public interface OnRobotStateListener {
    void getBattery(int battery);
    void getCharge(int charge);

}
