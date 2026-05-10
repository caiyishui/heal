package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.extra_func.IExtraFunctionReq;
import com.csjbot.coshandler.listener.OnGetCurrentMapImageListener;
import com.csjbot.coshandler.listener.OnHotWordsListener;

import java.util.List;

/**
 * Created by Administrator on 2019/7/15.
 */

public class Extra implements IExtraFunctionReq {

    private IExtraFunctionReq extraFunctionReq;

    Extra() {
        extraFunctionReq = ReqFactory.getExtraFunctionInstance();
    }

    @Override
    public void getHotWords(OnHotWordsListener listener) {
        extraFunctionReq.getHotWords(listener);
    }

    @Override
    public void setHotWords(List<String> hotwords) {
        extraFunctionReq.setHotWords(hotwords);
    }

    @Override
    public void startFaceFollow() {
        extraFunctionReq.startFaceFollow();
    }

    @Override
    public void stopFaceFollow() {
        extraFunctionReq.stopFaceFollow();
    }

    @Override
    public void resetRobot() {
        extraFunctionReq.resetRobot();
    }

    @Override
    public void getCurrentMapBitmap(OnGetCurrentMapImageListener listener) {
        extraFunctionReq.getCurrentMapBitmap(listener);
    }

    @Override
    public void connectMqttServer(String userName, String password, String clientId) {
        extraFunctionReq.connectMqttServer(userName, password, clientId);
    }

    @Override
    public void sendMesageToDataSocket(String json) {
        extraFunctionReq.sendMesageToDataSocket(json);
    }

    @Override
    public void sendMqttMessage(String msg) {
        extraFunctionReq.sendMqttMessage(msg);
    }

    @Override
    public void connectCustomerServers() {
        extraFunctionReq.connectCustomerServers();
    }


    @Override
    public void startDispatchKeyEvent() {
        extraFunctionReq.startDispatchKeyEvent();
    }

    @Override
    public void connectScanWebSocket(String type, String sn) {
        extraFunctionReq.connectScanWebSocket(type, sn);
    }

    @Override
    public void connectDataWebSocket(String type, String sn) {
        extraFunctionReq.connectDataWebSocket(type, sn);
    }
}
