package com.csjbot.coshandler.client_req.config;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnMicroVolumeListener;

/**
 * Created by jingwc on 2017/11/20.
 */

public class ConfigReqImpl extends BaseClientReq implements IConfigReq {

    @Override
    public void setMicroVolume(int volume) {
        sendReq(getJson(REQConstants.SET_MICRO_VOLUME_REQ,"volume",volume));
    }

    @Override
    public void getMicroVolume(OnMicroVolumeListener listener) {
        CsjRobot.getInstance().setOnMicroVolumeListener(listener);
        sendReq(getJson(REQConstants.GET_MICRO_VOLUME_REQ));
    }
}
