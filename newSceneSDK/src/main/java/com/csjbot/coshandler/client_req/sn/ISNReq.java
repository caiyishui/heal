package com.csjbot.coshandler.client_req.sn;

import com.csjbot.coshandler.listener.OnDeviceInfoListener;
import com.csjbot.coshandler.listener.OnSNListener;

/**
 * Created by xiasuhuei321 on 2017/10/23.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public interface ISNReq {
    void getSN(OnSNListener listener);

    void getDeviceList(OnDeviceInfoListener listener);

    void setSN(String sn);
}
