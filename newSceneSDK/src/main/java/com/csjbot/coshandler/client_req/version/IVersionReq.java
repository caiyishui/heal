package com.csjbot.coshandler.client_req.version;

import com.csjbot.coshandler.listener.OnGetVersionListener;
import com.csjbot.coshandler.listener.OnUpgradeListener;

/**
 * Created by jingwc on 2017/11/8.
 */

public interface IVersionReq {
    void getVersion(OnGetVersionListener listener);

    void softwareCheck(OnUpgradeListener listener);

    void softwareUpgrade(OnUpgradeListener listener);
}
