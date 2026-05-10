package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.listener.OnBodyCtrlListener;

/**
 * Created by jingwc on 2017/9/20.
 */

public class RbBody extends RbBase {
    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {
        CosLogger.debug("handleNTFMessage-->" + msgId);
        switch (msgId) {
            case NTFConstants.ROBOT_BODY_TOUCH_NTF:
                CsjRobot.getInstance().pushHeadTouch();
                break;
            case NTFConstants.ROBOT_BODY_CTRL_NTF:
                CsjRobot.getInstance().pushBodyCtrl();
                break;
        }
    }

    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {
        CosLogger.debug("handleRSPMessage-->" + msgId);
    }
}
