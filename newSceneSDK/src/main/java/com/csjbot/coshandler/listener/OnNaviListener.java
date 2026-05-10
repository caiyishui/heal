package com.csjbot.coshandler.listener;


/**
 * Created by jingwc on 2019/7/6.
 */

public interface OnNaviListener {

    void moveResult(String json);

    /**
     * 移动到某点的消息是否下发成功
     */
    void messageSendResult(String json);

    /**
     * 取消任务是否成功
     */
    void cancelResult(String json);

    void goHome();
}
