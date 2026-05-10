package com.csjbot.coshandler.client_req.custom_service;

/**
 * Copyright (c) 2018, SuZhou CsjBot. All Rights Reserved.
 * www.csjbot.com
 * <p>
 * Created by 浦耀宗 at 2018/03/27 0027-10:49.
 * Email: puyz@csjbot.com
 */

public interface ICustomServiceReq {
    /**
     * 请求人工客服
     *
     * @param sn
     */
    void callHumanService(String sn);

    /**
     * 挂断人工客服
     *
     * @param sn
     */
    void hangUpHumanService(String sn);

    /**
     * 人工客服连接成功时，发送心跳包
     *
     * @param sn
     */
    void sendHumanServiceHeartBeat(String sn);

    /**
     * 人工客服介入
     */
    void humanInterventi(String sn);

    /**
     * 人工客服非介入
     */
    void humanHangupInterventi(String sn);
}
