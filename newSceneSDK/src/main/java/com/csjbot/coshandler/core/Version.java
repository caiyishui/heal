package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.version.IVersionReq;
import com.csjbot.coshandler.listener.OnGetVersionListener;
import com.csjbot.coshandler.listener.OnUpgradeListener;

/**
 * Created by Administrator on 2019/7/15.
 */
public class Version implements IVersionReq {

    private IVersionReq versionReq;

    /**
     * Instantiates a new Version.
     */
    public Version() {
        versionReq = ReqFactory.getVersionInstance();
    }

    @Override
    public void getVersion(OnGetVersionListener listener) {
        versionReq.getVersion(listener);
    }

    @Override
    public void softwareCheck(OnUpgradeListener listener) {
        versionReq.softwareCheck(listener);
    }

    @Override
    public void softwareUpgrade(OnUpgradeListener listener) {
        versionReq.softwareUpgrade(listener);
    }
}
