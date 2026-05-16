package com.csjbot.coshandler.core.interfaces;


import androidx.annotation.IntRange;

public interface IActionV2 {
    /**
     * Alice moves her head up
     */
    void AliceHeadUp();

    /**
     * Alice moves her head up down
     */
    void AliceHeadDown();


    /**
     * Alice's head moves to the left
     *
     * @deprecated The latest Alice robots don't support it
     */
    void AliceHeadLeft();

    /**
     * Alice's head moves to the right
     *
     * @deprecated The latest Alice robots don't support it
     */
    void AliceHeadRight();


    /**
     * Alice head horizontal reset
     *
     * @deprecated The latest Alice robots don't support it
     */
    void AliceHeadHReset();

    /**
     * Alice raises her left arm
     */
    void AliceLeftArmUp();

    /**
     * Alice put her left arm down
     */
    void AliceLeftArmDown();

    /**
     * Alice raises her right arm
     */
    void AliceRightArmUp();

    /**
     * Alice put her right arm down
     */
    void AliceRightArmDown();

    /**
     * Snow waves her left arm
     *
     * @param count waves count from = 0, to = 20
     */
    void SnowLeftArmSwing(@IntRange(from = 0, to = 20) int count);

    /**
     * Snow waves her right arm
     *
     * @param count waves count from = 0, to = 20
     */
    void SnowRightArmSwing(@IntRange(from = 0, to = 20) int count);

    /**
     * Snow waves her double arms
     *
     * @param count waves count from = 0, to = 20
     */
    void SnowDoubleArmSwing(@IntRange(from = 0, to = 20) int count);

    //2023-04-19 最新 alice头部左右控制
    void AliceNewActionHeadLeftRightCtrl(int angle);

    //2023-04-19 最新 alice头部上下控制
    void AliceNewActionHeadUpDownCtrl(int angle);

    //2023-04-19 最新 alice左手臂控制
    void AliceNewActionLeftHandCtrl(int angle);

    //2023-04-19 最新 alice右手臂控制
    void AliceNewActionRightHandCtrl(int angle);

    //2023-04-19 最新 alice自定义控制，头左右角度，上下角度，左手臂，右手臂
    void ALiceNewActionCustomerCtrl(int headLeft, int headUp, int lefthand, int righthand);

    //2023-04-19 最新 alice自定义控制 复位指令
    void AliceNewActionReset();


    //2023-04-19 最新 Timo 头部左右控制
    void TimoActionHeadLeftRightCtrl(int angle);

    //2023-04-19 最新 Timo 头部上下控制
    void TimoActionHeadUpDownCtrl(int angle);

    //2023-04-19 最新 Timo 左手臂控制
    void TimoActionLeftHandCtrl(int angle);

    //2023-04-19 最新 Timo 右手臂控制
    void TimoActionRightHandCtrl(int angle);

    //2023-04-19 最新 Timo 自定义控制，头左右角度，上下角度，左手臂，右手臂
    void TimoActionCustomerCtrl(int headLeft, int headUp, int lefthand, int righthand);

    //2023-04-19 最新 Timo 自定义控制 复位指令
    void TimoActionReset();
}
