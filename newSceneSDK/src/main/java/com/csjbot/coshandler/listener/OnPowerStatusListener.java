package com.csjbot.coshandler.listener;

/**
 * @author cll
 * @date :2022/8/8
 */
public interface OnPowerStatusListener {
    //未按下
    void onNotPressed();

    //按下
    void onPressed();

    //长按一秒
    void onLongPressOne();

    //长按5秒  该监听不会收到   5秒就直接关机了
    void onLongPressFive();
}
