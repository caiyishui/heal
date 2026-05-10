package com.csjbot.coshandler.core.interfaces;

import com.csjbot.coshandler.listener.OnGoRotationListener;

/**
 * Created by jingwc on 2017/9/20.
 */

public interface IChassis {
    /**
     * 向左转
     */
    void turnLeft(OnGoRotationListener listener);

    /**
     * 向右转
     */
    void turnRight(OnGoRotationListener listener);

    void moveLeft();

    void moveRight();

    void moveForward();

    void moveBack();
}
