package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.global.RSPConstants;
import com.csjbot.coshandler.log.CsjlogProxy;

/**
 * Created by jingwc on 2017/10/25.
 */

public class RbRobotState extends RbBase {
    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {
        CosLogger.info("收到消息"+dataSource);
    }
    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {
        switch (msgId) {
            case RSPConstants.ROBOT_GET_BATTERY_RSP:
                // TODO: 2018/05/19 0019 是否有更加好的办法
                int errorCode = getIntSingleField(dataSource, "error_code");
                if (errorCode == 0) {
                    int battery = getIntSingleField(dataSource, "battery");
                    CsjRobot.getInstance().pushBattery(battery);
                } else {
                    CsjlogProxy.getInstance().error(RSPConstants.ROBOT_GET_BATTERY_RSP + " error , code is " + errorCode);
                }
                break;

            case RSPConstants.GET_EMERGENCY_STATUS_RSP:
                CsjRobot.getInstance().pushEmergencyStatus(getIntSingleField(dataSource, "status"));
                break;
        }
    }
}
