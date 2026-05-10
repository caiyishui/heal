package com.csjbot.coshandler.client_req.expression;


import com.csjbot.coshandler.client_req.base.IClientReq;
import com.csjbot.coshandler.listener.OnExpressionListener;

/**
 * 表情接口
 * Created by jingwc on 2017/8/14.
 */

public interface IExpressionReq extends IClientReq {

    void setExpression(int expression,int once,int time);

    void getExpression(OnExpressionListener listener);

    void updateExpression();

    //新表情通讯协议
    //微笑
    void AmySmileExpression();

    //开心
    void AmyHappyExpression();

    //说话
    void AmySpeakExpression();

    //音乐
    void AmyMusicExpression();

    //警告
    void AmyWarnExpression();
}
