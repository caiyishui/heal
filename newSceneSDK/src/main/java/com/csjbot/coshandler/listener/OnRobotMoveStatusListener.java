package com.csjbot.coshandler.listener;

/**
 * @author cll
 * @date :2021/5/19
 * 运动状态的监听，
 */
public interface OnRobotMoveStatusListener {
    void blocked();

    void waitShort();

    void waitLong();

    void onRunning();
}
