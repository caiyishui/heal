package com.csjbot.coshandler.listener;

/**
 * web端控制机器人导览，上一个点  下一个点   暂停等指令监听
 * @author cll
 * @date :2022/1/4
 */
public interface OnRobotSerialPowerInfoListener {
    void onPowerInfo(int powerState, int powerValue);
}
