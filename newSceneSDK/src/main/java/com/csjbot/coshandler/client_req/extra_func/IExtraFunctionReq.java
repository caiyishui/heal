package com.csjbot.coshandler.client_req.extra_func;

import com.csjbot.coshandler.listener.OnGetCurrentMapImageListener;
import com.csjbot.coshandler.listener.OnHotWordsListener;

import java.util.List;

/**
 * Copyright (c) 2018, SuZhou CsjBot. All Rights Reserved.
 * www.csjbot.com
 * <p>
 * Created by 浦耀宗 at 2018/05/23 0023-15:22.
 * Email: puyz@csjbot.com
 */

public interface IExtraFunctionReq {
    void getHotWords(OnHotWordsListener listener);

    void setHotWords(List<String> hotwords);


    void startFaceFollow();

    void stopFaceFollow();

    /**
     * 恢复出厂设置
     */
    void resetRobot();

    void getCurrentMapBitmap(OnGetCurrentMapImageListener listener);

    void connectMqttServer(String userName, String password, String clientId);

    //往数据用的websocket发送消息
    void sendMesageToDataSocket(String json);

    void sendMqttMessage(String msg);

    //连接人工客服服务
    void connectCustomerServers();


    void startDispatchKeyEvent();

    /**
     * 扫图用的webSocket
     *
     * @param type
     * @param sn
     */
    void connectScanWebSocket(String type, String sn);

    /**
     * 收发数据用的webSocket  例如电量等信息
     *
     * @param type
     * @param sn
     */
    void connectDataWebSocket(String type, String sn);
}
