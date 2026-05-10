package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;


import com.csjbot.coshandler.log.Csjlogger;

/**
 * Created by jingwc on 2017/9/18.
 */

public class BaseAgentApiUrl {

    private static String DEFAULT_ADRESS = "https://bdpro.csjbot.com:8443/";

    public static void setDefaultAdress(String addr) {
        DEFAULT_ADRESS = addr;
        Csjlogger.error("setDefaultAdress = " + DEFAULT_ADRESS);
    }

    public static String getDefaultAdress() {
        return DEFAULT_ADRESS;
    }



}