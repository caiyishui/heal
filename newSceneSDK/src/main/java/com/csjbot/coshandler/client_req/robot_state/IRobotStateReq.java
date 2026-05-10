package com.csjbot.coshandler.client_req.robot_state;

import com.csjbot.coshandler.listener.OnDetectPersonListener;
import com.csjbot.coshandler.listener.OnEmergencyStatusListener;
import com.csjbot.coshandler.listener.OnPowerStatusListener;
import com.csjbot.coshandler.listener.OnRobotTypeListener;
import com.csjbot.coshandler.listener.OnMotoOverloadListener;
import com.csjbot.coshandler.listener.OnRobotStateListener;
import com.csjbot.coshandler.listener.OnWarningCheckSelfListener;

/**
 * Created by jingwc on 2017/10/25.
 */

public interface IRobotStateReq {

    /**
     * 机器人关机
     */
    void shutdown();

    /**
     * 机器人重启
     */
    void reboot();

    /**
     * 获取电量
     */
    void getBattery(OnRobotStateListener listener);

    /**
     * 获取充电状态
     */
    void getCharge(OnRobotStateListener listener);



    /**
     * 自检
     */
    void checkSelf(OnWarningCheckSelfListener listener);

    /**
     * 获取硬件版本
     */
    void getRobotHWVersion();

    /**
     * 获取思岚版本
     */
    void getSlamVersion();
    /**
     * 获取Linux储存的机器人硬件类型，默认是迎宾
     */
    void getRobotType(OnRobotTypeListener robotTypeListener);

    /**
     * 设置Linux储存的机器人硬件类型，默认是迎宾
     */
    void setRobotType(String type);

    /**
     * 设置机器人时区
     *
     * @param timeOffset
     * @param region
     */
    void setTimezone(String timeOffset, String region);

    /**
     * 创建sessionId
     */
    void makeSessionId();

    /**
     * 获取是否有人 人体检测
     */
    void getPerson(OnDetectPersonListener listener);


    void setLinuxServerAddr(String addr, String port);

    void getMotoOverloadState(OnMotoOverloadListener listener);

    void clearMotoOverload();

    void getEmergencyStatus(OnEmergencyStatusListener listener);

    void getSlamHealth();



    //X代校准时间
    void setXRobotTime(String year, String month, String day, String hour, String minute, String second, String week);


    void timingPowerOffForXRobot(boolean isOpen, String year, int month, int day, int hour, int minute, int second);

    /**
     * 双开门控制 开门
     */
    void doubleDoorOpenControl();

    /**
     * 双开门控制 关门
     */
    void doubleDoorCloseControl();

    //开上层门
    void openOneFloorDoor();

    //关上层门
    void closeOneFloorDoor();

    //开下层门
    void openTwoFloorDoor();

    //关下层门
    void closeTwoFloorDoor();

    /**
     * 舱门，主动获取舱门状态
     */
    void getDoorStatus();



    /**
     * 释放急停
     */
    void releaseEmergency();
}
