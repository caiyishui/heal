package com.csjbot.coshandler.client_req.chassis;


import com.csjbot.coshandler.listener.OnDestReachableListener;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnMapListListener;
import com.csjbot.coshandler.listener.OnMapListener;
import com.csjbot.coshandler.listener.OnNaviListener;
import com.csjbot.coshandler.listener.OnPositionListener;
import com.csjbot.coshandler.listener.OnRobotDockStateListener;
import com.csjbot.coshandler.listener.OnSpeedGetListener;
import com.csjbot.coshandler.listener.OnMapStateListener;

/**
 * Created by 浦耀宗
 * File_name:IChassisReq.java
 * Project_name:csjsdk
 * Creation time: 20-9-9 下午3:16
 * 苏州穿山甲机器人股份有限公司 All rights reserved
 */
public interface IChassisReq extends IStatusSearch {
    /**
     * Gets position.
     *
     * @param listener the listener
     */
    void getPosition(OnPositionListener listener);

    /**
     * Move direction.
     *
     * @param direction direction:方向 :0 前;1 后;2 左;3 右
     */
    void move(int direction);

    /**
     * @param direction 0x01: 表示前进 10cm;
     *                  0x02:表示后退 10cm;
     *                  0x03: 表示左转 45 度;
     *                  0x04: 表示右转45 度;
     *                  0x05:表示立即停止;
     */
    void moveBySerial(int direction);

    /**
     * Android 直接下发线速度和角速度指令，为了安全考虑，需要维持线速度和角速度，Android端需要≤50ms 下发一次该指令，
     * 主控每 50ms刷新一次速度指令，如果未收到新的指令，主控会将线速度和角速度置为零。
     *
     * @param linear  目标线速度，数据类型转换成int 型。数据正表示前进，数据负表示后退，单位 mm/s。
     * @param angular 目标角速度，数据类型转换成 int 型。数据正表示左转，数据负表示右转，单位 mrad/s。
     */
    void moveSerial(int linear, int angular);

    /**
     * Navi to target.
     *
     * @param json     the json, contains Position info see sample
     * @param listener the listener
     */
    void navi(String json, OnNaviListener listener);

    /**
     * Navi to target.
     *
     * @param json the json, contains Position info see sample
     */
    void navi(String json);

    /**
     * Send all current navigation points to the underlying layer
     *
     * @param json all navi posisions
     * @deprecated
     */
    void sendAllNaviPoint(String json);

    /**
     * Cancel navigation
     *
     * @param listener the Navi listener
     */
    void cancelNavi(OnNaviListener listener);

    /**
     * turn to a specific angle
     *
     * @param rotation the degree  of angle
     */
    void goAngle(int rotation);

    /**
     * Step angle
     *
     * @param rotation Rotation>0:turn left;                 Rotation<0:turn right
     * @param listener the listener
     */
    void moveAngle(int rotation, OnGoRotationListener listener);

    /**
     * Back to the charging point
     *
     * @param listener the Navi listener
     */
    void goHome(OnNaviListener listener);

    /**
     * save map, use the default name
     */
    void saveMap();

    /**
     * Save map.
     *
     * @param onMapListener the on map listener
     */
    void saveMap(OnMapListener onMapListener);

    /**
     * save map.
     *
     * @param name the name
     */
    void saveMap(String name);

    /**
     * Save map.
     *
     * @param name          the name
     * @param onMapListener the on map listener
     */
    void saveMap(String name, OnMapListener onMapListener);

    /**
     * Load map, use the default name
     */
    void loadMap();

    /**
     * Load map.
     *
     * @param onMapListener the on map listener
     */
    void loadMap(OnMapListener onMapListener);

    /**
     * Load map.
     *
     * @param name the name
     */
    void loadMap(String name);

    /**
     * Load map.
     *
     * @param name          the name
     * @param onMapListener the on map listener
     */
    void loadMap(String name, OnMapListener onMapListener);

    /**
     * Load map. Let the robot out of a specific point on the map
     *
     * @param name     the name
     * @param x        the x
     * @param y        the y
     * @param rotation the rotation
     */
    void loadMap(String name, float x, float y, float rotation);


    /**
     * Load map.
     *
     * @param name          the name
     * @param x             the x
     * @param y             the y
     * @param rotation      the rotation
     * @param onMapListener the on map listener
     */
    void loadMap(String name, float x, float y, float rotation, OnMapListener onMapListener);


    /**
     * Speed setting
     *
     * @param speed the speed (0.3 ~ 1.2)              Different robots have different restrictions
     */
    void setSpeed(float speed);

    /**
     * Get Speed
     *
     * @param listListener the speed listener
     */
    void getSpeed(OnSpeedGetListener listListener);

    /**
     * Sets power off time.
     *
     * @param data the json of Timing power off
     * @deprecated
     */
    void setPowerTime(String data);

    /**
     * Gets map list.
     *
     * @param listListener the list listener
     */
    void getMapList(OnMapListListener listListener);


    /**
     * Get map recovery status
     *
     * @param listener the listener
     */
    void getMapState(OnMapStateListener listener);

    /**
     * Gets docker state.
     *
     * @param listener the listener
     */
    void getDockerState(OnRobotDockStateListener listener);

    void setNaviMode(int mode);

    void destReachable(float x, float y, float rotation, OnDestReachableListener listener);
}
