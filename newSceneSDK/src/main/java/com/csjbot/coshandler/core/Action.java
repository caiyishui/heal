package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.body_action.IBodyActionReq;
import com.csjbot.coshandler.client_req.chassis.IChassisReq;
import com.csjbot.coshandler.core.interfaces.IAction;
import com.csjbot.coshandler.core.interfaces.IActionV2;
import com.csjbot.coshandler.core.interfaces.IChassis;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnBodyCtrlListener;
import com.csjbot.coshandler.listener.OnDestReachableListener;
import com.csjbot.coshandler.listener.OnDoubleDoorStateListener;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnMapListListener;
import com.csjbot.coshandler.listener.OnMapListener;
import com.csjbot.coshandler.listener.OnMapStateListener;
import com.csjbot.coshandler.listener.OnNaviListener;
import com.csjbot.coshandler.listener.OnNaviSearchListener;
import com.csjbot.coshandler.listener.OnPositionListener;
import com.csjbot.coshandler.listener.OnRobotDockStateListener;
import com.csjbot.coshandler.listener.OnSpeedGetListener;

/**
 * Created by Administrator on 2019/7/15.
 */
public class Action implements IBodyActionReq, IChassisReq, IAction, IActionV2, IChassis {

    private IBodyActionReq bodyActionReq;
    private IChassisReq chassisReq;

    /**
     * Instantiates a new Action.
     */
    public Action() {
        bodyActionReq = ReqFactory.getBodyActionReqInstance();
        chassisReq = ReqFactory.getChassisReqInstance();
    }

    @Override
    public void reset() {
        bodyActionReq.reset();
    }

    @Override
    public void action(int bodyPart, int action) {
        bodyActionReq.action(bodyPart, action);
    }

    @Override
    public void actionV2(String cmd, int angle) {
        bodyActionReq.actionV2(cmd, angle);
    }

    @Override
    public void CustomerCtrlV2(int headLeft, int headUp, int lefthand, int righthand) {
        bodyActionReq.CustomerCtrlV2(headLeft, headUp, lefthand, righthand);
    }

    @Override
    public void resetV2() {
        bodyActionReq.resetV2();
    }

    @Override
    public void actionNew(int part, int direction, int angle, int speed) {
        bodyActionReq.actionNew(part, direction, angle, speed);
    }

    @Override
    public void startWaveHands(int intervalTime) {
        bodyActionReq.startWaveHands(intervalTime);
    }

    @Override
    public void stopWaveHands() {
        bodyActionReq.stopWaveHands();
    }

    @Override
    public void startDance() {
        bodyActionReq.startDance();
    }

    @Override
    public void stopDance() {
        bodyActionReq.stopDance();
    }

    @Override
    public void openDoubleDoor(OnDoubleDoorStateListener listener) {
        bodyActionReq.openDoubleDoor(listener);
    }

    @Override
    public void closeDoubleDoor(OnDoubleDoorStateListener listener) {
        bodyActionReq.closeDoubleDoor(listener);
    }

    @Override
    public void getDoubleDoorState(OnDoubleDoorStateListener listener) {
        bodyActionReq.getDoubleDoorState(listener);
    }

    @Override
    public void getPosition(OnPositionListener listener) {
        chassisReq.getPosition(listener);
    }

    @Override
    public void move(int direction) {
        chassisReq.move(direction);
    }

    @Override
    public void moveBySerial(int direction) {
        chassisReq.moveBySerial(direction);
    }

    @Override
    public void moveSerial(int linear, int angular) {
        chassisReq.moveSerial(linear, angular);
    }

    @Override
    public void navi(String json, OnNaviListener listener) {
        chassisReq.navi(json, listener);

    }

    @Override
    public void navi(String json) {
        chassisReq.navi(json);
    }

    @Override
    public void sendAllNaviPoint(String json) {
        chassisReq.sendAllNaviPoint(json);
    }

    @Override
    public void cancelNavi(OnNaviListener listener) {
        chassisReq.cancelNavi(listener);
    }

    @Override
    public void goAngle(int rotation) {
        chassisReq.goAngle(rotation);
    }

    @Override
    public void moveAngle(int rotation, OnGoRotationListener listener) {
        chassisReq.moveAngle(rotation, listener);
    }

    @Override
    public void goHome(OnNaviListener listener) {
        chassisReq.goHome(listener);
    }

    @Override
    public void saveMap() {
        chassisReq.saveMap();
    }

    @Override
    public void saveMap(OnMapListener onMapListener) {
        chassisReq.saveMap(onMapListener);
    }

    @Override
    public void saveMap(String name) {
        chassisReq.saveMap(name);
    }

    @Override
    public void saveMap(String name, OnMapListener onMapListener) {
        chassisReq.saveMap(name, onMapListener);
    }

    @Override
    public void loadMap() {
        chassisReq.loadMap();
    }

    @Override
    public void loadMap(OnMapListener onMapListener) {
        chassisReq.loadMap(onMapListener);
    }

    @Override
    public void loadMap(String name) {
        chassisReq.loadMap(name);
    }

