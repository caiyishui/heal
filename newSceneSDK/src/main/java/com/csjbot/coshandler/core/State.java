package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.config.IConfigReq;
import com.csjbot.coshandler.client_req.robot_state.IRobotStateReq;
import com.csjbot.coshandler.client_req.sn.ISNReq;
import com.csjbot.coshandler.listener.OnDetectPersonListener;
import com.csjbot.coshandler.listener.OnDeviceInfoListener;
import com.csjbot.coshandler.listener.OnEmergencyStatusListener;
import com.csjbot.coshandler.listener.OnRobotTypeListener;
import com.csjbot.coshandler.listener.OnMicroVolumeListener;
import com.csjbot.coshandler.listener.OnMotoOverloadListener;
import com.csjbot.coshandler.listener.OnRobotStateListener;
import com.csjbot.coshandler.listener.OnSNListener;
import com.csjbot.coshandler.listener.OnWarningCheckSelfListener;

/**
 * Created by Administrator on 2019/7/15.
 */

public class State implements IRobotStateReq, ISNReq, IConfigReq {

    private IRobotStateReq robotStateReq;
    private ISNReq snReq;
    private IConfigReq configReq;

    State() {
        robotStateReq = ReqFactory.getRobotStateInstantce();
        snReq = ReqFactory.getSnReqInstance();
        configReq = ReqFactory.getConfigReqInstance();
    }

    // 充电中
    public final static int CHARGING = 1;

    // 不在充电
    public final static int NOT_CHARGING = 0;

    // 机器人连接状态
    private boolean isConnect;

    // 机器人电量值
    private int electricity;

    // 机器人充电状态
    private int chargeState;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public int getElectricity() {
        return electricity;
    }

    void setElectricity(int electricity) {
        this.electricity = electricity;
    }

    public int getChargeState() {
        return chargeState;
    }

    void setChargeState(int chargeState) {
        this.chargeState = chargeState;
    }

    @Override
    public void shutdown() {
        robotStateReq.shutdown();
    }

    @Override
    public void reboot() {
        robotStateReq.reboot();
    }

    @Override
    public void getBattery(OnRobotStateListener listener) {
        robotStateReq.getBattery(listener);
    }

    @Override
    public void getCharge(OnRobotStateListener listener) {
        robotStateReq.getCharge(listener);
    }

    @Override
    public void checkSelf(OnWarningCheckSelfListener listener) {
        robotStateReq.checkSelf(listener);
    }

    @Override
    public void getRobotHWVersion() {
        robotStateReq.getRobotHWVersion();
    }

    /**
     * 获取思岚版本
     */
    @Override
    public void getSlamVersion() {
        robotStateReq.getSlamVersion();
    }

    @Override
    public void getRobotType(OnRobotTypeListener robotTypeListener) {
        robotStateReq.getRobotType(robotTypeListener);
    }

    @Override
    public void setRobotType(String type) {
        robotStateReq.setRobotType(type);
    }

    @Override
    public void setTimezone(String timeOffset, String region) {
        robotStateReq.setTimezone(timeOffset, region);
    }

    @Override
    public void makeSessionId() {
        robotStateReq.makeSessionId();
    }

    @Override
    public void getPerson(OnDetectPersonListener listener) {
        robotStateReq.getPerson(listener);
    }

    @Override
    public void setLinuxServerAddr(String addr, String port) {
        robotStateReq.setLinuxServerAddr(addr, port);
    }

    @Override
    public void getMotoOverloadState(OnMotoOverloadListener listener) {
        robotStateReq.getMotoOverloadState(listener);
    }

    @Override
    public void clearMotoOverload() {
        robotStateReq.clearMotoOverload();
    }

    @Override
    public void getEmergencyStatus(OnEmergencyStatusListener listener) {
        robotStateReq.getEmergencyStatus(listener);
    }

    @Override
    public void getSlamHealth() {
        robotStateReq.getSlamHealth();
    }

    @Override
    public void setXRobotTime(String year, String month, String day, String hour, String minute, String second, String week) {
        robotStateReq.setXRobotTime(year, month, day, hour, minute, second, week);
    }

    @Override
    public void timingPowerOffForXRobot(boolean isOpen, String year, int month, int day, int hour, int minute, int second) {
        robotStateReq.timingPowerOffForXRobot(isOpen, year, month, day, hour, minute, second);
    }

    @Override
    public void doubleDoorOpenControl() {
        robotStateReq.doubleDoorOpenControl();
    }

    @Override
    public void doubleDoorCloseControl() {
        robotStateReq.doubleDoorCloseControl();
    }

    @Override
    public void openOneFloorDoor() {
        robotStateReq.openOneFloorDoor();
    }

    @Override
    public void closeOneFloorDoor() {
        robotStateReq.closeOneFloorDoor();
    }

    @Override
    public void openTwoFloorDoor() {
        robotStateReq.openTwoFloorDoor();
    }

    @Override
    public void closeTwoFloorDoor() {
        robotStateReq.closeTwoFloorDoor();
    }

    @Override
    public void getDoorStatus() {
        robotStateReq.getDoorStatus();
    }

    @Override
    public void releaseEmergency() {
        robotStateReq.releaseEmergency();
    }

    @Override
    public void setMicroVolume(int volume) {
        configReq.setMicroVolume(volume);
    }

    @Override
    public void getMicroVolume(OnMicroVolumeListener listener) {
        configReq.getMicroVolume(listener);
    }

    @Override
    public void getSN(OnSNListener listener) {
        snReq.getSN(listener);
    }

    @Override
    public void getDeviceList(OnDeviceInfoListener listener) {
        snReq.getDeviceList(listener);
    }

    @Override
    public void setSN(String sn) {
        snReq.setSN(sn);
    }


}
