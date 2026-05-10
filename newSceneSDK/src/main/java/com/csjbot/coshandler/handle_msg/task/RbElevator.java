package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.global.RSPConstants;

import org.json.JSONObject;

public class RbElevator extends RbBase {
    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {
        switch (msgId) {
            case NTFConstants.ROBOT_ELEVATOR_CTRL_NTF:
                int error_code = getIntSingleField(dataSource, "error_code");
                String cmd_type = getSingleField(dataSource, "cmd_type");
                String result = getSingleField(dataSource, "result");
                CsjRobot.getInstance().pushElevatorCtrl(error_code, cmd_type, result);
                break;
            default:
                break;
        }
    }

    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {
        switch (msgId) {
            case RSPConstants.ROBOT_ELEVATOR_INFO_RSP:
                int min = getIntSingleField(dataSource, "min");
                int max = getIntSingleField(dataSource, "max");
                CsjRobot.getInstance().pushElevatorInfo(min, max);
                break;
            case RSPConstants.ROBOT_ELEVATOR_OPEN_RSP:
                int error_code = getIntSingleField(dataSource, "error_code");
                CsjRobot.getInstance().pushElevatorOpenState(error_code);
                break;
            case RSPConstants.ROBOT_ELEVATOR_CTRL_RSP:
                break;
            default:
                break;
        }

    }
}