    @Override
    public void loadMap(String name, OnMapListener onMapListener) {
        chassisReq.loadMap(name, onMapListener);
    }

    @Override
    public void loadMap(String name, float x, float y, float rotation) {
        chassisReq.loadMap(name, x, y, rotation);
    }

    @Override
    public void loadMap(String name, float x, float y, float rotation, OnMapListener onMapListener) {
        chassisReq.loadMap(name, x, y, rotation, onMapListener);
    }

    @Override
    public void setSpeed(float speed) {
        chassisReq.setSpeed(speed);
    }


    @Override
    public void getSpeed(OnSpeedGetListener listListener) {
        chassisReq.getSpeed(listListener);
    }

    /**
     * @deprecated
     */
    @Override
    public void setPowerTime(String data) {
        chassisReq.setPowerTime(data);
    }

    @Override
    public void getMapList(OnMapListListener listListener) {
        chassisReq.getMapList(listListener);
    }

    @Override
    public void getMapState(OnMapStateListener listener) {
        chassisReq.getMapState(listener);
    }

    @Override
    public void getDockerState(OnRobotDockStateListener listener) {
        chassisReq.getDockerState(listener);
    }

    @Override
    public void setNaviMode(int mode) {
        chassisReq.setNaviMode(mode);
    }

    @Override
    public void destReachable(float x, float y, float rotation, OnDestReachableListener listener) {
        chassisReq.destReachable(x, y, rotation, listener);
    }

    @Override
    public void search(OnNaviSearchListener listener) {
        chassisReq.search(listener);
    }


    public void leftLargeArmUp(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        leftLargeArmUp();
    }

