package com.csjbot.coshandler.client_req.body_action;


import com.csjbot.coshandler.client_req.base.IClientReq;
import com.csjbot.coshandler.listener.OnDoubleDoorStateListener;

/**
 * 肢体动作接口
 * Created by jingwc on 2017/8/14.
 */
public interface IBodyActionReq extends IClientReq {

    /**
     * Reset arm and head joints, only useful for 9th generation Alice
     */
    void reset();


    /**
     * new Action. see docs,only useful for 9th generation Alice
     *
     * @param part      the part
     * @param direction the direction
     * @param angle     the angle
     * @param speed     the speed
     */
    void actionNew(int part, int direction, int angle, int speed);

    /**
     * Body movements,only useful for 8th generation Alice
     *
     * @param bodyPart Body parts
     * @param action   action
     */
    void action(int bodyPart, int action);

    /**
     * 适配 2.0板子的 Alice 以及 Timo
     *
     * @param cmd
     * @param angle
     */
    void actionV2(String cmd, int angle);

    /**
     * 适配 2.0板子的 Alice 以及 Timo
     */
    void CustomerCtrlV2(int headLeft, int headUp, int lefthand, int righthand);


    void resetV2();

    /**
     * The robot began to swing its hands left and right
     * only useful for 8th generation Alice
     *
     * @param intervalTime the interval time
     */
    void startWaveHands(int intervalTime);

    /**
     * The robot stops swinging its hands left and right
     * only useful for 8th generation Alice
     */
    void stopWaveHands();

    /**
     * Start dancing
     */
    void startDance();

    /**
     * Stop dancing
     */
    void stopDance();

    /**
     * Amanda/ Foodcar open door
     *
     * @param listener the listener
     */
    void openDoubleDoor(OnDoubleDoorStateListener listener);

    /**
     * Amanda/ Foodcar close door
     *
     * @param listener the listener
     */
    void closeDoubleDoor(OnDoubleDoorStateListener listener);


    /**
     * Gets  Amanda/ Foodcar's door state.
     *
     * @param listener the listener
     */
    void getDoubleDoorState(OnDoubleDoorStateListener listener);
}
