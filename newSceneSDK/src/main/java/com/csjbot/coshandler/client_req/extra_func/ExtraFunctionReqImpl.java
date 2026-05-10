package com.csjbot.coshandler.client_req.extra_func;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnGetCurrentMapImageListener;
import com.csjbot.coshandler.listener.OnHotWordsListener;

import java.util.List;

/**
 * Copyright (c) 2018, SuZhou CsjBot. All Rights Reserved.
 * www.csjbot.com
 * <p>
 * Created by 浦耀宗 at 2018/05/23 0023-15:25.
 * Email: puyz@csjbot.com
 */

public class ExtraFunctionReqImpl extends BaseClientReq implements IExtraFunctionReq {


    @Override
    public void getHotWords(OnHotWordsListener listener) {
        CsjRobot.getInstance().setOnHotWordsListener(listener);
        sendReq(getJson(REQConstants.SPEECH_GET_USERWORDS_REQ));
    }

    @Override
    public void setHotWords(List<String> hotwords) {
        sendReq(getHotWordJson(REQConstants.SPEECH_SET_USERWORDS_REQ, hotwords));
    }

    @Override
    public void startFaceFollow() {
        sendReq(getJson(REQConstants.FACE_FOLLOW_START_REQ));
    }

    @Override
    public void stopFaceFollow() {
        sendReq(getJson(REQConstants.FACE_FOLLOW_CLOSE_REQ));
    }

    @Override
    public void resetRobot() {
        sendReq(getJson("RESET_ROBOT_REQ"));
    }

    @Override
    public void getCurrentMapBitmap(OnGetCurrentMapImageListener listener) {
        CsjRobot.getInstance().setOnGetCurrentMapImageListener(listener);
        sendReq(getJson(REQConstants.GET_CURRENT_MAP_IMAGE_REQ));
    }

    @Override
    public void connectMqttServer(String userName, String password, String clientId) {
        sendReq(getJson(REQConstants.MQTT_CLIENT_START_REQ, "USER_NAME", userName,
                "PASS_WORD", password, "CLIENT_ID", clientId));
    }

    @Override
    public void sendMesageToDataSocket(String json) {
        sendReq(getSendDataJson(json));
    }

    @Override
    public void sendMqttMessage(String msg) {
        sendReq(getMQTTSendDataJson(msg));
    }

    @Override
    public void connectCustomerServers() {
        sendReq(getJson(REQConstants.START_CUSTOMER_SERVERS_REQ));
    }

    @Override
    public void startDispatchKeyEvent() {
        sendReq(getJson(REQConstants.REMOTE_CTRL_OPEN_REQ));
    }

    @Override
    public void connectScanWebSocket(String type, String sn) {
        sendReq(getScanWebSocketJson(type, sn));
    }

    @Override
    public void connectDataWebSocket(String type, String sn) {
        sendReq(getDataWebSocketJson(type, sn));
    }
}
