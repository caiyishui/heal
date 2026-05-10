package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;

/**
 * Created by jingwc on 2017/9/18.
 */

public class ServerFactory {

    public static <T extends IApi> T createApi() {
        return (T) new ApiImpl();
    }
}
