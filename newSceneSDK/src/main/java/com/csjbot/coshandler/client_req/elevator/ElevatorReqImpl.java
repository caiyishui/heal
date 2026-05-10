package com.csjbot.coshandler.client_req.elevator;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnElevatorCtrlListener;
import com.csjbot.coshandler.listener.OnElevatorStateListener;
import com.csjbot.coshandler.util.JsonFormatUtil;

import java.util.Locale;

public class ElevatorReqImpl extends BaseClientReq implements IElevatorReq {
    @Override
    public void openElevatorModule(OnElevatorStateListener listener) {
        CsjRobot.getInstance().setOnElevatorStateListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_OPEN_REQ));
    }

    @Override
    public void getElevatorInfo(int elevatorId, OnElevatorStateListener listener) {
        CsjRobot.getInstance().setOnElevatorStateListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_INFO_REQ, "elevator_id", elevatorId));
    }

    @Override
    public void callInfo(int elevatorId, int currentFloor, int targetFloor, OnElevatorCtrlListener listener) {
        CsjRobot.getInstance().setOnElevatorCtrlListener(listener);
        String json = "{\"msg_id\":\"%s\",\"cmd_type\":\"%s\"," +
                "\"elevator_id\":%d,\"current_floor\":%d,\"target_floor\":%d}";
        sendReq(String.format(Locale.getDefault(), json, REQConstants.ROBOT_ELEVATOR_CTRL_REQ,
                REQConstants.RobotElevatorCtrl.CALLINFO, elevatorId, currentFloor, targetFloor));
    }

    @Override
    public void callingElevator(OnElevatorCtrlListener listener) {
        CsjRobot.getInstance().setOnElevatorCtrlListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_CTRL_REQ, "cmd_type",
                REQConstants.RobotElevatorCtrl.CALLING));
    }

    @Override
    public void enteringElevator(OnElevatorCtrlListener listener) {
        CsjRobot.getInstance().setOnElevatorCtrlListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_CTRL_REQ, "cmd_type",
                REQConstants.RobotElevatorCtrl.ENTERING));
    }

    @Override
    public void insideElevator(OnElevatorCtrlListener listener) {
        CsjRobot.getInstance().setOnElevatorCtrlListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_CTRL_REQ, "cmd_type",
                REQConstants.RobotElevatorCtrl.INSIDE));
    }

    @Override
    public void leavingElevator(OnElevatorCtrlListener listener) {
        CsjRobot.getInstance().setOnElevatorCtrlListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_CTRL_REQ, "cmd_type",
                REQConstants.RobotElevatorCtrl.LEAVING));
    }

    @Override
    public void outsideElevator(OnElevatorCtrlListener listener) {
        CsjRobot.getInstance().setOnElevatorCtrlListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_CTRL_REQ, "cmd_type",
                REQConstants.RobotElevatorCtrl.OUTSIDE));
    }

    @Override
    public void cancelElevator(OnElevatorCtrlListener listener) {
        CsjRobot.getInstance().setOnElevatorCtrlListener(listener);
        sendReq(JsonFormatUtil.SplicingSimpleJson(REQConstants.ROBOT_ELEVATOR_CTRL_REQ, "cmd_type",
                REQConstants.RobotElevatorCtrl.CANCEL));
    }
}
