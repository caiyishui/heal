package com.csjbot.coshandler.client_req.custom_service;

import android.util.Log;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.log.CsjlogProxy;

import java.util.Locale;

/**
 * Copyright (c) 2018, SuZhou CsjBot. All Rights Reserved.
 * www.csjbot.com
 * <p>
 * Created by 浦耀宗 at 2018/03/27 0027-10:57.
 * Email: puyz@csjbot.com
 */

public class CustomServiceImpl extends BaseClientReq implements ICustomServiceReq {

    @Override
    public void callHumanService(String sn) {
        String json = "{\"msg_id\":\"REQ_CALL_HUMAN_SERVICE\",\"sid\":%s,\"sn\":\"%s\"}";
        json = String.format(Locale.getDefault(), json, System.currentTimeMillis(), sn);
        CsjlogProxy.getInstance().debug("callHumanService : " + json);
        sendReq(json);
    }


    @Override
    public void hangUpHumanService(String sn) {
        String json = "{\"msg_id\": \"REQ_HANGUP_HUMAN_SERVICE\", \"sid\":%s}";
        json = String.format(Locale.getDefault(),json,System.currentTimeMillis());
        CsjlogProxy.getInstance().debug("callHumanService : " + json);
        sendReq(json);
    }

    @Override
    public void sendHumanServiceHeartBeat(String sn) {
        String json = "{\"msg_id\": \"REQ_HUMAN_SERVICE_STATUS\", \"sid\":%s, \"sn\": \"%s\", \"status\": 200}";
        json = String.format(Locale.getDefault(), json, System.currentTimeMillis(), sn);
        CsjlogProxy.getInstance().debug("sendHumanServiceHeartBeat : " + json);
        sendReq(json);
    }

    @Override
    public void humanInterventi(String sn) {
        String json = "{\"msg_id\": \"REQ_HUMAN_INTERVENTI\", \"sid\":%s}";
        json = String.format(Locale.getDefault(), json, System.currentTimeMillis());
        CsjlogProxy.getInstance().debug("humanInterventi : " + json);
        sendReq(json);
    }

    @Override
    public void humanHangupInterventi(String sn) {
        String json = "{\"msg_id\": \"REQ_HANGUP_HUMAN_INTERVENTI\", \"sid\":%s}";
        json = String.format(Locale.getDefault(), json, System.currentTimeMillis());
        CsjlogProxy.getInstance().debug("humanHangupInterventi : " + json);
        sendReq(json);
    }
}
