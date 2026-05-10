package com.csjbot.coshandler.service;


import android.util.Log;

import com.csjbot.cosclient.CosClientAgent;
import com.csjbot.cosclient.constant.ClientConstant;
import com.csjbot.cosclient.entity.CommonPacket;
import com.csjbot.cosclient.entity.MessagePacket;
import com.csjbot.cosclient.listener.ClientEvent;
import com.csjbot.cosclient.listener.EventListener;
import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.handle_msg.MessageHandleFactory;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.util.JsonFormatUtil;

/**
 * 处理消息service
 * Created by jingwc on 2017/8/11.
 */

public class HandlerMsgSocketService extends BaseReceiveService implements EventListener {
    private static String[] mInterceptor;
    /**
     * 底层通信对外接口
     */
    private CosClientAgent mAgent;
    private String data;

    private long timestamp;

    public static void setMsgInterceptor(String... interceptor) {
        mInterceptor = interceptor;
    }

    @Override
    protected void connect() {
        Log.e("CSJBOT", "clientConfig is " + clientConfig.toString());

        mAgent = CosClientAgent.createRosClientAgent(this, true, clientConfig);
        if (mAgent.isConnected()) {
            mAgent.disConnect();
        }
        mAgent.connect(CsjRobot.getDefaultIpAddr(), CsjRobot.getDefaultPort());
    }

    @Override
    protected void handleMessage(String json) {
        if (mInterceptor != null && mInterceptor.length > 0) {
            for (String s : mInterceptor) {
                if (json.contains(s)) {
                    CsjlogProxy.getInstance().info("interceptor " + s);
                    return;
                }
            }
        }

        MessageHandleFactory.getInstance().startWork(json);
    }

    /**
     * 接受底层消息.并分发下去
     *
     * @param event
     */
    @Override
    public void onEvent(ClientEvent event) {
        switch (event.eventType) {
            case ClientConstant.EVENT_RECONNECTED:
            case ClientConstant.EVENT_CONNECT_SUCCESS:
                Log.e("CSJBOT", "EVENT_CONNECT_SUCCESS");
                connectStatus(ClientConstant.EVENT_CONNECT_SUCCESS);
                sendMessage(JsonFormatUtil.SplicingSimpleJson(CmdConstants.SET_ROBOT_TYPE_CMD, "type", clientConfig.getRobotType()));
                sendMessage(JsonFormatUtil.SplicingSimpleJson(CmdConstants.ROBOT_SDK_AGENT_ENABLE,
                        "asrEnable", clientConfig.isEnableAsr(),
                        "slamEnable", clientConfig.isEnableSlam(),
                        "faceEnable", clientConfig.isEnableFace()));
                break;
            case ClientConstant.EVENT_CONNECT_FAILD:
                connectStatus(ClientConstant.EVENT_CONNECT_FAILD);
                Log.e("CSJBOT", "EVENT_CONNECT_FAILD" + String.valueOf(event.data));
                break;
            case ClientConstant.EVENT_CONNECT_TIME_OUT:
                connectStatus(ClientConstant.EVENT_CONNECT_TIME_OUT);
                Log.e("CSJBOT", "EVENT_CONNECT_TIME_OUT  " + String.valueOf(event.data));
                break;
            case ClientConstant.SEND_FAILED:
                sendFailed();
                Log.e("CSJBOT", "SEND_FAILED");
                break;
            case ClientConstant.EVENT_DISCONNET:
                connectStatus(ClientConstant.EVENT_DISCONNET);
                Log.e("CSJBOT", "EVENT_DISCONNET");
                break;
            case ClientConstant.EVENT_PACKET:
                MessagePacket packet = (MessagePacket) event.data;
                // 数据去重
                String tempData = new String(packet.getContent());
                long tempTimestamp = System.currentTimeMillis();
                if (tempData.equals(data) && (tempTimestamp - timestamp) < 100) {
                    // 如果100毫秒内接受两条相同数据,则丢弃第二条
                    return;
                }
                data = tempData;
                timestamp = tempTimestamp;

                String msg = new String(packet.getContent());
                handleMessage(msg);

                if (directListener != null) {
                    directListener.onRecMessage(msg);
                }
                break;
            default:
                break;
        }
    }


    public void sendMessage(String json) {
        MessagePacket packet = new CommonPacket(json.getBytes());
        mAgent.sendMessage(packet);
    }

    @Override
    protected void connectStatus(int type) {
        CsjRobot.getInstance().pushConnect(type);
        Log.d("CSJBOT", "class:HandlerMsgService method:connectStatus type:" + type);
    }

    @Override
    protected void sendFailed() {
        Log.d("CSJBOT", "class:HandlerMsgService method:sendFailed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgent != null && mAgent.isConnected()) {
            mAgent.disConnect();
        }
    }
}
