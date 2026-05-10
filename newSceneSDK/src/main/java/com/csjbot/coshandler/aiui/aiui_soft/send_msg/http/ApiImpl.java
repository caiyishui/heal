package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;

import com.csjbot.coshandler.log.Csjlogger;
import com.csjbot.coshandler.util.ConfInfoUtil;

import io.reactivex.Observer;
import okhttp3.ResponseBody;

/**
 * Created by jingwc on 2018/3/29.
 */

public class ApiImpl extends BaseImpl implements IApi {
    String sn = ConfInfoUtil.getSN();
    @Override
    public ApiService getRetrofit() {
        return getRetrofit(ApiService.class);
    }

    @Override
    public void getAnswerV3(String sn, String body, Observer<ResponseBody> observer) {
        try {
            scheduler(getRetrofit().getAnswerV3(sn, getBody(body))).subscribe(observer);
        } catch (Exception e) {
            Csjlogger.error(e.toString());
        }
    }

    @Override
    public void getAiuiKey(String sn, String mac, Observer<ResponseBody> observer) {
        scheduler(getRetrofit().getAiuiKey(sn, mac)).subscribe(observer);
    }
}
