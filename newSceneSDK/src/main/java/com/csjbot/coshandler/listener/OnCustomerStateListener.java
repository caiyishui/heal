package com.csjbot.coshandler.listener;

/**
 * @author ShenBen
 * @date 2019/2/19 15:11
 * @email 714081644@qq.com
 */
public interface OnCustomerStateListener {
    /**
     * 返回回调
     *
     * @param controlId 控制动作，0: 请求连接，1: 挂断
     * @param state     对应动作的状态返回
     */
    void response(int type, int state);
}
