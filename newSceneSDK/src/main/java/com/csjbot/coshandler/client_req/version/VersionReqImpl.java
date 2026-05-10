package com.csjbot.coshandler.client_req.version;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnGetVersionListener;
import com.csjbot.coshandler.listener.OnUpgradeListener;

/**
 * Created by jingwc on 2017/11/8.
 */

public class VersionReqImpl extends BaseClientReq implements IVersionReq {

    @Override
    public void getVersion(OnGetVersionListener listener) {
        CsjRobot.getInstance().setOnGetVersionListener(listener);
        sendReq(getJson(REQConstants.GET_VERSION_REQ));
    }

    @Override
    public void softwareCheck(OnUpgradeListener listener) {
        CsjRobot.getInstance().setOnUpgradeSoftwareCheckListener(listener);
        sendReq(getJson(REQConstants.UPGRADE_CHECK_REQ));
    }

    @Override
    public void softwareUpgrade(OnUpgradeListener listener) {
        CsjRobot.getInstance().setOnUpgradeSoftwareUpgradeListener(listener);
        sendReq(getJson(REQConstants.UPGRADE_TOTAL_REQ));
    }
}
