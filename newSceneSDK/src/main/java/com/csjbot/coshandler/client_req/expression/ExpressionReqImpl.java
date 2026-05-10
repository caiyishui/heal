package com.csjbot.coshandler.client_req.expression;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnExpressionListener;

/**
 * Created by jingwc on 2017/9/9.
 */

public class ExpressionReqImpl extends BaseClientReq implements IExpressionReq {

    @Override
    public void setExpression(int expression, int once, int time) {
//        sendReq(getExpressionJson(REQConstants.SET_ROBOT_EXPRESSION_REQ, expression, once, time));
        sendReq(getExpressionJson(REQConstants.ALICE_EXPRESSION_REQ, expression, once, time));
    }

    @Override
    public void getExpression(OnExpressionListener listener) {
        CsjRobot.getInstance().setOnExpressionListener(listener);
        sendReq(getJson(REQConstants.GET_ROBOT_EXPRESSION_REQ));
    }

    @Override
    public void updateExpression() {
        sendReq(getJson(REQConstants.UPGRADE_ROBOT_EXPRESSION_REQ));
    }

    @Override
    public void AmySmileExpression() {
        sendReq(getJson(REQConstants.AMY_EXPRESSION_NEW_REQ, "expression", 4));

    }

    @Override
    public void AmyHappyExpression() {
        sendReq(getJson(REQConstants.AMY_EXPRESSION_NEW_REQ, "expression", 2));

    }

    @Override
    public void AmySpeakExpression() {
        sendReq(getJson(REQConstants.AMY_EXPRESSION_NEW_REQ, "expression", 3));

    }

    @Override
    public void AmyMusicExpression() {
        sendReq(getJson(REQConstants.AMY_EXPRESSION_NEW_REQ, "expression", 5));

    }

    @Override
    public void AmyWarnExpression() {
        sendReq(getJson(REQConstants.AMY_EXPRESSION_NEW_REQ, "expression", 1));

    }
}
