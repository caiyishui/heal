package com.csjbot.coshandler.client_req.robot_state;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnDetectPersonListener;
import com.csjbot.coshandler.listener.OnEmergencyStatusListener;
import com.csjbot.coshandler.listener.OnPowerStatusListener;
import com.csjbot.coshandler.listener.OnRobotTypeListener;
import com.csjbot.coshandler.listener.OnMotoOverloadListener;
import com.csjbot.coshandler.listener.OnRobotStateListener;
import com.csjbot.coshandler.listener.OnWarningCheckSelfListener;

import java.util.Locale;

/**
 * Created by jingwc on 2017/10/25.
 */

public class RobotStateReqImpl extends BaseClientReq implements IRobotStateReq {
    @Override
    public void shutdown() {
        sendReq(getJson(REQConstants.ROBOT_SHUTDOWN_REQ));
    }

    @Override
    public void reboot() {
        sendReq(getJson(REQConstants.ROBOT_REBOOT_REQ));
    }

    @Override
    public void getBattery(OnRobotStateListener listener) {
        CsjRobot.getInstance().setOnRobotStateBatteryListener(listener);
        sendReq(getJson(REQConstants.ROBOT_GET_BATTERY_REQ));
    }

    @Override
    public void getCharge(OnRobotStateListener listener) {
        CsjRobot.getInstance().setOnRobotStateChargeListener(listener);
        sendReq(getJson(REQConstants.ROBOT_GET_CHARGE_REQ));
    }

    @Override
    public void checkSelf(OnWarningCheckSelfListener listener) {
        CsjRobot.getInstance().setOnWarningCheckSelfListener(listener);
        sendReq(getJson(REQConstants.WARNING_CHECK_SELF_REQ));
    }

    /**
     * 获取硬件版本
     */
    @Override
    public void getRobotHWVersion() {
        sendReq(getJson(REQConstants.GET_HARDWARE_INFO_REQ));
    }

    /**
     * 获取思岚版本
     */
    @Override
    public void getSlamVersion() {
        sendReq(getJson(REQConstants.GET_SLAM_VERSION_REQ));
    }

    @Override
    public void getRobotType(OnRobotTypeListener robotTypeListener) {
        CsjRobot.getInstance().setOnRobotTypeListener(robotTypeListener);
        sendReq(getJson(REQConstants.GET_ROBOT_TYPE_REQ));
    }

    /**
     * 设置Linux储存的机器人硬件类型，默认是迎宾
     *
     * @param type
     */
    @Override
    public void setRobotType(String type) {
        sendReq(getJson(CmdConstants.SET_ROBOT_TYPE_CMD, "type", type));
    }

    @Override
    public void setTimezone(String timeOffset, String region) {
        String json = "{\"msg_id\":\"" + CmdConstants.SET_TIMEZONE_CMD + "\"" +
                ",\"time_offset\":\"" + timeOffset + "\"" +
                ",\"region\":\"" + region + "\"}";
        sendReq(json);
    }

    @Override
    public void makeSessionId() {
        sendReq(getJson(REQConstants.ROBOT_MAKE_SESSIONID_REQ));
    }

    @Override
    public void getPerson(OnDetectPersonListener listener) {
        CsjRobot.getInstance().setOnDetectPersonListeners(listener);
        sendReq(getJson(REQConstants.DEVICE_DETECT_PERSON_NEAR_REQ));
    }

    @Override
    public void setLinuxServerAddr(String addr, String port) {
        String json = "{\"msg_id\":\"SET_SERVERINFO_CMD\",\"ip\":\"%s\",\"port\":\"%s\"}";
        sendReq(String.format(Locale.getDefault(), json, addr, port));
    }

    @Override
    public void getMotoOverloadState(OnMotoOverloadListener listener) {
        CsjRobot.getInstance().setOnMotoOverloadListener(listener);
        sendReq(getJson(REQConstants.GET_MOTOR_OVERLOAD_STATUS_REQ));
    }

    @Override
    public void clearMotoOverload() {
        sendReq(getJson(REQConstants.MOTOR_OVERLOAD_CLEAR_REQ));
    }

    @Override
    public void getEmergencyStatus(OnEmergencyStatusListener listener) {
        CsjRobot.getInstance().setOnEmergencyStatusListener(listener);
        sendReq(getJson(REQConstants.GET_EMERGENCY_STATUS_REQ));
    }

    @Override
    public void getSlamHealth() {
        sendReq(getJson(REQConstants.GET_SENSOR_HEALTH_REQ));
    }

    @Override
    public void setXRobotTime(String year, String month, String day, String hour, String minute, String second, String week) {
        sendReq(getXRobotTimeJson(year, month, day, hour, minute, second, week));
    }

    @Override
    public void timingPowerOffForXRobot(boolean isOpen, String year, int month, int day, int hour, int minute, int second) {
        sendReq(getXTimingRobotJson(isOpen, year, month, day, hour, minute, second));
    }

    @Override
    public void doubleDoorOpenControl() {
        sendReq(getDoorCtrlJson(true, 0));
    }

    @Override
    public void doubleDoorCloseControl() {
        sendReq(getDoorCtrlJson(false, 0));
    }

    @Override
    public void openOneFloorDoor() {
        sendReq(getDoorCtrlJson(true, 1));
    }

    @Override
    public void closeOneFloorDoor() {
        sendReq(getDoorCtrlJson(false, 1));
    }

    @Override
    public void openTwoFloorDoor() {
        sendReq(getDoorCtrlJson(true, 2));
    }

    @Override
    public void closeTwoFloorDoor() {
        sendReq(getDoorCtrlJson(false, 2));
    }

    @Override
    public void getDoorStatus() {
        sendReq(getJson(REQConstants.DEVICE_DOOR_STATUS_REQ));
    }

    @Override
    public void releaseEmergency() {
        sendReq(getJson(REQConstants.RELEASE_EMERGENCY_REQ));
    }

}
