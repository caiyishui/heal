package com.csjbot.coshandler.global;

import com.csjbot.cosclient.constant.ClientConstant;

/**
 * 底层连接的ip地址与端口
 * Created by jingwc on 2017/8/12.
 */

public class ConnectConstants {
    /**
     * ip地址
     */
//    public static final String serverIp = "192.168.8.216";
//    public static final String serverIp = "192.168.1.192";
    public static final String serverIp = "192.168.99.101";
    /**
     * 端口
     */
    public static final int serverPort = 60002;

    public static final int serverCaeraPort = 60003;

    /**
     * 连接状态
     */
    public static final class ConnectStatus{
        /* 连接成功 */
        public static final int SUCCESS = ClientConstant.EVENT_CONNECT_SUCCESS;
        /* 连接失败 */
        public static final int FAILD = ClientConstant.EVENT_CONNECT_FAILD;
        /* 连接超时 */
        public static final int TIMEOUT = ClientConstant.EVENT_CONNECT_TIME_OUT;
        /* 断开连接 */
        public static final int DISCONNECT = ClientConstant.EVENT_DISCONNET;
    }
}
