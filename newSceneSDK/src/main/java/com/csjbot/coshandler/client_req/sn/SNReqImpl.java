package com.csjbot.coshandler.client_req.sn;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnDeviceInfoListener;
import com.csjbot.coshandler.listener.OnSNListener;

/**
 * Created by xiasuhuei321 on 2017/10/23.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public class SNReqImpl extends BaseClientReq implements ISNReq {

    @Override
    public void getSN(OnSNListener listener) {
        CsjRobot.getInstance().setOnSNListener(listener);
        sendReq(getJson(REQConstants.GET_SN_REQ));
    }

    @Override
    public void getDeviceList(OnDeviceInfoListener listener) {
        CsjRobot.getInstance().setOnDeviceInfoListener(listener);
        sendReq(getJson(REQConstants.GET_LOCAL_DEVICE_REQ));
    }

    @Override
    public void setSN(String sn) {
        sendReq(getJson(REQConstants.SET_SN_REQ, "sn", sn));
    }
}
