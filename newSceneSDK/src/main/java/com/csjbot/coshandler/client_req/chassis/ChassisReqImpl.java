package com.csjbot.coshandler.client_req.chassis;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnDestReachableListener;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnMapListListener;
import com.csjbot.coshandler.listener.OnMapListener;
import com.csjbot.coshandler.listener.OnNaviListener;
import com.csjbot.coshandler.listener.OnNaviSearchListener;
import com.csjbot.coshandler.listener.OnPositionListener;
import com.csjbot.coshandler.listener.OnRobotDockStateListener;
import com.csjbot.coshandler.listener.OnSpeedGetListener;
import com.csjbot.coshandler.listener.OnMapStateListener;

import java.util.Locale;

/**
 * Created by jingwc on 2017/9/20.
 */

public class ChassisReqImpl extends BaseClientReq implements IChassisReq, IStatusSearch {

    @Override
    public void getPosition(OnPositionListener listener) {
        CsjRobot.getInstance().setOnPositionListener(listener);
        sendReq(getJson(REQConstants.NAVI_GET_CURPOS_REQ));
    }

    @Override
    public void move(int direction) {
        sendReq(getJson(REQConstants.NAVI_ROBOT_MOVE_REQ, "direction", direction));
    }

    @Override
    public void moveBySerial(int direction) {
        sendReq(getJson(REQConstants.NAVI_ROBOT_MOVE_BY_SERIAL_REQ, "direction", direction));
    }

    @Override
    public void moveSerial(int linear, int angular) {
        sendReq(getJson(REQConstants.NAVI_ROBOT_MOVE_SERIAL_REQ, "linear", linear, "angular", angular));
    }

    @Override
    public void navi(String json, OnNaviListener listener) {
        CsjRobot.getInstance().setOnNaviMoveResultListener(listener);
        sendReq(getChassisJson(REQConstants.NAVI_ROBOT_MOVE_TO_REQ, "pos", json));
    }

    @Override
    public void navi(String json) {
        sendReq(getChassisJson(REQConstants.NAVI_ROBOT_MOVE_TO_REQ, "pos", json));
    }

    @Override
    public void sendAllNaviPoint(String json) {
        String points = "{\"msg_id\": \"GET_ALL_NAVI_POINTS_RSP\"," + "\"points\": " + json + "}";
        sendReq(points);
    }

    @Override
    public void cancelNavi(OnNaviListener listener) {
        CsjRobot.getInstance().setOnNaviCancelListener(listener);
        sendReq(getJson(REQConstants.NAVI_ROBOT_CANCEL_REQ));
    }


    @Override
    public void goAngle(int rotation) {
        sendReq(getJson(REQConstants.NAVI_GO_ROTATION_TO_REQ, "rotation", rotation));
    }

    @Override
    public void moveAngle(int rotation, OnGoRotationListener listener) {
        CsjRobot.getInstance().setOnGoRotationListener(listener);
        sendReq(getJson(REQConstants.NAVI_GO_ROTATION_REQ, "rotation", rotation));
    }

    @Override
    public void goHome(OnNaviListener listener) {
        CsjRobot.getInstance().setOnNaviGoHomeListener(listener);
        sendReq(getJson(REQConstants.NAVI_GO_HOME_REQ));
    }

    @Override
    public void saveMap() {
        sendReq(getJson(REQConstants.NAVI_GET_MAP_REQ));
    }

    @Override
    public void saveMap(OnMapListener onMapListener) {
        CsjRobot.getInstance().setOnMapListener(onMapListener);
        sendReq(getJson(REQConstants.NAVI_GET_MAP_REQ));

    }

    @Override
    public void saveMap(String name) {
        sendReq(getJson(REQConstants.NAVI_GET_MAP_REQ, "name", name));
    }

    @Override
    public void saveMap(String name, OnMapListener onMapListener) {
        CsjRobot.getInstance().setOnMapListener(onMapListener);
        sendReq(getJson(REQConstants.NAVI_GET_MAP_REQ, "name", name));
    }

    @Override
    public void loadMap() {
        sendReq(getJson(REQConstants.NAVI_SET_MAP_REQ));
    }

    @Override
    public void loadMap(OnMapListener onMapListener) {
        CsjRobot.getInstance().setOnMapListener(onMapListener);
        sendReq(getJson(REQConstants.NAVI_SET_MAP_REQ));
    }

    @Override
    public void loadMap(String name) {
        sendReq(getJson(REQConstants.NAVI_SET_MAP_REQ, "name", name));
    }

    @Override
    public void loadMap(String name, OnMapListener onMapListener) {
        CsjRobot.getInstance().setOnMapListener(onMapListener);
        sendReq(getJson(REQConstants.NAVI_SET_MAP_REQ, "name", name));
    }

    @Override
    public void loadMap(String name, float x, float y, float rotation) {
        String send = "{\"msg_id\":\"%s\",\"name\":\"%s\",\"x\":%f,\"y\":%f,\"rotation\":%f}";
        sendReq(String.format(Locale.getDefault(), send, REQConstants.NAVI_SET_MAP_AND_POSE_REQ, name, x, y, rotation));
    }

    @Override
    public void loadMap(String name, float x, float y, float rotation, OnMapListener onMapListener) {
        CsjRobot.getInstance().setOnMapListener(onMapListener);
        String send = "{\"msg_id\":\"%s\",\"name\":\"%s\",\"x\":%f,\"y\":%f,\"rotation\":%f}";
        sendReq(String.format(Locale.getDefault(), send, REQConstants.NAVI_SET_MAP_AND_POSE_REQ, name, x, y, rotation));
    }

    @Override
    public void setSpeed(float speed) {
        sendReq(getJson(REQConstants.NAVI_ROBOT_SET_SPEED_REQ, "speed", speed));
    }

    @Override
    public void getSpeed(OnSpeedGetListener listListener) {
        CsjRobot.getInstance().setOnSpeedGetListener(listListener);
        sendReq(getJson(REQConstants.NAVI_ROBOT_GET_SPEED_REQ));
    }

    @Override
    public void setPowerTime(String data) {
        sendReq(getChassisJson(REQConstants.ROBOT_SET_POWERTIME_REQ, "data", data));
    }

    @Override
    public void getMapState(OnMapStateListener listener) {
        CsjRobot.getInstance().setOnMapStateListener(listener);
        sendReq(getJson("NAVI_GET_MAPSTATUS_REQ"));
    }

    @Override
    public void getDockerState(OnRobotDockStateListener listener) {
        CsjRobot.getInstance().setOnRobotDockStateListener(listener);
        sendReq(getJson(REQConstants.ROBOT_GET_DOCK_STATUS_REQ));
    }

    @Override
    public void setNaviMode(int mode) {
        sendReq(getJson(REQConstants.NAVI_SET_MODE_REQ, "mode", mode));
    }

    @Override
    public void destReachable(float x, float y, float rotation, OnDestReachableListener listener) {
        CsjRobot.getInstance().setOnDestReachableListener(listener);
        sendReq(getDestReachableJson(x, y, rotation));
    }

    @Override
    public void getMapList(OnMapListListener listListener) {
        CsjRobot.getInstance().setOnMapListListener(listListener);
        sendReq(getJson(REQConstants.NAVI_GET_MAPLIST_REQ));
    }

    @Override
    public void search(OnNaviSearchListener listener) {
        CsjRobot.getInstance().setOnNaviSearchListener(listener);
        sendReq(getJson(REQConstants.NAVI_GET_STATUS_REQ));
    }
}
