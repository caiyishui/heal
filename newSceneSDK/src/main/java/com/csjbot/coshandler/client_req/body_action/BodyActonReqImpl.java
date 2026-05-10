package com.csjbot.coshandler.client_req.body_action;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnDoubleDoorStateListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 肢体动作实现类
 * Created by jingwc on 2017/8/14.
 */

public class BodyActonReqImpl extends BaseClientReq implements IBodyActionReq {

    @Override
    public void reset() {
        sendReq(getBodyActionJson(CmdConstants.ROBOT_BODY_CTRL_CMD, 1, 1));
    }

    @Override
    public void actionNew(int part, int direction, int angle, int speed) {
        sendReq(getNewBodyActionJson(CmdConstants.ROBOT_BODY_CTRL_CMD, part, direction, angle, speed));
    }

    @Override
    public void action(int bodyPart, int action) {
        sendReq(getBodyActionJson(CmdConstants.ROBOT_BODY_CTRL_CMD, bodyPart, action));
    }

    @Override
    public void actionV2(String cmd, int angle) {
        sendReq(getAliceActionCtrlJson(cmd, angle));
    }

    @Override
    public void CustomerCtrlV2(int headLeft, int headUp, int lefthand, int righthand) {
        sendReq(getAliceActionCustomerCtrlJson(CmdConstants.ALICE_CUSTOMER_CTRL, headLeft, headUp, lefthand, righthand));
    }

    @Override
    public void resetV2() {
        sendReq(getJson(REQConstants.ALICE_NEW_ACTION_CTRL_RESET_REQ));
    }

    @Override
    public void startWaveHands(int intervalTime) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("msg_id", REQConstants.BodyAction.ROBOT_ARM_LOOP_START_REQ);
            jo.put("interval_time", intervalTime);

            sendReq(jo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopWaveHands() {
        sendReq(getJson(REQConstants.BodyAction.ROBOT_ARM_LOOP_STOP_REQ));
    }

    @Override
    public void startDance() {
        sendReq(getJson(REQConstants.ROBOT_DANCE_START_REQ));
    }

    @Override
    public void stopDance() {
        sendReq(getJson(REQConstants.ROBOT_DANCE_STOP_REQ));
    }

    @Override
    public void openDoubleDoor(OnDoubleDoorStateListener listener) {
        if (listener != null) {
            CsjRobot.getInstance().setOnDoubleDoorStateListener(listener);
        }
        sendReq(getJson(REQConstants.DOUBLE_DOORS_CONTROL, "open", true));
    }

    @Override
    public void closeDoubleDoor(OnDoubleDoorStateListener listener) {
        if (listener != null) {
            CsjRobot.getInstance().setOnDoubleDoorStateListener(listener);
        }
        sendReq(getJson(REQConstants.DOUBLE_DOORS_CONTROL, "open", false));
    }

    @Override
    public void getDoubleDoorState(OnDoubleDoorStateListener listener) {
        if (listener != null) {
            CsjRobot.getInstance().setOnDoubleDoorStateListener(listener);
        }
        sendReq(getJson(REQConstants.DEVICE_DOOR_STATUS_REQ));
    }
}
