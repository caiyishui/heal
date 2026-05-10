package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.custom_service.ICustomServiceReq;

public class Customer implements ICustomServiceReq {
    ICustomServiceReq iCustomServiceReq;

    Customer() {
        iCustomServiceReq = ReqFactory.getCustomServiceInstantce();
    }

    @Override
    public void callHumanService(String sn) {
        iCustomServiceReq.callHumanService(sn);
    }

    @Override
    public void hangUpHumanService(String sn) {
        iCustomServiceReq.hangUpHumanService(sn);
    }

    @Override
    public void sendHumanServiceHeartBeat(String sn) {
        iCustomServiceReq.sendHumanServiceHeartBeat(sn);
    }

    @Override
    public void humanInterventi(String sn) {
        iCustomServiceReq.humanInterventi(sn);
    }

    @Override
    public void humanHangupInterventi(String sn) {
        iCustomServiceReq.humanHangupInterventi(sn);
    }
}
