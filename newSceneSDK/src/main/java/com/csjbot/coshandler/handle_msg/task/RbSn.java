package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.coshandler.core.CsjRobot;

/**
 * Created by xiasuhuei321 on 2017/10/23.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public class RbSn extends RbBase {
    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {

    }

    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {
        CsjRobot.getInstance().pushSn(dataSource);
    }
}
