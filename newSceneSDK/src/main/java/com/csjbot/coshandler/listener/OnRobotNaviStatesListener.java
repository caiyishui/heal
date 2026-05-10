package com.csjbot.coshandler.listener;

public interface OnRobotNaviStatesListener {
    //第一个参数：表示导航是否已经准备好，第二个参数表示当前导航的运动模式：-1(左转)， 0(直行)， 1(右转)
    void onNavi(boolean ready, int motion_mode);
}
