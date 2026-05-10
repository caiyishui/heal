package com.csjbot.coshandler.listener;

public interface OnElevatorStateListener {
    void openState(int error);

    void getRobotid(String robotid);

    void getElevatorInfo(int min, int max);
}
