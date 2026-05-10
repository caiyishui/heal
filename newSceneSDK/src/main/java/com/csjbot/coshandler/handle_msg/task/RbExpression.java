package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.RSPConstants;

/**
 * 负责表情模块的消息的处理
 * Created by jingwc on 2017/8/12.
 */

public class RbExpression extends RbBase {

    @Override
    protected void handleNTFMessage(String dataSource,String msgId) {
        CosLogger.debug("RbExpression-->handleNTFMessage-->");
    }

    @Override
    protected void handleRSPMessage(String dataSource,String msgId) {
        switch (msgId){
            case RSPConstants.GET_ROBOT_EXPRESSION_RSP:
                int expression = getIntSingleField(dataSource,"expression");
                CsjRobot.getInstance().pushExpression(expression);
                CosLogger.debug("RbExpression-->handleRSPMessage-->msgId:GET_ROBOT_EXPRESSION_RSP");
                break;
            default:
                CosLogger.debug("RbExpression-->handleNTFMessage-->DEFAULT:");
                break;
        }

    }
}