    @Override
    public void leftLargeArmUp() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(3, 2, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPart.LEFT_ARM, REQConstants.BodyAction.LEFT_UP);
        }
    }

    public void leftLargeArmDown(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        leftLargeArmDown();
    }

    @Override
    public void leftLargeArmDown() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(3, 1, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPart.LEFT_ARM, REQConstants.BodyAction.RIGHT_DOWN);
        }
    }

    @Override
    public void leftSmallArmUp() {
        bodyActionReq.action(REQConstants.BodyPart.LEFT_FOREARM, REQConstants.BodyAction.LEFT_UP);
    }

    @Override
    public void leftSmallArmDown() {
        bodyActionReq.action(REQConstants.BodyPart.LEFT_FOREARM, REQConstants.BodyAction.RIGHT_DOWN);
    }


    public void righLargeArmUp(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        righLargeArmUp();
    }

    @Override
    public void righLargeArmUp() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(4, 1, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPart.RIGHT_ARM, REQConstants.BodyAction.LEFT_UP);
        }
    }

    public void rightLargeArmDown(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        rightLargeArmDown();
    }


    @Override
    public void rightLargeArmDown() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(4, 2, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPart.RIGHT_ARM, REQConstants.BodyAction.RIGHT_DOWN);
        }

    }

    @Override
    public void rightSmallArmUp() {
        bodyActionReq.action(REQConstants.BodyPart.RIGHT_FOREARM, REQConstants.BodyAction.LEFT_UP);
    }

    @Override
    public void rightSmallArmDown() {
        bodyActionReq.action(REQConstants.BodyPart.RIGHT_FOREARM, REQConstants.BodyAction.RIGHT_DOWN);
    }

    @Override
    public void doubleLargeArmUp() {
        bodyActionReq.action(REQConstants.BodyPart.DOUBLE_ARM, REQConstants.BodyAction.LEFT_UP);
    }

    @Override
    public void doubleLargeArmDown() {
        bodyActionReq.action(REQConstants.BodyPart.DOUBLE_ARM, REQConstants.BodyAction.RIGHT_DOWN);
    }

    @Override
    public void doubleSmallArmUp() {
        bodyActionReq.action(REQConstants.BodyPart.DOUBLE_FOREARM, REQConstants.BodyAction.LEFT_UP);
    }

    @Override
    public void doubleSmallArmDown() {
        bodyActionReq.action(REQConstants.BodyPart.DOUBLE_FOREARM, REQConstants.BodyAction.RIGHT_DOWN);
    }

    @Override
    public void nodAction() {
        if (CsjRobot.isNewBodyAction) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bodyActionReq.actionNew(1, 1, 10, 3);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bodyActionReq.actionNew(1, 2, 10, 3);
                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bodyActionReq.action(REQConstants.BodyPart.HEAD, REQConstants.BodyAction.DOWN);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bodyActionReq.action(REQConstants.BodyPart.HEAD, REQConstants.BodyAction.UP);
                }
            }).start();
        }
    }

    @Override
    public void startWave(int intervalTime) {
        bodyActionReq.startWaveHands(intervalTime);
    }

    @Override
    public void stopWave() {
        bodyActionReq.stopWaveHands();
    }

    @Override
    public void snowRightArm() {
        bodyActionReq.action(1, 1);
    }

    @Override
    public void snowLeftArm() {
        bodyActionReq.action(2, 1);
    }

    @Override
    public void snowDoubleArm() {
        bodyActionReq.action(3, 1);
    }

    @Override
    public void AliceHeadUp() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(1, 2, 10, 3);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.HEAD1, REQConstants.BodyActionV2.HEAD_UP);
        }

    }

    @Override
    public void AliceHeadDown() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(1, 1, 10, 3);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.HEAD1, REQConstants.BodyActionV2.HEAD_DOWN);
        }

    }

    @Override
    public void AliceHeadLeft() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(2, 1, 40, 3);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.HEAD2, REQConstants.BodyActionV2.HEAD_LEFT);
        }
    }

    @Override
    public void AliceHeadRight() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(2, 2, 40, 3);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.HEAD2, REQConstants.BodyActionV2.HEAD_RIGHT);
        }
    }

    @Override
    public void AliceHeadHReset() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(5, 1, 1, 3);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.HEAD2, REQConstants.BodyActionV2.HORIZONTAL_RESET);
        }
    }

    public void AliceLeftArmUp(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        AliceLeftArmUp();
    }

    @Override
    public void AliceLeftArmUp() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(3, 2, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.LEFT_ARM, REQConstants.BodyActionV2.ARM_UP);
        }

    }

    public void AliceLeftArmDown(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        AliceLeftArmDown();
    }

    @Override
    public void AliceLeftArmDown() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(3, 1, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.LEFT_ARM, REQConstants.BodyActionV2.ARM_DOWN);
        }

    }

    public void AliceRightArmUp(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        AliceRightArmUp();
    }

    @Override
    public void AliceRightArmUp() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(4, 1, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.RIGHT_ARM, REQConstants.BodyActionV2.ARM_UP);
        }

    }

    public void AliceRightArmDown(OnBodyCtrlListener listener) {
        CsjRobot.getInstance().setOnBodyCtrlListener(listener);
        AliceRightArmDown();
    }

    @Override
    public void AliceRightArmDown() {
        if (CsjRobot.isNewBodyAction) {
            bodyActionReq.actionNew(4, 2, 30, 2);
        } else {
            bodyActionReq.action(REQConstants.BodyPartV2.RIGHT_ARM, REQConstants.BodyActionV2.ARM_DOWN);
        }

    }

    @Override
    public void SnowLeftArmSwing(int count) {
        bodyActionReq.action(REQConstants.SnowBodyActionV2.SNOW_LEFT_ARM_SWING, count);
    }

    @Override
    public void SnowRightArmSwing(int count) {
        bodyActionReq.action(REQConstants.SnowBodyActionV2.SNOW_RIGHT_ARM_SWING, count);
    }

    @Override
    public void SnowDoubleArmSwing(int count) {
        bodyActionReq.action(REQConstants.SnowBodyActionV2.SNOW_DOUBLE_ARM_SWING, count);
    }

    @Override
    public void AliceNewActionHeadLeftRightCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_HEAD_LEFT_RIGHT_CTRL, angle);
    }

    @Override
    public void AliceNewActionHeadUpDownCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_HEAD_UP_DOWN_CTRL, angle);
    }

    @Override
    public void AliceNewActionLeftHandCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_LEFT_HAND_CTRL, angle);
    }

    @Override
    public void AliceNewActionRightHandCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_RIGHT_HAND_CTRL, angle);
    }

    @Override
    public void ALiceNewActionCustomerCtrl(int headLeft, int headUp, int lefthand, int righthand) {
        bodyActionReq.CustomerCtrlV2(headLeft, headUp, lefthand, righthand);
    }

    @Override
    public void AliceNewActionReset() {
        bodyActionReq.resetV2();
    }

    @Override
    public void TimoActionHeadLeftRightCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_HEAD_LEFT_RIGHT_CTRL, angle);
    }

    @Override
    public void TimoActionHeadUpDownCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_HEAD_UP_DOWN_CTRL, angle);
    }

    @Override
    public void TimoActionLeftHandCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_LEFT_HAND_CTRL, angle);
    }

    @Override
    public void TimoActionRightHandCtrl(int angle) {
        bodyActionReq.actionV2(CmdConstants.ALICE_RIGHT_HAND_CTRL, angle);
    }

    @Override
    public void TimoActionCustomerCtrl(int headLeft, int headUp, int lefthand, int righthand) {
        bodyActionReq.CustomerCtrlV2(headLeft, headUp, lefthand, righthand);
    }

    @Override
    public void TimoActionReset() {
        bodyActionReq.resetV2();
    }

    @Override
    public void turnLeft(OnGoRotationListener listener) {
        chassisReq.moveAngle(90, listener);
    }

    @Override
    public void turnRight(OnGoRotationListener listener) {
        chassisReq.moveAngle(-90, listener);
    }

    @Override
    public void moveLeft() {
        chassisReq.move(REQConstants.MoveDirection.LEFT);
    }

    @Override
    public void moveRight() {
        chassisReq.move(REQConstants.MoveDirection.RIGHT);
    }

    @Override
    public void moveForward() {
        chassisReq.move(REQConstants.MoveDirection.FORWARD);
    }

    @Override
    public void moveBack() {
        chassisReq.move(REQConstants.MoveDirection.BACK);
    }
}
