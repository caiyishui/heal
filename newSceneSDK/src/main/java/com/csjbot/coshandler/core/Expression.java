package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.expression.IExpressionReq;
import com.csjbot.coshandler.core.interfaces.IExpression;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnExpressionListener;

/**
 * Created by Administrator on 2019/7/15.
 */

public class Expression implements IExpressionReq, IExpression {

    private IExpressionReq expressionReq;

    Expression() {
        expressionReq = ReqFactory.getExpressionReqInstance();
    }

    @Override
    public void setExpression(int expression, int once, int time) {
        expressionReq.setExpression(expression, once, time);
    }

    @Override
    public void getExpression(OnExpressionListener listener) {
        expressionReq.getExpression(listener);
    }

    @Override
    public void updateExpression() {
        expressionReq.updateExpression();
    }

    @Override
    public void AmySmileExpression() {
        expressionReq.AmySmileExpression();
    }

    @Override
    public void AmyHappyExpression() {
        expressionReq.AmyHappyExpression();
    }

    @Override
    public void AmySpeakExpression() {
        expressionReq.AmySpeakExpression();
    }

    @Override
    public void AmyMusicExpression() {
        expressionReq.AmyMusicExpression();
    }

    @Override
    public void AmyWarnExpression() {
        expressionReq.AmyWarnExpression();
    }

    @Override
    public void happy() {
        setExpression(REQConstants.Expression.HAPPY, 1, 5000);
    }

    @Override
    public void sadness() {
        setExpression(REQConstants.Expression.SADNESS, 1, 5000);
    }

    @Override
    public void surprised() {
        setExpression(REQConstants.Expression.SURPRISED, 1, 5000);
    }

    @Override
    public void smile() {
        setExpression(REQConstants.Expression.SMILE, 1, 5000);
    }

    @Override
    public void normal() {
        setExpression(REQConstants.Expression.NORMAL, 0, 0);
    }

    @Override
    public void angry() {
        setExpression(REQConstants.Expression.ANGRY, 1, 5000);
    }

    @Override
    public void lightning() {
        setExpression(REQConstants.Expression.LIGHTNING, 0, 0);
    }

    @Override
    public void sleepiness() {
        setExpression(REQConstants.Expression.SLEEPINESS, 1, 5000);
    }

    public void expression(int expression) {
        expressionReq.setExpression(expression
                , REQConstants.Expression.NO
                , REQConstants.Expression.NO);
    }
}
