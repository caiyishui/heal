package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;


import io.reactivex.Observer;
import okhttp3.ResponseBody;

/**
 * Created by jingwc on 2018/3/29.
 */

public interface IApi {
    void getAnswerV3(String sn, String body, Observer<ResponseBody> observer);

    void getAiuiKey(String sn, String mac, Observer<ResponseBody> observer);
}
