package com.csjbot.coshandler.listener;

/**
 * Created by jingwc on 2017/9/5.
 */

public interface OnFaceListener {
    void personInfo(String json);
    void personNear(boolean person);
}
