package com.csjbot.coshandler.core.interfaces;

/**
 * Created by jingwc on 2017/9/5.
 */

public interface IAction {

    /**
     * Left arm up
     */
    void leftLargeArmUp();

    /**
     * Left arm down
     */
    void leftLargeArmDown();


    /**
     * Left forearm up
     *
     * @deprecated Current robots do not support
     */
    void leftSmallArmUp();

    /**
     * Left forearm down
     *
     * @deprecated Current robots do not support
     */
    void leftSmallArmDown();

    /**
     * Right arm up
     */
    void righLargeArmUp();

    /**
     * Right arm down
     */
    void rightLargeArmDown();


    /**
     * Right forearm up
     *
     * @deprecated Current robots do not support
     */
    void rightSmallArmUp();

    /**
     * Right forearm down
     *
     * @deprecated Current robots do not support
     */
    void rightSmallArmDown();

    /**
     * Double boom up
     *
     * @deprecated Current robots do not support
     */
    void doubleLargeArmUp();

    /**
     * Double boom down
     *
     * @deprecated Current robots do not support
     */
    void doubleLargeArmDown();


    /**
     * Double forearms up
     *
     * @deprecated Current robots do not support
     */
    void doubleSmallArmUp();

    /**
     * Double forearms dwon
     *
     * @deprecated Current robots do not support
     */
    void doubleSmallArmDown();

    /**
     * nodding motion
     */
    void nodAction();


    /**
     * Start waving
     */
    void startWave(int intervalTime);

    /**
     * Stop waving
     */
    void stopWave();

    /**
     * Snow's right arm swings
     */
    void snowRightArm();

    /**
     * Snow's left arm swings
     */
    void snowLeftArm();

    /**
     * Snow's double arms swings
     */
    void snowDoubleArm();
}
