package com.csjbot.coshandler.client_req.config;

import com.csjbot.coshandler.listener.OnMicroVolumeListener;

/**
 * Created by jingwc on 2017/11/20.
 */

public interface IConfigReq {

    void setMicroVolume(int volume);

    void getMicroVolume(OnMicroVolumeListener listener);
}
