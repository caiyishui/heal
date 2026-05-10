package com.csjbot.coshandler.listener;

/**
 * Created by jingwc on 2017/10/25.
 */
public interface OnRobotDockStateListener {
    int STATE_ON_DOCK = 1;
    int STATE_NOT_ON_DOCK = 0;

    /**
     * Gets dock state.
     *
     * @param state the state : state = 1  on dock; other not
     */
    void getDockState(int state);
}
